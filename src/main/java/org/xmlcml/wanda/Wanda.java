package org.xmlcml.wanda;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.cmine.files.CTreeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Wanda {

	public static void main(String[] args) throws ParseException, IOException {
		// parse arguments from command line - what to summarise (all plugins?)
		Options options = new Options();
		options.addOption("q", "project", true, "CProject");
		BasicParser parser = new BasicParser();
		CommandLine commandLine = parser.parse(options, args);
		String cProjectPath = commandLine.getOptionValue("project");
		if (cProjectPath == null) {
			System.out.println("No CProject Given: use -q or --project");
			System.exit(0);
		}
		System.out.println("Project Path: " + cProjectPath);
		// Open CProject as specified by command line
		CProject cProject = new CProject(new File(cProjectPath));

		new SnippetSummariser().summarise(cProject);
		// TODO: Write HTML tables

	}
	
}
