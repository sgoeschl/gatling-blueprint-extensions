package org.github.sgoeschl.gatling.blueprint.extensions.file;

import org.github.sgoeschl.gatling.blueprint.extensions.SimulationCoordinates;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.github.sgoeschl.gatling.blueprint.extensions.file.HierarchicalFileLocator.locateFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HierarchicalFileLocatorTest {

    private final static File ROOT_DIRECTORY = new File("./src/test/files");

    private final SimulationCoordinates coordinates = new SimulationCoordinates("application", "tenant", "site", "scope");

    @Test
    public void shouldLocateExistingFiles() {
        assertEquals(5, locateFile(ROOT_DIRECTORY, coordinates, "foo.csv").size());
        assertEquals(1, locateFile(ROOT_DIRECTORY, coordinates, "bar.csv").size());
    }

    @Test
    public void shouldReturnEmptyListForNonExistingFile() {
        assertTrue(locateFile(ROOT_DIRECTORY, coordinates, "unknown.csv").isEmpty());
    }

    @Test
    public void shouldLocateInAscendingOrder() {
        final List<File> files = locateFile(ROOT_DIRECTORY, coordinates, "foo.csv");

        assertTrue(files.get(0).getAbsolutePath().endsWith("scope/foo.csv"));
        assertTrue(files.get(1).getAbsolutePath().endsWith("application/foo.csv"));
        assertTrue(files.get(2).getAbsolutePath().endsWith("site/foo.csv"));
        assertTrue(files.get(3).getAbsolutePath().endsWith("tenant/foo.csv"));
        assertTrue(files.get(4).getAbsolutePath().contains("files/foo.csv"));
    }


}
