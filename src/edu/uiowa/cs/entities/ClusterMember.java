/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.entities;

import java.util.HashMap;

/**
 *
 * @author WakeupBlake
 */
public class ClusterMember{
    private SemanticDescriptorVector sdv;
    private Double similarity;

    /**
     * @return the similarity
     */
    
    
    
    public ClusterMember(SemanticDescriptorVector sdv, Double similarity) {
        this.sdv = sdv;
        this.similarity = similarity;
    }

    public ClusterMember(Double similarity) {
        this.similarity = similarity;
    }

    public ClusterMember() {
    }

    public Double getSimilarity() {
        return similarity;
    }

    /**
     * @param similarity the similarity to set
     */
    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    /**
     * @return the sdv
     */
    public SemanticDescriptorVector getSdv() {
        return sdv;
    }

    /**
     * @param sdv the sdv to set
     */
    public void setSdv(SemanticDescriptorVector sdv) {
        this.sdv = sdv;
    }
    
}
