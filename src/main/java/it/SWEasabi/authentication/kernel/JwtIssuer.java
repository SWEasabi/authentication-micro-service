package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.BlacklistService;
import it.SWEasabi.authentication.services.KeysService;

public class JwtIssuer 
{
    private KeysService keys;
    private BlacklistService blacklistService;
    private JwtVerifier verifier;

    public JwtIssuer(KeysService Keys, BlacklistService BlacklistService)
    {
        keys = Keys;
        blacklistService = BlacklistService;
        verifier = new JwtVerifier(keys, blacklistService);
    }
    public String issueRefreshToken(String username)
    {
        JwtPackager packager = new JwtPackager(7200, "refresh");
        packager.addClaim("username", username);
        return packager.pack(keys.getRefreshKey());
    }
    public String issueAccessToken(String refreshJwt)
    {
        if(verifier.isAValidRefreshJwt(refreshJwt))
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
        return null;
    }
    public String updateRefreshToken(String refreshJwt)
    {
        if(verifier.isAValidRefreshJwt(refreshJwt))
        {
            try
            {
                String username = JwtExtractor.getUsername(refreshJwt, keys.getRefreshKey());
                return issueRefreshToken(username);
            }
            catch(Exception e)
            {
                return null;
            }
        }
        return null;
    }
}
