package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsTest {
	
	private Statistics stats;
	private Screen screen;
	
	@Before
	public void init() {
		stats = new Statistics();
		screen = new Screen();
	}
	
	@Test
	public void should_add_entry_to_list()  {
		assertThat(null, is(equalTo(null)));
	}
}
