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
package org.matrixlab.gdms.dbmd;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.vertx.core.Vertx;
import java.util.Map;
import org.matrixlab.gdms.dbmd.dto.ParamsCommit;
import org.matrixlab.gdms.dbmd.dto.Output;
import org.matrixlab.gmds.core.JsonInterface;
import org.matrixlab.gmds.core.Persist;
import org.matrixlab.gmds.core.Processor;

/**
 *
 * @author alexmylnikov
 */
public class DbmdProcessor extends Processor<String, Output, Map<String, String>> implements JsonInterface<DbmdProcessor>{
    
    private ParamsCommit params = null;
    private Output output = null;
//    private Map<String, String> params;
    
    // Set persist instance to null as a default if you are not going to use persistence
    private Persist persist = null;
    
    private Vertx vertx;
    
    private String command = null;
    private String input = null;
    
    // private defaul constructor
    //===========================
    private DbmdProcessor(){}

    
    public static Processor newInstance() {
        return new DbmdProcessor();
    }

    public static Processor newInstance(Vertx vertx) {
        DbmdProcessor processor = new DbmdProcessor();
        processor.setVertx(vertx);
        
        return processor;
    }

    @Override
    public Output process() {
        CommandExec.valueOf(getCommand()).execute(this);
        
        return this.getOutput();
    }

    @Override
    public Output process(String input) {
        this.setInput(input);
        CommandExec.valueOf(getCommand()).execute(this);
        
        return this.getOutput();
    }

    @Override
    public Output process(String input, Map<String, String> params) {
        this.setInput(input);
        this.setParams(params);
        CommandExec.valueOf(getCommand()).execute(this);
        
        return this.getOutput();
    }

    @Override
    public Output process(String command, String input) {
        this.setCommand(command);
        this.setInput(input);
        CommandExec.valueOf(getCommand()).execute(this);
        
        return this.getOutput();
    }

    @Override
    public Output process(String command, String input, Map<String, String> params) {
        this.setCommand(command);
        this.setInput(input);
        this.setParams(params);
        CommandExec.valueOf(getCommand()).execute(this);
        
        return this.getOutput();
    }
    
    // Properties
    //==========================================================================
    
     /**
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * @return the output
     */
    public Output getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(Output output) {
        this.output = output;
    }

    /**
     * @return the params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, String> params) {
        this.params = (ParamsCommit) params;
    }

    /**
     * @return the vertx
     */
    public Vertx getVertx() {
        return vertx;
    }

    /**
     * @param vertx the vertx to set
     */
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    /**
     * @return the persist
     */
    public Persist getPersist() {
        return persist;
    }

    /**
     * @param persist the persist to set
     */
    public void setPersist(Persist persist) {
        this.persist = persist;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    // JSON support
    //==========================================================================
    @Override
    public JsonObject toJsonObject() {
        JsonParser parser = new JsonParser();
        String jsonString = new Gson().toJson(this, DbmdProcessor.class);
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        
        return jsonObject;
    }

    @Override
    public String toJsonString() {
        return new Gson().toJson(this, DbmdProcessor.class);
    }

    @Override
    public DbmdProcessor fromJsonObject(JsonObject json) {
        return new Gson().fromJson(json, DbmdProcessor.class);
    }

    @Override
    public DbmdProcessor fromJsonString(String json) {
        return new Gson().fromJson(json, DbmdProcessor.class);
    }

}
