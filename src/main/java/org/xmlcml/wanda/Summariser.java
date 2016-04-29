package org.xmlcml.wanda;

import com.sun.istack.internal.NotNull;
import org.xmlcml.cmine.files.CProject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 *
 * Created by tom on 27/04/16.
 */

public interface Summariser {

    /**
     * Returns the name of the Summariser.
     *
     * The Summariser should be named something
     * sort and simple and ideally all lowercase, no spaces etc.
     * This is because it will be used as a directory name
     *
     * @return name of summariser
     */
    @NotNull
    public String getName();

    /**
     *  Given a CProject the summariser will summarise results generated by
     *  ami and place them in the CProject under the summary subfolder
     *
     * @param CProject
     *
     */
    public void summarise(CProject CProject) throws IOException;

    /**
     * get a list of the plugins which this summariser can summarise.
     *
     * @return Set of strings of the plugins this summariser can summarise
     */
    public Set<String> getSummarisablePlugins();

    }
