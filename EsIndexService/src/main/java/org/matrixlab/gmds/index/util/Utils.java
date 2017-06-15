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
package org.matrixlab.gmds.index.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.matrixlab.gmds.jsonbuilder.api.RestIndexer;

/**
 *
 * @author alexmy
 */
public class Utils implements RestIndexer {

    /**
     *
     * @param url
     * @param port
     * @param credentialsProvider
     * @return
     */
    @Override
    public RestClient createRestClient(String url, int port, CredentialsProvider credentialsProvider) {
        RestClient restClient = RestClient.builder(new HttpHost(url, port))
                .setHttpClientConfigCallback((HttpAsyncClientBuilder httpClientBuilder)
                        -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)).build();

        return restClient;
    }

    /**
     *
     * @param uid
     * @param psw
     * @return
     */
    @Override
    public CredentialsProvider setCredentials(String uid, String psw) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(uid, psw));

        return credentialsProvider;
    }

    /**
     * 
     * @param client
     * @param index
     * @param type
     * @param id
     * @param json
     * @return 
     */
    @Override
    public Response index(RestClient client, String index, String type, String id, String json) {

        String endpoint = "/" + index + "/" + type + "/" + id;

        try {
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);

            Response indexResponse = client.performRequest("PUT", endpoint, Collections.<String, String>emptyMap(), entity);

            System.out.println(indexResponse.toString());

            return indexResponse;
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * 
     * @param client
     * @param props
     * @param json
     * @return 
     */
    @Override
    public Response index(RestClient client, Map<String, String> props, String json) {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param client
     * @param index
     * @param type
     * @param id
     * @param json
     * @return 
     */
    @Override
    public Response reindex(RestClient client, String index, String type, String id, String json) {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param client
     * @param props
     * @param json
     * @return 
     */
    @Override
    public Response reindex(RestClient client, Map<String, String> props, String json) {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param client
     * @param index
     * @param type
     * @param id
     * @param json
     * @return 
     */
    @Override
    public Response updateIndex(RestClient client, String index, String type, String id, String json) {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param client
     * @param props
     * @param json
     * @return 
     */
    @Override
    public Response updsteIndex(RestClient client, Map<String, String> props, String json) {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }
}
