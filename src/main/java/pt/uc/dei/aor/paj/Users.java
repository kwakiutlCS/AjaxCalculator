package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Users implements Serializable{
	
	private List<User> users = new ArrayList<>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user){
		users.add(user);
	}
	
	public void printUsers(){
		for(User u : users){
			System.out.println(u.getUsername());
		}
	}
	
	public List<User> getLoggedUsers() {
		List<User> logged = new ArrayList<User>();
		for (User u : users) {
			if (u.isLoggedIn()) logged.add(u);
		}
		return logged;
	}
}
