/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.iterators;

import edu.uiowa.cs.entities.Word;

/**
 *
 * @author WakeUpBlake
 */

public class RemoveStopWord implements ApplyFunction<Word, Word> {
        //array of stopwords
        String[] stop = {"a", "about", "above", "after", "again", "against", "all", "am", 
        "an", "and", "any", "are", "aren't", "as", "at", "be", "because", "been", "before", 
        "being", "below", "between", "both", "but", "by", "can't", "cannot", "could", "couldn't", 
        "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", 
        "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", 
        "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", 
        "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", 
        "into", "is", "isn't", "it", "it's", "its", "itself", "let's", "me", "more", "most", 
        "mustn't", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", 
        "or", "other", "ought", "our", "ourselves", "ours", "out", "over", "own", "same", 
        "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "some", 
        "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", 
        "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", 
        "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't", 
        "we", "we'd", "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when", 
        "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", 
        "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", 
        "your", "yours", "yourself", "yourselves"};
        
        //takes string and returns empty string if word is "stopword" else returns word
        /*@Override
        public String apply(String x) {
            String ret = x;
            
            for(String i: stop){
                for(char in:x.getChars(0, 0, x, 0)){
                    
                }
                System.out.println("x="+x+"end");
                System.out.println("i="+i+"end");
                i="you";
                System.out.println("x.equal(i)="+x.equals(i));
                
                if(x.equals(i)) {
                    ret = "";
                    System.out.println("ret="+ret);
                }
            }
            return ret.toLowerCase();
        }
*/

    @Override
    public Word apply(Word x) {
        Word ret = x;
            
            for(String i: stop){
                
                if(x.getWord_content().equals(i)) {
                    ret.setWord_content("");
                    //System.out.println("ret="+ret);
                }
                    
                
            }
            ret.setWord_content(ret.getWord_content().toLowerCase());
            return ret;
    }
    }