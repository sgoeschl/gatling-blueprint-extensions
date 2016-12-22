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
import java.util.Properties;

/**
 * Load the values of "environment.properties" hierarchically and merge with
 * <ul>
 * <li>./user.properties</li>
 * <li>~/.gatling.properties</li>
 * </ul>
 * <br>
 * This allows to overwrite configured values on an ad-hoc base (user.properties)
 * or permanently, e.g. to provide proxy settings.
 */
public class EnvironmentPropertiesResolver {

    private static final String USER_PROPERTIES = "user.properties";
    private static final String GATLING_PROPERTIES = ".gatling.properties";
    private static final Properties EMPTY_PROPERTIES = new Properties();

    public static Properties resolveProperties(String rootDirectoryName, SimulationCoordinates simulationCoordinates) {
        final Properties properties = PropertiesResolver.resolveProperties(rootDirectoryName, simulationCoordinates, "environment.properties");
        properties.putAll(getUserProperties());
        properties.putAll(getUserHomeGatlingProperties());
        return properties;
    }

    private static Properties getUserProperties() {
        File file = new File(getUserWorkingDirectory(), USER_PROPERTIES);
        return file.exists() ? PropertiesResolver.load(file) : EMPTY_PROPERTIES;
    }

    private static Properties getUserHomeGatlingProperties() {
        if (getUserHomeDirectory() != null) {
            File file = new File(getUserHomeDirectory(), GATLING_PROPERTIES);
            return file.exists() ? PropertiesResolver.load(file) : EMPTY_PROPERTIES;
        }
        return EMPTY_PROPERTIES;
    }

    private static String getUserHomeDirectory() {
        return System.getProperty("user.home");
    }

    private static String getUserWorkingDirectory() {
        return System.getProperty("user.dir");
    }

}
