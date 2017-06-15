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
package org.matrixlab.gmds.index;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import java.util.Properties;
import org.matrixlab.gmds.core.Runner;
import org.matrixlab.gmds.index.dto.ParamsIndexHttpServer;

/**
 *
 * @author alexmylnikov
 */
public class IndexHttpServer extends AbstractVerticle {

    Properties props;
    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {
        Runner.runVerticle(IndexHttpServer.class);
    }

    @Override
    public void start() throws Exception {

        String host     = getParams().get(ParamsIndexHttpServer.HOST);
        Integer port    = Integer.valueOf(getParams().get(ParamsIndexHttpServer.PORT));
        
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
  
        router.post(host).handler(ctx -> { new IndexController(vertx).process(ctx);});

        vertx.createHttpServer().requestHandler(router::accept).listen(port);
    }
    
    /**
     * Ideally all params should be taken from mds (Meta data Store)
     * In case of CoreOS etcd can be used to discover related repository.
     * 
     * @return 
     */
    ParamsIndexHttpServer getParams() {
        ParamsIndexHttpServer params = new ParamsIndexHttpServer();
        params.put(ParamsIndexHttpServer.HOST, "/dbmd/:command");
        params.put(ParamsIndexHttpServer.PORT, "1947");
        
        return params;
    }

}
