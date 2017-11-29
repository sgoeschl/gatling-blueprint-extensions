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
package org.github.sgoeschl.gatling.blueprint.extensions.boon;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static io.advantageous.boon.core.Lists.list;
import static io.advantageous.boon.core.Maps.map;
import static org.github.sgoeschl.gatling.blueprint.extensions.boon.FilteringJsonPrettyPrinter.prettyPrint;
import static org.github.sgoeschl.gatling.blueprint.extensions.boon.FilteringJsonPrettyPrinter.print;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilteringJsonPrettyPrinterTest {

    private static final List<?> personJson = list(
            map("id", 2, "salary", 200, "firstName", "John", "lastName", "Smith")
    );

    private static final String personJsonUnsortedOutput = "[\n" +
            "  {\n" +
            "   \"id\": 2,\n" +
            "   \"salary\": 200,\n" +
            "   \"firstName\": \"John\",\n" +
            "   \"lastName\": \"Smith\"\n" +
            "  }]";

    private static final List<?> departmentJson = list(
            map("name", "Engineering",
                    "employees", list(
                            map("id", 1, "salary", 100, "firstName", "Rick", "lastName", "Hightower",
                                    "contactInfo", map("phoneNumbers",
                                            list("555-555-0000")
                                    )
                            ),
                            map("id", 2, "salary", 200, "firstName", "John", "lastName", "Smith",
                                    "contactInfo", map("phoneNumbers", list("555-555-1215",
                                            "555-555-1214", "555-555-1213"))),
                            map("id", 3, "salary", 300, "firstName", "Drew", "lastName", "Donaldson",
                                    "contactInfo", map("phoneNumbers", list("555-555-1216"))),
                            map("id", 4, "salary", 400, "firstName", "Nick", "lastName", "LaySacky",
                                    "contactInfo", map("phoneNumbers", list("555-555-1217")))

                    )
            ),
            map("name", "HR",
                    "employees", list(
                            map("id", 5, "salary", 100, "departmentName", "HR",
                                    "firstName", "Dianna", "lastName", "Hightower",
                                    "contactInfo",
                                    map("phoneNumbers", list("555-555-1218"))),
                            map("id", 6, "salary", 200, "departmentName", "HR",
                                    "firstName", "Derek", "lastName", "Smith",
                                    "contactInfo",
                                    map("phoneNumbers", list("555-555-1219"))),
                            map("id", 7, "salary", 300, "departmentName", "HR",
                                    "firstName", "Tonya", "lastName", "Donaldson",
                                    "contactInfo", map("phoneNumbers", list("555-555-1220"))),
                            map("id", 8, "salary", 400, "departmentName", "HR",
                                    "firstName", "Sue", "lastName", "LaySacky",
                                    "contactInfo", map("phoneNumbers", list("555-555-9999")))

                    )
            ),
            map("name", "Manufacturing", "employees", Collections.EMPTY_LIST),
            map("name", "Sales", "employees", Collections.EMPTY_LIST),
            map("name", "Marketing", "employees", Collections.EMPTY_LIST)
    );

    @Test
    public void shouldHandleNullValue() {
        assertEquals("[]", print(null));
    }

    @Test
    public void shouldHandleEmptyStringValue() {
        assertEquals("[]", print(""));
    }

    @Test
    public void shouldHandleSpaceStringValue() {
        assertEquals("[]", print(" "));
    }

    @Test
    @Ignore("Only manual testing")
    public void shouldPrettyPrintUnsortedJsonToStdout() {
        System.out.println(print(departmentJson));
    }

    @Test
    public void shouldPrettyPrintUnsortedJson() {
        assertEquals(personJsonUnsortedOutput, print(personJson));
        assertEquals(personJsonUnsortedOutput, print(personJson, Collections.emptyList()));
    }

    @Test
    public void shouldPrettyPrintJson() {
        assertTrue(prettyPrint(departmentJson).contains("id"));
    }

    @Test
    public void shouldRemoveSingleElement() {
        assertFalse(print(departmentJson, "id").contains("id"));
    }

    @Test
    public void shouldRemoveElementTree() {
        assertFalse(print(departmentJson, "employees").contains("contactInfo"));
    }

    @Test
    public void shouldHandleNullJson() {
        assertEquals("[]", print(null));
    }
}
