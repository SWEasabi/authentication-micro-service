package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import static org.junit.Assert.*;

public class JwtExtractorTest {
    KeysService keys;
    BlacklistService blacklist;
    UsersService user;
    @Before
    public void setUp() {
        keys = new LocalKeysService();
        blacklist = new LocalBlacklistService();
        user = new LocalUsersService();
    }

    @Test
    public void testNullJwt() {
        try {
            JwtExtractor.getUsername(null, keys.getRefreshKey());
        } catch (Exception e) {
            assertEquals("The token is null.", e.getMessage());
        }
    }

    @Test
    public void testNullKeys() {
        try {
            JwtExtractor.getUsername("jwt", null);
        } catch (Exception e) {
            assertEquals("The Secret cannot be null", e.getMessage());
        }
    }

    @Test
    public void testGetUsername() {
        JwtAuthority authority = new JwtAuthority(keys, blacklist);
        String username = "Admin";
        Authenticator.authenticate(user, username, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
        String refreshJwt = authority.issueRefreshToken(username);
        try {
            assertEquals(username, JwtExtractor.getUsername(refreshJwt, keys.getRefreshKey()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}