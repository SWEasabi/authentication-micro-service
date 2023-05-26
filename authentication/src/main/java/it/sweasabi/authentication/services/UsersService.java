package it.sweasabi.authentication.services;

public interface UsersService
{
    // ritorna l'hash della password dell'utente, se esiste, altrimenti stringa vuota
    public String getPasswordHashFromUser(String username);
}