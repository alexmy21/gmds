/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.dbmdservice.util;

import org.matrixlab.dbmdservice.api.RepoVocab;
import org.matrixlab.jsonbuilder.impl.ObjectJson;
import org.matrixlab.jsonbuilder.impl.Map2JsonString;
import org.matrixlab.dbmdservice.api.Consts;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     * @param repo
     * @param tree
     * @param conn
     * @return
     * @throws IOException
     * @throws SQLException 
     */
    public static ObjectId metaTreeCommit(Repository repo, TreeFormatter tree, Connection conn) throws IOException, SQLException {

        DatabaseMetaData dbmd = conn.getMetaData();
        ObjectJson builder = new ObjectJson();
        String mapString = metaDbInfo(builder, dbmd);
        ObjectInserter objectInserter = repo.newObjectInserter();
        ObjectId blobId = objectInserter.insert(Constants.OBJ_BLOB, mapString.getBytes());
        objectInserter.flush();

        tree.append(Consts.DATABASE, FileMode.REGULAR_FILE, blobId);
        tree = Utils.putTableMeta(repo, conn, dbmd, objectInserter, tree);

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
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    public static ObjectId dataTreeCommit(Repository datarepo, TreeFormatter tree, Connection conn) throws SQLException, IOException {
        DatabaseMetaData dbmd = conn.getMetaData();
        ObjectJson builder = new ObjectJson();
        String mapString = metaDbInfo(builder, dbmd);
        ObjectInserter objectInserter = datarepo.newObjectInserter();
        ObjectId blobId = objectInserter.insert(Constants.OBJ_BLOB, mapString.getBytes());
        objectInserter.flush();

        tree.append("DATABASE", FileMode.REGULAR_FILE, blobId);
        tree = Utils.putTableMeta(datarepo, conn, dbmd, objectInserter, tree);

        ObjectId treeId = objectInserter.insert(tree);
        objectInserter.flush();

        System.out.println("Tree ID: " + treeId.getName());

        return treeId;
    }

    /**
     * 
     * @param builder
     * @param dbmd
     * @return
     * @throws SQLException 
     */
    public static String metaDbInfo(ObjectJson builder, DatabaseMetaData dbmd) throws SQLException {

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
     * @return 
     */
    public static TreeFormatter putTableMeta(Repository repo, Connection conn, 
            DatabaseMetaData dbmd, ObjectInserter objectInserter, TreeFormatter dbMetaTree) {

        String[] types = {"TABLE"};

        try {

            ResultSet tables = dbmd.getTables(null, null, null, types);
            RevWalk walkTbl = new RevWalk(repo);

            while (tables.next()) {
//                System.out.println("***************************************");

                String tableName = tables.getString(Consts.TABLE_NAME);

                TreeFormatter tblMetaTree = new TreeFormatter();
                
                // Build tree for table attributs
                buildMetaTree(tables, Consts.TABLE_META_ATTRIBUTES, objectInserter, tblMetaTree);
 
                ResultSet columns = dbmd.getColumns(null, null, tableName, null);

                while (columns.next()) {
                    putColumnMeta(columns, objectInserter, tblMetaTree, walkTbl);
                }
                ObjectId tblMetaTreeId = objectInserter.insert(tblMetaTree);
                objectInserter.flush();
                
                dbMetaTree.append(Consts.TABLE_META, walkTbl.parseTree(tblMetaTreeId));

                
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
     * @param tblTree
     * @param walkTbl
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    public static ObjectId putColumnMeta(ResultSet columns, ObjectInserter objectInserter, TreeFormatter tblTree, RevWalk walkTbl) throws SQLException, IOException {

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

    private static void buildMetaTree(ResultSet set, String[] attrs, ObjectInserter inserter, TreeFormatter tree) throws IOException, SQLException {
        for (String attr : attrs) {
            ObjectJson builder = new ObjectJson();
            builder.addDoc(attr, set.getString(attr));
            String attrJson = new Map2JsonString().convert(builder.getObjectAsMap());
            ObjectId objectId = inserter.insert(Constants.OBJ_BLOB, attrJson.getBytes());
            inserter.flush();
            
            tree.append(attr, FileMode.REGULAR_FILE, objectId);
        }
    }
}
