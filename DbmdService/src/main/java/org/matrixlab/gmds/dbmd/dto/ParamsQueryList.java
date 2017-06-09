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
package org.matrixlab.gmds.dbmd.dto;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import org.matrixlab.gmds.core.JsonInterface;

/**
 * This class provides params (actually just one parameter - repo path) to access gmds repo
 *
 * @author alexmylnikov
 */
public class ParamsQueryList extends HashMap<String, String> implements JsonInterface <ParamsQueryList> {

    public static final String REPO_PATH = "REPO_PATH";
    
    @Override
    public JsonObject toJsonObject() {
        JsonParser parser = new JsonParser();
        String jsonString = new Gson().toJson(this, ParamsQueryList.class);
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        
        return jsonObject;
    }

    @Override
    public String toJsonString() {
        return new Gson().toJson(this, ParamsQueryList.class);
    }

    @Override
    public ParamsQueryList fromJsonObject(JsonObject json) {
        return new Gson().fromJson(json, ParamsQueryList.class);
    }

    @Override
    public ParamsQueryList fromJsonString(String json) {
        return new Gson().fromJson(json, ParamsQueryList.class);
    }

}
