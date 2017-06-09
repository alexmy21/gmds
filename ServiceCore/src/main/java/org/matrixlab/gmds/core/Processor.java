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
package org.matrixlab.gmds.core;

import io.vertx.core.Vertx;

/**
 * The main interface in Service containers
 * @author alexmy
 * 
 * @param <I>
 * @param <O>
 * @param <P>
 * @param <S>
 */
public abstract class Processor <I, O, P, S> {

    I input;
    O output;
    P params;
    S persist;
    
    String command;
    Vertx vertx;
    
    public abstract Processor newInstance();
    public abstract Processor newInstance(I input, O output, P param);
    
    // Special case of new Instance for Vertx
    public abstract Processor newInstance(Vertx vertx);
    public abstract Processor newInstance(Vertx vertx, I input, O output, P param);
    
    public abstract O process();
    public abstract O process(String command);
    
    public abstract O process(I input);
    public abstract O process(String command, I input);
    
    public abstract O process(I input, P params);
    public abstract O process(String command, I input, P params);
    
    
    /**
     * @return the input
     */
    public abstract I getInput();

    /**
     * @param input the input to set
     */
    public abstract void setInput(I input);

    /**
     * @return the output
     */
    public abstract O getOutput();

    /**
     * @param output the output to set
     */
    public abstract void setOutput(O output);

    /**
     * @return the param
     */
    public abstract P getParams();

    /**
     * @param param the param to set
     */
    public abstract void setParams(P param);

    
    /**
     * @return the persist
     */
    public abstract S getPersist();

    /**
     * @param persist the persist to set
     */
    public abstract void setPersist(S persist);

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return the vertx
     */
    public Vertx getVertx() {
        return vertx;
    }

    /**
     * @param vertx the vertx to set
     */
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }
}
