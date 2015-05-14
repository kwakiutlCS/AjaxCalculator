package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegisterTest {
	
	@Mock
	private Users users;
	@Mock
	private Login login;
	
	@InjectMocks
	private Register register;
	
	
	
	@Test
	public void should_not_register_with_password_and_confirm_differents() {
		register.setUsername("palerma");
		register.setPassword("a");
		register.setConfpassword("b");
		assertThat(register.confirmRegister(null), is(equalTo(null)));
	}
	
	
	@Test
	public void should_register_with_valid_password_and_confirm() {
		register.setUsername("palerma");
		register.setPassword("a");
		register.setConfpassword("a");
		assertThat(register.confirmRegister(null), is(equalTo("/calculator/index?faces-redirect=true")));
	}
	
	@Test
	public void should_not_register_with_no_username() {
		register.setUsername("");
		register.setPassword("a");
		register.setConfpassword("a");
		assertThat(register.confirmRegister(null), is(equalTo(null)));
	}
	
	@Test
	public void should_not_register_with_username_already_there() {
		Mockito.when(users.getUser("tonto")).thenReturn(new User("tonto", "", false));
		register.setUsername("tonto");
		register.setPassword("a");
		register.setConfpassword("a");
		assertThat(register.confirmRegister(null), is(equalTo(null)));
	}
	
	@Test
	public void should_not_register_with_no_password() {
		register.setUsername("tonto");
		register.setPassword("");
		register.setConfpassword("");
		assertThat(register.confirmRegister(null), is(equalTo(null)));
	}
	
	@Test
	public void should_add_user_with_valid_register() {
		register.setUsername("toto");
		register.setPassword("a");
		register.setConfpassword("a");
		register.confirmRegister(null);
		Mockito.verify(users).addUser(Mockito.any(User.class));
	}
}

