/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.jsonbuilder.impl;

import org.matrixlab.jsonbuilder.api.ObjectBuilder;
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
