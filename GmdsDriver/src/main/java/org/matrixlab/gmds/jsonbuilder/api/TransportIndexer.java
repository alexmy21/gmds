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

package org.matrixlab.gmds.jsonbuilder.api;

import java.util.Map;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;

/**
 *
 * @author alexmylnikov
 */
public interface TransportIndexer {
    
//    TransportClient getTransportClient(String url, int port);
    
    void setTransportClient(TransportClient client);
    
    IndexResponse index(String index, String type, String id, String json);
    
    IndexResponse index(TransportClient client, Map<String, String> props, String json);
    
    IndexResponse reindex(TransportClient client, String index, String type, String id, String json);
    
    IndexResponse reindex(TransportClient client, Map<String, String> props, String json);
     
    IndexResponse updateIndex(TransportClient client, String index, String type, String id, String json);
    
    IndexResponse updsteIndex(TransportClient client, Map<String, String> props, String json);
    
}
