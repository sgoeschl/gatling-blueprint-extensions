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

import org.junit.Test;

import java.io.File;

import static org.github.sgoeschl.gatling.blueprint.extensions.DataFileResolver.resolveFile;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DataFileResolverTest {

    private static final File ROOT_DIRECTORY = new File("./src/test/files");

    private final String[] pathElements = new String[] { "tenant", "site", "application", "scope" };

    @Test
    public void shouldFindConfigurationFile() {
        final File file = resolveFile(ROOT_DIRECTORY, pathElements, "foo.csv");

        assertNotNull(file);
        assertTrue(file.getAbsolutePath().endsWith(path("scope", "foo.csv")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfConfigurationFileIsNotFound() {
        resolveFile(ROOT_DIRECTORY, pathElements, "not-found.csv");
    }

    private static String path(String directory, String file) {
        return directory + File.separator + file;
    }
}
