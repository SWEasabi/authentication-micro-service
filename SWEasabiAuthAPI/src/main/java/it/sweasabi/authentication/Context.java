package it.sweasabi.authentication;


import org.json.JSONObject;

import it.sweasabi.authentication.kernel.Authenticator;
import it.sweasabi.authentication.kernel.Blacklister;
import it.sweasabi.authentication.kernel.JwtIssuer;
import it.sweasabi.authentication.services.BlackListService;
import it.sweasabi.authentication.services.KeysService;
import it.sweasabi.authentication.services.LocalBlacklistService;
import it.sweasabi.authentication.services.LocalKeysService;
import it.sweasabi.authentication.services.LocalUsersService;
import it.sweasabi.authentication.services.UsersService;

public class Context 
{
    static UsersService userService = new LocalUsersService();
    static KeysService keys = new LocalKeysService();
    static BlackListService blackListService = new LocalBlacklistService();
    static JwtIssuer issuer = new JwtIssuer(keys, blackListService);
    
    /*public static void main( String[] args )
    {
        //String json = login("smau", "password");
        //System.out.println( json );

        // test credenziali corrette
        //String json = listener("{\"function\": \"login\", \"username\": \"Admin\", \"password\": \"5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8\"}");
        //System.out.println(json);

        // test credenziali sbagliate
        //String json = listener("{\"function\": \"login\", \"username\": \"Admin\", \"password\": \"wrongPsw\"}");
        //System.out.println(json);

        // test credenziali mancanti
        //String json = listener("{\"function\": \"login\", \"username\": \"Admin\"}");
        //System.out.println(json);

        // test logout
        //String json = listener("{\"function\": \"logout\", \"refresh\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY4NTA5MTk3OCwibWFwIjp7InVzZXJuYW1lIjoiQWRtaW4ifSwidHlwZSI6ImFjY2VzcyJ9.PB9oCqlCE_71TtmW71w0Cun5txgdydb_i4RbyN0KbJI\"}");
        //System.out.println(json);
    }*/
    
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
                case "getNewAccess":
                    return refreshAccessJwt(json);
                case "getNewRefresh":
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
