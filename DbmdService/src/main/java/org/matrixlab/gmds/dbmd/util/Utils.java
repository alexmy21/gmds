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

package org.matrixlab.gmds.dbmd.util;

import org.matrixlab.gmds.dbmd.api.RepoVocab;
import org.matrixlab.gmds.jsonbuilder.impl.ObjectJson;
import org.matrixlab.gmds.jsonbuilder.impl.Map2JsonString;
import org.matrixlab.gmds.dbmd.api.Consts;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TreeFormatter;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 *
 * @author alexmy
 */
public class Utils {

    /**
     *
     * @param dbmdJson
     * @param repo
     * @param tree
     * @param conn
     * @param clmnTree
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public static ObjectId metaTreeCommit(ObjectJson dbmdJson, Repository repo, TreeFormatter tree, 
            Connection conn, Boolean clmnTree) throws IOException, SQLException {

        DatabaseMetaData dbmd = conn.getMetaData();
//        ObjectJson dbmdJson = new ObjectJson();
        String mapString = metaDbInfo(dbmd);
        ObjectInserter objectInserter = repo.newObjectInserter();
        ObjectId blobId = objectInserter.insert(Constants.OBJ_BLOB, mapString.getBytes());
        objectInserter.flush();

        tree.append(Consts.DATABASE, FileMode.REGULAR_FILE, blobId);
        Utils.putTableMeta(repo, conn, dbmd, objectInserter, tree, clmnTree);

        ObjectId treeId = objectInserter.insert(tree);
        objectInserter.flush();

        System.out.println("Tree ID: " + treeId.getName());

        return treeId;
    }

    /**
     *
     * @param datarepo
     * @param tree
     * @param conn
     * @param clmnTree
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static ObjectId dataTreeCommit(Repository datarepo, TreeFormatter tree, 
            Connection conn, Boolean clmnTree) throws SQLException, IOException {
        
        DatabaseMetaData dbmd = conn.getMetaData();
//        ObjectJson dbmdJson = new ObjectJson();
        String mapString = metaDbInfo(dbmd);
        
        // Build Db_Info object, general info about Database
        ObjectInserter objectInserter = datarepo.newObjectInserter();
        ObjectId blobId = objectInserter.insert(Constants.OBJ_BLOB, mapString.getBytes());
        objectInserter.flush();

        tree.append("DATABASE", FileMode.REGULAR_FILE, blobId);
        
        // Continue building Database Tree
        Utils.putTableMeta(datarepo, conn, dbmd, objectInserter, tree, clmnTree);

        ObjectId treeId = objectInserter.insert(tree);
        objectInserter.flush();

        System.out.println("Tree ID: " + treeId.getName());

        return treeId;
    }

    /**
     *
     * @param dbmd
     * @return
     * @throws SQLException
     */
    public static String metaDbInfo(DatabaseMetaData dbmd) throws SQLException {
        
        ObjectJson builder = new ObjectJson();

        builder.addDoc(RepoVocab.URL, dbmd.getURL())
                .addDoc(RepoVocab.DatabaseProductVersion, dbmd.getDatabaseProductVersion())
                .addDoc(RepoVocab.DriverName, dbmd.getDriverName());

        String mapString = new Map2JsonString().convert(builder.getObjectAsMap());

        System.out.println("DB Info Map: " + mapString);

        return mapString;
    }

    /**
     *
     * @param repo
     * @param conn
     * @param dbmd
     * @param objectInserter
     * @param dbMetaTree
     * @param clmnTree
     * @return
     */
    public static TreeFormatter putTableMeta(Repository repo, Connection conn, DatabaseMetaData dbmd, 
            ObjectInserter objectInserter, TreeFormatter dbMetaTree, Boolean clmnTree) {

        String[] types = {"TABLE"};

        try {

            ResultSet tables = dbmd.getTables(null, null, null, types);
            RevWalk walkTbl = new RevWalk(repo);

            while (tables.next()) {
                System.out.println("***************************************");
                String tableName = tables.getString(Consts.TABLE_NAME);

                TreeFormatter tblMetaTree = new TreeFormatter();

                // Build tree for table attributs
                buildMetaTree(tables, Consts.TABLE_META_ATTRIBUTES, objectInserter, tblMetaTree);

                ResultSet columns = dbmd.getColumns(null, null, tableName, null);
                if (clmnTree) {
                    addColumnsAsTrees(columns, objectInserter, tblMetaTree, walkTbl, dbMetaTree);
                } else {
                    addColumnsAsObjects(columns, objectInserter, tblMetaTree, walkTbl, dbMetaTree);
                }

                TreeFormatter tblDataTree = new TreeFormatter();
                ObjectId tblDataTreeId = objectInserter.insert(tblDataTree);
                objectInserter.flush();

                dbMetaTree.append(Consts.TABLE_DATA, walkTbl.parseTree(tblDataTreeId));
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        return dbMetaTree;
    }

    /**
     * 
     * @param columns
     * @param objectInserter
     * @param tblMetaTree
     * @param walkTbl
     * @param dbMetaTree
     * @throws IOException
     * @throws SQLException 
     */
    public static void addColumnsAsObjects(ResultSet columns, 
            ObjectInserter objectInserter, TreeFormatter tblMetaTree, RevWalk walkTbl, 
            TreeFormatter dbMetaTree) throws IOException, SQLException {
        
        while (columns.next()) {
            putColumnMetaAsObject(columns, objectInserter, tblMetaTree, walkTbl);
        }
        
        ObjectId tblMetaTreeId = objectInserter.insert(tblMetaTree);
        objectInserter.flush();
        
        dbMetaTree.append(Consts.TABLE_META, walkTbl.parseTree(tblMetaTreeId));
    }

    /**
     * 
     * @param columns
     * @param objectInserter
     * @param tblMetaTree
     * @param walkTbl
     * @param dbMetaTree
     * @throws SQLException
     * @throws IOException 
     */
    public static void addColumnsAsTrees(ResultSet columns, 
            ObjectInserter objectInserter, TreeFormatter tblMetaTree, RevWalk walkTbl, 
            TreeFormatter dbMetaTree) throws SQLException, IOException {
        
        while (columns.next()) {
            putColumnMetaAsTree(columns, objectInserter, tblMetaTree, walkTbl);
        }
        
        ObjectId tblMetaTreeId = objectInserter.insert(tblMetaTree);
        objectInserter.flush();
        
        dbMetaTree.append(Consts.TABLE_META, walkTbl.parseTree(tblMetaTreeId));
    }

    /**
     * 
     * @param columns
     * @param objectInserter
     * @param tblTree
     * @param walkTbl
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    public static ObjectId putColumnMetaAsObject(ResultSet columns, 
            ObjectInserter objectInserter, TreeFormatter tblTree, RevWalk walkTbl) 
            throws SQLException, IOException {

        // Build object for column attributs
        ObjectId objectId = buildMetaObject(columns, Consts.COLUMN_META_ATTRIBUTES, objectInserter);

        tblTree.append(Consts.COLUMN_META, FileMode.REGULAR_FILE, (AnyObjectId) objectId);

        // Add empty tree for the column content
        TreeFormatter clmnDataTree = new TreeFormatter();
        ObjectId clmnDataTreeId = objectInserter.insert(clmnDataTree);
        objectInserter.flush();

        tblTree.append(Consts.COLUMN_DATA, walkTbl.parseTree(clmnDataTreeId));

        return objectId;
    }

    /**
     *
     * @param columns
     * @param objectInserter
     * @param tblTree
     * @param walkTbl
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static ObjectId putColumnMetaAsTree(ResultSet columns, 
            ObjectInserter objectInserter, TreeFormatter tblTree, RevWalk walkTbl) 
            throws SQLException, IOException {

        // Add tree for the column Metadata
        TreeFormatter clmnMetaTree = new TreeFormatter();

        // Build tree for column attributs
        buildMetaTree(columns, Consts.COLUMN_META_ATTRIBUTES, objectInserter, clmnMetaTree);

        ObjectId clmnMetaTreeId = objectInserter.insert(clmnMetaTree);
        objectInserter.flush();

        tblTree.append(Consts.COLUMN_META, walkTbl.parseTree(clmnMetaTreeId));

        // Add empty tree for the column content
        TreeFormatter clmnDataTree = new TreeFormatter();
        ObjectId clmnDataTreeId = objectInserter.insert(clmnDataTree);
        objectInserter.flush();

        tblTree.append(Consts.COLUMN_DATA, walkTbl.parseTree(clmnDataTreeId));

        return clmnMetaTreeId;
    }

    private static ObjectId buildMetaObject(ResultSet set, String[] attrs, ObjectInserter inserter) 
            throws IOException, SQLException {

        ObjectJson objectJson = new ObjectJson();
        
        for (String attr : attrs) {
            objectJson.addDoc(attr, set.getString(attr));
        }

        String attrJson = new Map2JsonString().convert(objectJson.getObjectAsMap());
        ObjectId objectId = inserter.insert(Constants.OBJ_BLOB, attrJson.getBytes());
        inserter.flush();
        
        System.out.println("MetaObject: " + attrJson);
        
        return objectId;
    }

    private static ObjectId buildMetaTree(ResultSet set, String[] attrs, ObjectInserter inserter, TreeFormatter tree) 
            throws IOException, SQLException {
        
        for (String attr : attrs) {
            ObjectJson objectJson = new ObjectJson();
            objectJson.addDoc(attr, set.getString(attr));
            
            String attrJson = new Map2JsonString().convert(objectJson.getObjectAsMap());
            ObjectId objectId = inserter.insert(Constants.OBJ_BLOB, attrJson.getBytes());
            inserter.flush();
            
            System.out.println("MetaObject: " + attrJson);

            tree.append(attr, FileMode.REGULAR_FILE, objectId);
        }
        return tree.computeId(inserter);
    }
    
    public static String doubleQuote(String str){
        
        return "\"" + str + "\"";
    }
}
