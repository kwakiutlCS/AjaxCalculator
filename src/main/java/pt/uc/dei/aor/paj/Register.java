package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class Register implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject Users users;
	@Inject Login login;
	
	private String username;
	private String password;
	private String confpassword;
	private boolean exists=false;
	
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
	public String getConfpassword() {
		return confpassword;
	}
	public void setConfpassword(String confpassword) {
		this.confpassword = confpassword;
	}
	
	
	public String confirmRegister() {
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
	    return confirmRegister(session);
	}
	
	public String confirmRegister(HttpSession session){
		for(User u: users.getUsers()){
			if(username.equals(u.getUsername())){
				exists=true;
				System.out.println(exists);
			}
		}
		
		if(exists==false){
			if(password.equals(confpassword)){
				User u = new User();
				u.setUsername(username);
				u.setPassword(password);
				u.setLoggedIn(true);
				users.addUser(u);
				login.setUsername(username);
				login.setLoggedin(true);
				users.printUsers();
				
				if (session != null)
					session.setAttribute("login", login);
			    
			    return "/calculator/index?faces-redirect=true";
			}
			
		}
		return null;
		
	}
	

}
