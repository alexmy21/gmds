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
