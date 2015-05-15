package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements 
HttpSessionListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		HttpSession session = se.getSession();
		Login login = (Login) session.getAttribute("login");

		User u = login.getUsers().getUser(login.getUsername());
		if (u != null) u.setLoggedIn(false);
		
	}


}

