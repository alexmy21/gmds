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

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.matrixlab.gmds.dbmd.dto.Output;
import org.matrixlab.gmds.core.Constants;
import org.matrixlab.gmds.core.PersistUtils;
import org.matrixlab.gmds.core.Processor;
import org.matrixlab.gmds.core.Status;

/**
 *
 * @author alexmy
 */
public class DbmdController extends AbstractVerticle {
    
    String command;

    public DbmdController(Vertx vertx) {
        this.vertx = vertx;
    }
    
    public void process(RoutingContext routingContext) {

        String requestJson = routingContext.getBodyAsString();
        this.command = routingContext.request().getParam(Constants.command).replaceAll("-", "_").toUpperCase();
        HttpServerResponse response = routingContext.response();

        if (!PersistUtils.validateInput(requestJson)) {
            response.setStatusMessage("Invalid request");
            response.setStatusCode(Status.ERROR.getStatusCode());
            response.putHeader(Constants.contentTypeName, Constants.contentTypeValue).end("Invalid request");
        } else {
            Processor processor = new DbmdProcessor().newInstance(this.vertx);
            String result;
            if ((null != requestJson) && !requestJson.isEmpty()) {
                setInput(processor, requestJson, command);
                Output output = (Output) processor.process();
                
                result = output.toJsonString() == null? "No data" : output.toJsonString();
            } else {
                result = "No data";
            }

            response.putHeader(Constants.contentTypeName, Constants.contentTypeValue).end(result);
        }
    }
   
    private void setInput(Processor processor, String json, String command){
        processor.setInput(json);
        processor.setCommand(command);
    }
}
