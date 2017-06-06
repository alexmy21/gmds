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
package org.matrixlab.gdms.dbmd.dto;

import com.google.gson.JsonObject;
import org.matrixlab.gmds.core.JsonInterface;

/**
 *
 * @author alexmylnikov
 */
public class InputCommit implements JsonInterface {
    private String URL;
    private String REPO_PATH;

    /**
     * @return the URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @param URL the URL to set
     */
    public void setURL(String URL) {
        this.URL = URL;
    }
   
    /**
     * @return the REPO_PATH
     */
    public String getREPO_PATH() {
        return REPO_PATH;
    }

    /**
     * @param REPO_PATH the REPO_PATH to set
     */
    public void setREPO_PATH(String REPO_PATH) {
        this.REPO_PATH = REPO_PATH;
    }

    @Override
    public JsonObject toJsonObject() {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toJsonString() {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object fromJsonObject(JsonObject json, Class cl) {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object fromJsonString(String json, Class cl) {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }
}
