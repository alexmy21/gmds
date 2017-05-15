/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.jsonbuilder.api;

import java.util.Map;

/**
 *
 * @author alexmy
 * @param <K>
 * @param <V>
 */
public interface ObjectBuilder <K, V> {
    
    ObjectBuilder addDoc(K docName, V json);    
    
    ObjectBuilder removeDoc(K docName);
    
    V getDoc(K docName);
    
    Map<K, V> getObjectAsMap();
    
}
