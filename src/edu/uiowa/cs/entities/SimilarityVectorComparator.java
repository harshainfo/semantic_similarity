/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.entities;

import java.util.Comparator;

/**
 *
 * @author Harsha
 */
public class SimilarityVectorComparator implements Comparator<SimilarityVector>{

    @Override
    public int compare(SimilarityVector t, SimilarityVector t1) {
        //System.out.println(t.getWord()+"="+t.getCosineSimilarity());
        //System.out.println(t1.getWord()+"="+t1.getCosineSimilarity());
        return t.getSimilarity().compareTo(t1.getSimilarity());
    }
    
}
