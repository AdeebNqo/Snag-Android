package co.snagapp.android.worker.impl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import co.snagapp.android.worker.IClassifier;

/**
 * Machine-learning spam detector using Bayesian Algorithm
 *
 * @author raju rama krishna
 * http://javatroops.blogspot.co.nz
 *
 * Updated by zola on 2015/07/17.
 */
public class SpamClassifier implements IClassifier {

    int spamCount = 0;
    int hamCount = 0;
    Map<String, Node> map = new HashMap<String, Node>();
    List<String> commonWords = new ArrayList<String>();

    @Override
    public boolean isSpam(String sms) {
        return detect(sms);
    }

    @Override
    public void addSpamSMS(String from, String sms) {

    }

    @Override
    public void train(String from, String sms) {

    }

    @Override
    public Collection getLikelySpamMessages() {
        return null;
    }

    public void loadCachedData() throws Exception {
        BufferedReader br = new BufferedReader( new FileReader( new File("C:\\Apps\\common-words.txt")));
        String line = br.readLine();
        while(line != null) {
            commonWords.add(line);
            line = br.readLine();
        }

        br = new BufferedReader( new FileReader( new File("C:\\Apps\\spam.txt")));
        line = br.readLine();
        while(line != null) {
            line = line.toLowerCase();
            String[] words = line.split("\\s+");
            for( String s: words ) {
                if(s.length() > 3 && !commonWords.contains(s)) {
                    spamCount++;
                    if( map.containsKey(s)) {
                        map.get(s).spamCount++;
                    } else {
                        map.put(s, new Node( 1, 0));
                    }
                }
            }
            line = br.readLine();
        }

        br = new BufferedReader( new FileReader( new File("C:\\Apps\\ham.txt")));
        line = br.readLine();
        while(line != null) {
            line = line.toLowerCase();
            String[] words = line.split(" ");
            for( String s: words ) {
                if(s.length() > 3 && !commonWords.contains(s)) {
                    hamCount++;
                    if( map.containsKey(s)) {
                        map.get(s).hamCount++;
                    } else {
                        map.put(s, new Node( 0, 1));
                    }
                }
            }
            line = br.readLine();
        }


        Set<String> keys = map.keySet();
        for( String key: keys ) {
            Node node = map.get(key);
            double res = ((node.spamCount)/(double)(spamCount))/(double)(((node.spamCount)/(double)(spamCount)) + (node.hamCount)/(double)(hamCount));
            node.probability = res;
        }

        br.close();
    }

    public boolean detect( String s ) {
        boolean result = false;
        s = s.toLowerCase();
        String[] sArr = s.split("\\s+");
        TreeMap<Double, List<Double>> interestMap = new TreeMap<>(Collections.reverseOrder());
        for( String x: sArr ) {
            if(x.length()> 3 && !commonWords.contains(x)) {
                double i = 0.5;
                double p = 0.5;
                if(map.containsKey(x)) {
                    p = map.get(x).probability;
                }
                i = Math.abs(i - p);
                if( !interestMap.containsKey(i) ) {
                    List<Double> values = new ArrayList<>();
                    values.add(p);
                    interestMap.put(i, values);
                } else {
                    interestMap.get(i).add(p);
                }
            }
        }

        List<Double> probabilities = new ArrayList<>();
        int count = 0;
        Set<Double> set = interestMap.keySet();
        for( Double d: set ) {
            List<Double> list = interestMap.get(d);
            for(Double x: list) {
                count++;
                probabilities.add(x);
                if(count == 15) {
                    break;
                }
            }
            if(count == 15) {
                break;
            }
        }

        double res = 1;
        double numerator = 1;
        double denominator = 1;
        for( Double d: probabilities ) {
            numerator = numerator * d;
            denominator = denominator * (1- d);
        }
        res = numerator/(numerator +denominator);
        if(res >= 0.9) {
            result = true;
        }

        return result;
    }

    public void moveToSpam( String s ) {
        s = s.toLowerCase();
        String[] sArr = s.split("\\s+");
        for( String x: sArr ) {
            if(x.length()> 3 && !commonWords.contains(x)) {
                spamCount++;
                if( map.containsKey(x)) {
                    map.get(x).spamCount++;
                } else {
                    map.put(x, new Node( 1, 0));
                }
            }
        }

        Set<String> keys = map.keySet();
        for( String key: keys ) {
            Node node = map.get(key);
            double res = ((node.spamCount)/(double)(spamCount))/(((node.spamCount)/(double)(spamCount)) + (node.hamCount)/(double)(hamCount));
            node.probability = res;
        }
    }

    class Node {


        int spamCount;
        int hamCount;
        double probability;

        public Node( int spamCount, int hamCount ) {
            this.spamCount = spamCount;
            this.hamCount = hamCount;
        }

        public String toString() {
            return String.valueOf(probability);
        }
    }
}
