package it.SWEasabi.authentication.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersServiceTest {

    UsersService user;

    @Before
    public void setUp() {
        user = new LocalUsersService();
    }

    @Test
    public void testGetPasswordHashFromAdmin() {
        String userHashPassword = user.getPasswordHashFromUser("Admin");
        assertEquals(userHashPassword, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
    }

    @Test
    public void testGetPasswordHashFromUser() {
        String userHashPassword = user.getPasswordHashFromUser("User");
        assertEquals(userHashPassword, "");
    }
}