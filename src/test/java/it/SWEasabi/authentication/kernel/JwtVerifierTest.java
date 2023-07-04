package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JwtVerifierTest {

    KeysService key;
    String username;
    String blacklistToken;
    @Before
    public void setUp() {
        key = new LocalKeysService();
        username = "User";
        blacklistToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidHlwZSI6InJlZnJlc2giLCJtYXAiOnsidXNlcm5hbWUiOiJwcm92YSJ9fQ.730xa753uEigNwcnPnAQJYam8k1-yFey1hwuoqMStsk";
    }

    @Test
    public void testIsAValidAccessJwt() {
        assertTrue(JwtVerifier.isAValidAccessJwt(JwtIssuer.issueAccessToken(JwtIssuer.issueRefreshToken(username, key), key), key));
    }

    @Test
    public void testIsNotAValidAccessJwt() {
        assertFalse(JwtVerifier.isAValidAccessJwt(blacklistToken, key));
    }

    @Test
    public void testIsAValidRefreshJwt() {
        assertTrue(JwtVerifier.isAValidRefreshJwt(JwtIssuer.issueRefreshToken(username, key), key));
    }

    @Test
    public void testIsNotAValidRefreshJwt() {
        assertFalse(JwtVerifier.isAValidRefreshJwt(blacklistToken, key));
    }

}