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

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.matrixlab.gmds.dbmd.dto.ParamsCommit;

/**
 *
 * @author alexmylnikov
 */
public class JsonTest {
    private static final String DATABASE = "jdbc:derby://localhost:1527/sample";
    private static final String URL = DATABASE + ";create=true;user=app;password=app";
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        Map<String, String> map = new HashMap<>();
        
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        String json = new Gson().toJson(map, Map.class);
        System.out.println(json);
        
        ParamsCommit paramsCommit = new Gson().fromJson(json, ParamsCommit.class);
        
        System.out.println(new Gson().toJson(paramsCommit, ParamsCommit.class));
        
        Connection conn = null;
        
        conn = DriverManager.getConnection(URL);
    }
    
}
