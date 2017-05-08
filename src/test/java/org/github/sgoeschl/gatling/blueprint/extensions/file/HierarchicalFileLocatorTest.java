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

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.github.sgoeschl.gatling.blueprint.extensions.file.HierarchicalFileLocator.locateFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HierarchicalFileLocatorTest {

    private static final File ROOT_DIRECTORY = new File("./src/test/files");

    private final String[] pathElements = new String[] { "tenant", "site", "application", "scope" };

    @Test
    public void shouldLocateExistingFiles() {
        assertEquals(5, locateFile(ROOT_DIRECTORY, pathElements, "foo.csv").size());
        assertEquals(1, locateFile(ROOT_DIRECTORY, pathElements, "bar.csv").size());
    }

    @Test
    public void shouldReturnEmptyListForNonExistingFile() {
        assertTrue(locateFile(ROOT_DIRECTORY, pathElements, "unknown.csv").isEmpty());
    }

    @Test
    public void shouldLocateInAscendingOrder() {
        final List<File> files = locateFile(ROOT_DIRECTORY, pathElements, "foo.csv");

        assertTrue(files.get(0).getAbsolutePath().endsWith(path("scope", "foo.csv")));
        assertTrue(files.get(1).getAbsolutePath().endsWith(path("application", "foo.csv")));
        assertTrue(files.get(2).getAbsolutePath().endsWith(path("site", "foo.csv")));
        assertTrue(files.get(3).getAbsolutePath().endsWith(path("tenant", "foo.csv")));
        assertTrue(files.get(4).getAbsolutePath().contains(path("files", "foo.csv")));
    }

    private static String path(String directory, String file) {
        return directory + File.separator + file;
    }
}
