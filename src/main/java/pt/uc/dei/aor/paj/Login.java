package pt.uc.dei.aor.paj;


import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@SessionScoped
public class Login implements Serializable{
	
	

	@Inject Users users;
	
	private static final long serialVersionUID = 5700530280095667660L;
	
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
	
	
	public String login(){
		
		for(User u: users.getUsers()){
			if(u.getUsername().equals(username) && u.getPassword().equals(password)){
//				if(user.isLoggedIn()){
//					
//					
//				}else{
					u.setLoggedIn(true);
					return "index?faces-redirect=true";
//				}
				

				
				
			}
			
		}
		return null;		
	}
	
	public String logout(){
		for(User u: users.getUsers()){
			if(u.getUsername().equals(username)){
				u.setLoggedIn(false);
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				return "login?faces-redirect=true";
			}
			
		}
			return null;
		
		
		
	}
	
	
}
