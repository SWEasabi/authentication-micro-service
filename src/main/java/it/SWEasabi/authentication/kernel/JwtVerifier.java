package it.SWEasabi.authentication.kernel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
/*
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.MissingClaimException;
*/

import it.SWEasabi.authentication.services.BlacklistService;
import it.SWEasabi.authentication.services.KeysService;

public class JwtVerifier
{
    private KeysService keys;
    private BlacklistService blacklistService;
    public JwtVerifier(KeysService Keys, BlacklistService BlacklistService)
    {
        keys = Keys;
        blacklistService = BlacklistService;
    }

    private boolean verify(String jwt, String key, String type)
    {
        try
        {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
                .withIssuer("auth0")
                .withClaimPresence("map")
                .withClaimPresence("type")
                .build();
            DecodedJWT decodedJWT = verifier.verify(jwt);

            if(decodedJWT.getClaim("type").asString().equals(type))
            {
                return true;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return false;
    }
    public boolean isAValidAccessJwt(String jwt)
    {
        return verify(jwt, keys.getAccessKey(), "access");
    }
    public boolean isAValidRefreshJwt(String jwt)
    {
        if(RefreshJwtBlacklister.isBlacklisted(blacklistService, jwt))
        {
            return false;
        }
        return verify(jwt, keys.getRefreshKey(), "refresh");
    }
}
