This is a very first draft code for the MDS. It runs against Derby DB that comes preinstalled with NetBeans IDE. This is Maven project so it should run out of the box.
Look at it as a reference implementation. This project solves only one task: putting db metadata into MDS installed as a single node. Distributed version will be next.
In addition I wanted to push content from MDS to Elasticsearch for indexing and search, but I will add it later, maybe by the end of this week. Actually it is very simple, because as you will see from code, all objects that go to MDS are JSON. ElasticSearch 5.4 has graph support as well. 

In order to run it, you have to create two directories: one for metadata and another for data (I want to keep meta and data separately), and to edit properties in

 
package org.matrixlab.dbmdservice.api;

/**
 *
 * @author alexmy
 */
public interface Consts {
    
    String DATABASE = "DATABASE";
    
    String META_REPO_PATH = "/Users/alexmylnikov1/NetBeansProjects/metarepo1/";
    String DATA_REPO_PATH = "/Users/alexmylnikov1/NetBeansProjects/datarepo1/";
. . . . . 
 
}

Start up main class is org.matrixlab.dbmdservice.MetaMain.

This file is in DbmdService subproject.

Let me know if you have any questions.
