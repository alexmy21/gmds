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

05/24/2017

Initial implementation of Elastic Search index. In order to run this release you have to have Elasticsearch 5.4 with x-pack installed. 
This is intermediate version and it will be refactored by next release. 

Right now both mds and Elasticsearch indexing are in one DbmdService subproject.
In next release Elasticsearchwill be moved to EsIndexService.

In order to run it use MetaMain class as main class. 

Note that some addition information about project can be found in the following document:

https://docs.google.com/document/d/1SJX8SvTRadimrvOLJF4R3k-zR-rsz2rcIQq183jc_Lk/edit#