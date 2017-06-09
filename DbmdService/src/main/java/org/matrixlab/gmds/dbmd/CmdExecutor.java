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

package org.matrixlab.gmds.dbmd;

import org.matrixlab.gmds.dbmd.DbmdProcessor;
import org.matrixlab.gmds.dbmd.command.Commit;
import org.matrixlab.gmds.dbmd.command.QueryList;

/**
 *
 * @author alexmy
 */
public enum CmdExecutor {

    COMMIT {
                @Override
                public void execute(DbmdProcessor processor) {
                    new Commit(processor).run();
                }
            },
    QUERYLIST {
                @Override
                public void execute(DbmdProcessor processor) {
                    new QueryList(processor).run();
                }
            };

    abstract public void execute(DbmdProcessor processor);
}
