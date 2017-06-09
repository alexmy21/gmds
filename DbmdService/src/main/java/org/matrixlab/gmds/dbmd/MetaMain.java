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

import org.matrixlab.gmds.dbmd.dto.Output;
import org.matrixlab.gmds.core.Processor;
import org.matrixlab.gmds.dbmd.api.Consts;

/**
 *
 * @author alexmy
 */
public class MetaMain {

    private static final String DATABASE = "jdbc:derby://localhost:1527/sample";
    private static final String URL = DATABASE + ";create=true;user=app;password=app";
    public static final String REPO_PATH = Consts.META_REPO_PATH + ".git";

    private static final String JSON = "{\"URL\":" + URL + "," + "\"REPO_PATH\":" + REPO_PATH;

    public static void main(String[] args) {

        Processor processor = new DbmdProcessor().newInstance();
        String result;

        setInput(processor, JSON, "COMMIT");
        Output output = (Output) processor.process();

        result = output.toJsonString() == null ? "No data" : output.toJsonString();
        System.out.println("RESULT: " + result);
    }

    private static void setInput(Processor processor, String json, String command) {
        processor.setInput(json);
        processor.setCommand(command);
    }
}
