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

/**
 * Created by alexmy on 07/12/15.
 */
public interface Constants {
    
    String PROCESSOR = "processor";
    String INPUT = "input";
    String OUTPUT = "output";
    String OUTPUT_FILE = "outputfile";
    String INPUT_FILE = "inputfile";
    String PARAMS = "params";
    String COMMAND = "command";
    String END_OF_STREAM = "!@#$END$%^";
    
    Integer workerPoolSize = 10;

    String hzListIndex = "HZ_LIST_INDEX";
    String esTool = "ES_TOOL";
    String command = "command";
    String agent = "agent";
    String input = "input";
    String esIndex = "index";
    String esType = "type";
    String mongoDBName = "db_name";
    String mongoCollectionParam = "collection";
    String mongoDBParam = "database";

    // Configuration Files
    String configurationFileName = "env.properties";

    // Sections representing in properties file
    String generic = "GENERIC";
    String elasticSearchSection = "ELASTICSEARCH";
    String hazelcastSection = "HAZELCAST";
    String mongoDBSection = "MONGODB";

    String sellerSection = "SELLER";
    String catalogSection = "CATALOG";
    String indicesSection = "INDICES";
    String esSearchSection = "ESSEARCH";
    String productSection = "PRODUCT";
    String suggesterSection = "SUGGESTER";

    // Server configuration
    String contentTypeName = "content-type";
    String contentTypeValue = "application/json";
    String uri = "uri";
    String host = "host";
    String port = "port";

    String inventoryVertbox = "INVENTORY_HTTP_VERT_BOX";
    String basketVertbox = "BASKET_HTTP_VERT_BOX";
    String schedularEngineVertbox = "SCHEDULAR_ENGINE_HTTP_VERT_BOX";
    String authUrlPaths = "AUTH.urlPaths";
    String sessionUserKey = "vertx.user";
}
