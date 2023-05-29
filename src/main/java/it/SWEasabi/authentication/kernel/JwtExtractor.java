package it.SWEasabi.authentication.kernel;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtExtractor
{
    private static Map<String, Object> getClaimsMap(String token, String signature) throws Exception
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
    public static String getUsername(String token, String signature) throws Exception
    {
        Map<String, Object> map = getClaimsMap(token, signature);
        return map.get("username").toString();
    }
}
