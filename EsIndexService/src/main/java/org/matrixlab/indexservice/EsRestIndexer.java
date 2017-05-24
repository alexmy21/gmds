/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.indexservice;

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
import org.matrixlab.jsonbuilder.api.RestIndexer;

/**
 *
 * @author alexmylnikov
 */
public class EsRestIndexer implements RestIndexer {

    public static void main(String[] args) {

        RestClient client = null;
        String url = "localhost";
        int port = 9200;

        EsRestIndexer indexer = new EsRestIndexer();
        CredentialsProvider credentialsProvider = indexer.setCredentials("elastic", "changeme");

        RestClient restClient = indexer.createRestClient(url, port, credentialsProvider);

        System.out.println("Connected");

        //index a document
        String str = "{\"DriverName\":\"Apache Derby Network Client JDBC Driver\",\"DatabaseProductVersion\":\"10.10.2.0 - (1582446)\",\"URL\":\"jdbc:derby://localhost:1527/sample;create\\u003dtrue\"}";

        HttpEntity entity = new NStringEntity(str, ContentType.APPLICATION_JSON);
        
        Response indexResponse = indexer.index(restClient, "mds", "local", "23456789", str);

        System.out.println(indexResponse.toString());

    }

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
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(uid, psw));

        return credentialsProvider;
    }

    @Override
    public Response index(RestClient client, String index, String type, String id, String json) {

        String endpoint = "/" + index + "/" + type + "/" + id;
        
        try {
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);

            Response indexResponse = client.performRequest("POST", endpoint, Collections.<String, String>emptyMap(), entity);

            System.out.println(indexResponse.toString());

            return indexResponse;
        } catch (IOException ex) {
            Logger.getLogger(EsRestIndexer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Response index(RestClient client, Map<String, String> props, String json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response reindex(RestClient client, String index, String type, String id, String json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response reindex(RestClient client, Map<String, String> props, String json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response updateIndex(RestClient client, String index, String type, String id, String json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response updsteIndex(RestClient client, Map<String, String> props, String json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
