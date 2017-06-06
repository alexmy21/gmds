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
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.matrixlab.gmds.driver.core.Commands;

/**
 *
 * @author alexmy
 */
public class Branches {

    public static void main(String[] args) throws IOException, GitAPIException {
        Repository repo = Commands.getRepo(Consts.META_REPO_PATH);

        Git git = new Git(repo);

        // Create a new branch
        git.branchCreate().setName("newBranch").call();
// Checkout the new branch
        git.checkout().setName("newBranch").call();
        // List the existing branches
        List<Ref> listRefsBranches = git.branchList().setListMode(ListMode.ALL).call();
        listRefsBranches.forEach((refBranch) -> {
            System.out.println("Branch : " + refBranch.getName());
        });
// Go back on "master" branch and remove the created one
        git.checkout().setName("master");
        git.branchDelete().setBranchNames("newBranch");
    }
}
