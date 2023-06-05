package it.SWEasabi.authentication.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlacklistServiceTest {

    BlacklistService blacklist;

    @Before
    public void setUp() {
        blacklist = new LocalBlacklistService();
    }

    @Test
    public void testIsBlacklisted() {
        String refreshJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidHlwZSI6InJlZnJlc2giLCJtYXAiOnsidXNlcm5hbWUiOiJwcm92YSJ9fQ.730xa753uEigNwcnPnAQJYam8k1-yFey1hwuoqMStsk";
        assertTrue(blacklist.isBlacklisted(refreshJwt));
    }

    @Test
    public void testIsNotBlacklisted() {
        String refreshJwt = "false";
        assertFalse(blacklist.isBlacklisted(refreshJwt));
    }

    @Test
    public void testBlacklist() {
        String refreshJwt = "";
        blacklist.blacklist(refreshJwt);
    }
}