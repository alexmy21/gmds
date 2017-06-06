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
package org.matrixlab.gmds.dbmd.impl;

import org.matrixlab.gmds.dbmd.api.Consts;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.matrixlab.gmds.dbmd.api.Diffs;
import org.matrixlab.gmds.driver.core.Commands;

/**
 *
 * @author alexmy
 */
public class CommitDiffs implements Diffs {

    public static void main(String[] args) throws IOException, GitAPIException {

        Repository repo = Commands.getRepo(Consts.META_REPO_PATH);

        // Get the id of the tree associated to the two commits
        ObjectId head = repo.resolve("HEAD^{tree}");
        ObjectId previousHead = repo.resolve("HEAD~^{tree}");

        List<DiffEntry> list = new CommitDiffs().listDiffs(repo, previousHead, head);
        if (list != null) {
            // Simply display the diff between the two commits
            list.forEach((diff) -> {
                System.out.println(diff);
            });
        }
    }

    @Override
    public List<DiffEntry> listDiffs(Repository repo, ObjectId previousHead, ObjectId currentHead) {

        if (previousHead != null && currentHead != null) {
            try {
                Git git = new Git(repo);
                // Instanciate a reader to read the data from the Git database
                ObjectReader reader = repo.newObjectReader();

                // Create the tree iterator for each commit
                CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
                oldTreeIter.reset(reader, previousHead);
                CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
                newTreeIter.reset(reader, currentHead);
                List<DiffEntry> listDiffs = git.diff().setOldTree(oldTreeIter).setNewTree(newTreeIter).call();

                return listDiffs;
            } catch (IOException | GitAPIException ex) {
                Logger.getLogger(CommitDiffs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    @Override
    public List<DiffEntry> listDiffs(Repository repo) {
        try {
            // Get the id of the tree associated to the two commits
            ObjectId currentHead = repo.resolve("HEAD^{tree}");
            ObjectId previousHead = repo.resolve("HEAD~^{tree}");
            
            if (previousHead != null && currentHead != null) {

                Git git = new Git(repo);
                // Instanciate a reader to read the data from the Git database
                ObjectReader reader = repo.newObjectReader();

                // Create the tree iterator for each commit
                CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
                oldTreeIter.reset(reader, previousHead);
                CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
                newTreeIter.reset(reader, currentHead);
                List<DiffEntry> listDiffs = git.diff().setOldTree(oldTreeIter).setNewTree(newTreeIter).call();

                return listDiffs;
            }
        } catch (IncorrectObjectTypeException ex) {
            Logger.getLogger(CommitDiffs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RevisionSyntaxException | IOException | GitAPIException ex) {
            Logger.getLogger(CommitDiffs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
