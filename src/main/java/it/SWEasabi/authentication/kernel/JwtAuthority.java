package it.SWEasabi.authentication.kernel;

import it.SWEasabi.authentication.services.BlacklistService;
import it.SWEasabi.authentication.services.KeysService;

public class JwtAuthority
{
    private KeysService keys;
    private BlacklistService blacklistService;

    public JwtAuthority(KeysService Keys, BlacklistService BlacklistService)
    {
        keys = Keys;
        blacklistService = BlacklistService;
    }
    // ----------------------- ISSUER -----------------------
    public String issueRefreshToken(String username)
    {
        return JwtIssuer.issueRefreshToken(username, keys);
    }
    public String issueAccessToken(String refreshJwt)
    {
        if(isAValidRefreshJwt(refreshJwt))
        {
            return JwtIssuer.issueAccessToken(refreshJwt, keys);
        }
        return null;
    }
    public String updateRefreshToken(String refreshJwt)
    {
        if(isAValidRefreshJwt(refreshJwt))
        {
            invalidateRefreshJwt(refreshJwt);
            return JwtIssuer.updateRefreshToken(refreshJwt, keys);
        }
        return null;
    }
    // ----------------------- VERIFIER -----------------------
    public boolean isAValidAccessJwt(String jwt)
    {
        return JwtVerifier.isAValidAccessJwt(jwt, keys);
    }
    public boolean isAValidRefreshJwt(String jwt)
    {
        if(RefreshJwtBlacklister.isBlacklisted(blacklistService, jwt))
        {
            return false;
        }
        return JwtVerifier.isAValidRefreshJwt(jwt, keys);
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
