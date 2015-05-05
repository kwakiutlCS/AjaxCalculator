package pt.uc.dei.aor.paj;


import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class Login implements Serializable{
	
	@Inject Users users;
	@Inject User user;
	
	public String login(){
		System.out.println("qqr coisa");
		for(User u: users.getUsers()){
			if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())){
				
				System.out.println(user.getUsername());
				return "index2?faces-redirect=true";
			}
			
		}
		return null;
		
		
	}
	
	
}
