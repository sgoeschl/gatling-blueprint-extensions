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
import org.junit.Test;

import java.util.Properties;

import static org.github.sgoeschl.gatling.blueprint.extensions.file.EnvironmentPropertiesResolver.resolveProperties;
import static org.junit.Assert.assertEquals;

public class EnvironmentPropertiesResolverTest {

    private final static String ROOT_DIRECTORY_NAME = "./src/test/files";

    private final SimulationCoordinates coordinates = new SimulationCoordinates("application", "tenant", "site", "scope");

    @Test
    public void shouldLocateInAscendingOrder() {
        final Properties properties = resolveProperties(ROOT_DIRECTORY_NAME, coordinates);

        assertEquals("scope", properties.getProperty("value"));
    }
}
