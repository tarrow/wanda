package org.xmlcml.wanda;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.cmine.files.ResultElement;
import org.xmlcml.cmine.files.ResultsElement;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by tom on 27/04/16.
 */
public class FrequencySummariser extends AbstractSummariser {

    private static Set<String> SUMMARISABLE_PLUGINS = Sets.newHashSet("gene", "sequence", "species");

    private String name = "frequency";

    public String getName() {
        return name;
    }

    public void summarise(CProject cproject) throws IOException {
        // Make a map between the string names of the plugins and the files to write
        // their frequencies in.
        Map<String, FileOutputStream> pluginOutputFileMap = generatePluginOutputFileMap(cproject, "json");

        // Make a map to write all frequency summaries for each results file in



        Map<ResultsFile,WordCountVector> resultsFilesWordCounts = new HashMap<ResultsFile, WordCountVector>();
        // for each file build a full WordCountVector
        Collection<File> resultsFiles = ResultsFileList(cproject);
        for (File file : resultsFiles) {

            ResultsFile resultsFile = new ResultsFileImpl(file);
            ResultsElement results = resultsFile.getResultsElement();
            for( ResultElement result : results.getOrCreateResultElementList() ) {
                String exactString = result.getExact();
                if (resultsFilesWordCounts.containsKey(resultsFile)) {
                    resultsFilesWordCounts.get(resultsFile).addString(exactString);
                }
                else {
                    WordCountVector wordCountVector = new WordCountVector();
                    wordCountVector.addString(exactString);
                    resultsFilesWordCounts.put(resultsFile, wordCountVector);
                }
            }
        }

        //The number of documents that contain each term once or more.
        WordCountVector booleanDocumentFrequency = new WordCountVector();
        for (Entry<ResultsFile, WordCountVector> entry : resultsFilesWordCounts.entrySet()) {
            Set<String> words = entry.getValue().getWordSet();
            for (String word : words)
            booleanDocumentFrequency.addString(word);
        }

        //Write the per plugin frequencies for whole CProject
        //TODO Merge duplicate entries that we put in here by not adding WordVectors
        Gson gson = new Gson();

        Map<String, WordCountVector> wordFrequencyByPlugin = new HashMap<String, WordCountVector>();

        for (Entry<ResultsFile, WordCountVector> entry : resultsFilesWordCounts.entrySet()) {
            if (wordFrequencyByPlugin.containsKey(entry.getKey().getPluginName())) {
                wordFrequencyByPlugin.get(entry.getKey().getPluginName()).addWordCountVector(entry.getValue());
            }
            else { wordFrequencyByPlugin.put(entry.getKey().getPluginName(),entry.getValue()); }
        }

        for (Map.Entry<String, WordCountVector> entry : wordFrequencyByPlugin.entrySet()) {
            Map<String, Integer> aMap = entry.getValue().getMap();
            String json = gson.toJson(aMap);
            String pluginName = entry.getKey();
            if (pluginOutputFileMap.containsKey(pluginName)) {
                pluginOutputFileMap.get(pluginName).write(json.getBytes());
            }
        }

        //Write the per document frequencies and TF-IDF for whole CProject
        for (Entry<ResultsFile, WordCountVector> entry : resultsFilesWordCounts.entrySet()) {
            File outfile = constructJSONOutputFileForIndividualPapers(cproject, entry.getKey());
            Map<String, Object> map = new HashMap<String, Object>();
            for (Map.Entry<String, Integer> wordMagnitude : entry.getValue().getMap().entrySet()) {
                Integer freq = wordMagnitude.getValue();
                Integer docFreq = booleanDocumentFrequency.getCount(wordMagnitude.getKey());
                map.put("word", wordMagnitude.getKey());
                map.put("freq", freq);
                map.put("tf-idf", (float)freq / docFreq);
                FileWriter writer = new FileWriter(outfile, true);
                gson.toJson(map, writer);
                writer.close();
            }
        }


    }

    private File constructJSONOutputFileForIndividualPapers(CProject cproject, ResultsFile resultsFile) throws FileNotFoundException {
        File outfile = new File(cproject.getDirectory(), SummaryFolder+name+"/IndividualPapers/"+resultsFile.getCTreeName()+"/"+name+".json");
        outfile.getParentFile().mkdirs();
        return outfile;
    }

    /** Tom to remove by end of 9/5/16
    protected Map<String, FileOutputStream> generateIndividualPaperOutputFileMap(CProject cproject, String extension) throws FileNotFoundException {
        String name = getName();
        Map<String, FileOutputStream> individualPaperOutputFileMap = new HashMap<String, FileOutputStream>();
        for ( String pluginName : getSummarisablePlugins() ) {
            File outfile =  new File(cproject.getDirectory(), SummaryFolder+name+"/IndividualPapers/"+"/"+name+"."+extension);
            outfile.getParentFile().mkdirs();
            individualPaperOutputFileMap.put(pluginName, new FileOutputStream(outfile));
        }
        return individualPaperOutputFileMap;
    }
     **/

    public Set<String> getSummarisablePlugins() {
        return SUMMARISABLE_PLUGINS;
    }
}
