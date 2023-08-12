package it.SWEasabi.authentication;

import it.SWEasabi.authentication.kernel.Authenticator;
import it.SWEasabi.authentication.kernel.JwtAuthority;
import it.SWEasabi.authentication.kernel.LoginResult;
import it.SWEasabi.authentication.services.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoreAuthServiceTest {

    JwtAuthority authority;
    CoreAuthService auth;
    KeysService key;
    BlacklistService blacklist;
    UsersService user;
    String username;
    String blacklistToken;

    @Before
    public void setUp() throws Exception {
        key = new LocalKeysService();
        blacklist = new LocalBlacklistService();
        authority = new JwtAuthority(key, blacklist);
        user = new LocalUsersService();
        auth = new CoreAuthService(user, key, blacklist);
        username = "User";
        blacklistToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidHlwZSI6InJlZnJlc2giLCJtYXAiOnsidXNlcm5hbWUiOiJwcm92YSJ9fQ.730xa753uEigNwcnPnAQJYam8k1-yFey1hwuoqMStsk";
    }

    @Test
    public void testIssueRefreshToken() {
        assertEquals(authority.issueRefreshToken(username), auth.refreshRefreshJwt(username));
    }

    @Test
    public void testIssueRefreshTokenFalse() {
        assertNotEquals(authority.issueRefreshToken("user2"), auth.refreshRefreshJwt(username));
        assertNotEquals(authority.issueRefreshToken(username), auth.refreshRefreshJwt("user2"));
    }

    @Test
    public void testIssueAccessToken() {
        assertEquals(authority.issueAccessToken(auth.refreshRefreshJwt(username)), auth.refreshAccessJwt(auth.refreshRefreshJwt(username)));
    }

    @Test
    public void testIssueAccessTokenFalse() {
        assertNull(auth.refreshAccessJwt(blacklistToken));
    }

    @Test
    public void testLogin() {
        LoginResult result = auth.login("Admin", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
        assertEquals(result.getRefreshJwt(), authority.issueRefreshToken("Admin"));
        assertEquals(result.getAccessJwt(), authority.issueAccessToken(authority.issueRefreshToken("Admin")));
    }

    @Test
    public void testLoginFalse() {
        LoginResult result = auth.login("Admin", "false");
        assertNotEquals(result.getRefreshJwt(), authority.issueRefreshToken("Admin"));
        assertNotEquals(result.getAccessJwt(), authority.issueAccessToken(authority.issueRefreshToken("Admin")));
    }

/*    @Test
    public void testLogout() {
        String refreshJwt = auth.refreshRefreshJwt(username);
        assertTrue(authority.isAValidRefreshJwt(refreshJwt));
        assertTrue(auth.logout(refreshJwt));
        assertFalse(authority.isAValidRefreshJwt(refreshJwt)); //Non funziona perché il metodo per blacklistare non fa nulla
    }*/
}