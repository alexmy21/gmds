/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
