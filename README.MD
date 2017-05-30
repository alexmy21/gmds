

This branch is created to perform a heavy refactoring of the master to implement a service-container architecture. 
The configuration so far is the same as in master.

In order to run it, you have to create two directories: one for metadata and another for data (I want to keep meta and data separately), 
and to edit properties in Consts.java file. The database is still sample db in Derby database. You can use any other db, but the set of 
metadata is very limited and include only general properties about jdbc driver, tables and columns.

 
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