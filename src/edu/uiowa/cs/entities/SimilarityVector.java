/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.entities;

import edu.uiowa.cs.entities.SemanticDescriptorVector;
import edu.uiowa.cs.iterators.Vector;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Harsha
 */
public class SimilarityVector{
    private String word;
    private Double similarity;

    
    public SimilarityVector(String word, Double cosineSimilarity) {
        this.word = word;
        this.similarity = cosineSimilarity;
    }

    public SimilarityVector() {
    }

    public String getWord() {
        return word;
    }

    /**
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * @return the similarity
     */
    public Double getSimilarity() {
        return similarity;
    }

    /**
     * @param similarity the similarity to set
     */
    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    
    
    

   

    
    
    
    
    


    
}
