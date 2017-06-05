/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.gdms.dbmd;

import org.matrixlab.gMDS.index.DbIndex;
import org.matrixlab.gmds.dbmd.api.Consts;
import org.matrixlab.gmds.dbmd.util.Utils;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TreeFormatter;
import org.matrixlab.gmds.driver.core.Commands;
import org.matrixlab.gmds.jsonbuilder.impl.ObjectJson;

/**
 *
 * @author alexmy
 */
public class MetaMain {

    private static final String database = "jdbc:derby://localhost:1527/sample";
    private static final String URL = database + ";create=true;user=app;password=app";
    private static final String esUrl = "localhost";
    private static final int esPort = 9200;

    public static void main(String[] args) throws IOException, GitAPIException {

        Connection conn = null;
        Repository repo = Commands.getRepo(Consts.META_REPO_PATH + ".git");                             // (1)

        ObjectId lastCommitId = repo.resolve(Constants.HEAD);                                           // (2)
        System.out.println("Last Commit: " + lastCommitId);

        try {
            conn = DriverManager.getConnection(URL);

            TreeFormatter tree = new TreeFormatter();                                                   // (3)

            if (conn != null) {
                ObjectJson dbmdJson = new ObjectJson();
                ObjectId treeId = Utils.metaTreeCommit(dbmdJson, repo, tree, conn, false);              // (4)
                ObjectId lastTreeId = Commands.getLastCommitTreeId(repo, lastCommitId);                 // (5)
                ObjectId commitId = Commands.processCommit(lastCommitId, treeId, repo, lastTreeId);     // (6)

                if (commitId != null) {
                    List<DiffEntry> list = Diffs.listDiffs(repo, lastTreeId, treeId);                   // (7)
                    if (list != null) {
                        // Simply display the diff between the two commits
                        list.forEach((diff) -> {
                            // TODO
                            // Perform indexing of added to commit objects
                        });
                    }
                    // Index the whole commit tree                                                      // (9)
                    DbIndex dbIndex = new DbIndex(Consts.META_REPO_PATH + ".git", esUrl, esPort);
                    dbIndex.indexCommitTree(repo);
                }
            } else {
                System.out.println("Metadata not supported");
            }
        } catch (SQLException ex1) {
            System.err.println(ex1);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex2) {
                System.out.println(ex2.getMessage());
            }
        }
    }
}
