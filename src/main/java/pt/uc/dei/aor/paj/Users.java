package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Users implements Serializable{
	private static final long serialVersionUID = 1L;
	private Set<User> users = Collections.synchronizedSet(new TreeSet<User>());

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void addUser(User user){
		users.add(user);
	}
	
	public Set<User> getLoggedUsers() {
		Set<User> logged = new TreeSet<>();
		for (User u : users) {
			if (u.isLoggedIn()) logged.add(u);
		}
		return logged;
	}
	
	
	public synchronized User getUser(String username) {
		for (User u : users) {
			if (u.getUsername().equals(username)) return u;
		}
		return null;
	}
}
