package org.xmlcml.wanda;

import com.google.common.collect.Sets;
import com.google.common.io.Files;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.Serializer;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.xmlcml.cmine.args.FileXPathSearcher;
import org.xmlcml.cmine.files.*;
import org.xmlcml.cmine.util.XMLUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by tom on 27/04/16.
 *
 * This creates summaries of results files that contain snippets.
 * It does this per plugin across the whole of the CProject and it
 * writes them into the summary/snippet/ directory
 *
 */
public class SnippetSummariser extends AbstractSummariser {

    private static Set<String> SUMMARISABLE_PLUGINS = Sets.newHashSet("gene", "sequence", "species");

    protected String SummaryFolder = "summary/";

    private static String name = "snippet";

    public SnippetSummariser() {

    }

    public Set getSummarisablePlugins() {
        return SUMMARISABLE_PLUGINS;
    }

    public String getName() {
        return name;
    }

    public void summarise(CProject cproject) throws IOException {

        // Make a map between the string names of the plugins and the files to write
        // their summaries in.
        Map<String, FileOutputStream> pluginOutputFileMap = generatePluginOutputFileMap(cproject);


        // Make a map of elements in which we store many sets of results indexed by plugin name
        Map<String, Element> pluginResultsListMap = new HashMap<String, Element>();
        for (String pluginName : SUMMARISABLE_PLUGINS) {
            pluginResultsListMap.put(pluginName, new Element("ProjectSnippets"));
        }

        // Find all results files in each CTree and add the contents of the results files
        // to PluginResultsListMap
        Collection<File> resultsFiles = ResultsFileList(cproject);
        for (File file : resultsFiles) {
            ResultsFileImpl resultsFile = new ResultsFileImpl(file);
            String pluginName = resultsFile.getPluginName();
            if (pluginResultsListMap.containsKey(pluginName)) {
                pluginResultsListMap.get(pluginName).appendChild(resultsFile.getResultsElement());
            }
        }


        // Write all of the results stores to the correct file
        for (Map.Entry<String, Element> entry : pluginResultsListMap.entrySet()) {
            Serializer serializer = new Serializer(pluginOutputFileMap.get(entry.getKey()), "UTF-8");
            Document document = new Document(entry.getValue());
            serializer.write(document);
        }
    }


}

