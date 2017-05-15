/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.jsonbuilder.api;

/**
 *
 * @author alexmy
 * @param <S>
 * @param <T>
 */
public interface Converter <S, T> {
    
    T convert(S source);
    
}
