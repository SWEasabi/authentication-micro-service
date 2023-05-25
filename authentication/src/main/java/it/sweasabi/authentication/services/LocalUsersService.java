package it.sweasabi.authentication.services;

public class LocalUsersService implements UsersService
{
    // ritorna l'hash della password dell'utente, se esiste, altrimenti stringa vuota
    public String getPasswordHashFromUser(String username)
    {
        if(username.equals("Admin"))
        {
            return "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
        }
        return "";
    }
}