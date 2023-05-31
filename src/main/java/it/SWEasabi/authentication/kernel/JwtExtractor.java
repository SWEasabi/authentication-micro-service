package it.SWEasabi.authentication.kernel;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

class JwtExtractor
{
    private static Map<String, Object> getClaimsMap(String jwt, String key) throws Exception
    {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
                .withIssuer("auth0")
                .withClaimPresence("map")
                .withClaimPresence("type")
                .build();
        DecodedJWT decodedJWT = verifier.verify(jwt);
        Map<String, Object> map = decodedJWT.getClaim("map").asMap();
        
        return map;
    }
    public static String getUsername(String jwt, String key) throws Exception
    {
        Map<String, Object> map = getClaimsMap(jwt, key);
        return map.get("username").toString();
    }
}
