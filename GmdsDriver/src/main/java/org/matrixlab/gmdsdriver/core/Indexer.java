/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.gmdsdriver.core;

import java.util.Map;

/**
 *
 * @author alexmylnikov
 */
public interface Indexer {
    
    void index();
    
    void index(Map<String, String> props);
    
    void reindex();
    
    void reindex(Map<String, String> props);
    
}
