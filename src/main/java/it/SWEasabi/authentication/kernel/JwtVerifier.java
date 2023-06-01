package it.SWEasabi.authentication.kernel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.SWEasabi.authentication.services.KeysService;

class JwtVerifier
{
    private static boolean verify(String jwt, String key, String type)
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
    public static boolean isAValidAccessJwt(String jwt, KeysService keys)
    {
        return verify(jwt, keys.getAccessKey(), "access");
    }
    public static boolean isAValidRefreshJwt(String jwt, KeysService keys)
    {
        return verify(jwt, keys.getRefreshKey(), "refresh");
    }
}
