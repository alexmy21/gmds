/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
