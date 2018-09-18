/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.iterators;


// Interface for classes to define the functions needed for Reduce.
public interface ReduceFunction<InT,OutT> {
    /* Take the accumulated value soFar and combine it with x
    to return a new accumulated value
    */ 
	public OutT combine(OutT soFar, InT x);		

    /* Return the initial accumulated value */
	public OutT initialValue(InT x);
}

