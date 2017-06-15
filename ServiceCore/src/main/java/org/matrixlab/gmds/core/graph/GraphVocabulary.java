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

package org.matrixlab.gmds.core.graph;

/**
 *
 * @author alexmy
 */
public interface GraphVocabulary {

    public String COLOR = "color";
    public String STATUS = "status";
    public Integer COMPLETE = 1;
    public Integer BACK_LOG = 2;
    public Integer CANCEL = 3;
    
    public String START_NODE = "start-node";

    public String SOURCE_DB_URL = "source-db-url";
    public String TARGET_DB_URL = "target-db-url";

    // Graph objects
    public String DB = "db";
    public String TABLE = "table";
    public String VIEW = "view";
    public String PRIMARY_KEY = "pk";
    public String FOREIGN_KEY = "fk";
    public String ROW = "row";
    public String COLUMN = "column";

    // Constraint may be presented as a part of metadata
    public String CONSTRAINT = "constraint";

    // Graph relations
    public String DB_DB = "db-db";
    public String DB_TABLE = "db-table";
    public String TABLE_TABLE = "table-table";
    public String TABLE_VIEW = "table-view";
    public String TABLE_COLUMN = "table-column";
    public String COLUMN_COLUMN = "column-column";

    // Samples
    public String SAMPLE = "sample";
    public String SAMPLE_SIZE = "sample-size";
    public String SAMPLE_TYPE = "sample-type";
    public String SAMPLE_RANDOM = "sample-random";
    public String SAMPLE_SYSTEMATIC = "sample-systematic";
    public String SAMPLE_CLUSTER = "sample-cluster";
    public String SAMPLE_STRATIFIED = "sample-stratified";

    // Graph Node status colors
    public String UNTOUCHED = "untouched";  // Untouched node
    public String WHITE = "white";          // Node touched and data references is transfered for FORWARD direction
    public String BLUE = "blue";            // Node touched and data references is transfered forBACKWARD direction
    public String GREY = "grey";            // Node is in data processing and is trying to reach other connected nodes 
                                            // to mark them "white"
    public String RED = "red";              // Node processing completed but some UNTOUCHED marked nodes connected to it 
                                            // (in oposit to current direction) are still untouched
    public String BLACK = "black";          // Node processing completed and no untouched connected nodes left

}
