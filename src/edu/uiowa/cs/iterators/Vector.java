/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.iterators;

import java.util.List;

/**
 *
 * @author Harsha
 */
public interface Vector<InT, OutT> {
    public OutT apply(InT x);
    
}
