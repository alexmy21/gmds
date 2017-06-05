/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
