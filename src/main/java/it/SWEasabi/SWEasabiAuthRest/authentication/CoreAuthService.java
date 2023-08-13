package it.SWEasabi.SWEasabiAuthRest.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import it.SWEasabi.authentication.kernel.Authenticator;
import it.SWEasabi.authentication.kernel.JwtAuthority;
import it.SWEasabi.authentication.kernel.LoginResult;
import it.SWEasabi.authentication.services.BlacklistService;
import it.SWEasabi.authentication.services.KeysService;
import it.SWEasabi.authentication.services.UsersService;

@Component
public class CoreAuthService 
{
    private UsersService userService;
    private JwtAuthority authority;
    
    public CoreAuthService(UsersService UserService, KeysService KeysService, BlacklistService BlacklistService)
    {
        userService = UserService;
        authority = new JwtAuthority(KeysService, BlacklistService);
    }
    public LoginResult login(String username, String password)
    {
        boolean status = false;
        String refreshJwt, accessJwt;
        refreshJwt = accessJwt = "";
        if(Authenticator.authenticate(userService, username, password))
        {
            refreshJwt = authority.issueRefreshToken(username);
            accessJwt = authority.issueAccessToken(refreshJwt);
        }
        return new LoginResult(status, accessJwt, refreshJwt);
    }
    public boolean logout(String refreshJwt)
    {
        return authority.invalidateRefreshJwt(refreshJwt);
    }
    public String refreshAccessJwt(String jwt)
    {
        return authority.issueAccessToken(jwt);
    }
    public String refreshRefreshJwt(String jwt)
    {
        return authority.issueRefreshToken(jwt);
    }
}
