package it.SWEasabi.SWEasabiAuthRest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import it.SWEasabi.authentication.CoreAuthService;
import it.SWEasabi.authentication.kernel.LoginResult;
import it.SWEasabi.authentication.services.BlacklistService;
import it.SWEasabi.authentication.services.KeysService;
import it.SWEasabi.authentication.services.LocalBlacklistService;
import it.SWEasabi.authentication.services.LocalKeysService;
import it.SWEasabi.authentication.services.LocalUsersService;
import it.SWEasabi.authentication.services.UsersService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class Controller
{
	private final CoreAuthService core;
	UsersService usersService = new LocalUsersService();
	KeysService keysService = new LocalKeysService();
	BlacklistService blacklistService = new LocalBlacklistService();

	public Controller()
	{
		core = new CoreAuthService(usersService, keysService, blacklistService);
	}

	@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
	@PostMapping("/login")
	String LoginListener(@RequestBody String json)
	{
		JsonObject rq = new Gson().fromJson(json, JsonObject.class);
		String username = rq.get("username").toString();
		String password = rq.get("password").toString();
		
		LoginResult result = core.login(username, password);

		JsonObject response = new JsonObject();
		response.addProperty("status", result.getStatus());
		response.addProperty("access", result.getAccessJwt());
		response.addProperty("refresh", result.getRefreshJwt());

		return response.toString();
	}
	@PostMapping("/logout")
	String LogoutListener(@RequestBody String json)
	{
		JsonObject rq = new Gson().fromJson(json, JsonObject.class);
		String jwt = rq.get("refresh").toString();
		
		boolean status = core.logout(jwt);

		JsonObject response = new JsonObject();
		response.addProperty("status", status);

		return response.toString();
	}
	@PostMapping("/refresh/access")
	String RefreshAccessListener(@RequestBody String json)
	{
		JsonObject rq = new Gson().fromJson(json, JsonObject.class);
		String jwt = rq.get("refresh").toString();
		
		String newJwt = core.refreshAccessJwt(jwt);
		boolean status = !newJwt.equals("");

		JsonObject response = new JsonObject();
		response.addProperty("status", status);
		response.addProperty("access", newJwt);

		return response.toString();
	}
	@PostMapping("/refresh/refresh")
	String RefreshRefreshListener(@RequestBody String json)
	{
		JsonObject rq = new Gson().fromJson(json, JsonObject.class);
		String jwt = rq.get("refresh").toString();
		
		String newJwt = core.refreshRefreshJwt(jwt);
		boolean status = !newJwt.equals("");

		JsonObject response = new JsonObject();
		response.addProperty("status", status);
		response.addProperty("refresh", newJwt);

		return response.toString();
	}
}
