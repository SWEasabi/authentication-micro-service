package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.LocalUsersService;
import it.SWEasabi.authentication.services.UsersService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthenticatorTest {
    UsersService user;
    @Before
    public void setUp() throws Exception {
        user = new LocalUsersService();
    }

    @Test
    public void testAuthenticate() {
        assertTrue(Authenticator.authenticate(user, "Admin", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"));
    }

    @Test
    public void testAuthenticationFailed() {
        assertFalse(Authenticator.authenticate(user, "Admin", "password"));
        assertFalse(Authenticator.authenticate(user, "User", "password"));
        assertFalse(Authenticator.authenticate(user, "User", "")); //La password per qualsiasi user diverso da admin è "".
    }
}