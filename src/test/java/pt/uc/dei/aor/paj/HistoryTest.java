package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoryTest {
	
	private History history;
	private Screen screen;
	
	@Before
	public void init() {
		history = new History();
		screen = new Screen();
	}
	
	@Test
	public void should_add_entry_to_list()  {
		int size = history.getListEntry().size();
		screen.concat("1");
		screen.concat("+");
		screen.concat("2");
		history.addEntry(screen, "4", 100);
		assertThat(history.getListEntry().size(), is(equalTo(size+1)));
	}
}
