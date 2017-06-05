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

import org.matrixlab.gmds.jsonbuilder.api.ObjectBuilder;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexmy
 */
public class ObjectJson implements ObjectBuilder<String, Object> {
    
    DatabaseMetaData metadata;
    
    Map<String, Object> repoMap;
    
    public ObjectJson(){
        this.repoMap = new HashMap<>();
    }

    @Override
    public ObjectBuilder addDoc(String docName, Object json) {
        this.repoMap.put(docName, json);
        return this;
    }
    
    @Override
    public ObjectBuilder removeDoc(String docName) {
        this.repoMap.remove(docName);
        return this;
    }

    @Override
    public Map<String, Object> getObjectAsMap() {
        return this.repoMap;
    }

    @Override
    public Object getDoc(String docName) {
        return this.repoMap.get(docName);
    }
    
}
