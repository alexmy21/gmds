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
package org.matrixlab.gmds.dbmd.command;

import io.vertx.core.AbstractVerticle;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TreeFormatter;
import org.matrixlab.gmds.core.Command;
import org.matrixlab.gmds.dbmd.DbmdProcessor;
import org.matrixlab.gmds.dbmd.dto.ParamsCommit;
import org.matrixlab.gmds.dbmd.impl.CommitDiffs;
import org.matrixlab.gmds.dbmd.util.Utils;
import org.matrixlab.gmds.driver.core.Commands;
import org.matrixlab.gmds.jsonbuilder.impl.ObjectJson;

/**
 *
 * @author alexmylnikov
 */
public class Commit extends AbstractVerticle implements Command {

//    private final DbmdProcessor processor;
    private final String url;
    private final String repoPath;
    
    Connection conn = null;

    public Commit(DbmdProcessor processor) {
//        this.processor = processor;
        ParamsCommit paramsCommit = new ParamsCommit().fromJsonString(processor.getInput());
        this.url        = paramsCommit.get(ParamsCommit.URL);
        this.repoPath   = paramsCommit.get(ParamsCommit.REPO_PATH);
    }

    @Override
    public void run() {

        try {
            Repository repo = Commands.getRepo(repoPath);                                               // (1)
            ObjectId lastCommitId = repo.resolve(Constants.HEAD);                                       // (2)
            System.out.println("Last Commit: " + lastCommitId);
            conn = DriverManager.getConnection(url);
            TreeFormatter tree = new TreeFormatter();                                                   // (3)

            if (conn != null) {
                ObjectJson dbmdJson = new ObjectJson();
                ObjectId treeId;

                treeId = Utils.metaTreeCommit(dbmdJson, repo, tree, conn, false);                       // (4)

                ObjectId lastTreeId = Commands.getLastCommitTreeId(repo, lastCommitId);                 // (5)
                ObjectId commitId   = Commands.processCommit(lastCommitId, treeId, repo, lastTreeId);     // (6)

                if (commitId != null) {
                    List<DiffEntry> list = new CommitDiffs().listDiffs(repo, lastTreeId, treeId);       // (7)
                    if (list != null) {
                        // Simply display the diff between the two commits
                        list.forEach((diff) -> {
                            // TODO
                            // Perform indexing of added to commit objects
                        });
                    }
                }
            } else {
                System.out.println("Metadata not supported");
            }
        } catch (SQLException ex1) {
            System.err.println(ex1);
        } catch (LargeObjectException | IOException ex) {
            Logger.getLogger(Commit.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConn(conn);
        }
    }

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public void stop() {
        closeConn(conn);
    }
    
    private void closeConn(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("======> Connection closed!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
