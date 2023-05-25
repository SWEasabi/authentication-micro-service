package it.sweasabi.authentication;

import it.sweasabi.authentication.services.UserService;

class Authenticator
{
    public static boolean authenticate(UserService userService, String username, String password)
    {
        return userService.getPasswordHashFromUser(username).equals(password);
    }
}
