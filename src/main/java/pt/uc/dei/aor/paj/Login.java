package pt.uc.dei.aor.paj;

import java.util.ArrayList;
import java.util.List;

public class Login {
	
	
	private String loggedIn="";
	
	public String login(String username, String password){
		for(User u: users){
			if(u.getUsername().equals(username) && u.getPassword().equals(password)){
				
				loggedIn = true;
				return "index2.xhtml";
			}
			
		}
		return loggedIn;
		
		
	}
	
	
}
