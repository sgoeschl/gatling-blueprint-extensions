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

import org.github.sgoeschl.gatling.blueprint.extensions.utils.Validate;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public final class FileUtil {

    private static final String SEPARATOR = "-";

    public static File createFile(File directory, String extension, List<String> fileNameParts) {
        Validate.notNull(directory, "directory");
        Validate.notEmpty(fileNameParts, "fileNameParts");
        Validate.notEmpty(extension, "extension");

        final String fileName = createConcatenatedFileName(extension, fileNameParts);

        return new File(directory, fileName);
    }

    public static void writeToFile(File file, String content) {
        Validate.notNull(file, "file");

        createDirectoryWhenMissing(file);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.write(content != null ? content : "");
        } catch (IOException e) {
            throw new RuntimeException("Unable to write to file", e);
        }
    }

    private static String createConcatenatedFileName(String extension, List<String> fileNameParts) {
        final StringBuilder stringBuilder = new StringBuilder();
        fileNameParts.forEach(namePart -> stringBuilder.append(normalizeNamePart(namePart)).append(SEPARATOR));
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(".").append(extension).toString();
    }

    private static String normalizeNamePart(String fileNamePart) {
        return fileNamePart.trim().replace("/", SEPARATOR);
    }

    private static void createDirectoryWhenMissing(File file) {
        final File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        if (!directory.exists()) {
            throw new RuntimeException("Failed to create directory: " + directory);
        }
    }

}
