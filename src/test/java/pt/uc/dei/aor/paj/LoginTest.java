package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {
	
	@Mock
	private Users users;

	
	@InjectMocks
	private Login login;
	
	@Before
	public void init(){
		
		
		
		
	}
	
	
	@Test
	public void should_perform_login(){
		
		login.setUsername("a");
		login.setPassword("a");	    
	   
	    when(users.getUser("a")).thenReturn(new User("a", "a", false)); 		
	    assertThat(login.login(), is(equalTo("calculator/index?faces-redirect=true")));		
	}
	
	@Test
	public void should_not_perform_login(){
		login.setUsername("a");
		login.setPassword("a");
		
		when(users.getUser("a")).thenReturn(new User("a", "b", false)); 	
		assertThat(login.login(), is(equalTo(null)));	
	}
	
	@Test
	public void should_not_perform_login_with_no_user(){
		login.setUsername("a");
		login.setPassword("a");
		
		when(users.getUser("a")).thenReturn(null); 	
		assertThat(login.login(), is(equalTo(null)));	
	}
	
	
	@Test
	
	public void should_perform_logout(){
		

		login.setUsername("a");
		login.setPassword("a");	
		
		when(users.getUser("a")).thenReturn(new User("a", "a", true));
		 assertThat(login.logout(), is(equalTo("/login?faces-redirect=true")));	
		
	}

}
