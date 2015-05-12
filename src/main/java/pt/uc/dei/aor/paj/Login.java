package pt.uc.dei.aor.paj;


import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


@Named
@SessionScoped
public class Login implements Serializable{
	
	@Inject Users users;
	
	private static final long serialVersionUID = 5700530280095667660L;
	
	private String username;
	private String password;
	private boolean loggedin=false;

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
				if(u.isLoggedIn()){
					
					FacesContext.getCurrentInstance().addMessage(":login:msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Already logged in!", "Error!"));
					
				}else{
					u.setLoggedIn(true);

					loggedin = true;
					FacesContext context = FacesContext.getCurrentInstance();
				    HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
				    session.setAttribute("login", this);
					return "calculator/index?faces-redirect=true";
				}

				

				
				
			}
			
		}
		return null;		
	}
	
	public String logout(){
		for(User u: users.getUsers()){
			if(u.getUsername().equals(username)){
				u.setLoggedIn(false);

				loggedin = false;

				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				return "/login?faces-redirect=true";
			}
			
		}
			return null;
		
		
		
	}

	public boolean isLoggedin() {
		return loggedin;
	}


	public void setLoggedin(boolean loggedin) {
		this.loggedin = loggedin;

	}
	
	public Users getUsers() { return users; }
}
