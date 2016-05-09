package org.xmlcml.wanda;

import org.xmlcml.cmine.files.ResultsElement;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by tom on 29/04/16.
 */
public interface ResultsFile {

    public String getPluginName();

    public ResultsElement getResultsElement();

    public File getFile();

    public String getCTreeName() throws FileNotFoundException;
}
