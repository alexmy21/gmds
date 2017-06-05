/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.gdms.dbmd;

import org.matrixlab.gmds.dbmd.api.Consts;
import org.matrixlab.gmds.dbmd.util.Utils;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DataMain {

    private static final String database = "jdbc:derby://localhost:1527/sample";
    private static final String URL = database + ";create=true;user=app;password=app";

    public static void main(String[] args) throws IOException, GitAPIException {

        Connection conn = null;
        Repository datarepo = Commands.getRepo(Consts.DATA_REPO_PATH + ".git");                             // (1)

        ObjectId lastCommitId = datarepo.resolve(Constants.HEAD);                                           // (2)
        System.out.println("Last Commit: " + lastCommitId);

        try {
            conn = DriverManager.getConnection(URL);

            TreeFormatter tree = new TreeFormatter();                                                       // (3)

            if (conn != null) {
                ObjectJson dbmdJson = new ObjectJson();
                ObjectId treeId = Utils.dataTreeCommit(datarepo, tree, conn, true);                         // (4)
                ObjectId lastTreeId = Commands.getLastCommitTreeId(datarepo, lastCommitId);                 // (5)
                ObjectId commitId = Commands.processCommit(lastCommitId, treeId, datarepo, lastTreeId);     // (6)
                if (commitId != null) {
                    List<DiffEntry> list = Diffs.listDiffs(datarepo, lastTreeId, treeId);                   // (7)
                    if (list != null) {
                        // Simply display the diff between the two commits
                        list.forEach((diff) -> {
                            // TODO
                            // Perform indexing of added to commit objects                                  // (8)
                            
                            // Print trace results
                            System.out.println(diff);
                        });
                        
                        // Index the whole commit tree                                                      // (9)
                        
                        System.out.print(dbmdJson.getObjectAsMap());
                    }
                }
            } else {
                System.out.println("Metadata not supported");
            }
        } catch (SQLException ex1) {
            System.err.println(ex1);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DataMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
