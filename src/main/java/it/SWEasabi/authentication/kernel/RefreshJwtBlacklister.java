package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.BlacklistService;

public class RefreshJwtBlacklister 
{
    public static void blacklist(BlacklistService service, String refreshJwt)
    {
        service.blacklist(refreshJwt);
    }
    public static boolean isBlacklisted(BlacklistService service, String refreshJwt)
    {
        return service.isBlacklisted(refreshJwt);
    }
}
