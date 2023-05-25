package it.sweasabi.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
/*
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.MissingClaimException;
*/

import java.util.Map;

class JwtVerifier
{
    private static boolean verify(String token, String signature, String type)
    {
        try
        {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(signature))
                .withIssuer("auth0")
                .withClaimPresence("map")
                .withClaimPresence("type")
                .build();
            DecodedJWT jwt = verifier.verify(token);

            if(jwt.getClaim("type").asString().equals(type))
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
    public static boolean isAValidAccessJwt(String token, String signature)
    {
        return verify(token, signature, "access");
    }
    public static boolean isAValidRefreshJwt(String token, String signature)
    {
        if(Blacklister.isAlreadyBlacklisted(token))
        {
            return false;
        }
        return verify(token, signature, "refresh");
    }
    public static Map<String, Object> getClaimsMap(String token, String signature) throws Exception
    {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(signature))
                .withIssuer("auth0")
                .withClaimPresence("map")
                .withClaimPresence("type")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Object> map = jwt.getClaim("map").asMap();
        
        return map;
    }
}
