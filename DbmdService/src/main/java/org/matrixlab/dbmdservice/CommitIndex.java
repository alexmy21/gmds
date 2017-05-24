/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.dbmdservice;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
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
import org.matrixlab.dbmdservice.api.Consts;
import org.matrixlab.gmdsdriver.core.Commands;
import org.matrixlab.indexservice.EsRestIndexer;
import org.matrixlab.jsonbuilder.impl.ObjectJson;

/**
 *
 * @author alexmylnikov
 */
public class CommitIndex {
    
    private static String esurl = "localhost";
    private static int esport = 9300;

    public CommitIndex(String esurl, int esport) {
        CommitIndex.esurl = esurl;
        CommitIndex.esport = esport;
    }

    public static void main(String[] args) {
        CommitIndex commitIndex = new CommitIndex(esurl, esport);
        try (Repository repository = Commands.getRepo(Consts.META_REPO_PATH + ".git")) {
            commitIndex.indexCommitTree(repository);
        } catch (IOException ex) {
            Logger.getLogger(CommitIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static RevTree getRevTree(final Repository repository) {
        try (Git git = new Git(repository)) {
            Iterable<RevCommit> commits = git.log().all().call();
            RevCommit array[] = Iterables.toArray(commits, RevCommit.class);
            return array[0].getTree();
        } catch (GitAPIException | IOException ex) {
            Logger.getLogger(CommitIndex.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public synchronized void indexCommitTree(final Repository repository) {
        
        RestClient client = null;
        
        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(getRevTree(repository));

            treeWalk.setRecursive(true);
            EsRestIndexer esIndexer = new EsRestIndexer();
            CredentialsProvider crp = esIndexer.setCredentials("elastic", "changeme");
            client = esIndexer.createRestClient(esurl, esport, crp);
            
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
                    String objId = (String) map.get(Consts.OBJECT_ID);
//                    String jsonStr = new Gson().toJson(map.get(Consts.OBJECT_JSON), String.class);
                    String jsonStr = (String) map.get(Consts.OBJECT_JSON);
                    
                    System.out.println("ID: " + objId);
                    
                    esIndexer.index(client, "mds", "local_derby", objId, jsonStr);
                }
            }
        } catch (IncorrectObjectTypeException ex) {
            Logger.getLogger(CommitIndex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CorruptObjectException ex) {
            Logger.getLogger(CommitIndex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CommitIndex.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (client != null){
                try {
                    client.close();
                } catch (IOException ex) {
                    Logger.getLogger(CommitIndex.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static ObjectJson buildObject(TreeWalk tree, FileMode fileMode, ObjectLoader loader) throws LargeObjectException {
        ObjectJson objectJson = new ObjectJson();
        objectJson.addDoc(Consts.FILE_MODE_NAME, getFileMode(fileMode));
        objectJson.addDoc(Consts.FILE_MODE_CODE, fileMode);
        objectJson.addDoc(Consts.FILE_TYPE, fileMode.getObjectType());
        objectJson.addDoc(Consts.OBJECT_ID, tree.getObjectId(0).getName());
        objectJson.addDoc(Consts.OBJECT_SIZE, loader.getSize());
        objectJson.addDoc(Consts.OBJECT_JSON, new String(loader.getBytes()));
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
}
