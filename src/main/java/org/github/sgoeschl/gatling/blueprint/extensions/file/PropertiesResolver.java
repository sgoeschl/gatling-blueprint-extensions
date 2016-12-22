/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.github.sgoeschl.gatling.blueprint.extensions.file;

import org.github.sgoeschl.gatling.blueprint.extensions.SimulationCoordinates;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.github.sgoeschl.gatling.blueprint.extensions.file.HierarchicalFileLocator.locateFile;

public class PropertiesResolver {

    public static Properties resolveProperties(String rootDirectoryName, SimulationCoordinates simulationCoordinates, String propertyFileName) {
        final Properties result = new Properties();
        final List<File> propertyFiles = locateFile(rootDirectoryName, simulationCoordinates, propertyFileName);
        Collections.reverse(propertyFiles);
        propertyFiles.forEach(propertyFile -> result.putAll(load(propertyFile)));
        return result;
    }

    public static Properties load(File propertyFile) {
        final Properties properties = new Properties();
        try (InputStream is = new FileInputStream(propertyFile)) {
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Unable to load : " + propertyFile, e);
        }
    }
}