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

package org.matrixlab.gmds.index;

import org.matrixlab.gmds.index.command.IndexRest;
import org.matrixlab.gmds.index.command.QueryRest;


/**
 *
 * @author alexmy
 */
public enum CmdExecutor {

    INDEX_REST {
                @Override
                public void execute(IndexProcessor processor) {
                    new IndexRest(processor).run();
                }
            },
    QUERY {
                @Override
                public void execute(IndexProcessor processor) {
                    new QueryRest(processor).run();
                }
            };

    abstract public void execute(IndexProcessor processor);
}
