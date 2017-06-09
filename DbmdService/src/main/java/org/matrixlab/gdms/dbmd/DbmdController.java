/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.gdms.dbmd;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import java.util.Map;
import org.matrixlab.gdms.dbmd.dto.Output;
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
    Map<String, String> params;

    public DbmdController(Vertx vertx, Map<String, String> params) {
        this.vertx = vertx;
        this.params = params;
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
            params.put(Constants.command, command);
            Processor processor = DbmdProcessor.newInstance(this.vertx);
            String result;
            if ((null != requestJson) && !requestJson.isEmpty()) {
                setInput(processor, requestJson, command);
                Output output = (Output) processor.process(command);
                result = output.toJsonString();
            } else {
                result = "No data";
            }

            if (result == null) {
                result = "No data";
            }

            response.putHeader(Constants.contentTypeName, Constants.contentTypeValue).end(result);
        }
    }
   
    private void setInput(Processor processor, String json, String command){
        
    }
}
