package it.SWEasabi.SWEasabiAuthRest;


import it.SWEasabi.authentication.CoreAuthService;
import it.SWEasabi.authentication.kernel.LoginInput;
import it.SWEasabi.authentication.kernel.LoginResult;
import it.SWEasabi.authentication.kernel.LogoutInput;
import it.SWEasabi.authentication.kernel.LogoutResult;
import it.SWEasabi.authentication.kernel.RefreshAccessInput;
import it.SWEasabi.authentication.kernel.RefreshAccessOutput;
import it.SWEasabi.authentication.kernel.RefreshRefreshInput;
import it.SWEasabi.authentication.kernel.RefreshRefreshOutput;
import it.SWEasabi.authentication.services.BlacklistService;
import it.SWEasabi.authentication.services.KeysService;
import it.SWEasabi.authentication.services.UsersService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller
{
	private CoreAuthService core;
	
	//UsersService usersService = new LocalUsersService();
	//KeysService keysService = new LocalKeysService();
	//BlacklistService blacklistService = new LocalBlacklistService();
	
	public Controller(UsersService usersService, KeysService keysService, BlacklistService blacklistService)
	{
		core = new CoreAuthService(usersService, keysService, blacklistService);
	}

    @CrossOrigin (origins = "http://localhost:4200", maxAge = 3600)
    @PostMapping ("/login")
    LoginResult LoginListener (@RequestBody LoginInput input) {
        return core.login (input.username (), input.password ());
    }

    @PostMapping ("/logout")
    LogoutResult LogoutListener (@RequestBody LogoutInput input) {
        boolean status = core.logout (input.refresh ());
        return new LogoutResult (status);
    }
    @PostMapping ("/refresh/access")
    RefreshAccessOutput RefreshAccessListener (@RequestBody RefreshAccessInput input) {
        String newJwt = core.refreshAccessJwt (input.refresh ());
        boolean status = !newJwt.isEmpty ();
        return new RefreshAccessOutput (status, newJwt);
    }
    @PostMapping ("/refresh/refresh")
    RefreshRefreshOutput RefreshRefreshListener (@RequestBody RefreshRefreshInput input) {
        String newJwt = core.refreshRefreshJwt (input.refresh ());
        boolean status = !newJwt.isEmpty ();
        return new RefreshRefreshOutput (status, newJwt);
    }
}
