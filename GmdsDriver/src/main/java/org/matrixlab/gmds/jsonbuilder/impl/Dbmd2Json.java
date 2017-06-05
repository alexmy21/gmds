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

package org.matrixlab.gmds.jsonbuilder.impl;

import com.google.gson.JsonObject;
import org.matrixlab.gmds.jsonbuilder.api.Converter;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexmy
 */
public class Dbmd2Json implements Converter<DatabaseMetaData, JsonObject> {

    private String metaRepo;
    
    public Dbmd2Json() {
        this.metaRepo = null;
    }
    
    public Dbmd2Json(String metaRepo) {
        this.metaRepo = metaRepo;
    }
    
    @Override
    public JsonObject convert(DatabaseMetaData source) {
        
        if(metaRepo == null){
            try {
                this.metaRepo = source.getURL();
            } catch (SQLException ex) {
                Logger.getLogger(Dbmd2Json.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        JsonObject sourceJson = new JsonObject();
        
        sourceJson.addProperty(metaRepo, metaRepo);
        
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }
    
}
