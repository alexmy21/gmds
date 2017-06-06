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

package org.matrixlab.gmds.dbmd.playground;

import org.matrixlab.gmds.dbmd.api.Consts;
import java.io.IOException;
import java.util.Collection;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 *
 * @author alexmy
 */
public class RefList {

    public static void main(String[] args) throws IOException, GitAPIException {
        
        Repository repo = new FileRepository(Consts.META_REPO_PATH + ".git");
        
        // get a list of all known heads, tags, remotes, ...
        Collection<Ref> allRefs = repo.getAllRefs().values();

        // a RevWalk allows to walk over commits based on some filtering that is defined
        try (RevWalk revWalk = new RevWalk(repo)) {
            for (Ref ref : allRefs) {
                revWalk.markStart(revWalk.parseCommit(ref.getObjectId()));
            }
            System.out.println("Walking all commits starting with " + allRefs.size() + " refs: " + allRefs);
            int count = 0;
            for (RevCommit commit : revWalk) {
                System.out.println("Commit: " + commit);
                count++;
            }
            System.out.println("Had " + count + " commits");
        }
    }
}
