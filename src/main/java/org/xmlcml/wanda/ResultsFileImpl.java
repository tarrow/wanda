package org.xmlcml.wanda;

import nu.xom.Document;
import org.xmlcml.cmine.files.ResultsElement;
import org.xmlcml.cmine.util.XMLUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Thomas Arrow on 29/04/16.
 */
public class ResultsFileImpl implements ResultsFile {

    private ResultsElement resultsElements = null;
    private String pluginName = null;
    private File file;
    private String ctreeName = null;

    public ResultsFileImpl(File infile) {
        this.file = infile;
    }

    public File getFile() { return file; }

    public String getCTreeName() throws FileNotFoundException {
        if (ctreeName==null) {
            setCTreeName();
        }
        return ctreeName;
    }

    public void setCTreeName() throws FileNotFoundException {
        ctreeName = file.getParentFile().getParentFile().getParentFile().getParentFile().getName();
        if ( ctreeName == null ) {
            throw new FileNotFoundException();
        }
    }

    public void setResultsElements () {
        Document document = XMLUtils.parseWithoutDTD(file);
        resultsElements = ResultsElement.createResultsElement(document.getRootElement());
    }

    public String getPluginName() {
        if (pluginName == null) {
            setPluginName();
        }
        return pluginName;
    }

    public void setPluginName() {
        pluginName = file.getParentFile().getParentFile().getName(); // the directory above the dir results.xml is in
    }

    public ResultsElement getResultsElement() {
        if (resultsElements == null) {
            setResultsElements();
        }
        return resultsElements;
    }

}
