package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.BlacklistService;
import it.SWEasabi.authentication.services.KeysService;

public class JwtAuthority
{
    private KeysService keysService;
    private BlacklistService blacklistService;

    public JwtAuthority(KeysService ks, BlacklistService bs)
    {
        keysService = ks;
        blacklistService = bs;
    }
    // ----------------------- ISSUER -----------------------
    public String issueRefreshToken(String username)
    {
        return JwtIssuer.issueRefreshToken(username, keysService);
    }
    public String issueAccessToken(String refreshJwt)
    {
        if(isAValidRefreshJwt(refreshJwt))
        {
            return JwtIssuer.issueAccessToken(refreshJwt, keysService);
        }
        return null;
    }
    public String updateRefreshToken(String refreshJwt)
    {
        if(isAValidRefreshJwt(refreshJwt))
        {
            invalidateRefreshJwt(refreshJwt);
            return JwtIssuer.updateRefreshToken(refreshJwt, keysService);
        }
        return null;
    }
    // ----------------------- VERIFIER -----------------------
    public boolean isAValidAccessJwt(String jwt)
    {
        return JwtVerifier.isAValidAccessJwt(jwt, keysService);
    }
    public boolean isAValidRefreshJwt(String jwt)
    {
        if(RefreshJwtBlacklister.isBlacklisted(blacklistService, jwt))
        {
            return false;
        }
        return JwtVerifier.isAValidRefreshJwt(jwt, keysService);
    }
    public boolean invalidateRefreshJwt(String jwt)
    {
        if(isAValidRefreshJwt(jwt))
        {
            RefreshJwtBlacklister.blacklist(blacklistService, jwt);
            return true;
        }
        return false;
    }
}
