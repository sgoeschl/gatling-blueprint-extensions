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

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileUtilTest {

    private static final File DIRECTORY = new File("./target");

    @Test
    public void shouldCreateFileWithSinglePathElement() {
        final File file = FileUtil.createFile(DIRECTORY, "json", singletonList("bar"));

        assertEquals(new File(DIRECTORY, "bar.json"), file);
    }

    @Test
    public void shouldCreateFileWithMultiplePathElement() {
        final File file = FileUtil.createFile(DIRECTORY, "json", asList("foo", "bar"));

        assertEquals(new File(DIRECTORY, "foo-bar.json"), file);
    }

    @Test
    public void shouldCreateFileWithPathElementContainingSlash() {
        final File file = FileUtil.createFile(DIRECTORY, "json", singletonList("foo/bar"));

        assertEquals(new File(DIRECTORY, "foo-bar.json"), file);
    }

    @Test
    public void shouldWriteContentToFile() {
        final String content = "content";
        final File file = FileUtil.createFile(new File(DIRECTORY, "foo"), "json", singletonList("content"));

        FileUtil.writeToFile(file, "content");

        assertTrue(file.exists());
        assertEquals(content.length(), file.length());
    }

    @Test
    public void shouldWriteNullContentToFile() {
        final File file = FileUtil.createFile(new File(DIRECTORY, "foo"), "json", singletonList("null"));

        FileUtil.writeToFile(file, null);

        assertTrue(file.exists());
    }

}
