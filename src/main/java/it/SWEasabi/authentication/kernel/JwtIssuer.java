package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.KeysService;

class JwtIssuer 
{
    public static String issueRefreshToken(String username, KeysService keys)
    {
        JwtPackager packager = new JwtPackager(7200, "refresh");
        packager.addClaim("username", username);
        return packager.pack(keys.getRefreshKey());
    }
    public static String issueAccessToken(String refreshJwt, KeysService keys)
    {
        try
        {
            String username = JwtExtractor.getUsername(refreshJwt, keys.getRefreshKey());
            JwtPackager packager = new JwtPackager(120, "access");
            packager.addClaim("username", username);
            return packager.pack(keys.getAccessKey());
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public static String updateRefreshToken(String refreshJwt, KeysService keys)
    {
        try
        {
            String username = JwtExtractor.getUsername(refreshJwt, keys.getRefreshKey());
            return JwtIssuer.issueRefreshToken(username, keys);
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
