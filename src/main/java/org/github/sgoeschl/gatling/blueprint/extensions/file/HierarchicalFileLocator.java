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

package org.github.sgoeschl.gatling.blueprint.extensions.file;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Determine the list of matching files while walking a directory tree.
 */
public class HierarchicalFileLocator {

    public static List<File> locateFile(File rootDirectory, String[] pathElements, String fileName) {
        final File leafDirectory = getLeafDirectory(rootDirectory, pathElements);
        return locateFile(rootDirectory, leafDirectory, fileName, new ArrayList<>());
    }

    private static List<File> locateFile(File rootDirectory, File currentDirectory, String fileName, List<File> files) {
        if (stopRecursingIntoParentDirectory(rootDirectory, currentDirectory)) {
            return files;
        }

        final File file = new File(currentDirectory, fileName);

        if (file.exists()) {
            files.add(file);
        }

        return locateFile(rootDirectory, currentDirectory.getParentFile(), fileName, files);
    }

    private static File getLeafDirectory(File rootDirectory, String[] pathElements) {
        return Paths.get(rootDirectory.getAbsolutePath(), pathElements).toFile();
    }

    private static boolean stopRecursingIntoParentDirectory(File rootDirectory, File currentDirectory) {
        return rootDirectory.getAbsolutePath().length() > currentDirectory.getAbsolutePath().length();
    }

}
