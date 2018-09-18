/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.iterators;

import edu.uiowa.cs.entities.Word;
import edu.uiowa.cs.entities.WordOccurence;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Harsha
 */
public class ReduceWords implements ReduceFunction<Word, WordOccurence>{

    @Override
    public WordOccurence combine(WordOccurence soFar, Word x) {
        List<Integer> arr= soFar.getOccurences();
        arr.add(x.getSentence_count());
        return new WordOccurence(x.getWord_content(), arr);
    }

    @Override
    public WordOccurence initialValue(Word x) {
        List<Integer> arr= new ArrayList<Integer>();
        arr.add(x.getSentence_count());
        return new WordOccurence(x.getWord_content(), arr);
    }

   
    
}
