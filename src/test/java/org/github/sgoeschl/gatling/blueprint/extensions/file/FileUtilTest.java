package org.github.sgoeschl.gatling.blueprint.extensions.file;

import org.junit.Test;

import java.io.File;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileUtilTest {

    final private static File DIRECTORY = new File("./target");

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
