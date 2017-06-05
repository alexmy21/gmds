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
import org.apache.http.client.CredentialsProvider;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

/**
 *
 * @author alexmylnikov
 */
public interface RestIndexer {
    
    CredentialsProvider setCredentials(String uid, String psw);
    
    RestClient createRestClient(String url, int port, CredentialsProvider credentialsProvider);
    
    Response index(RestClient client, String index, String type, String id, String json);
    
    Response index(RestClient client, Map<String, String> props, String json);
    
    Response reindex(RestClient client, String index, String type, String id, String json);
    
    Response reindex(RestClient client, Map<String, String> props, String json);
     
    Response updateIndex(RestClient client, String index, String type, String id, String json);
    
    Response updsteIndex(RestClient client, Map<String, String> props, String json);
}
