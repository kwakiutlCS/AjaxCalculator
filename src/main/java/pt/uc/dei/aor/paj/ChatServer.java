package pt.uc.dei.aor.paj;

import java.util.ArrayList;
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
	private User user;
	
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
		User u = new User();
		u.setUsername(user.getUsername());
		m.setSender(u);
		messages.add(m);
		message.setText("");
	}
}
