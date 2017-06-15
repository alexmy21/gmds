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

package org.matrixlab.gmds.index.api;

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
    
    // WekaHttpClient
    public static String HZ_JSON = "hz_json";
    public static String INIT_PROPS = "init.properties";
    public static String HOST_URL_UPLOAD = "host_url_upload";
    public static String HOST_URL_PROCESS = "host_url_process";
    public static String CLIENT_FILE_PATH = "client_file_path";
    
    // WekaHttpServer
    public static String FILE_NAME = "filename";
    public static String UPLOAD_FOLDER = "upload_folder";
    public static String PORT = "port";
    
    // Weka Processor
    public static String NUM_OF_FOLDS = "num_of_folds";
    public static String CLASSIFIER_LIST = "classifier_list";
    public static String LIST_STRING_DEL = "list_string_del";
    public static String J48 = "J48";
    public static String PART = "PART";
    public static String DECISION_TABLE = "DecisionTable";
    public static String DECISION_STUMP = "DecisionStump";
}
