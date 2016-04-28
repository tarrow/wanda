package org.xmlcml.wanda;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.xmlcml.cmine.args.FileXPathSearcher;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.cmine.files.CTreeList;
import org.xmlcml.cmine.files.SnippetsTree;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * Created by tom on 27/04/16.
 */
public class SnippetSummariser implements Summariser {
    private static String name = "snippet";

    public String getName() {
        return name;
    }

    public void summarise(CProject CProject) {
        CTreeList cTreeList = CProject.getCTreeList();
        FileFilter fileFilter = new WildcardFileFilter("results/*/*/results.xml");
        for (CTree ctree : cTreeList) {
            File[] resultsFiles = ctree.getDirectory().listFiles(fileFilter);
            FileXPathSearcher fileXPathSearcher = new FileXPathSearcher(ctree, "file(**/species/genus/results.xml)search(//result)");
            fileXPathSearcher.search();
            SnippetsTree snippetsTree = fileXPathSearcher.getSnippetsTree();
            System.out.println(snippetsTree);
        }
    }
}
