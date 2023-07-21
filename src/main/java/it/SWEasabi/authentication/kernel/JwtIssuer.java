package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.KeysService;

class JwtIssuer 
{
    public static String issueRefreshToken(String username, KeysService keysService)
    {
        JwtPackager packager = new JwtPackager(7200, "refresh");
        packager.addClaim("username", username);
        return packager.pack(keysService.getRefreshKey());
    }
    public static String issueAccessToken(String refreshJwt, KeysService keysService)
    {
        try
        {
            String username = JwtExtractor.getUsername(refreshJwt, keysService.getRefreshKey());
            JwtPackager packager = new JwtPackager(120, "access");
            packager.addClaim("username", username);
            return packager.pack(keysService.getAccessKey());
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public static String updateRefreshToken(String refreshJwt, KeysService keysService)
    {
        try
        {
            String username = JwtExtractor.getUsername(refreshJwt, keysService.getRefreshKey());
            return JwtIssuer.issueRefreshToken(username, keysService);
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
