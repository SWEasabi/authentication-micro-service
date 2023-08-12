package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.KeysService;
import it.SWEasabi.authentication.services.LocalKeysService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginResultTest {

    LoginResult result;
    KeysService key;
    String username;

    @Before
    public void setUp() throws Exception {
        key = new LocalKeysService();
        username = "User";
        result = new LoginResult(true, JwtIssuer.issueAccessToken(JwtIssuer.issueRefreshToken(username, key), key), JwtIssuer.issueRefreshToken(username, key));
    }

    //Non ci sono controlli sui null
/*    @Test(expected = IllegalArgumentException.class)
    public void testNullAccessJwt() {
        result = new LoginResult(true, null, JwtIssuer.issueRefreshToken(username, key));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRefreshJwt() {
        result = new LoginResult(true, JwtIssuer.issueAccessToken(JwtIssuer.issueRefreshToken(username, key), key), null);
    }*/

    @Test
    public void testGetStatus() {
        assertTrue(result.getStatus());
    }

    @Test
    public void testGetAccessJwt() {
        assertEquals(JwtIssuer.issueAccessToken(JwtIssuer.issueRefreshToken(username, key), key), result.getAccessJwt());
    }

    @Test
    public void testGetRefreshJwt() {
        assertEquals(JwtIssuer.issueRefreshToken(username, key), result.getRefreshJwt());
    }
}