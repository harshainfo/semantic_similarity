/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Harsha
 */
public class WordOccurence {
    private String content;
    private List<Integer> occurences;

    public WordOccurence() {
    }

    public WordOccurence(String content, List<Integer> occurences) {
        this.content = content;
        this.occurences = occurences;
    }

    
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the occurences
     */
    public List<Integer> getOccurences() {
        return occurences;
    }

    /**
     * @param occurences the occurences to set
     */
    public void setOccurences(List<Integer> occurences) {
        this.occurences = occurences;
    }
    
    
    
    
}
