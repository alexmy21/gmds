/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.indexservice;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.CredentialsProvider;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.elasticsearch.client.RestClient;
//import org.matrixlab.dbmdservice.api.Consts;
import org.matrixlab.gmdsdriver.core.Commands;
import org.matrixlab.jsonbuilder.impl.ObjectJson;

/**
 *
 * @author alexmylnikov
 */
public class DbIndex {
    
    private String repo;
    private String esurl = "localhost";
    private int esport = 9200;
    
    private static final String OBJECT_ID = "OBJECT_ID";
    private static final String OBJECT_JSON = "OBJECT_JSON";
    private static final String FILE_MODE_NAME = "FILE_MODE_NAME";
    private static final String FILE_TYPE = "FILE_TYPE";
    private static final String OBJECT_SIZE = "OBJECT_SIZE";

    public DbIndex(){}
    
    public DbIndex(String repo, String esurl, int esport) {
        this.repo = repo;
        this.esurl = esurl;
        this.esport = esport;
    }

    public static void main(String[] args) {
        DbIndex commitIndex = new DbIndex();
        try (Repository repository = Commands.getRepo(commitIndex.getRepo())) {
            commitIndex.indexCommitTree(repository);
        } catch (IOException ex) {
            Logger.getLogger(DbIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static RevTree getRevTree(final Repository repository) {
        try (Git git = new Git(repository)) {
            Iterable<RevCommit> commits = git.log().all().call();
            RevCommit array[] = Iterables.toArray(commits, RevCommit.class);
            return array[0].getTree();
        } catch (GitAPIException | IOException ex) {
            Logger.getLogger(DbIndex.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void indexCommitTree(final Repository repository) {
        
        RestClient client = null;
        
        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(getRevTree(repository));

            treeWalk.setRecursive(true);
            EsRestIndexer esIndexer = new EsRestIndexer();
            CredentialsProvider crp = esIndexer.setCredentials("elastic", "changeme");
            client = esIndexer.createRestClient(getEsurl(), getEsport(), crp);
            
            Map<String, Object> map = null;
            while (treeWalk.next()) {
                System.out.println("*****************************************************");
                if (treeWalk.isSubtree()) {
                    System.out.println("\"OBJECT_NODE\": " + treeWalk.getPathString() + "\n\t");
                    treeWalk.enterSubtree();
//                    System.out.println(treeWalk.getNameString() + ": " + treeWalk.getPathString());
                } else {
                    FileMode fileMode = treeWalk.getFileMode(0);
                    ObjectLoader loader = repository.open(treeWalk.getObjectId(0));

                    ObjectJson objectJson = buildObject(treeWalk, fileMode, loader);
                    map = objectJson.getObjectAsMap();

//                    System.out.println(map);
                }

                if (map != null) {
                    String objId = (String) map.get(OBJECT_ID);
//                    String jsonStr = new Gson().toJson(map.get(Consts.OBJECT_JSON), String.class);
                    String jsonStr = (String) map.get(OBJECT_JSON);
                    
                    System.out.println("ID: " + objId);
                    
                    esIndexer.index(client, "mds", "local_derby", objId, jsonStr);
                }
            }
        } catch (IncorrectObjectTypeException ex) {
            Logger.getLogger(DbIndex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CorruptObjectException ex) {
            Logger.getLogger(DbIndex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbIndex.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (client != null){
                try {
                    client.close();
                } catch (IOException ex) {
                    Logger.getLogger(DbIndex.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static ObjectJson buildObject(TreeWalk tree, FileMode fileMode, ObjectLoader loader) throws LargeObjectException {
        ObjectJson objectJson = new ObjectJson();
        objectJson.addDoc(FILE_MODE_NAME, getFileMode(fileMode));
        objectJson.addDoc(FILE_MODE_NAME, fileMode);
        objectJson.addDoc(FILE_TYPE, fileMode.getObjectType());
        objectJson.addDoc(OBJECT_ID, tree.getObjectId(0).getName());
        objectJson.addDoc(OBJECT_SIZE, loader.getSize());
        objectJson.addDoc(OBJECT_JSON, new String(loader.getBytes()));
        return objectJson;
    }

    private static String getFileMode(FileMode fileMode) {
        if (fileMode.equals(FileMode.EXECUTABLE_FILE)) {
            return "\"Executable File\"";
        } else if (fileMode.equals(FileMode.REGULAR_FILE)) {
            return "\"Normal File\"";
        } else if (fileMode.equals(FileMode.TREE)) {
            return "\"Directory\"";
        } else if (fileMode.equals(FileMode.SYMLINK)) {
            return "\"Symlink\"";
        } else {
            // there are a few others, see FileMode javadoc for details
            throw new IllegalArgumentException("Unknown type of file encountered: " + fileMode);
        }
    }

    /**
     * @return the repo
     */
    public String getRepo() {
        return repo;
    }

    /**
     * @param repo the repo to set
     */
    public void setRepo(String repo) {
        this.repo = repo;
    }

    /**
     * @return the esurl
     */
    public String getEsurl() {
        return esurl;
    }

    /**
     * @param esurl the esurl to set
     */
    public void setEsurl(String esurl) {
        this.esurl = esurl;
    }

    /**
     * @return the esport
     */
    public int getEsport() {
        return esport;
    }

    /**
     * @param esport the esport to set
     */
    public void setEsport(int esport) {
        this.esport = esport;
    }
}
