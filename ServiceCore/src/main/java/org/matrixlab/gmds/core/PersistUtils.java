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

import com.google.gson.Gson;

import java.util.List;
import java.util.Properties;

/**
 *
 * Created by alexmy on 03/09/16.
 */
public class PersistUtils {

    private static final Gson gson = new Gson();

    public static Boolean validateInput(String json) {

        return true;

    }

    public static Boolean validateParams(Properties props) {

        return true;
    }

    public static Boolean validatePersist(String json) {

        return true;
    }

    public static Boolean validateOutput(String json) {

        return true;
    }

    // Working with Hazelcast collections
    //=========================================================
    /**
     *
     * @param list
     * @return
     */
    public static String getLast(List<String> list) {
        if (list != null && list.size() > 0) {
            return list.get(list.size() - 1);
        } else {
            return null;
        }
    }

    /**
     *
     * @param list
     * @param index
     * @return
     */
    public static String getFromHead(List<String> list, int index) {

        if (list != null && list.size() > index) {
            return list.get(index);
        } else {
            return null;
        }
    }

    /**
     * Removes an item from the list on the (size - index) position in the list,
     * so, if size = 5 and index = 3, it will remove item with index = 1 (the
     * second from the beginning); with index = 4, it will remove first; with
     * index = 0, remove last.
     *
     * @param list
     * @param index
     * @return
     */
    public static String getFromTail(List<String> list, int index) {

        if (list != null && list.size() > index) {
            return list.get(list.size() - (index + 1));
        } else {
            return null;
        }
    }

    /**
     *
     * @param list
     * @param index
     * @return
     */
    public static Boolean delete(List<String> list, int index) {
        if (list != null && list.size() > index) {
            return list.remove(index) != null;
        } else {
            return false;
        }
    }

    /**
     *
     * @param list
     * @return
     */
    public static Boolean deleteAll(List<String> list) {
        if (list != null) {
            return list.removeAll(list);
        } else {
            return false;
        }
    }

    public static Boolean addToEnd(List<String> list, String data) {
        if (list != null) {
            return list.add(data);
        } else {
            return false;
        }
    }

}
