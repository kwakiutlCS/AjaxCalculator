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
	private Message message;
	@Inject
	private Login login;
	
	private List<Message> messages = new ArrayList<>();
	
	
	
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public void sendMsg() {
		Message m = new Message();
		m.setText(message.getText());
		for (User u : users.getUsers()) {
			if (u.getUsername().equals(login.getUsername())) {
				m.setSender(u);
				break;
			}
		}
		m.setDate(new GregorianCalendar());
		messages.add(m);
		message.setText("");
	}
}
