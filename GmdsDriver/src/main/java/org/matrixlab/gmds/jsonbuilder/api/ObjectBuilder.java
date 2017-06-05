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

package org.matrixlab.gmds.jsonbuilder.api;

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
