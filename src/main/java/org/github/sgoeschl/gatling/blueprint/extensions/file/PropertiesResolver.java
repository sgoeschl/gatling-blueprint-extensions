/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.github.sgoeschl.gatling.blueprint.extensions.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.github.sgoeschl.gatling.blueprint.extensions.file.HierarchicalFileLocator.locateFile;

public class PropertiesResolver {

    public static Properties resolveProperties(File rootDirectory, String[] pathElements, String propertyFileName) {
        final Properties result = new Properties();
        final List<File> propertyFiles = locateFile(rootDirectory, pathElements, propertyFileName);
        Collections.reverse(propertyFiles);
        propertyFiles.forEach(propertyFile -> result.putAll(load(propertyFile)));
        return result;
    }

    public static Properties load(File propertyFile) {
        final Properties properties = new Properties();
        try (InputStream is = new FileInputStream(propertyFile)) {
            properties.load(is);
            return trimValues(properties);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load : " + propertyFile, e);
        }
    }

    private static Properties trimValues(Properties properties) {
        final Properties result = new Properties();
        for (String key : properties.stringPropertyNames()) {
            result.put(key, properties.getProperty(key).trim());
        }
        return result;
    }
}
