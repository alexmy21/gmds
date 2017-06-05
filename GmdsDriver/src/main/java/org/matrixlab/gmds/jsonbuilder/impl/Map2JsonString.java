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

import com.google.gson.Gson;
import org.matrixlab.gmds.jsonbuilder.api.Converter;
import java.util.Map;

/**
 *
 * @author alexmy
 */
public class Map2JsonString implements Converter<Map<String, Object>, String> {

    @Override
    public String convert(Map<String, Object> source) {
        return new Gson().toJson(source, Map.class);
    }

}
