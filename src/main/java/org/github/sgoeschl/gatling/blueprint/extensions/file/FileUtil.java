package org.github.sgoeschl.gatling.blueprint.extensions.file;

import org.github.sgoeschl.gatling.blueprint.extensions.utils.Validate;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public final class FileUtil {

    private final static String SEPARATOR = "-";

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
            if (!directory.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + directory);
            }
        }
    }

}
