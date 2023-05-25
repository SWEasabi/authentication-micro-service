package it.sweasabi.authentication.kernel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.HashMap;

abstract class BaseJwt
{
    private int timeToLive; // secondi
    private String type;
    private String signature;
    private HashMap<String, Object> claims;

    public BaseJwt(int TimeToLive, String Signature, String Type)
    {
        timeToLive = TimeToLive;
        type = Type;
        signature = Signature;
        claims = new HashMap<String, Object>();
    }
    public void addClaim(String name, Object item)
    {
        claims.put(name, item);
    }
    public String create()
    {
        JWTCreator.Builder builder = JWT.create().withIssuer("auth0").withExpiresAt(new Date(System.currentTimeMillis() + timeToLive * 1000));
        builder.withClaim("map", claims);
        builder.withClaim("type", type);
        return builder.sign(Algorithm.HMAC256(signature));
    }
}