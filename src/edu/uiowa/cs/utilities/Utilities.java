/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.utilities;

import edu.uiowa.cs.entities.Cluster;
import edu.uiowa.cs.entities.ClusterMember;
import edu.uiowa.cs.entities.SimilarityVector;
import edu.uiowa.cs.entities.SimilarityVectorComparator;
import edu.uiowa.cs.entities.SemanticDescriptorVector;
import edu.uiowa.cs.entities.Sentence;
import edu.uiowa.cs.entities.Word;
import edu.uiowa.cs.iterators.Apply;
import edu.uiowa.cs.iterators.FlatApply;
import edu.uiowa.cs.iterators.RemoveStopWord;
import edu.uiowa.cs.iterators.SplitBy;
import edu.uiowa.cs.iterators.StemWord;
import edu.uiowa.cs.iterators.ToLowerCase;
import edu.uiowa.cs.readers.SentenceReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Utilities {

    public static Set populateSet(Iterator<Word> iter) {
        Set<Word> set = new HashSet<>();
        while (iter.hasNext()) {
            Word word = iter.next();
            if (!set.contains(word)) {
                set.add(word);
            }
        }
        return set;
    }

    public static HashMap<String, ArrayList<Integer>> populateMap(Iterator<Word> iter) {
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();
        while (iter.hasNext()) {
            Word w = iter.next();
            ArrayList<Integer> intArray = new ArrayList<>();
            if (!w.getWord_content().trim().equals("")) {
                if (map.containsKey(w.getWord_content())) {
                    intArray = map.get(w.getWord_content());
                    intArray.add(w.getSentence_count());
                } else {
                    intArray.add(w.getSentence_count());
                }

                map.put(w.getWord_content().trim().replace("\n", ""), intArray);
            }

        }
        return map;
    }

    public static SemanticDescriptorVector populateVector(Map<String, ArrayList<Integer>> map, String key, boolean sparseAllowed) {
        if (key != null && !key.equalsIgnoreCase("")) {
            ArrayList<Integer> intArray = map.get(key);
            String word = key;
            HashMap<String, Double> mapVector = new HashMap<>();

            for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()) {

                /*this condition prevents creating a vector element for the same string as word
                
                Ex: If the condition was not here the vector for "man would be"
                man=[liver=1, spiteful=1, man=3]
                
                With the condition
                man=[liver=1, spiteful=1]
                 */
                if (!entry.getKey().equalsIgnoreCase(word)) {
                    ArrayList<Integer> intArray2 = entry.getValue();
                    double count = 0;

                    boolean matchingSentence_CountFound = false;
                    for (Integer i : intArray) {

                        if (intArray2.contains(i)) {
                            matchingSentence_CountFound = true;
                            String word2 = entry.getKey();
                            count = 1;
                            if (mapVector.containsKey(word2)) {
                                count = mapVector.get(word2) + count;
                            }
                            mapVector.put(entry.getKey(), count);

                        }
                    }
                    if (matchingSentence_CountFound == false) {
                        mapVector.put(entry.getKey(), 0.0);
                    }

                }

            }
            SemanticDescriptorVector vector = new SemanticDescriptorVector(word, mapVector);

            return vector;

        } else {
            return null;
        }
    }
    //takes two vectors and returns the cosine similarity of the two vectors

    public static Double getCosineSimilarity(SemanticDescriptorVector u, SemanticDescriptorVector v) {

        Double numerator = 0.0;
        Double denominator = 0.0;
        Double sum_u_squared = 0.0;
        Double sum_v_squared = 0.0;

        Map<String, Double> uMap = u.getMapVector();
        Map<String, Double> vMap = v.getMapVector();

        //Calculate u_suared
        for (Entry<String, Double> vEntry : vMap.entrySet()) {
            sum_v_squared += vEntry.getValue() * vEntry.getValue();
        }

        for (Entry<String, Double> uEntry : uMap.entrySet()) {
            sum_u_squared += uEntry.getValue() * uEntry.getValue();

            if (v.getMapVector().containsKey(uEntry.getKey())) {
                //Initialize ui and vi
                Double ui = uEntry.getValue();
                Double vi = v.getMapVector().get(uEntry.getKey());

                //Calculate numerator from multiplication
                Double uivi = ui * vi;
                numerator += uivi;

            }
        }

        //Multiply sum_u_squared and sum_v_squared and do the division
        denominator = Math.sqrt(sum_u_squared * sum_v_squared);
        if (numerator == 0.0 || denominator == 0.0) {
            return 0.0;
        } else {
            Double ret = new Double((double) numerator / (double) denominator);
            return ret;
        }

    }

    public static Map<String, ArrayList<Integer>> populateMapFromFilename(String filename) throws IOException {
        Iterator<Sentence> lines = new SentenceReader(filename);
        /*Iterator<Sentence> lines2 = new SentenceReader(filename);
        while(lines2.hasNext()){
            System.out.println(""+lines2.next().getContent());
        }
         */

        Iterator<Word> words = new FlatApply<>(new SplitBy(",|:|;|\"|--| |'"), lines);
        Iterator<Word> lowercaseWords = new Apply<>(new ToLowerCase(), words);
        Iterator<Word> stop = new Apply(new RemoveStopWord(), lowercaseWords);
        Iterator<Word> stem = new Apply(new StemWord(), stop);

        //set is used to remove duplication of the same word that has occured in multiple places in the same sentence, as that maked no sense for the semantic descriptor vector
        Set<Word> set = Utilities.populateSet(stem);

        //Iterator is created from set    
        Iterator<Word> iter = set.iterator();

        //set above contains two entries namely "man,1" and "man,2" ,if the word "man" appeared in first and second sentences
        //map below makes unique keys so that entries such as "man,1" and "man,2" will be included in a single entry as "man,{1,2}"
        Map<String, ArrayList<Integer>> map = Utilities.populateMap(iter);
        return map;
    }

    public static Queue<SimilarityVector> getTopJSimilarWords(List<SemanticDescriptorVector> vectors, SemanticDescriptorVector sdv, Integer j, String function) throws IOException {
        //Map<String, ArrayList<Integer>> map = Utilities.populateMapFromFilename(filename);
        Queue<SimilarityVector> queue = new PriorityQueue<>(j, new SimilarityVectorComparator());

        //SemanticDescriptorVector u = Utilities.populateVector(map, sdv.getWord(), true);
        //System.out.println("" + u.toString());
        //System.out.println("" + u.toString());
        for (SemanticDescriptorVector u : vectors) {

            if (!u.getWord().equals(sdv.getWord())) {
                //SemanticDescriptorVector v = Utilities.populateVector(map, entry.getKey(), true);
                //System.out.println("" + v.toString());
                SimilarityVector sv = null;
                if (function.equalsIgnoreCase("cosinesimilarity")) {
                    sv = new SimilarityVector(u.getWord(), Utilities.getCosineSimilarity(u, sdv));
                } else if (function.equalsIgnoreCase("euclideandistance")) {
                    sv = new SimilarityVector(u.getWord(), Utilities.getEuclideanDistanceSimilarity(u, sdv));
                } else if (function.equalsIgnoreCase("euclideannormalizeddistance")) {
                    sv = new SimilarityVector(u.getWord(), Utilities.getEuclideanNormalizedDistanceSimilarity(u, sdv));
                }
                queue.add(sv);
            }

        }
        if (queue.size() > j) {
            while (queue.size() > j) {
                queue.poll();
            }
            return queue;
        }else if(queue.size() <= j){
            System.out.println("Cannot compute "+j+" similar words for "+sdv.getWord());
        }
        return null;

    }

    public static List<SemanticDescriptorVector> getAllVectors(Map<String, ArrayList<Integer>> map) {
        List<SemanticDescriptorVector> list = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
            SemanticDescriptorVector sdv = Utilities.populateVector(map, entry.getKey(), true);
            list.add(sdv);
            //System.out.println("" + sdv.getWord() + "" + sdv.getMapVector());
        }

        return list;

    }

    public static Double getEuclideanDistanceSimilarity(SemanticDescriptorVector u, SemanticDescriptorVector v) {

        Double sqrtFinal = 0.0;
        Double sum_u_min_v_squared = 0.0;
        Double u_min_v_squared = 0.0;
        Double ui = 0.0;
        Double vi = 0.0;
        //Integer sum_v_squared = 0;

        Map<String, Double> uMap = u.getMapVector();
        Map<String, Double> vMap = v.getMapVector();

        for (Entry<String, Double> uEntry : uMap.entrySet()) {
            ui = uEntry.getValue();

            if (vMap.containsKey(uEntry.getKey())) {
                //Initialize vi
                vi = vMap.get(uEntry.getKey());

            } else {
                vi = 0.0;
            }

            u_min_v_squared = (ui - vi) * (ui - vi);
            sum_u_min_v_squared += u_min_v_squared;
        }

        //Calculating square root
        sqrtFinal = Math.sqrt(sum_u_min_v_squared);

        //negating the final value
        return sqrtFinal * (-1);

    }

    public static Double getEuclideanNormalizedDistanceSimilarity(SemanticDescriptorVector u, SemanticDescriptorVector v) {

        Double sqrtFinal = 0.0;
        Double sum_u_squared = 0.0;
        Double sqrt_sum_u_squared = 0.0;
        Double sum_v_squared = 0.0;
        Double sqrt_sum_v_squared = 0.0;

        Double u_fraction = 0.0;
        Double v_fraction = 0.0;
        Double sum_u_min_v_squared = 0.0;
        Double u_min_v_squared = 0.0;
        Double ui = 0.0;
        Double vi = 0.0;

        Map<String, Double> uMap = u.getMapVector();
        Map<String, Double> vMap = v.getMapVector();

        //Calculate u_squared
        for (Entry<String, Double> uEntry : uMap.entrySet()) {
            sum_u_squared += uEntry.getValue() * uEntry.getValue();
        }

        //Calculate sqrt of sum_v_squared
        sqrt_sum_u_squared = Math.sqrt(sum_u_squared);

        //Calculate v_squared
        for (Entry<String, Double> vEntry : vMap.entrySet()) {
            sum_v_squared += vEntry.getValue() * vEntry.getValue();
        }

        //Calculate sqrt of sum_v_squared
        sqrt_sum_v_squared = Math.sqrt(sum_v_squared);

        for (Entry<String, Double> uEntry : uMap.entrySet()) {
            ui = uEntry.getValue();
            u_fraction = ui / sqrt_sum_u_squared;

            if (vMap.containsKey(uEntry.getKey())) {
                vi = vMap.get(uEntry.getKey());
                v_fraction = vi / sqrt_sum_v_squared;

            } else {
                v_fraction = 0.0;
            }
            u_min_v_squared = (u_fraction - v_fraction) * (u_fraction - v_fraction);
            sum_u_min_v_squared += u_min_v_squared;
        }

        //Calculating square root
        sqrtFinal = Math.sqrt(sum_u_min_v_squared);

        //negating the final value
        return sqrtFinal * (-1);

    }

    public static List<Cluster> getKClusters(List<SemanticDescriptorVector> vectors, Integer k, Integer iter) throws IOException {
        //Map<String, ArrayList<Integer>> map = Utilities.populateMapFromFilename(filename);

        List<Cluster> clusters = new ArrayList<>();
        List<SemanticDescriptorVector> totalVectors = vectors;
        for (SemanticDescriptorVector s : totalVectors) {
            System.out.print("" + s.getWord() + ",");

        }
        System.out.println("");

        List<Integer> randoms = new ArrayList<>();

        for (int i = 0; i < k; i++) {

            Integer newRandomIndex = new Random().nextInt() % totalVectors.size();

            if (!randoms.contains(newRandomIndex) && newRandomIndex > 0) {
                randoms.add(newRandomIndex);

                SemanticDescriptorVector centroidSDV = totalVectors.get(newRandomIndex.intValue());
                clusters.add(i, new Cluster(centroidSDV));
            } else {
                i--;
            }

        }

        System.out.println("********Initial****************");
        int i = 0;
        for (Cluster cluster3 : clusters) {
            System.out.println("Cluster" + cluster3.toString() + " " + i++);
            System.out.println("centroid " + cluster3.getCentroid().getWord());
        }
        System.out.println("********Initial Over****************");

        for (int iterCount = 0; iterCount < iter; iterCount++) {

            List<Cluster> clusters2 = new ArrayList<>();
            clusters2.addAll(clusters);

            for (Cluster cluster : clusters) {
                cluster.setMembers(new ArrayList<ClusterMember>());
            }

            for (SemanticDescriptorVector sdv : totalVectors) {
                Double eucDistanceMin = 100000000.0;
                Cluster matchingCluster = new Cluster();

                ClusterMember member = new ClusterMember();

                for (Cluster cluster : clusters) {
                    SemanticDescriptorVector clusterCentroid = cluster.getCentroid();

                    Double eucDistanceNew = getEuclideanDistanceSimilarity(sdv, clusterCentroid);
                    if (eucDistanceMin == 0.0) {
                        eucDistanceMin = eucDistanceNew;

                    } else if (eucDistanceNew < eucDistanceMin) {
                        matchingCluster = cluster;
                        eucDistanceMin = eucDistanceNew;
                    }

                }
                member.setSdv(sdv);
                member.setSimilarity(eucDistanceMin);
                matchingCluster.addMember(member);

            }
            int clusterCount=0;
            for(Cluster cluster:clusters){
                Double sum_of_eudistanceToMean = 0.0;
                for(ClusterMember member:cluster.getMembers()){
                    sum_of_eudistanceToMean += member.getSimilarity();
                }
                System.out.println("Average Distance To Mean in Cluster "+clusterCount++ +" after Iteration "+iterCount+"="+(sum_of_eudistanceToMean/cluster.getMembers().size()));
            }

            //recalculating means for clusters
            int j = 0;
            for (Cluster cluster : clusters) {

                System.out.println("Cluster " + j++ + " =" + cluster.toString());
                Integer cluster_size = cluster.getMembers().size();

                //initially sets centroid vector values in the mean vector values
                HashMap<String, Double> meanVector = new HashMap<>();
                meanVector.put(cluster.getCentroid().getWord(), 1.0);
                for (Map.Entry<String, Double> vector : cluster.getCentroid().getMapVector().entrySet()) {
                    meanVector.put(vector.getKey(), vector.getValue().doubleValue());

                }

                //increment mean vector values by iterate through members' vectors
                for (ClusterMember member : cluster.getMembers()) {
                    for (Map.Entry<String, Double> memberVector : member.getSdv().getMapVector().entrySet()) {
                        Double current_sum_vector_i = meanVector.get(memberVector.getKey());
                        Double new_sum_vector_i = current_sum_vector_i + memberVector.getValue();
                        meanVector.put(memberVector.getKey(), new_sum_vector_i);
                    }
                }
                //calculate actual mean vector by dividing by cluster size
                for (Map.Entry<String, Double> meanVectorEntry : meanVector.entrySet()) {
                    meanVector.put(meanVectorEntry.getKey(), meanVectorEntry.getValue() / cluster_size);
                }

                SemanticDescriptorVector sdvCentroid = new SemanticDescriptorVector("", meanVector);

                cluster.setCentroid(sdvCentroid);
            }
        }
        return clusters;
    }

    public static List<Queue<SimilarityVector>> getTopJFromKClusters(List<Cluster> clusterList, Integer k, Integer j) throws IOException {
        List<Queue<SimilarityVector>> clusterTops = new ArrayList<>();
        for(Cluster cluster:clusterList){
            List<SemanticDescriptorVector> clusterMembers = getAllVectorsFromCluster(cluster);
                  
            Queue<SimilarityVector> queue = getTopJSimilarWords(clusterMembers, cluster.getCentroid(), j, "euclideandistance");
            clusterTops.add(queue);
            
        }
        
        return clusterTops;
   }
    
    public static List<SemanticDescriptorVector> getAllVectorsFromCluster(Cluster cluster){
        List<SemanticDescriptorVector> vectors = new ArrayList<>();
        vectors.add(cluster.getCentroid());
        for(ClusterMember member:cluster.getMembers()){
            vectors.add(member.getSdv());
        }
        return vectors;
    }

}
