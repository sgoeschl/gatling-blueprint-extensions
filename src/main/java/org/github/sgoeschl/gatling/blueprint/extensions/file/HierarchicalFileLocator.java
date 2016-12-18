package org.github.sgoeschl.gatling.blueprint.extensions.file;

import org.github.sgoeschl.gatling.blueprint.extensions.SimulationCoordinates;
import org.github.sgoeschl.gatling.blueprint.extensions.utils.Validate;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HierarchicalFileLocator {

    public static List<File> locateFile(String rootDirectoryName, SimulationCoordinates simulationCoordinates, String fileName) {
        return locateFile(new File(rootDirectoryName), simulationCoordinates, fileName);
    }

    public static List<File> locateFile(File rootDirectory, SimulationCoordinates simulationCoordinates, String fileName) {
        Validate.notNull(rootDirectory, "rootDirectory");
        Validate.notNull(simulationCoordinates, "simulationCoordinates");
        Validate.notEmpty(fileName, "fileName");
        Validate.isTrue(rootDirectory.exists(), rootDirectory.getAbsolutePath() + " does not exist");

        final String[] pathElements = getPathElements(simulationCoordinates);
        final File leafDirectory = getLeafDirectory(rootDirectory, pathElements);

        return locateFile(rootDirectory, leafDirectory, fileName, new ArrayList<>());
    }

    private static List<File> locateFile(File rootDirectory, File currentDirectory, String fileName, List<File> files) {
        if (stopRecursingIntoParentDirectory(rootDirectory, currentDirectory)) {
            return files;
        }

        File file = new File(currentDirectory, fileName);

        if (file.exists()) {
            files.add(file);
        }

        return locateFile(rootDirectory, currentDirectory.getParentFile(), fileName, files);
    }

    private static File getLeafDirectory(File rootDirectory, String[] pathElements) {
        return Paths.get(rootDirectory.getAbsolutePath(), pathElements).toFile();
    }

    private static boolean stopRecursingIntoParentDirectory(File rootDirectory, File currentDirectory) {
        return rootDirectory.getParentFile().getAbsolutePath().length() >= currentDirectory.getAbsolutePath().length();
    }

    private static String[] getPathElements(SimulationCoordinates simulationCoordinates) {
        return new String[] {
                simulationCoordinates.getTenant(),
                simulationCoordinates.getSite(),
                simulationCoordinates.getApplication(),
                simulationCoordinates.getScope()
        };
    }

}
