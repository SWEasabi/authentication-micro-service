package it.sweasabi.authentication;

import org.json.JSONObject;

public class Context 
{
    private static String privateSignature = "chiavePrivata";
    public static void main( String[] args )
    {
        //String json = login("smau", "password");
        //System.out.println( json );

        // test credenziali corrette
        
        String json = login("{\"username\": \"smau\", \"password\": \"hashedPsw\"}");
        System.out.println( json );

        // test credenziali sbagliate
        /*
        String json = login("{\"username\": \"smau\", \"password\": \"wrongPsw\"}");
        System.out.println( json );
        */

        // test credenziali mancanti
        /*
        String json = login("{\"username\": \"smau\"");
        System.out.println( json );
        */
    }
    public static String login(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            if(Authenticator.authenticate(username, password))
            {
                String refreshJwt = JwtIssuer.issueRefreshToken(username, privateSignature);
                String accessJwt = JwtIssuer.issueAccessToken(refreshJwt, privateSignature);

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
    public static String logout(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            String refreshJwt = jsonObject.getString("refresh");
            Blacklister.blacklist(refreshJwt, privateSignature);

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
    public static String refreshAccessJwt(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            String refreshJwt = jsonObject.getString("refresh");
            String accessToken = JwtIssuer.issueAccessToken(refreshJwt, privateSignature);
            
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
    public static String refreshRefreshJwt(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            String refreshJwt = jsonObject.getString("refresh");
            String newRefreshJwt = JwtIssuer.updateRefreshToken(refreshJwt, privateSignature);
            
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
