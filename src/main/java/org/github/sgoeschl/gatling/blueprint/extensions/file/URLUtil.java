package org.github.sgoeschl.gatling.blueprint.extensions.file;

import org.github.sgoeschl.gatling.blueprint.extensions.utils.Validate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public final class URLUtil {

    private final static String SLASH = "/";
    private final static String MULTIPLE_ENTRY_SEPARATOR = ",";
    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static String getURL(String baseURLs, String relativeUrl) {
        Validate.notEmpty(baseURLs, "baseURLs");

        final String baseURL = getRandomBaseURL(convertToList(baseURLs));
        return buildURL(baseURL, relativeUrl);
    }

    private static String getRandomBaseURL(List<String> baseURLs) {
        return baseURLs.get(RANDOM.nextInt((baseURLs.size())));
    }

    private static List<String> convertToList(String baseURLs) {
        Validate.notEmpty(baseURLs, "baseURLs");

        if (baseURLs.contains(MULTIPLE_ENTRY_SEPARATOR)) {
            return Arrays.stream(baseURLs.split(MULTIPLE_ENTRY_SEPARATOR))
                    .map(URLUtil::normalizePart)
                    .collect(toList());
        } else {
            return singletonList(normalizePart(baseURLs));
        }
    }

    private static String normalizePart(String namePart) {
        return namePart.trim();
    }

    private static String buildURL(String baseURL, String relativeURL) {
        if (isNullOrEmpty(relativeURL)) {
            return baseURL;
        } else if (baseURL.endsWith(SLASH) && relativeURL.startsWith(SLASH)) {
            return baseURL + relativeURL.substring(1);
        } else if (!baseURL.endsWith(SLASH) && !relativeURL.startsWith(SLASH)) {
            return baseURL + SLASH + relativeURL;
        } else {
            return baseURL + relativeURL;
        }
    }

    private static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty() || value.trim().isEmpty();
    }
}
