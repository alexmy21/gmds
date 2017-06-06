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

package org.matrixlab.gdms.dbmd;

import org.matrixlab.gdms.dbmd.command.Commit;
import org.matrixlab.gdms.dbmd.command.Query;

/**
 *
 * @author alexmy
 */
public enum CommandExec {

    COMMIT {
                @Override
                public void execute(DbmdProcessor tool) {
                    new Commit(tool).run();
                }
            },
    QUERY {
                @Override
                public void execute(DbmdProcessor tool) {
                    new Query(tool).run();
                }
            };

    abstract public void execute(DbmdProcessor tool);
}
