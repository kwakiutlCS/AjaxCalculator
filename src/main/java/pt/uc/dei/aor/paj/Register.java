package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class Register implements Serializable{
	
	@Inject User user;
	@Inject Users users;
	
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
	
	public String confirmRegister(){
		for(User u: users.getUsers()){
			if(username.equals(u.getUsername())){
				exists=true;
				System.out.println(exists);
			}
		}
		
		if(exists==false){
			if(password.equals(confpassword)){
				user.setUsername(username);
				user.setPassword(password);
				users.addUser(user);
				users.printUsers();
				user.setLoggedIn(true);
				return "index?faces-redirect=true";
			}
			
		}
		return null;
		
	}
	

}
