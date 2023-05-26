package it.sweasabi.authentication.kernel;

import it.sweasabi.authentication.services.UsersService;

public class Authenticator
{
    public static boolean authenticate(UsersService userService, String username, String password)
    {
        return userService.getPasswordHashFromUser(username).equals(password);
    }
}
