package it.sweasabi.authentication.kernel;

class Blacklister 
{
    public static void blacklist(String refreshJwt)
    {
        if(JwtVerifier.isAValidRefreshJwt(refreshJwt))
        {
            // blacklista nel db
        }
    }
    public static boolean isAlreadyBlacklisted(String refreshJwt)
    {
        // fa il controllo nel db
        return false;
    }
}
