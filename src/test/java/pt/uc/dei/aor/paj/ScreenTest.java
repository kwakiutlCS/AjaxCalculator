package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;


public class ScreenTest {

	private Screen screen;
	
	@Before
	public void init() {
		screen = new Screen();
	}
	
	
	@Test
	public void should_initialize_screen_with_zero() {
		assertThat(screen.getEntries().size(), is(equalTo(1)));
		assertThat(screen.getEntries().get(0), is(equalTo("0")));
	}
	
	@Test
	public void should_change_expression_when_concat_called() {
		screen.concat("4");
		screen.concat("/");
		assertThat(screen.getExpression(), is(equalTo("4/")));
	}
	
	@Test
	public void should_clear_screen_correctly() {
		screen.concat("7");
		screen.concat("-");
		screen.concat("8");
		assertThat(screen.getExpression(), is(equalTo("7-8")));
		screen.clear();
		assertThat(screen.getExpression(), is(equalTo("0")));
	}
	
	@Test
	public void should_delete_entry_correctly() {
		screen.concat("7");
		screen.concat("-");
		screen.concat("8.8");
		assertThat(screen.getExpression(), is(equalTo("7-8.8")));
		screen.remove();
		assertThat(screen.getExpression(), is(equalTo("7-")));
	}
	
	
	@Test
	public void should_evaluate_expression_correctly() {
		screen.concat("7.2");
		screen.concat("-");
		screen.concat("8");
		screen.concat("/");
		screen.concat("2");
		screen.concat("^");
		screen.concat("2");
		assertThat(screen.getExpression(), is(equalTo("7.2-8/2^2")));
		screen.evaluate();
		assertThat(screen.getExpression(), is(equalTo("5.2")));
	}
	
	@Test
	public void should_clone_expression_correctly() {
		screen.concat("7");
		screen.concat("-");
		screen.concat("8");
		Screen sc = screen.getClone();
		assertThat(screen, is(equalTo(sc)));
		assertThat(screen.getEntries(), is(equalTo(sc.getEntries())));
		assertThat(screen.getExpression(), is(equalTo(sc.getExpression())));
		assertThat(screen.getPhase(), is(equalTo(sc.getPhase())));
	}
	
	@Test
	public void should_continue_expression_after_evaluation_with_operator() {
		screen.concat("7.5");
		screen.concat("-");
		screen.concat("8");
		screen.evaluate();
		screen.concat("mod");
		assertThat(screen.getExpression(), is(equalTo("-0.5mod")));
	}
	
	@Test
	public void should_reset_expression_after_evaluation_with_digit() {
		screen.concat("7.5");
		screen.concat("-");
		screen.concat("8");
		screen.evaluate();
		screen.concat("3");
		assertThat(screen.getExpression(), is(equalTo("3")));
	}
	
	@Test
	public void should_reset_expression_after_evaluation_with_dot() {
		screen.concat("7.5");
		screen.concat("-");
		screen.concat("8");
		screen.evaluate();
		screen.concat(".");
		assertThat(screen.getExpression(), is(equalTo("0.")));
	}
	
	@Test
	public void should_be_in_phase_2_after_error() {
		screen.concat("2");
		screen.concat("/");
		screen.concat("0");
		screen.evaluate();
		assertThat(screen.getPhase(), is(equalTo(2)));
	}
	
	@Test
	public void should_be_in_phase_1_after_evaluation() {
		screen.concat("2");
		screen.concat("/");
		screen.concat("3");
		screen.evaluate();
		assertThat(screen.getPhase(), is(equalTo(1)));
	}
	
	@Test
	public void should_be_in_phase_0_after_evaluation_and_operator() {
		screen.concat("2");
		screen.concat("/");
		screen.concat("3");
		screen.evaluate();
		screen.concat("+");
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	@Test
	public void should_be_in_phase_2_after_error_and_operator() {
		screen.concat("2");
		screen.concat("/");
		screen.evaluate();
		screen.concat("+");
		assertThat(screen.getPhase(), is(equalTo(2)));
	}
	
	@Test
	public void should_be_in_phase_0_after_error_and_digit() {
		screen.concat("2");
		screen.concat("/");
		screen.evaluate();
		screen.concat("3");
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	@Test
	public void should_be_in_phase_0_after_error_and_clear() {
		screen.concat("2");
		screen.concat("/");
		screen.evaluate();
		screen.clear();
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	@Test
	public void should_be_in_phase_0_after_error_and_remove() {
		screen.concat("2");
		screen.concat("/");
		screen.evaluate();
		screen.remove();
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	@Test
	public void should_be_in_phase_0_after_result_and_clear() {
		screen.concat("2");
		screen.concat("/");
		screen.concat("3");
		screen.evaluate();
		screen.clear();
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	@Test
	public void should_be_in_phase_0_after_result_and_remove() {
		screen.concat("2");
		screen.concat("/");
		screen.concat("3");
		screen.evaluate();
		screen.remove();
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
}
