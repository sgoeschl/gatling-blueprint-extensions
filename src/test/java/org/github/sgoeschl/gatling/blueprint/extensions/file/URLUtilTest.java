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
