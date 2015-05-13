package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChatServerTest {

	@Mock
	private Users users;
	@Mock
	private Login login;
	
	@InjectMocks
	private ChatServer chatServer;
	
	private User u1, u2, u3;
	
	@Before
	public void init() {
		u1 = new User();
		u1.setPassword("p");
		u1.setUsername("user1");
		u2 = new User();
		u2.setPassword("p");
		u2.setUsername("user2");
		u3 = new User();
		u3.setPassword("p");
		u3.setUsername("user3");
		Set<User> listUsers = new TreeSet<>();
		listUsers.add(u1);
		listUsers.add(u2);
		listUsers.add(u3);
		
		when(users.getUsers()).thenReturn(listUsers);
		when(login.getUsername()).thenReturn("user2");
		when(users.getUser("user1")).thenReturn(u1);
		when(users.getUser("user2")).thenReturn(u2);
		when(users.getUser("user3")).thenReturn(u3);
		
	}
	
	@Test
	public void should_add_correct_message() {
		int size = chatServer.getMessages().size();
		chatServer.setMessage("test");
		chatServer.sendMsg();
		assertThat(chatServer.getMessages().size(), is(equalTo(size+1)));
		assertThat(chatServer.getMessages().get(size).getText(), is(equalTo("test")));
	}
	
	@Test
	public void should_add_correct_user_to_message() {
		int size = chatServer.getMessages().size();
		chatServer.setMessage("test");
		chatServer.sendMsg();
		assertThat(chatServer.getMessages().size(), is(equalTo(size+1)));
		assertThat(chatServer.getMessages().get(size).getSender().getUsername(), is(equalTo("user2")));
	}
	
	@Test
	public void should_not_add_empty_message() {
		int size = chatServer.getMessages().size();
		chatServer.setMessage("");
		chatServer.sendMsg();
		assertThat(chatServer.getMessages().size(), is(equalTo(size)));
	}
	
	@Test
	public void should_add_secret_message_correctly() {
		chatServer.setMessage("/secret user1 mensagem");
		chatServer.sendMsg();
		assertThat(chatServer.getMessages().get(0).getReceiver().getUsername(), is(equalTo("user1")));
		assertThat(chatServer.getMessages().get(0).getText(), is(equalTo("mensagem")));
	}
	
	@Test
	public void should_not_add_secret_message_without_message() {
		chatServer.setMessage("/secret user1");
		chatServer.sendMsg();
		assertThat(chatServer.getMessages().size(), is(equalTo(0)));
	}
	
	@Test
	public void should_not_add_secret_message_without_user_present() {
		chatServer.setMessage("/secret user4 mensagem");
		chatServer.sendMsg();
		assertThat(chatServer.getMessages().size(), is(equalTo(0)));
	}
	
	@Test
	public void should_not_add_secret_message_to_himself() {
		chatServer.setMessage("/secret user2 mensagem");
		chatServer.sendMsg();
		assertThat(chatServer.getMessages().size(), is(equalTo(0)));
	}
	
	@Test
	public void should_filter_messages_correctly() {
		chatServer.setMessage("/secret user1 mensagem");
		chatServer.sendMsg();
		chatServer.setMessage("mensagem");
		chatServer.sendMsg();
		when(login.getUsername()).thenReturn("user1");
		chatServer.setMessage("/secret user3 mensagem");
		chatServer.sendMsg();
		when(login.getUsername()).thenReturn("user3");
		assertThat(chatServer.getMessages().size(), is(equalTo(3)));
		assertThat(chatServer.getMyMessages().size(), is(equalTo(2)));
		
	}
	
	@Test
	public void should_not_filter_messages_from_himself() {
		chatServer.setMessage("/secret user1 mensagem");
		chatServer.sendMsg();
		chatServer.setMessage("mensagem");
		chatServer.sendMsg();
		chatServer.setMessage("/secret user3 mensagem");
		chatServer.sendMsg();
		assertThat(chatServer.getMessages().size(), is(equalTo(3)));
		assertThat(chatServer.getMyMessages().size(), is(equalTo(3)));
		
	}
	
	
	@Test
	public void should_filter_messages_older_than_1h() {
		chatServer.setMessage("mensagem");
		chatServer.sendMsg();
		chatServer.setMessage("mensagem");
		chatServer.sendMsg();
		chatServer.setMessage("mensagem");
		chatServer.sendMsg();
		
		Message m = new Message();
		m.setText("kdfj");
		m.setSender(new User("user1", "p", false));
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MINUTE, -59);
		m.setDate(date);
		chatServer.getMessages().add(m);
		
		Message m2 = new Message();
		m2.setText("kdfj");
		m2.setSender(new User("user1", "p", false));
		GregorianCalendar date2 = new GregorianCalendar();
		date2.add(Calendar.MINUTE, -61);
		m2.setDate(date2);
		chatServer.getMessages().add(m2);
		
		assertThat(chatServer.getMessages().size(), is(equalTo(5)));
		assertThat(chatServer.getMyMessages().size(), is(equalTo(4)));
	}
}
