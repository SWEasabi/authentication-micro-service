package it.SWEasabi.authentication.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeysServiceTest {

    KeysService key;

    @Before
    public void setUp() {
        key = new LocalKeysService();
    }

    @Test
    public void testGetRefreshKey() {
        String refreshKey = key.getRefreshKey();
        assertEquals(refreshKey, "chiavePrivata");
    }

    @Test
    public void testGetAccessKey() {
        String accessKey = key.getAccessKey();
        assertEquals(accessKey, "chiavePubblica");
    }
}