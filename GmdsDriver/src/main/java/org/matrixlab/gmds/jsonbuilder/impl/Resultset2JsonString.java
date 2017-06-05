/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.gmds.jsonbuilder.impl;

import org.matrixlab.gmds.jsonbuilder.api.Converter;
import java.sql.Connection;
import java.sql.ResultSet;
import org.jooq.impl.DSL;

/**
 *
 * @author alexmy
 */
public class Resultset2JsonString implements Converter<ResultSet, String> {
    
    Connection conn;
    
    public Resultset2JsonString(Connection metadata){
        this.conn = metadata;
    }

    @Override
    public String convert(ResultSet source) {
        return DSL.using(conn).fetch(source).formatJSON();
    }    
}
