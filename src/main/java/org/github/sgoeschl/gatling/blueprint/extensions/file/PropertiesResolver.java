package org.github.sgoeschl.gatling.blueprint.extensions.file;

import org.github.sgoeschl.gatling.blueprint.extensions.SimulationCoordinates;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.github.sgoeschl.gatling.blueprint.extensions.file.HierarchicalFileLocator.locateFile;

public class PropertiesResolver {

    public static Properties resolveProperties(String rootDirectoryName, SimulationCoordinates simulationCoordinates, String propertyFileName) {
        final Properties result = new Properties();
        final List<File> propertyFiles = locateFile(rootDirectoryName, simulationCoordinates, propertyFileName);
        Collections.reverse(propertyFiles);
        propertyFiles.stream().forEach(propertyFile -> result.putAll(load(propertyFile)));
        return result;
    }

    public static Properties load(File propertyFile) {
        final Properties properties = new Properties();
        try (InputStream is = new FileInputStream(propertyFile)) {
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Unable to load : " + propertyFile, e);
        }
    }
}
