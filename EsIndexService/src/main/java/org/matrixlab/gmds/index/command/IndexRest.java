/*
 * Copyright 2017 Alex Mylnikov

 * This program is a free software: you can redistribute it and/or modify
 * it under the terms of the Apache License, Version 2.0 (the "License");
 * 
 * You may obtain a copy of the Apache 2 License at

 *      http://www.apache.org/licenses/LICENSE-2.0

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache 2 License for more details.
 *
 */
package org.matrixlab.gmds.index.command;

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
import org.matrixlab.gmds.core.Command;
import org.matrixlab.gmds.driver.core.Commands;
import org.matrixlab.gmds.index.IndexProcessor;
import org.matrixlab.gmds.index.dto.ParamsIndex;
import org.matrixlab.gmds.index.util.Utils;
import org.matrixlab.gmds.jsonbuilder.impl.ObjectJson;

/**
 *
 * @author alexmylnikov
 */
public class IndexRest implements Command {

    private String host = "localhost";
    private int port    = 9200;
    private final String repoPath;

    private static final String OBJECT_ID       = "OBJECT_ID";
    private static final String OBJECT_JSON     = "OBJECT_JSON";
    private static final String FILE_MODE_NAME  = "FILE_MODE_NAME";
    private static final String FILE_TYPE       = "FILE_TYPE";
    private static final String OBJECT_SIZE     = "OBJECT_SIZE";

    public IndexRest(IndexProcessor processor) {

        ParamsIndex paramsIndex = new ParamsIndex().fromJsonString(processor.getInput());
        this.host = paramsIndex.get(ParamsIndex.ES_HOST);
        this.port = Integer.valueOf(paramsIndex.get(ParamsIndex.ES_PORT));
        this.repoPath = paramsIndex.get(ParamsIndex.REPO_PATH);
    }

    @Override
    public void run() {
        try (Repository repository = Commands.getRepo(this.repoPath)) {
            indexCommitTree(repository);
        } catch (IOException ex) {
            Logger.getLogger(IndexRest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean check() {
        return true;
    }

    //==========================================================================
    // Private supporting methods
    //==========================================================================
    
    /**
     * 
     * @param repository 
     */
    private void indexCommitTree(final Repository repository) {

        RestClient client = null;

        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(getRevTree(repository));

            treeWalk.setRecursive(true);
            Utils utils = new Utils();
            CredentialsProvider crp = utils.setCredentials("elastic", "changeme");
            client = utils.createRestClient(this.host, this.port, crp);

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
                }

                if (map != null) {
                    String objId = (String) map.get(OBJECT_ID);
//                    String jsonStr = new Gson().toJson(map.get(Consts.OBJECT_JSON), String.class);
                    String jsonStr = (String) map.get(OBJECT_JSON);

                    System.out.println("ID: " + objId);

                    utils.index(client, "mds", "local_derby", objId, jsonStr);
                }
            }
        } catch (IncorrectObjectTypeException ex) {
            Logger.getLogger(IndexRest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CorruptObjectException ex) {
            Logger.getLogger(IndexRest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IndexRest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException ex) {
                    Logger.getLogger(IndexRest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * 
     * @return
     * @throws IOException 
     */
    private RevTree getRevTree() throws IOException {
        return getRevTree(Commands.getRepo(this.repoPath));
    }

    /**
     *
     * @param repository
     * @return
     */
    private RevTree getRevTree(final Repository repository) {
        
        try (Git git = new Git(repository)) {
            Iterable<RevCommit> commits = git.log().all().call();
            RevCommit array[] = Iterables.toArray(commits, RevCommit.class);
            return array[0].getTree();
        } catch (GitAPIException | IOException ex) {
            Logger.getLogger(IndexRest.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     *
     * @param fileMode
     * @return
     */
    private String getFileMode(FileMode fileMode) {
        
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
     *
     * @param tree
     * @param fileMode
     * @param loader
     * @return
     * @throws LargeObjectException
     */
    private ObjectJson buildObject(TreeWalk tree, FileMode fileMode, ObjectLoader loader) throws LargeObjectException {
        
        ObjectJson objectJson = new ObjectJson();
        objectJson.addDoc(FILE_MODE_NAME, getFileMode(fileMode));
        objectJson.addDoc(FILE_MODE_NAME, fileMode);
        objectJson.addDoc(FILE_TYPE, fileMode.getObjectType());
        objectJson.addDoc(OBJECT_ID, tree.getObjectId(0).getName());
        objectJson.addDoc(OBJECT_SIZE, loader.getSize());
        objectJson.addDoc(OBJECT_JSON, new String(loader.getBytes()));
        
        return objectJson;
    }
}
