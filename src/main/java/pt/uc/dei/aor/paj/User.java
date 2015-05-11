package pt.uc.dei.aor.paj;

import java.io.Serializable;




public class User implements Serializable{
	
	private static final long serialVersionUID = 847213532139216089L;
	
	private boolean loggedIn = false;
	
	
	private String username;
	private String password;
	
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
	

	
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public String toString(){
		return username;
	}
	

}
