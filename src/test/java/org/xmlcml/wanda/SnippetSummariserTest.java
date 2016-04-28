package org.xmlcml.wanda;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tom on 27/04/16.
 */
public class SnippetSummariserTest {

    @Test
    public void getName() {
        SnippetSummariser snippetSummariser = new SnippetSummariser();
        assertEquals("snippet", snippetSummariser.getName());

    }

}