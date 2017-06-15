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
package org.matrixlab.gmds.index.dto;

import com.google.gson.JsonObject;
import java.util.HashMap;
import org.matrixlab.gmds.core.JsonInterface;

/**
 *
 * @author alexmylnikov
 */
public class ParamsIndexHttpServer extends HashMap<String, String> 
        implements JsonInterface <ParamsIndexHttpServer> {
    
    public static final String HOST = "HOST";
    public static final String PORT = "PORT";

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
    public ParamsIndexHttpServer fromJsonObject(JsonObject json) {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ParamsIndexHttpServer fromJsonString(String json) {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }
    
}
