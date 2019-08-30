package org.aimanj.utils;

import java.io.IOException;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.aimanj.utils.Version.DEFAULT;
import static org.aimanj.utils.Version.getTimestamp;
import static org.aimanj.utils.Version.getVersion;

public class VersionTest {

    @Test
    public void testGetVersion() throws IOException {
        assertThat(getVersion(), is(DEFAULT));
    }

    @Test
    public void testGetTimestamp() throws IOException {
        assertThat(getTimestamp(), is("2017-01-31 01:21:09.843 UTC"));
    }
}
