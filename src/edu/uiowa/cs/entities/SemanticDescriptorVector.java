
package edu.uiowa.cs.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SemanticDescriptorVector{
    private String word;
    private HashMap<String, Double> mapVector;   
    

    public SemanticDescriptorVector(String word, HashMap<String, Double> vector) {
        this.word = word;
        this.mapVector = vector;
    }

    public SemanticDescriptorVector() {
                
        
    }
    
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public HashMap<String, Double> getMapVector() {
        return mapVector;
    }

    public void setMapVector(HashMap<String, Double> vector) {
        this.mapVector = mapVector;
    }

    @Override
    public String toString() {
        String st = this.getWord();
        st = st +"=[";
        for(Map.Entry<String, Double> entry : mapVector.entrySet()){
            st = st + entry.getKey()+"="+entry.getValue()+",";
        }
        st = st + "]";
        return st;
    }

    @Override
    public boolean equals(Object o) {
        return this.getWord().equalsIgnoreCase(((SemanticDescriptorVector) o).getWord());
    }

    @Override
    public int hashCode() {
        return this.getWord().hashCode();
    }
    
    
    
    
    

    
    
    
}
