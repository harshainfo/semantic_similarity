package edu.uiowa.cs.similarity;

//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.IntArrayData;
import edu.uiowa.cs.entities.Cluster;
import edu.uiowa.cs.entities.SimilarityVector;
import edu.uiowa.cs.entities.SimilarityVectorComparator;
import edu.uiowa.cs.entities.SemanticDescriptorVector;
import edu.uiowa.cs.entities.Sentence;
import edu.uiowa.cs.entities.Word;
import edu.uiowa.cs.entities.WordOccurence;
import edu.uiowa.cs.iterators.Apply;
import edu.uiowa.cs.iterators.ApplyFunction;
import edu.uiowa.cs.iterators.FlatApply;
import edu.uiowa.cs.iterators.Reduce;
import edu.uiowa.cs.iterators.ReduceWords;
import edu.uiowa.cs.iterators.RemoveStopWord;
import edu.uiowa.cs.iterators.SplitBy;
import edu.uiowa.cs.iterators.StemWord;
import edu.uiowa.cs.iterators.ToLowerCase;
import edu.uiowa.cs.readers.SentenceReader;
import edu.uiowa.cs.utilities.Utilities;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws IOException {

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        CommandLine cmd = null;

        //options
        options.addRequiredOption("f", "file", true, "input file to process");
        options.addOption("h", false, "print this help message");
        options.addOption("s", false, "prints sentences");
        options.addOption("v", false, "prints unique words with semantic descriptive vector");
        options.addOption("t", true, "prints top J similar words for 'word' format: word,j");
        options.addOption("m", true, "uses similarity function for top J similar words options:euc, eucnorm, <empty>-cosinesimilarity");
        options.addOption("k", true, "prints k clusters with j iterations format : k,j");
        options.addOption("j", true, "prints top j closest words to mean in k clusters with i iterations format : k,i,j");

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            new HelpFormatter().printHelp("Main", options, true);
            System.exit(1);
        }

        String filename = cmd.getOptionValue("f");

        if (!new File(filename).exists()) {
            System.err.println("file does not exist " + filename);
            System.exit(1);

        }

        if (cmd.hasOption("h")) {
            HelpFormatter helpf = new HelpFormatter();
            helpf.printHelp("Main", options, true);
            System.exit(0);
        }

        if (cmd.hasOption("s")) {

            Iterator<Sentence> lines = new SentenceReader(filename);

            while (lines.hasNext()) {
                System.out.println(lines.next().getContent().trim());
            }

        }
        if (cmd.hasOption("v")) {
            List<SemanticDescriptorVector> list = Utilities.getAllVectors(Utilities.populateMapFromFilename(filename));
            Iterator<SemanticDescriptorVector> iter = list.iterator();
            while (iter.hasNext()) {
                System.out.println(iter.next().toString());
            }
        }
        if (cmd.hasOption("t")) {
            String tOptionValue = cmd.getOptionValue("t");
            String word = tOptionValue.substring(0, tOptionValue.indexOf(","));
            String j = tOptionValue.substring(tOptionValue.indexOf(",") + 1, tOptionValue.length());
            int intJ = Integer.parseInt(j);
            List<SemanticDescriptorVector> list = Utilities.getAllVectors(Utilities.populateMapFromFilename(filename));

            String function = "cosinesimilarity";
            if (cmd.hasOption("m")) {
                String mOptionValue = cmd.getOptionValue("m");

                if (mOptionValue.equals("euc")) {
                    function = "euclideandistance";
                } else if (mOptionValue.equals("eucnorm")) {
                    function = "euclideannormalizeddistance";
                }
            }

            SemanticDescriptorVector foundSDV = null;

            for (SemanticDescriptorVector sdv : list) {
                if (sdv.getWord().equalsIgnoreCase(word)) {
                    foundSDV = sdv;
                    break;
                }

            }
            if (foundSDV != null && foundSDV.getWord().equals(word)) {

                Queue<SimilarityVector> queue = Utilities.getTopJSimilarWords(list, foundSDV, intJ, function);
                if (queue.size() < intJ) {
                    System.out.println("Cannot compute " + intJ + " similar words for " + word);
                } else {
                    System.out.print("[");
                    for (SimilarityVector sv : queue) {
                        System.out.print("Pair{" + sv.getWord() + "," + sv.getSimilarity() + "},");
                    }
                    System.out.print("]");
                }
            } else {
                System.out.println("Word not found!");

            }
        }
        if (cmd.hasOption("k")) {
            
            String tOptionValue = cmd.getOptionValue("k");
            String stK = tOptionValue.substring(0, tOptionValue.indexOf(","));
            String stJ = tOptionValue.substring(tOptionValue.indexOf(",") + 1, tOptionValue.length());
            int intK = Integer.parseInt(stK);
            int intJ = Integer.parseInt(stJ);
            //String stI = tOptionValue.substring(tOptionValue.indexOf(",",) + 1, tOptionValue.length());
            List<SemanticDescriptorVector> list = Utilities.getAllVectors(Utilities.populateMapFromFilename(filename));
            List<Cluster> clusters = Utilities.getKClusters(list, intK, intJ);
            Iterator<Cluster> clusIter = clusters.iterator();
            int i = 0;
            while (clusIter.hasNext()) {
                System.out.println("Cluster " + i++);
                System.out.println(clusIter.next().toString());
            }

        }

        if (cmd.hasOption("j")) {
            
            String tOptionValue = cmd.getOptionValue("j");
            //String st[] = 
            String stK = tOptionValue.substring(0, tOptionValue.indexOf(","));
            String stKRest = tOptionValue.substring(tOptionValue.indexOf(",") + 1, tOptionValue.length());
            String stJ = stKRest.substring(0, stKRest.indexOf(","));
            String stI = stKRest.substring(stKRest.indexOf(",") + 1, stKRest.length());
            int intK = Integer.parseInt(stK);
            int intJ = Integer.parseInt(stJ);
            int intI = Integer.parseInt(stI);
            List<SemanticDescriptorVector> list = Utilities.getAllVectors(Utilities.populateMapFromFilename(filename));
            List<Cluster> clusters = Utilities.getKClusters(list, intK, intJ);
            List<Queue<SimilarityVector>> queueList = Utilities.getTopJFromKClusters(clusters, intK, intI);

            int i = 0;
            for (Queue<SimilarityVector> vectorQueue : queueList) {
                System.out.println("Cluster " + i++);
                for (SimilarityVector vector : vectorQueue) {
                    System.out.println(vector.getWord()+"="+vector.getSimilarity());
                }
            }
            
            
        }

    }
}
