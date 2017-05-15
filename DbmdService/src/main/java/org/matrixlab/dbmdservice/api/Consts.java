/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.dbmdservice.api;

/**
 *
 * @author alexmy
 */
public interface Consts {
    
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
    
}
