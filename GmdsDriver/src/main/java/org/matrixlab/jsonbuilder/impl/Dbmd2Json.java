/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.jsonbuilder.impl;

import com.google.gson.JsonObject;
import org.matrixlab.jsonbuilder.api.Converter;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexmy
 */
public class Dbmd2Json implements Converter<DatabaseMetaData, JsonObject> {

    private String metaRepo;
    
    public Dbmd2Json() {
        this.metaRepo = null;
    }
    
    public Dbmd2Json(String metaRepo) {
        this.metaRepo = metaRepo;
    }
    
    @Override
    public JsonObject convert(DatabaseMetaData source) {
        
        if(metaRepo == null){
            try {
                this.metaRepo = source.getURL();
            } catch (SQLException ex) {
                Logger.getLogger(Dbmd2Json.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        JsonObject sourceJson = new JsonObject();
        
        sourceJson.addProperty(metaRepo, metaRepo);
        
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }
    
}
