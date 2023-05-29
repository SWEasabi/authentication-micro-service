package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.BlackListService;

public class Blacklister 
{
    public static void blacklist(BlackListService service, String refreshJwt)
    {
        service.blacklist(refreshJwt);
    }
    public static boolean isBlacklisted(BlackListService service, String refreshJwt)
    {
        return service.isBlacklisted(refreshJwt);
    }
}
