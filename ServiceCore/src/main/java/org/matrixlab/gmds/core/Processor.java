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
public interface Processor <I, O, P, S> {
    Processor newInstance();
    Processor newInstance(I input, O output, P param, S persist);
    
    Processor newInstance(Vertx vertx);
    Processor newInstance(Vertx vertx, I input, O output, P param, S persist);
    
    O process();
    O process(I input);
    O process(I input, P params);
    O process(I input, P params, S persist);
    
}
