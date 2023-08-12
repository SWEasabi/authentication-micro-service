package it.SWEasabi.authentication.entities;

public class AuthUser {
	
	private String username;
	private String password;
	
	private AuthUser() {
		username="";
		password="";
	}
	
	private AuthUser(String username, String password) {
		this.username=username;
		this.password=password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
