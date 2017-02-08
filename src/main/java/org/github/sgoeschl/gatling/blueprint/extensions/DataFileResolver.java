/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.github.sgoeschl.gatling.blueprint.extensions;

import org.github.sgoeschl.gatling.blueprint.extensions.utils.Validate;

import java.io.File;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.github.sgoeschl.gatling.blueprint.extensions.file.HierarchicalFileLocator.locateFile;

/**
 * Finds the "most specialized" data, i.e. with the greatest file depth assuming
 * that we don't merge data files.
 */
public class DataFileResolver {

    public static File resolveFile(File rootDirectory, SimulationCoordinates simulationCoordinates, String fileName) {
        return resolveFile(rootDirectory, simulationCoordinates.getPathElements(), fileName);
    }

    public static File resolveFile(File rootDirectory, String[] pathElements, String fileName) {
        Validate.notNull(rootDirectory, "rootDirectory");
        Validate.notNull(rootDirectory.exists(), "rootDirectory does not exist: " + rootDirectory);
        Validate.notNull(pathElements, "pathElements");
        Validate.notEmpty(fileName, "fileName");
        Validate.isTrue(rootDirectory.exists(), rootDirectory.getAbsolutePath() + " does not exist");

        final List<File> configurationFiles = locateFile(rootDirectory, pathElements, fileName);

        if (configurationFiles.isEmpty()) {
            handleNoConfigurationFileFound(rootDirectory, pathElements, fileName);
        }

        return configurationFiles.get(0);
    }

    private static void handleNoConfigurationFileFound(File rootDirectory, String[] pathElements, String configurationFileName) {
        final String path = stream(pathElements).map(String::toString).collect(joining("/"));
        throw new IllegalArgumentException(format(
                "No configuration file found: rootDirectoryName=%s, coordinates=%s, configurationFileName=%s",
                rootDirectory,
                path,
                configurationFileName));
    }

}
