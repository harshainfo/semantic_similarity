/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.iterators;

import edu.uiowa.cs.entities.Sentence;
import edu.uiowa.cs.entities.Word;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Harsha
 */
public class SplitBy  implements FlatApplyFunction<Sentence, Word> {
    
    private String ch;

        public SplitBy(String c) {
            this.ch = c;
        }

        

    @Override
    public List<Word> apply(Sentence x) {
        List<Word> s = new ArrayList<Word>();
            String[] sArray = x.getContent().split(ch);
            int sentence_count = x.getSentence_count();
            for(String sWord:sArray){
                Word w = new Word();
                w.setWord_content(sWord);
                w.setSentence_count(sentence_count);
                s.add(w);
            }
            return s;
    }
    
}
