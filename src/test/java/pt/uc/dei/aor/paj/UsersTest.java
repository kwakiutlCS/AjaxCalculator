package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;


public class UsersTest {
	private Users users;
	private User u1, u2, u3;
	
	@Before
	public void init() {
		users = new Users();
		u1 = new User();
		u1.setUsername("user1");
		u1.setPassword("p");
		u2 = new User();
		u2.setUsername("user2");
		u3 = new User();
		u3.setUsername("user3");
	}
	
	@Test
	public void should_add_users_correctly() {
		int size = users.getUsers().size();
		users.addUser(u1);
		users.addUser(u2);
		users.addUser(u3);
		assertThat(users.getUsers().size(), is(equalTo(size+3)));
	}
	
	@Test
	public void should_return_existing_user() {
		users.addUser(u1);
		users.addUser(u3);
		assertThat(users.getUser("user1"), is(equalTo(u1)));
	}
	
	@Test
	public void should_return_null_when_user_doesnt_exist() {
		users.addUser(u1);
		users.addUser(u3);
		assertThat(users.getUser("user2"), is(equalTo(null)));
	}
	
	@Test
	public void should_return_only_logged_users() {
		int size = users.getUsers().size();
		users.addUser(u1);
		users.addUser(u2);
		users.addUser(u3);
		u1.setLoggedIn(true);
		u3.setLoggedIn(true);
		assertThat(users.getLoggedUsers().size(), is(equalTo(size+2)));
		assertThat(users.getLoggedUsers(), Matchers.not(Matchers.hasItem(u2)));
		assertThat(users.getLoggedUsers(), Matchers.hasItem(u1));
	}
	
}
