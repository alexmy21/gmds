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
package org.matrixlab.gmds.index.command;

import org.matrixlab.gmds.core.Command;
import org.matrixlab.gmds.index.IndexProcessor;
import org.matrixlab.gmds.index.dto.ParamsIndex;
import org.matrixlab.gmds.index.dto.ParamsQuery;
/**
 *
 * @author alexmylnikov
 */
public class QueryRest implements Command {

    private final IndexProcessor processor;
    private final String host;
    private final int port;

    private final String repoPath;

    public QueryRest(IndexProcessor processor) {
        this.processor = processor;
        
        ParamsQuery paramsQuery = new ParamsQuery().fromJsonString(processor.getInput());
        this.host = paramsQuery.get(ParamsQuery.ES_HOST);
        this.port = Integer.valueOf(paramsQuery.get(ParamsIndex.ES_PORT));
        this.repoPath = paramsQuery.get(ParamsIndex.REPO_PATH);
    }

    @Override
    public void run() {
        // TODO: implement QueryList command
    }

    @Override
    public boolean check() {
        return true;
    }

}
