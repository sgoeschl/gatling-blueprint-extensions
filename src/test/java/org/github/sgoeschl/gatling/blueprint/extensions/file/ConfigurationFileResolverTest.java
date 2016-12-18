package org.github.sgoeschl.gatling.blueprint.extensions.file;

import org.github.sgoeschl.gatling.blueprint.extensions.SimulationCoordinates;
import org.junit.Test;

import java.io.File;

import static org.github.sgoeschl.gatling.blueprint.extensions.file.ConfigurationFileResolver.resolveFile;
import static org.junit.Assert.assertTrue;

public class ConfigurationFileResolverTest {

    private final static String ROOT_DIRECTORY_NAME = "./src/test/files";

    private final SimulationCoordinates simulationCoordinates = new SimulationCoordinates("application", "tenant", "site", "scope");

    @Test
    public void shouldFindConfigurationFile() {
        final File file = resolveFile(ROOT_DIRECTORY_NAME, simulationCoordinates, "foo.csv");

        assertTrue(file.getAbsolutePath().endsWith("scope/foo.csv"));
    }
}
