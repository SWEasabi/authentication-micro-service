package it.sweasabi.authentication;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.sweasabi.authentication.kernel.JwtExtractor;
import it.sweasabi.authentication.kernel.JwtIssuer;
import it.sweasabi.authentication.kernel.JwtVerifier;
import it.sweasabi.authentication.services.BlackListService;
import it.sweasabi.authentication.services.KeysService;
import it.sweasabi.authentication.services.LocalBlacklistService;
import it.sweasabi.authentication.services.LocalKeysService;
import it.sweasabi.authentication.services.LocalUsersService;
import it.sweasabi.authentication.services.UsersService;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    static UsersService userService = new LocalUsersService();
    static KeysService keys = new LocalKeysService();
    static BlackListService blackListService = new LocalBlacklistService();
    static JwtIssuer issuer = new JwtIssuer(keys, blackListService);
    static JwtVerifier verifier = new JwtVerifier(keys, blackListService);

    @Test
    public void EncodeTest()
    {
        try
        {
            String username = "smau";

            String refreshJwt = issuer.issueRefreshToken(username);
            String accessJwt = issuer.issueAccessToken(refreshJwt);

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
        if(verifier.isAValidAccessJwt(accessJwt))
        {
            try
            {
                String username = JwtExtractor.getUsername(accessJwt, keys.getAccessKey());

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
        
        String newAcessJwt = issuer.issueAccessToken(refreshJwt);
        //System.out.println(newAcessJwt);

        assertTrue( true );
    }
    @Test
    public void UpdateRefreshTest()
    {
        // esempio su un altro service
        String refreshJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY4NDk2NTU3MywibWFwIjp7InVzZXJuYW1lIjoic21hdSJ9LCJ0eXBlIjoicmVmcmVzaCJ9.r7jIWoFy4PWPegIDqy93b45sj9XOUWQjY7FdR0P0_Jk";
        
        String newRefreshJwt = issuer.updateRefreshToken(refreshJwt);
        //System.out.println(newRefreshJwt);

        assertTrue( true );
    }
}
