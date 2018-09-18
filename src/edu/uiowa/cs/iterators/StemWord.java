/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.iterators;
import edu.uiowa.cs.entities.Word;
import opennlp.tools.stemmer.PorterStemmer;

/**
 *
 * @author WakeUpBlake
 */
public class StemWord implements ApplyFunction<Word, Word> {
        PorterStemmer s = new PorterStemmer();
        
        @Override
        public Word apply(Word x) {
            Word w = x;
            w.setWord_content(s.stem(x.getWord_content()));

            return w;
        }
    
    
}
