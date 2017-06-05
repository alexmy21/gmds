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
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.matrixlab.gmds.driver.core.Commands;

/**
 *
 * @author alexmy
 */
public class Diffs {

    public static void main(String[] args) throws IOException, GitAPIException {

        Repository repo = Commands.getRepo(Consts.META_REPO_PATH);

        // Get the id of the tree associated to the two commits
        ObjectId head = repo.resolve("HEAD^{tree}");
        ObjectId previousHead = repo.resolve("HEAD~^{tree}");

        List<DiffEntry> list = listDiffs(repo, previousHead, head);
        if(list != null){            
            // Simply display the diff between the two commits
            list.forEach((diff) -> {
                System.out.println(diff);
            });
        }
    }

    public static List<DiffEntry> listDiffs(Repository repo, ObjectId previousHead, ObjectId currentHead) throws GitAPIException, IOException {

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
        } else {
            return null;
        }
    }

}
