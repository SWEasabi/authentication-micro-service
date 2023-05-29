package it.SWEasabi.authentication.services;

public class LocalBlacklistService implements BlackListService
{
    public boolean isBlacklisted(String refreshJwt)
    {
        return refreshJwt.equals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidHlwZSI6InJlZnJlc2giLCJtYXAiOnsidXNlcm5hbWUiOiJwcm92YSJ9fQ.730xa753uEigNwcnPnAQJYam8k1-yFey1hwuoqMStsk");
    }

    public void blacklist(String refreshJwt)
    {
        // blacklista il refreshJwt
    }
}