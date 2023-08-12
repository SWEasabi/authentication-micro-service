package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.UsersService;

public class Authenticator
{
    public static boolean authenticate(UsersService userService, String username, String password)
    {
        return userService.getPasswordHashFromUser(username).equals(password);
    }
}
