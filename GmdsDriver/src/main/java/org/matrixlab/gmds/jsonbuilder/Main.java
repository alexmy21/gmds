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

package org.matrixlab.gmds.jsonbuilder;

/**
 *
 * @author alexmy
 */
import org.matrixlab.gmds.jsonbuilder.impl.ObjectJson;
import org.matrixlab.gmds.jsonbuilder.impl.Map2JsonString;
import org.matrixlab.gmds.jsonbuilder.impl.Resultset2JsonString;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    private static final String URL
            = "jdbc:derby://localhost:1527/sample;create=true;user=app;password=app";

    public static void main(String[] args) {
        Connection conn = null;
        DatabaseMetaData dbmd;
        try {
            conn = DriverManager.getConnection(URL);
            dbmd = conn.getMetaData();
            if (dbmd != null) {
                ObjectJson builder = new ObjectJson();
                
                builder.addDoc("URL", dbmd.getURL());
                builder.addDoc("DatabaseProductVersion", dbmd.getDatabaseProductVersion());
                builder.addDoc("DriverName", dbmd.getDriverName());
             
                System.out.println(new Map2JsonString().convert(builder.getObjectAsMap()));
                System.out.println("\n");
                
                printTableMeta(conn, dbmd);

            } else {
                System.out.println("Metadata not supported");
            }
        } catch (SQLException ex1) {
            System.err.println(ex1);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex2) {

            }
        }
    }

    private static synchronized void printTableMeta(Connection conn, DatabaseMetaData dbmd) throws SQLException {

        String[] types = {"TABLE"};

        ResultSet tableListMeta = dbmd.getTables(null, null, null, types);

        while (tableListMeta.next()) {

            System.out.println("**********************************************************************************************");
            String tableName = tableListMeta.getString("TABLE_NAME");
            ResultSet columns = dbmd.getColumns(null, null, tableName, null);

            System.out.println(new Resultset2JsonString(conn).convert(columns));            
        }
        
        System.out.println("\n");

    }
}
