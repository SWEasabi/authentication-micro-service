package it.SWEasabi.authentication.kernel;

import java.sql.Date;
import java.util.HashMap;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

class JwtPackager
{
    private final int timeToLive;
    private final String type;
    private final HashMap<String, Object> claims;

    public JwtPackager(int TimeToLive, String Type)
    {
        timeToLive = TimeToLive;
        type = Type;
        claims = new HashMap<String, Object>();
    }
    public void addClaim(String name, Object item)
    {
        claims.put(name, item);
    }
    public String pack(String Key)
    {
        JWTCreator.Builder builder = JWT.create().withIssuer("auth0").withExpiresAt(new Date(System.currentTimeMillis() + timeToLive * 1000L));
        builder.withClaim("map", claims);
        builder.withClaim("type", type);
        return builder.sign(Algorithm.HMAC256(Key));
    }
}
