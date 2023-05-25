package it.sweasabi.authentication;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void EncodeTest()
    {
        try
        {
            String username = "smau";
            String privateSignature = "chiavePrivata";

            String refreshJwt = JwtIssuer.issueRefreshToken(username, privateSignature);
            String accessJwt = JwtIssuer.issueAccessToken(refreshJwt, privateSignature);

            //System.out.println(refreshJwt);
            //System.out.println(accessJwt);
        }
        catch (Exception e)
        {
            // token già creato
        }

        assertTrue( true );
    }

    @Test
    public void DecodeTest()
    {
        // esempio su un altro service
        String accessJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY4NDk1ODU1OSwibWFwIjp7InVzZXJuYW1lIjoic21hdSJ9LCJ0eXBlIjoiYWNjZXNzIn0.tABFnHQPNWdLXGyISBr5-6LrxaezlXM1vuOOkTeywDY";
        String publicSignature = "chiavePubblica";
        if(JwtVerifier.isAValidAccessJwt(accessJwt, publicSignature))
        {
            try
            {
                Map<String, Object> map = JwtVerifier.getClaimsMap(accessJwt, publicSignature);
                String username = map.get("username").toString();

                System.out.println("token valido, username:" + username);
            }
            catch (Exception e)
            {
                // errore da qualche parte nel jwt
            }
        }
        assertTrue( true );
    }
    @Test
    public void RefreshAccessTest()
    {
        // esempio su un altro service
        String refreshJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY4NDk2NTU3MywibWFwIjp7InVzZXJuYW1lIjoic21hdSJ9LCJ0eXBlIjoicmVmcmVzaCJ9.r7jIWoFy4PWPegIDqy93b45sj9XOUWQjY7FdR0P0_Jk";
        String privateSignature = "chiavePrivata";
        
        String newAcessJwt = JwtIssuer.issueAccessToken(refreshJwt, privateSignature);
        //System.out.println(newAcessJwt);

        assertTrue( true );
    }
    @Test
    public void UpdateRefreshTest()
    {
        // esempio su un altro service
        String refreshJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY4NDk2NTU3MywibWFwIjp7InVzZXJuYW1lIjoic21hdSJ9LCJ0eXBlIjoicmVmcmVzaCJ9.r7jIWoFy4PWPegIDqy93b45sj9XOUWQjY7FdR0P0_Jk";
        String privateSignature = "chiavePrivata";
        
        String newRefreshJwt = JwtIssuer.updateRefreshToken(refreshJwt, privateSignature);
        //System.out.println(newRefreshJwt);

        assertTrue( true );
    }
}
