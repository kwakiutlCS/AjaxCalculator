package pt.uc.dei.aor.paj;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ChatServer {
	
	private static final int TIME_FILTER_HOURS = 1;
	private static final int TIME_DELETION_DAYS = 3;
	
	@Inject
	private Users users;
	@Inject
	private Login login;
	
	private List<Message> messages = Collections.synchronizedList(new LinkedList<Message>());
	
	
	
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public List<Message> getMessages() {
		return messages;
	}
	
	public List<Message> getMyMessages() {
		Calendar limit = new GregorianCalendar();
		limit.add(Calendar.HOUR_OF_DAY, -TIME_FILTER_HOURS);
		List<Message> msgs = new ArrayList<>();
		for (Message m : messages) {
			if ((m.getReceiver() == null || 
					m.getReceiver().getUsername().equals(login.getUsername()) || 
					m.getSender().getUsername().equals(login.getUsername())) && 
					m.getCalendar().after(limit))
				msgs.add(m);
		}
		return msgs;
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public void sendMsg(String message) {
		message = message.trim();
		if (message == null || message.equals("")) return;
		Message m = new Message();
		
		User sender = users.getUser(login.getUsername());
		if (sender == null) return;
		else m.setSender(sender);
		
		if (message.length() >= 7 && message.substring(0, 7).equals("/secret")) {
			if (message.equals("/secret") || message.charAt(7) != ' ') {
				return;
			}
			if (message.indexOf(' ', 8) == -1) {
				return;
			}
			int userEnd = message.indexOf(' ', 8);
			m.setText(message.substring(userEnd+1));
			User receiver = users.getUser(message.substring(8, userEnd));
			if (receiver == null || receiver.equals(sender)) {
				return;
			}
			m.setReceiver(receiver);
		}
		else {
			m.setText(message);
		}
		
		m.setDate(new GregorianCalendar());
		messages.add(m);
		cleanMessages();
	}
	
	
	
	
	private void cleanMessages() {
		Calendar limit = new GregorianCalendar();
		limit.add(Calendar.DAY_OF_MONTH, -TIME_DELETION_DAYS);
		
		for (int i = messages.size()-1; i >= 0; i--) {
			if (messages.get(i).getCalendar().before(limit)) {
				messages.remove(i);
			}
		}
	}
}
