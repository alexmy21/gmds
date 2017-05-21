/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.dbmdservice;

import java.io.IOException;
import java.util.Arrays;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.matrixlab.dbmdservice.api.Consts;
import org.matrixlab.gmdsdriver.core.Commands;

/**
 *
 * @author alexmylnikov
 */
public class CommitList {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = Commands.getRepo(Consts.META_REPO_PATH + ".git")) {
            try (Git git = new Git(repository)) {
                Iterable<RevCommit> commits = git.log().all().call();
                int count = 0;
                for (RevCommit commit : commits) {
                    System.out.println("LogCommit: " + commit);

                    RevTree tree = commit.getTree();
                    traverseTree(repository, tree);
                    count++;

                    break;
                }
                System.out.println(count);
            }
        }
    }

    public static void traverseTree(final Repository repository, RevTree tree) throws IOException {
        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(tree);
            // not walk the tree recursively so we only get the elements in the top-level directory
            treeWalk.setRecursive(false);
            while (treeWalk.next()) {
                System.out.println("===================================================");
                System.out.println("\"TREE_NODE\": " + treeWalk.getPathString() + "\n\t"
//                        + treeWalk.getObjectReader(). + "\n\t"
////                        + treeWalk.getFileMode().toString()
                );

                if (treeWalk.isSubtree()) {
                    System.out.println("\"OBJECT_NODE\": " + treeWalk.getPathString() + "\n\t");
//                    System.out.println("dir: " + treeWalk.getPathString());
                    treeWalk.enterSubtree();
                    System.out.println(treeWalk.getNameString() + ": " + treeWalk.getPathString());
//                    FileMode fileMode = treeWalk.getFileMode(0);
                    
                } else {
                    FileMode fileMode = treeWalk.getFileMode(0);
                    ObjectLoader loader = repository.open(treeWalk.getObjectId(0));
                    System.out.println(
                            treeWalk.getNameString() + ": " + treeWalk.getPathString() + "\n\t"
                            + "file_mode: " + getFileMode(fileMode)  + "\n\t"
                            + ", type: " + fileMode.getObjectType()  + "\n\t"
                            + ", mode: " + fileMode  + "\n\t"
                            + ", json:" + new String(loader.getBytes(), "utf-8")  + "\n\t"
                            + " size: " + loader.getSize());
//                    System.out.println("file: " + treeWalk.getPathString());
                }
                
//                break;

            }
        }
    }

    private static void printDirectory(Repository repository, RevTree tree) throws IOException {
        // look at directory, this has FileMode.TREE
        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(tree);
            treeWalk.setRecursive(false);
//            treeWalk.setFilter(PathFilter.create("src"));
            if (!treeWalk.next()) {
                throw new IllegalStateException("Did not find expected file 'README.md'");
            }

            // FileMode now indicates that this is a directory, i.e. FileMode.TREE.equals(fileMode) holds true
            FileMode fileMode = treeWalk.getFileMode(0);
            System.out.println("src: " + getFileMode(fileMode) + ", type: " + fileMode.getObjectType() + ", mode: " + fileMode);
        }
    }

    private static String getFileMode(FileMode fileMode) {
        if (fileMode.equals(FileMode.EXECUTABLE_FILE)) {
            return "Executable File";
        } else if (fileMode.equals(FileMode.REGULAR_FILE)) {
            return "Normal File";
        } else if (fileMode.equals(FileMode.TREE)) {
            return "Directory";
        } else if (fileMode.equals(FileMode.SYMLINK)) {
            return "Symlink";
        } else {
            // there are a few others, see FileMode javadoc for details
            throw new IllegalArgumentException("Unknown type of file encountered: " + fileMode);
        }
    }
}
