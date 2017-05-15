/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.gmdsdriver.core;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 *
 * @author alexmy
 */
public class Commands {

    // Commit processing support
    //==================================================================================================================
    /**
     * 
     * @param lastCommitId
     * @param treeId
     * @param repo
     * @param lastTreeId
     * @return
     * @throws IOException 
     */
    public static ObjectId processCommit(ObjectId lastCommitId, ObjectId treeId, Repository repo, ObjectId lastTreeId) throws IOException {

        CommitBuilder commitBuilder = new CommitBuilder();

        ObjectId commitId = null;

        if (lastCommitId == null) {
            commitId = Commands.commit(commitBuilder, treeId, repo);
            System.out.println("Initial Commit: " + commitId);
        } else {
            if (lastTreeId.equals(treeId)) {
                // Do nothing, because there is no changes in the tree
                System.out.println("Did nothing, because there is no changes in the commited tree!\n");
            } else {
                commitBuilder.setParentId(lastCommitId);
                commitId = Commands.commit(commitBuilder, treeId, repo);
                System.out.println("Current Commit: " + commitId);
            }
        }

        return commitId;
    }

    /**
     * 
     * @param commitBuilder
     * @param treeId
     * @param repo
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    public static ObjectId commit(CommitBuilder commitBuilder, ObjectId treeId, Repository repo) throws UnsupportedEncodingException, IOException {
        commitBuilder.setTreeId(treeId);
        commitBuilder.setMessage(System.currentTimeMillis() + ": My commit!\n");
        PersonIdent person = new PersonIdent("Alex", "alex.mylnikov@rokittech.com");
        commitBuilder.setAuthor(person);
        commitBuilder.setCommitter(person);

        commitBuilder.build();

        ObjectInserter commitInserter = repo.newObjectInserter();
        ObjectId commitId = commitInserter.insert(commitBuilder);
        commitInserter.flush();

        updateMasterRecord(repo, commitId);

        System.out.println("Commit Object ID: " + commitId.getName());

        return commitId;
    }

    /**
     * 
     * @param repo
     * @param lastCommitId
     * @return
     * @throws LargeObjectException
     * @throws IOException 
     */
    public static ObjectId getLastCommitTreeId(Repository repo, ObjectId lastCommitId) throws LargeObjectException, IOException {

        if (lastCommitId == null) {
            return null;
        } else {
            ObjectReader objectReader = repo.newObjectReader();
            ObjectLoader objectLoader = objectReader.open(lastCommitId);
            RevCommit oldCommit = RevCommit.parse(objectLoader.getBytes());

            return oldCommit.getTree().getId();
        }
    }

    /**
     *
     * @param repo
     * @param objectId
     * @throws IOException
     */
    public static void updateMasterRecord(Repository repo, ObjectId objectId) throws IOException {

        RefUpdate refUpdate = repo.updateRef(Constants.HEAD);
        refUpdate.setNewObjectId(objectId);
        final RefUpdate.Result result = refUpdate.forceUpdate();

        switch (result) {
            case NEW:
                System.out.println("New commit!\n");
                break;
            case FORCED:
                System.out.println("Forced change commit!\n");
                break;
            default: {
                System.out.println(result.name());
            }
        }
    }

    /**
     *
     * @param repo
     * @param blobId
     * @return
     * @throws LargeObjectException
     * @throws IOException
     */
    public static byte[] getObjectFromRepo(Repository repo, ObjectId blobId) throws LargeObjectException, IOException {
        ObjectReader objectReader = repo.newObjectReader();
        ObjectLoader objectLoader = objectReader.open(blobId);
//        int type = objectLoader.getType(); // Constants.OBJ_BLOB
        byte[] bytes = objectLoader.getBytes();

        return bytes;
    }

    /**
     *
     * @param repoPath
     * @return
     * @throws IOException
     */
    public static Repository getRepo(String repoPath) throws IOException {
        Repository repo;
        File repoFile = new File(repoPath);
        if (repoFile.exists()) {
            // Open an existing repository
            repo = new FileRepositoryBuilder()
                    .setGitDir(new File(repoPath))
                    .build();
        } else {
            // Create a new repository
            repo = FileRepositoryBuilder.create(
                    new File(repoPath));
            repo.create(true);
        }
        System.out.println(repo.getDirectory());
        return repo;
    }

}
