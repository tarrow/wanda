package org.xmlcml.wanda;

import org.xmlcml.cmine.files.ResultsElement;

import java.io.File;

/**
 * Created by tom on 29/04/16.
 */
public interface ResultsFile {

    public String getPluginName();

    public ResultsElement getResultsElement();

}
