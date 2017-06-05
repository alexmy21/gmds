/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
