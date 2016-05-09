package org.xmlcml.wanda;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.xmlcml.cmine.files.ResultsElement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Thomas Arrow on 29/04/16.
 */
public class ResultsFileImplTest {

    private ResultsFile resultsFile;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setup() throws IOException {

        File pluginFolder = temporaryFolder.newFolder("plugin");
        File optionFolder = new File(pluginFolder, "option");
        File resultsFilePath = new File(optionFolder, "results.xml");
        resultsFile = new ResultsFileImpl(resultsFilePath);
        optionFolder.mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(resultsFilePath);
        InputStream exampleResultsFileStream = getClass().getResourceAsStream("/results.xml");
        IOUtils.copy(exampleResultsFileStream, fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void getPluginNameTest_returns_the_right_pluginname() throws Exception {
        assertEquals("plugin", resultsFile.getPluginName());
    }

    @Test
    public void getResultsElement_returns_a_results_element() {
        ResultsElement result = resultsFile.getResultsElement();
    }

}