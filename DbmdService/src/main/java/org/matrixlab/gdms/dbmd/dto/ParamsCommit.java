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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import org.matrixlab.gmds.core.JsonInterface;

/**
 *
 * @author alexmylnikov
 */
public class ParamsCommit extends HashMap<String, String> implements JsonInterface<ParamsCommit>{
    public static final String URL = "URL";
    public static final String REPO_PATH = "REPO_PATH";

    @Override
    public JsonObject toJsonObject() {
        JsonParser parser = new JsonParser();
        String jsonString = new Gson().toJson(this, ParamsCommit.class);
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        
        return jsonObject;
    }

    @Override
    public String toJsonString() {
        return new Gson().toJson(this, InputQueryList.class);
    }

    @Override
    public ParamsCommit fromJsonObject(JsonObject json) {
        return new Gson().fromJson(json, ParamsCommit.class);
    }

    @Override
    public ParamsCommit fromJsonString(String json) {
        return new Gson().fromJson(json, ParamsCommit.class);
    }
}