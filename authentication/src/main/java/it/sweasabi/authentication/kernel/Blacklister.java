package it.sweasabi.authentication.kernel;

import it.sweasabi.authentication.services.BlackListService;

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
