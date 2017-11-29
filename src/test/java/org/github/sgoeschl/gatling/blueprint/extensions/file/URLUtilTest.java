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

import org.junit.Test;

import static org.github.sgoeschl.gatling.blueprint.extensions.file.URLUtil.getURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class URLUtilTest {

    @Test
    public void shouldBuildURLWithCorrectNumberOfSlashes() {
        final String expectedURL = "http://base.url/relative";

        assertEquals(expectedURL, getURL("http://base.url", "relative"));
        assertEquals(expectedURL, getURL("http://base.url/", "relative"));
        assertEquals(expectedURL, getURL("http://base.url", "/relative"));
        assertEquals(expectedURL, getURL("http://base.url/", "/relative"));
    }

    @Test
    public void shouldBuildURLWithMultipleBaseURLs() {
        final String baseURLs = "http://my1.website.tld, http://my2.website.tld, http://my3.website.tld";

        assertTrue(getURL(baseURLs, "relative").contains("website.tld/relative"));
    }

    @Test
    public void shouldBuildURLWithoutRelativeUrl() {
        final String expectedURL = "http://base.url";

        assertEquals(expectedURL, getURL("http://base.url", null));
        assertEquals(expectedURL, getURL("http://base.url", ""));
        assertEquals(expectedURL, getURL("http://base.url", " "));
    }


}
