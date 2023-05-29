package it.SWEasabi.authentication.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersServiceTest {

    UsersService user;

    @Before
    public void init() {
        System.out.print("\nINIZIO TEST ");
        user = new LocalUsersService();
    }

    @Test
    public void testGetPasswordHashFromAdmin() {
        System.out.println(" testGetPasswordHashFromAdmin");

        String userHashPassword = user.getPasswordHashFromUser("Admin");
        assertEquals(userHashPassword, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
    }

    @Test
    public void testGetPasswordHashFromUser() {
        System.out.println(" testGetPasswordHashFromUser");

        String userHashPassword = user.getPasswordHashFromUser("User");
        assertEquals(userHashPassword, "");
    }

    @After
    public void end() {
        System.out.println("\tFINE TEST");
    }
}