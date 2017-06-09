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
package org.matrixlab.gmds.dbmd.command;

import java.util.List;
import org.matrixlab.gmds.dbmd.DbmdProcessor;
import org.matrixlab.gmds.dbmd.dto.InputQueryList;
import org.matrixlab.gmds.dbmd.dto.ParamsQueryList;
import org.matrixlab.gmds.core.CommandCheck;

/**
 *
 * @author alexmylnikov
 */
public class QueryList implements Runnable, CommandCheck {

    private final DbmdProcessor processor;

    private final String REPO_PATH;
    private final List<String> objectIdList;

    public QueryList(DbmdProcessor processor) {
        this.processor = processor;
        this.REPO_PATH = processor.getParams().get(ParamsQueryList.REPO_PATH);
        this.objectIdList = new InputQueryList().fromJsonString(processor.getInput());
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
