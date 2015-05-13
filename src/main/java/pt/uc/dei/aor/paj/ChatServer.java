package pt.uc.dei.aor.paj;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ChatServer {
	
	@Inject
	private Users users;
	@Inject
	private Login login;
	
	private List<Message> messages = new ArrayList<>();
	private String message;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
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
		List<Message> msgs = new ArrayList<>();
		for (Message m : messages) {
			if (m.getReceiver() == null || 
					m.getReceiver().getUsername().equals(login.getUsername()) || 
					m.getSender().getUsername().equals(login.getUsername()))
				msgs.add(m);
		}
		return msgs;
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public void sendMsg() {
		message = message.trim();
		if (message == null || message.equals("")) return;
		Message m = new Message();
		
		User sender = users.getUser(login.getUsername());
		if (sender == null) return;
		else m.setSender(sender);
		
		if (message.length() >= 7 && message.substring(0, 7).equals("/secret")) {
			if (message.charAt(7) != ' ') return;
			if (message.indexOf(' ', 8) == -1) return;
			int userEnd = message.indexOf(' ', 8);
			m.setText(message.substring(userEnd+1));
			User receiver = users.getUser(message.substring(8, userEnd));
			if (receiver == null || receiver.equals(sender)) return;
			m.setReceiver(receiver);
		}
		else {
			m.setText(message);
		}
		
		m.setDate(new GregorianCalendar());
		messages.add(m);
		message = "";
	}
	
	
}
