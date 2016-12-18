package org.github.sgoeschl.gatling.blueprint.extensions.file;

import org.github.sgoeschl.gatling.blueprint.extensions.SimulationCoordinates;
import org.junit.Test;

import java.util.Properties;

import static org.github.sgoeschl.gatling.blueprint.extensions.file.EnvironmentPropertiesResolver.resolveProperties;
import static org.junit.Assert.assertEquals;

public class EnvironmentPropertiesResolverTest {

    private final static String ROOT_DIRECTORY_NAME = "./src/test/files";

    private final SimulationCoordinates coordinates = new SimulationCoordinates("application", "tenant", "site", "scope");

    @Test
    public void shouldLocateInAscendingOrder() {
        final Properties properties = resolveProperties(ROOT_DIRECTORY_NAME, coordinates);

        assertEquals("scope", properties.getProperty("value"));
    }
}
