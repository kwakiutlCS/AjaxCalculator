package pt.uc.dei.aor.paj;


import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class Login implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5700530280095667660L;
	@Inject Users users;
	@Inject User user;
	
	
	public String login(){
		
		for(User u: users.getUsers()){
			if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())){
				
				user.setLoggedIn(true);
				System.out.println(user.getUsername()+" -> "+user.isLoggedIn());
				return "index?faces-redirect=true";
				
				
			}
			
		}
		return null;		
	}
	
	public String logout(){
			user.setLoggedIn(false);
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			return "login?faces-redirect=true";
		
		
		
	}
	
	
}
