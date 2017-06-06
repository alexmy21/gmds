/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.gmds.core;


import java.util.List;

/**
 * Created by alexmy on 03/09/16.
 * 
 * @param <T> - Persistence storage;
 * @param <I> - Persistence Entry Id; 
 * @param <D> - Data to be persisted;
 */
public interface Persist<T, I, D> {
   
    // List support
    //
    Boolean push2List(I id, D data);
    
    D pullFromListHead(I id, int index);
    
    D pullFromListTail(I id, int index);
    
    D pullLastFromList(I id);
    
    List<D> pullAllFromList(I id);
    
    Boolean deleteItemFromList(I id, int index);
    
    Boolean deleteAllFromList(I id);
}
