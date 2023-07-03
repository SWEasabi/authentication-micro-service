package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JwtAuthorityTest {

    JwtAuthority authority;
    KeysService key;
    BlacklistService blacklist;
    String username;
    String blacklistToken;

    @Before
    public void setUp() throws Exception {
        key = new LocalKeysService();
        blacklist = new LocalBlacklistService();
        authority = new JwtAuthority(key, blacklist);
        username = "User";
        blacklistToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidHlwZSI6InJlZnJlc2giLCJtYXAiOnsidXNlcm5hbWUiOiJwcm92YSJ9fQ.730xa753uEigNwcnPnAQJYam8k1-yFey1hwuoqMStsk";
    }

    @Test
    public void testIssueRefreshToken() {
        assertEquals(authority.issueRefreshToken(username), JwtIssuer.issueRefreshToken(username, key));
    }

    @Test
    public void testIssueRefreshTokenFalse() {

        assertNotEquals(authority.issueRefreshToken("user2"), JwtIssuer.issueRefreshToken(username, key));
        assertNotEquals(authority.issueRefreshToken(username), JwtIssuer.issueRefreshToken("user2", key));
    }

    @Test
    public void testIssueAccessToken() {
        assertEquals(authority.issueAccessToken(JwtIssuer.issueRefreshToken(username, key)), JwtIssuer.issueAccessToken(JwtIssuer.issueRefreshToken(username, key), key));
    }

    @Test
    public void testIssueAccessTokenFalse() {
        assertNull(authority.issueAccessToken(blacklistToken));
    }

    @Test
    public void testUpdateRefreshToken() {
        authority.updateRefreshToken(JwtIssuer.issueRefreshToken(username, key));
    }

    @Test
    public void testUpdateNotValidRefreshToken() {
        assertNull(authority.updateRefreshToken(blacklistToken));
    }

    @Test
    public void testIsAValidAccessJwt() {
        assertTrue(authority.isAValidAccessJwt(JwtIssuer.issueAccessToken(JwtIssuer.issueRefreshToken(username, key), key)));
    }

    @Test
    public void testIsNotAValidAccessJwt() {
        assertFalse(authority.isAValidAccessJwt(blacklistToken));
    }

    @Test
    public void testIsAValidRefreshJwt() {
        assertTrue(authority.isAValidRefreshJwt(JwtIssuer.issueRefreshToken(username, key)));
    }

    @Test
    public void testIsNotAValidRefreshJwt() {
        assertFalse(authority.isAValidRefreshJwt(blacklistToken));
    }

    @Test
    public void testInvalidateRefreshJwt() {
        String refreshJwt = JwtIssuer.issueRefreshToken(username, key);
        assertTrue(authority.isAValidRefreshJwt(refreshJwt));
        assertTrue(authority.invalidateRefreshJwt(refreshJwt));
        assertFalse(authority.isAValidRefreshJwt(refreshJwt)); //Non funziona perché il metodo per blacklistare non fa nulla
    }

    @Test
    public void testInvalidateNotValidRefreshJwt() {
        assertFalse(authority.invalidateRefreshJwt(blacklistToken));
    }
}