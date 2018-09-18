package edu.uiowa.cs.readers;

import edu.uiowa.cs.entities.Sentence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class SentenceReader implements Iterator<Sentence> {
    private final Scanner scanner;
    //private final Scanner scanner2;
    private Sentence nextSentence;
    private int count;

    public SentenceReader(String filename) throws IOException {
        //scanner2 = new Scanner(new FileReader(filename)).useDelimiter("\\.|\\!|\\?");
        scanner = new Scanner(new FileReader(filename)).useDelimiter("\\.|\\!|\\?﻿");
        nextSentence = new Sentence();
        //nextSentence.setContent(scanner.next());
        count = 0;
        //nextSentence.setSentence_count(count);
        //if (scanner2.hasNext() && scanner2.hasNext()) {
         //   nextSentence = scanner.next();
            
        //}
        //scanner = new Scanner(new FileReader(filename)).useDelimiter("\\.|\\!|\\?");
        

    }

    /*
        Each call to next() returns a line from the file
     */
    @Override
    public Sentence next() {
        Sentence r = nextSentence;

        nextSentence.setContent(scanner.next());
        nextSentence.setSentence_count(++count);
        return r;
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNext();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
