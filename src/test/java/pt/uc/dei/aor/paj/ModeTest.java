package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;



public class ModeTest {
	
	private Mode mode;
	
	@Before
	public void init() {
		mode = new Mode();
		
	}
	@Test
	public void should_change_mode(){
		mode.changeMode();
		assertThat(mode.getMode(),is(equalTo(1)));
		mode.changeMode();
		assertThat(mode.getMode(),is(equalTo(2)));
	}
	
	@Test
	public void should_set_mode_advanced(){
		mode.setModeAdvanced(true);
		assertThat(mode.isModeAdvanced(), is(equalTo(true)));
		
	}
	

}
