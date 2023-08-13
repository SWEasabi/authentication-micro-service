package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.KeysService;
import it.SWEasabi.authentication.services.LocalKeysService;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class JwtPackagerTest {

    JwtPackager packager;

    //Non ci sono controlli sui null

/*    @Test(expected = IllegalArgumentException.class)
    public void testNegativeTimeToLive() {
        packager = new JwtPackager(-120, "access");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullType() {
        packager = new JwtPackager(120, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddClaimNullItem() {
        packager = new JwtPackager(120, "access");

        packager.addClaim("username", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddClaimNullName() {
        packager = new JwtPackager(120, "access");
        String username = "username";

        packager.addClaim("", username);
    }*/

    @Test
    public void testPack() {
        KeysService key = new LocalKeysService();
        packager = new JwtPackager(120, "access");
        packager.addClaim("username", "user");
        String token = packager.pack(key.getAccessKey());

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }
}