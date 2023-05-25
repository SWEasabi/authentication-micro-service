package it.sweasabi.authentication;

import java.util.Map;

class JwtIssuer 
{
    public static String issueRefreshToken(String username, String privateSignature)
    {
        RefreshJwt jwt = new RefreshJwt(privateSignature);
        jwt.addClaim("username", username);
        return jwt.create();
    }
    public static String issueAccessToken(String refreshJwt, String privateSignature)
    {
        String publicSignature = "chiavePubblica";
        if(JwtVerifier.isAValidRefreshJwt(refreshJwt, privateSignature))
        {
            try
            {
                Map<String, Object> map = JwtVerifier.getClaimsMap(refreshJwt, privateSignature);
                String username = map.get("username").toString();
                AccessJwt jwt = new AccessJwt(publicSignature);
                jwt.addClaim("username", username);
                return jwt.create();
            }
            catch(Exception e)
            {
                return null;
            }
        }
        return null;
    }
    public static String updateRefreshToken(String refreshJwt, String privateSignature)
    {
        if(JwtVerifier.isAValidRefreshJwt(refreshJwt, privateSignature))
        {
            try
            {
                Map<String, Object> map = JwtVerifier.getClaimsMap(refreshJwt, privateSignature);
                String username = map.get("username").toString();
                return issueRefreshToken(privateSignature, username);
            }
            catch(Exception e)
            {
                return null;
            }
        }
        return null;
    }
}
