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
	private Calculator calculator;
	private HistoryEntry historyentry;
	
	@Before
	public void init() {
		history = new History();
		screen = new Screen();
		calculator = new Calculator();
		
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
	
	@Test
	public void should_add_reused_result_to_top_of_list(){
		int size = history.getListEntry().size();
		screen.concat("1");
		screen.concat("+");
		screen.concat("2");
		history.addEntry(screen, "3", 100);
		screen = new Screen();
		screen.concat("5");
		screen.concat("+");
		screen.concat("2");
		history.addEntry(screen, "7", 100);
		assertThat(history.getListEntry().size(), is(equalTo(2)));
		history.addEntry(history.getListEntry().get(1).getExpression(), "3", 234L);
		assertThat(history.getListEntry().size(), is(equalTo(size+2)));
	}
}
