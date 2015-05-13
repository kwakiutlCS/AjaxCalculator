package pt.uc.dei.aor.paj;

import java.io.Serializable;




public class User implements Serializable, Comparable<User> {
	
	private static final long serialVersionUID = 847213532139216089L;
	
	private boolean loggedIn = false;
	
	
	private String username;
	private String password;
	
	public User(String username, String password, boolean loggedIn) {
		this.username = username;
		this.password = password;
		this.loggedIn = loggedIn;
	}
	
	public User() {}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	
	@Override
	public String toString(){
		return username;
	}
	@Override
	public int compareTo(User o) {
		return username.compareTo(o.username);
	}
	

	public boolean validateUser(String password) {
		return this.password.equals(password);
	}
}
