package it.sweasabi.authentication.services;

public interface BlackListService
{
    // ritorna se il refreshJwt è già nella blacklist
    public boolean isBlacklisted(String refreshJwt);
    // blacklista il refreshJwt
    public void blacklist(String refreshJwt);
}