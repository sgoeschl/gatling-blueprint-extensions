package org.github.sgoeschl.gatling.blueprint.extensions.file;

import org.github.sgoeschl.gatling.blueprint.extensions.SimulationCoordinates;

import java.io.File;
import java.util.List;
import java.util.Properties;

import static org.github.sgoeschl.gatling.blueprint.extensions.file.HierarchicalFileLocator.locateFile;

/**
 * Finds the "most specialized" file, i.e. with the greatest file depth.
 */
public class ConfigurationFileResolver {

    public static File resolveFile(String rootDirectoryName, SimulationCoordinates simulationCoordinates, String configurationFileName) {
        final List<File> configurationFiles = locateFile(rootDirectoryName, simulationCoordinates, configurationFileName);
        return configurationFiles.isEmpty() ? null : configurationFiles.get(0);
    }

}
