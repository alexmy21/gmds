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
package org.matrixlab.gmds.dbmd;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.vertx.core.Vertx;
import org.matrixlab.gmds.dbmd.dto.ParamsCommit;
import org.matrixlab.gmds.dbmd.dto.Output;
import org.matrixlab.gmds.core.JsonInterface;
import org.matrixlab.gmds.core.Persist;
import org.matrixlab.gmds.core.Processor;

/**
 *
 * @author alexmylnikov
 */
public class DbmdProcessor extends Processor<String, Output, ParamsCommit, Persist> implements JsonInterface<DbmdProcessor>{
    
    private String input        = null;
    private Output output       = null;
    private ParamsCommit params = null;
    
    // Set persist instance to null as a default if you are not going to use persistence
    private Persist persist     = null;
   
    /**
     *
     * @return
     */
    @Override
    public Processor newInstance() {
        Processor processor = new DbmdProcessor();
        return processor;
    }

    /**
     * 
     * @param vertx
     * @return 
     */
    @Override
    public Processor newInstance(Vertx vertx) {
        Processor processor = new DbmdProcessor();
        processor.setVertx(vertx);
        
        return processor;
    }

    /**
     * 
     * @param input
     * @param output
     * @param params
     * @return 
     */
    @Override
    public Processor newInstance(String input, Output output, ParamsCommit params) {
        Processor processor = new DbmdProcessor();
        processor.setInput(input);
        processor.setOutput(output);
        processor.setParams(params);
        
        return processor;
    }

    /**
     * 
     * @param vertx
     * @param input
     * @param output
     * @param params
     * @return 
     */
    @Override
    public Processor newInstance(Vertx vertx, String input, Output output, ParamsCommit params) {
        Processor processor = new DbmdProcessor();
        processor.setVertx(vertx);
        processor.setInput(input);
        processor.setOutput(output);
        processor.setParams(params);
        
        return processor;
    }
    
    // Processing 
    //==========================================================================
    @Override
    public Output process() {
        CmdExecutor.valueOf(getCommand()).execute(this);
        
        return this.getOutput();
    }

    @Override
    public Output process(String input) {
        this.setInput(input);
        CmdExecutor.valueOf(getCommand()).execute(this);
        
        return this.getOutput();
    }

    @Override
    public Output process(String input, ParamsCommit params) {
        this.setInput(input);
        this.setParams(params);
        CmdExecutor.valueOf(getCommand()).execute(this);
        
        return this.getOutput();
    }

    @Override
    public Output process(String command, String input) {
        this.setCommand(command);
        this.setInput(input);
        CmdExecutor.valueOf(getCommand()).execute(this);
        
        return this.getOutput();
    }

    @Override
    public Output process(String command, String input, ParamsCommit params) {
        this.setCommand(command);
        this.setInput(input);
        this.setParams(params);
        CmdExecutor.valueOf(getCommand()).execute(this);
        
        return this.getOutput();
    }
    
    // Properties
    //==========================================================================
    
     /**
     * @return the input
     */
    @Override
    public String getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    @Override
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * @return the output
     */
    @Override
    public Output getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    @Override
    public void setOutput(Output output) {
        this.output = output;
    }

    /**
     * @return the params
     */
    @Override
    public ParamsCommit getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    @Override
    public void setParams(ParamsCommit params) {
        this.params = (ParamsCommit) params;
    }

    /**
     * @return the persist
     */
    @Override
    public Persist getPersist() {
        return persist;
    }

    /**
     * @param persist the persist to set
     */
    @Override
    public void setPersist(Persist persist) {
        this.persist = persist;
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
