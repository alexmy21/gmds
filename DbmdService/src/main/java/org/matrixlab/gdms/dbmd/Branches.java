/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.gdms.dbmd;

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
