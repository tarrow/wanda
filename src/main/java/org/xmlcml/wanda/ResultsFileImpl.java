package org.xmlcml.wanda;

import nu.xom.Document;
import org.xmlcml.cmine.files.ResultsElement;
import org.xmlcml.cmine.util.XMLUtils;

import java.io.File;

/**
 * Created by tom on 29/04/16.
 */
public class ResultsFileImpl implements ResultsFile {

    private ResultsElement resultsElements;
    private String pluginName;

    public ResultsFileImpl(File file) {
        Document document = XMLUtils.parseWithoutDTD(file);
        resultsElements = ResultsElement.createResultsElement(document.getRootElement());
        pluginName = file.getParentFile().getParentFile().getName(); // the directory above the dir results.xml is in
    }

    public String getPluginName() {
        return pluginName;
    }

    public ResultsElement getResultsElement() {
        return resultsElements;
    }
}
