/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.iterators;

import edu.uiowa.cs.entities.Word;
import java.util.Locale;

/**
 *
 * @author Harsha
 */
public class ToLowerCase implements ApplyFunction<Word, Word> {

    @Override
    public Word apply(Word x) {
        Word w = new Word();
        w.setWord_content(x.getWord_content().toLowerCase());
        w.setSentence_count(x.getSentence_count());
        return w;

    }
}
