package sweasabi.SWEasabiAPI;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import it.sweasabi.authentication.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class Controller {
	
	//private final LampRepository repository;

	  Controller() {
	  }
	  
	  
	  
	  @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
	  @GetMapping("/login")
	  String CheckCredentials(@RequestBody String credentials) {
				
				JsonObject rq = new Gson().fromJson(credentials, JsonObject.class);
				rq.addProperty("function", "login");
				
				return Context.listener(rq.toString());
	  }
	  
	  
}
