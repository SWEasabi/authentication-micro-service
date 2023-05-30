package it.SWEasabi.authentication;


import org.json.JSONObject;

import it.SWEasabi.authentication.kernel.Authenticator;
import it.SWEasabi.authentication.kernel.RefreshJwtBlacklister;
import it.SWEasabi.authentication.kernel.JwtIssuer;
import it.SWEasabi.authentication.services.BlacklistService;
import it.SWEasabi.authentication.services.KeysService;
import it.SWEasabi.authentication.services.LocalBlacklistService;
import it.SWEasabi.authentication.services.LocalKeysService;
import it.SWEasabi.authentication.services.LocalUsersService;
import it.SWEasabi.authentication.services.UsersService;

public class CoreAuthService 
{
    private UsersService userService;
    private KeysService keysService;
    private BlacklistService blackListService;
    private JwtIssuer issuer;

    public CoreAuthService(UsersService UserService, KeysService KeysService, BlacklistService BlacklistService)
    {
        userService = UserService;
        keysService = KeysService;
        blackListService = BlacklistService;
        issuer = new JwtIssuer(keysService, blackListService);
    }

    public static String listener(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            String function = jsonObject.getString("function");
            switch(function)
            {
                case "login":
                    return login(json);
                case "logout":
                    return logout(json);
                case "access":
                    return refreshAccessJwt(json);
                case "refresh":
                    return refreshRefreshJwt(json);
            }
        }
        catch (Exception e) {}
        // errore credenziali/json
        JSONObject obj = new JSONObject();
        obj.put("errore", "JSON non valido");
        return obj.toString();
    }
    private static String login(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            if(Authenticator.authenticate(userService, username, password))
            {
                String refreshJwt = issuer.issueRefreshToken(username);
                String accessJwt = issuer.issueAccessToken(refreshJwt);

                JSONObject obj = new JSONObject();
                obj.put("refresh", refreshJwt);
                obj.put("access", accessJwt);
                return obj.toString();
            }
        }
        catch (Exception e) {}
        // errore credenziali/json
        JSONObject obj = new JSONObject();
        obj.put("errore", "Credenziali errate o mancanti");
        return obj.toString();
    }
    private static String logout(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            String refreshJwt = jsonObject.getString("refresh");
            Blacklister.blacklist(blackListService, refreshJwt);

            JSONObject obj = new JSONObject();
            obj.put("messaggio", "Ok");
            return obj.toString();
        }
        catch (Exception e)
        {
            JSONObject obj = new JSONObject();
            obj.put("errore", "Logout non effettuato");
            return obj.toString();
        }
    }
    private static String refreshAccessJwt(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            String refreshJwt = jsonObject.getString("refresh");
            String accessToken = issuer.issueAccessToken(refreshJwt);
            
            if(accessToken != null)
            {
                JSONObject obj = new JSONObject();
                obj.put("access", accessToken);
                return obj.toString();
            }            
        }
        catch (Exception e) {}
        JSONObject obj = new JSONObject();
        obj.put("errore", "Token fornito non valido");
        return obj.toString();
    }
    private static String refreshRefreshJwt(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            String refreshJwt = jsonObject.getString("refresh");
            String newRefreshJwt = issuer.updateRefreshToken(refreshJwt);
            
            if(newRefreshJwt != null)
            {
                JSONObject obj = new JSONObject();
                obj.put("refresh", newRefreshJwt);
                return obj.toString();
            }            
        }
        catch (Exception e) {}
        JSONObject obj = new JSONObject();
        obj.put("errore", "Token fornito non valido");
        return obj.toString();
    }
}
