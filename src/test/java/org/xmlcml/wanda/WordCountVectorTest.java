package org.xmlcml.wanda;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by tom on 09/05/16.
 */
public class WordCountVectorTest {

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
}
