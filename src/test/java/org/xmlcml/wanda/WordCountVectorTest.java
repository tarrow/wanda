package org.xmlcml.wanda;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by tom on 09/05/16.
 */
public class WordCountVectorTest {

    @Test
    public void WordCountConstructor_adds_HashMap_as_expected(){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("foo", 3);
        WordCountVector wordCountVector = new WordCountVector(map);
        int result = wordCountVector.getCount("foo");
        assertEquals(3, result);
    }

    @Test
    public void WordCountVectorShould_add_and_get_string() {
        WordCountVector wordCountVector = new WordCountVector();
        wordCountVector.addString("foo");
        assertEquals(1, wordCountVector.getCount("foo"));
    }

    @Test public void WordCountVectorShould_return_corect_wordset() {
        WordCountVector wordCountVector = new WordCountVector();
        wordCountVector.addString("foo");
        wordCountVector.addString("bar");
        Set<String> expected = Sets.newHashSet("foo", "bar");
        assertEquals(expected, wordCountVector.getWordSet());
    }

    @Test public void WordCountVectorShould_return_0_for_missing_string() {
        WordCountVector wordCountVector = new WordCountVector();
        int result = wordCountVector.getCount("foo");
        assertEquals(0, result);
    }

    @Test public void AddWordCountVector_should_combine_two_vectors() {
        WordCountVector vector1 = new WordCountVector();
        WordCountVector vector2 = new WordCountVector();
        vector1.addString("foo");
        vector2.addString("bar");
        vector1.addWordCountVector(vector2);
        Set<String> expected = Sets.newHashSet("foo", "bar");
        assertEquals(expected,vector1.getWordSet());
    }
}
