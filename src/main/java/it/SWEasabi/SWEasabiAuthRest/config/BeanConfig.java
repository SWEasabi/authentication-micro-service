package it.SWEasabi.SWEasabiAuthRest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import it.SWEasabi.authentication.CoreAuthService;
import it.SWEasabi.authentication.kernel.JwtAuthority;
import it.SWEasabi.authentication.services.BlacklistService;
import it.SWEasabi.authentication.services.KeysService;
import it.SWEasabi.authentication.services.LocalBlacklistService;
import it.SWEasabi.authentication.services.LocalKeysService;
import it.SWEasabi.authentication.services.LocalUsersService;
import it.SWEasabi.authentication.services.UsersService;

@Configuration
public class BeanConfig {
	
	@Bean
	UsersService getUserService() {
		return new LocalUsersService();
	}
	
	@Bean
	KeysService getKeysService() {
		return new LocalKeysService();
	}
	
	@Bean
	BlacklistService getBlacklistService() {
		return new LocalBlacklistService();
	}
}
