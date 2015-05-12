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
		List<User> listUsers = Arrays.asList(new User[]{u1, u2});
		
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
}
