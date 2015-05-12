package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
	
	@Before
	public void init() {
		User u1 = new User();
		u1.setPassword("p");
		u1.setUsername("user1");
		User u2 = new User();
		u2.setPassword("p");
		u2.setUsername("user2");
		User u3 = new User();
		u3.setPassword("p");
		u3.setUsername("user3");
		List<User> listUsers = Arrays.asList(new User[]{u1, u2, u3});
		
		when(users.getUsers()).thenReturn(listUsers);
		when(login.getUsername()).thenReturn("user2");
		
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
}
