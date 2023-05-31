package it.SWEasabi.authentication.services;

public interface BlacklistService
{
    // ritorna se il refreshJwt è già nella blacklist
    public boolean isBlacklisted(String refreshJwt);
    // blacklista il refreshJwt
    public void blacklist(String refreshJwt);
}