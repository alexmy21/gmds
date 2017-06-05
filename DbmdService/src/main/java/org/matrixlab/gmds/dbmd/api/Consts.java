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

package org.matrixlab.gmds.dbmd.api;

/**
 *
 * @author alexmy
 */
public interface Consts {
    
    String OBJECT_ID = "OBJECT_ID";
    
    String DATABASE = "DATABASE";
    
    String META_REPO_PATH = "/Users/alexmylnikov1/NetBeansProjects/metarepo1/";
    String DATA_REPO_PATH = "/Users/alexmylnikov1/NetBeansProjects/datarepo1/";
    
    String[] COLUMN_META_ATTRIBUTES = {"TABLE_SCHEM", "TABLE_NAME", "COLUMN_NAME", "DATA_TYPE",
                "TYPE_NAME", "COLUMN_SIZE", "DECIMAL_DIGITS", "NULLABLE"};
    
    String[] TABLE_META_ATTRIBUTES = {"TABLE_SCHEM", "TABLE_NAME"};
    
    String TABLE_NAME = "TABLE_NAME";
    String COLUMN_NAME = "COLUMN_NAME";
    
    String TABLE_META = "TABLE_META";
    String TABLE_DATA = "TABLE_DATA";
    
    String COLUMN_META = "COLUMN_META";
    String COLUMN_DATA = "COLUMN_DATA";
    
    String FILE_MODE_NAME   = "FILE_MODE_NAME";
    String FILE_MODE_CODE   = "FILE_MODE_CODE";
    String FILE_TYPE        = "FILE_TYPE";
    String OBJECT_SIZE      = "OBJECT_SIZE";
    String OBJECT_JSON      = "OBJECT_JSON";
}
