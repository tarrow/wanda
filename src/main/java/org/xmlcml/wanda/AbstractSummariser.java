package org.xmlcml.wanda;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.cmine.files.CTreeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by tom on 06/05/16.
 */
public abstract class AbstractSummariser implements Summariser {

    private static Set<String> SUMMARISABLE_PLUGINS;

    protected String SummaryFolder = "summary/";

    /**
     * Returns the fileoutputsteams where the summary of each plugin should go.
     * It is made from the CProject object which gives the top level folder as well as
     * the getPluginName and getName methods which your concrete class should provide.
     *
     * @param CProject
     * @return A map of plugin (identified by string) to the file its summary should be written to.
     * @throws FileNotFoundException
     */
    protected Map<String, FileOutputStream> generatePluginOutputFileMap(CProject CProject, String extension) throws FileNotFoundException {
        String name = getName();
        Map<String, FileOutputStream> pluginOutputFileMap = new HashMap<String, FileOutputStream>();
        for ( String pluginName : getSummarisablePlugins() ) {
            File outfile =  new File(CProject.getDirectory(), SummaryFolder+name+"/"+pluginName+"/"+name+"."+extension);
            outfile.getParentFile().mkdirs();
            pluginOutputFileMap.put(pluginName, new FileOutputStream(outfile));
        }
        return pluginOutputFileMap;
    }

    protected Map<String, FileOutputStream> generatePluginOutputFileMap(CProject CProject) throws FileNotFoundException {
        return generatePluginOutputFileMap(CProject, "xml");
    }

        /**
         * Returns a collection of all the files named results.xml that are contained within
         * any CTree in the CProject given.
         *
         * @param cproject
         * @return File objects for results.xml files in CTrees
         */
    protected Collection<File> ResultsFileList(CProject cproject) {
        CTreeList cTreeList = cproject.getCTreeList();
        WildcardFileFilter fileFilter = new WildcardFileFilter("results.xml");
        ArrayList<File> resultsFiles = new ArrayList<File>();
        for (CTree ctree : cTreeList) {
            List<File> resultsFilesChunk = new ArrayList(FileUtils.listFiles(ctree.getDirectory(), fileFilter, TrueFileFilter.INSTANCE));
            resultsFiles.addAll(resultsFilesChunk);
        }
        return resultsFiles;
    }


}
