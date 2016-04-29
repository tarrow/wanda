package org.xmlcml.wanda;

import org.xmlcml.cmine.files.CProject;

import java.util.Set;

/**
 * Created by tom on 27/04/16.
 */
public class FrequencySummariser implements Summariser {

    private String name = "frequency";

    public String getName() {
        return name;
    }

    public void summarise(CProject CProject) {

    }

    public Set<String> getSummarisablePlugins() {
        return null;
    }
}
