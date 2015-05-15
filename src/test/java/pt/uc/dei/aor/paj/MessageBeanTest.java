package pt.uc.dei.aor.paj;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MessageBeanTest {

	private MessageBean bean = new MessageBean();
	

	@Test
	public void should_prepare_secret_correctly() {
		bean.prepareSecret("user1");
		assertThat(bean.getText(), is(equalTo("/secret user1 ")));
	}
	
	
}
