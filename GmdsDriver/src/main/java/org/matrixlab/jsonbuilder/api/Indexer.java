/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.jsonbuilder.api;

import java.util.Map;

/**
 *
 * @author alexmylnikov
 */
public interface Indexer {
    
    void index();
    
    void index(Map<String, String> prope);
    
    void reindex();
    
    void reindex(Map<String, String> prope);
    
}
