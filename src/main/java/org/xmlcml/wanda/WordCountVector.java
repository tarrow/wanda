package org.xmlcml.wanda;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Thomas Arrow on 06/05/16.
 *
 * A simple map of word name and a frequency
 *
 */
public class WordCountVector {

    private Map<String, Integer> wordCountVector = new HashMap<String, Integer>();

    public void WordCountVector() {

    }

    public int getCount(String string) {
        if (wordCountVector.containsKey(string)) {
            return wordCountVector.get(string);
        }
        return 0;
    }

    public void addString(String string) {
        if (string == null) {return;}
        if (wordCountVector.containsKey(string)) {
            wordCountVector.put(string, wordCountVector.get(string)+1);
        }
        else { wordCountVector.put(string, 1); }
    }

    public Set<String> getWordSet() {
        return wordCountVector.keySet();
    }

    public Map<String, Integer> getMap() { return wordCountVector; }

}
