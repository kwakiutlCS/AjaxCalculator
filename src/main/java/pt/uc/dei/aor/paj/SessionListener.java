package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements 
HttpSessionListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private Login login;
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		User u = login.getUsers().getUser(login.getUsername());
		if (u != null) u.setLoggedIn(false);
		
	}


}

