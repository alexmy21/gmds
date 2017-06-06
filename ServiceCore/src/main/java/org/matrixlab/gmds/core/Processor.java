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

/**
 * The main interface in Service containers
 * @author alexmy
 * 
 * @param <I>
 * @param <O>
 * @param <P>
 */
public abstract class Processor <I, O, P> {
//    public abstract Processor newInstance();
//    Processor newInstance(I input, O output, P param);
    
//    public abstract Processor newInstance(Vertx vertx);
//    Processor newInstance(Vertx vertx, I input, O output, P param);
    
    public abstract O process();
    public abstract O process(String command);
    
    public abstract O process(I input);
    public abstract O process(String command, I input);
    
    public abstract O process(I input, P params);
    public abstract O process(String command, I input, P params);
}
