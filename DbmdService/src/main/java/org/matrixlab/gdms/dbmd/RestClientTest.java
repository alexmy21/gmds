/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.gdms.dbmd;

import java.io.IOException;
import java.util.Collections;
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

/**
 *
 * @author alexmylnikov
 */
public class RestClientTest {

    public static void main(String[] args) {

        try {
            RestClient client = null;
            String url = "localhost";
            int port = 9200;

            CredentialsProvider credentialsProvider = setCredentials("elastic", "changeme");

            RestClient restClient = createRestClient(url, port, credentialsProvider);

            System.out.println("Connected");

            //index a document
            HttpEntity entity = new NStringEntity(
                    "{\n"
                    + "    \"user\" : \"kimchy\",\n"
                    + "    \"post_date\" : \"2009-11-15T14:12:12\",\n"
                    + "    \"message\" : \"trying out Elasticsearch\"\n"
                    + "}", ContentType.APPLICATION_JSON);

            Response indexResponse = restClient.performRequest(
                    "PUT",
                    "/mds/test/12345678",
                    Collections.<String, String>emptyMap(),
                    entity);

            System.out.println(indexResponse.toString());

        } catch (IOException ex) {
            Logger.getLogger(RestClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static RestClient createRestClient(String url, int port, CredentialsProvider credentialsProvider) {
        RestClient restClient = RestClient.builder(new HttpHost(url, port))
                .setHttpClientConfigCallback((HttpAsyncClientBuilder httpClientBuilder)
                        -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)).build();

        return restClient;
    }

    public static CredentialsProvider setCredentials(String uid, String psw) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(uid, psw));

        return credentialsProvider;
    }
}
