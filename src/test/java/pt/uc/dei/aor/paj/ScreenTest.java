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
		screen.evaluate(new AngleUnit("Radianos", 1));
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
		screen.evaluate(new AngleUnit("Radianos", 1));
		screen.concat("mod");
		assertThat(screen.getExpression(), is(equalTo("-0.5mod")));
	}
	
	@Test
	public void should_reset_expression_after_evaluation_with_digit() {
		screen.concat("7.5");
		screen.concat("-");
		screen.concat("8");
		screen.evaluate(new AngleUnit("Radianos", 1));
		screen.concat("3");
		assertThat(screen.getExpression(), is(equalTo("3")));
	}
	
	@Test
	public void should_reset_expression_after_evaluation_with_dot() {
		screen.concat("7.5");
		screen.concat("-");
		screen.concat("8");
		screen.evaluate(new AngleUnit("Radianos", 1));
		screen.concat(".");
		assertThat(screen.getExpression(), is(equalTo("0.")));
	}
	
	@Test
	public void should_be_in_phase_2_after_error() {
		screen.concat("2");
		screen.concat("/");
		screen.concat("0");
		screen.evaluate(new AngleUnit("Radianos", 1));
		assertThat(screen.getPhase(), is(equalTo(2)));
	}
	
	@Test
	public void should_be_in_phase_1_after_evaluation() {
		screen.concat("2");
		screen.concat("/");
		screen.concat("3");
		screen.evaluate(new AngleUnit("Radianos", 1));
		assertThat(screen.getPhase(), is(equalTo(1)));
	}
	
	@Test
	public void should_be_in_phase_0_after_evaluation_and_operator() {
		screen.concat("2");
		screen.concat("/");
		screen.concat("3");
		screen.evaluate(new AngleUnit("Radianos", 1));
		screen.concat("+");
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	@Test
	public void should_be_in_phase_2_after_error_and_operator() {
		screen.concat("2");
		screen.concat("/");
		screen.evaluate(new AngleUnit("Radianos", 1));
		screen.concat("+");
		assertThat(screen.getPhase(), is(equalTo(2)));
	}
	
	@Test
	public void should_be_in_phase_0_after_error_and_digit() {
		screen.concat("2");
		screen.concat("/");
		screen.evaluate(new AngleUnit("Radianos", 1));
		screen.concat("3");
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	@Test
	public void should_be_in_phase_0_after_error_and_clear() {
		screen.concat("2");
		screen.concat("/");
		screen.evaluate(new AngleUnit("Radianos", 1));
		screen.clear();
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	@Test
	public void should_be_in_phase_0_after_error_and_remove() {
		screen.concat("2");
		screen.concat("/");
		screen.evaluate(new AngleUnit("Radianos", 1));
		screen.remove();
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	@Test
	public void should_be_in_phase_0_after_result_and_clear() {
		screen.concat("2");
		screen.concat("/");
		screen.concat("3");
		screen.evaluate(new AngleUnit("Radianos", 1));
		screen.clear();
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	@Test
	public void should_be_in_phase_0_after_result_and_remove() {
		screen.concat("2");
		screen.concat("/");
		screen.concat("3");
		screen.evaluate(new AngleUnit("Radianos", 1));
		screen.remove();
		assertThat(screen.getPhase(), is(equalTo(0)));
	}
	
	
	// expresssion concatenation
	@Test
	public void should_reuse_expression_correctly() {
		Screen other = new Screen();
		other.concat("2");
		other.concat("+");
		other.concat("4");
		screen.add(other);
		assertThat(screen.getEntries().size(), is(equalTo(3)));
		assertThat(screen.getExpression(), is(equalTo("2+4")));
	}
	
	@Test
	public void should_reuse_result_correctly() {
		screen.add("3.1");
		assertThat(screen.getEntries().size(), is(equalTo(1)));
		assertThat(screen.getExpression(), is(equalTo("3.1")));
	}
	
	@Test
	public void should_reuse_expression_after_operator() {
		Screen other = new Screen();
		other.concat("2");
		other.concat("+");
		other.concat("4");
		screen.concat("4");
		screen.concat("+");
		screen.add(other);
		assertThat(screen.getExpression(), is(equalTo("4+(2+4)")));
		assertThat(screen.getEntries().size(), is(equalTo(7)));
	}
	
	@Test
	public void should_reuse_expression_after_function() {
		Screen other = new Screen();
		other.concat("2");
		other.concat("+");
		other.concat("4");
		screen.concat("sin(");
		screen.add(other);
		assertThat(screen.getExpression(), is(equalTo("sin(2+4")));
		assertThat(screen.getEntries().size(), is(equalTo(4)));
	}
	
	@Test
	public void should_reuse_expression_after_parenthesis() {
		Screen other = new Screen();
		other.concat("2");
		other.concat("+");
		other.concat("4");
		screen.concat("4");
		screen.concat("+");
		screen.concat("(");
		screen.add(other);
		assertThat(screen.getExpression(), is(equalTo("4+(2+4")));
		assertThat(screen.getEntries().size(), is(equalTo(6)));
	}
	
	@Test 
	public void should_not_reuse_expression_if_passes_screen_size_limit_after_operator() {
		Screen other = new Screen();
		other.concat("2");
		other.concat("+");
		other.concat("4");
		other.concat("+");
		other.concat("4");
		screen.concat("4");
		screen.concat("+");
		screen.add(other);
		screen.concat("+");
		screen.add(other);
		screen.concat("-");
		assertThat(screen.getExpression().length(), is(equalTo(18)));
		screen.add(other);
		assertThat(screen.getExpression().length(), is(equalTo(18)));
	}
	
	@Test 
	public void should_not_reuse_expression_if_passes_screen_size_limit_after_function() {
		Screen other = new Screen();
		other.concat("2");
		other.concat("+");
		other.concat("4");
		other.concat("+");
		other.concat("4");
		screen.concat("4");
		screen.concat("+");
		screen.add(other);
		screen.concat("+");
		screen.add(other);
		screen.concat("sin(");
		assertThat(screen.getExpression().length(), is(equalTo(21)));
		screen.add(other);
		assertThat(screen.getExpression().length(), is(equalTo(21)));
	}
	
	@Test 
	public void should_not_reuse_expression_if_passes_screen_size_limit_after_parenthesis() {
		Screen other = new Screen();
		other.concat("2");
		other.concat("+");
		other.concat("4");
		other.concat("+");
		other.concat("4");
		screen.concat("4");
		screen.concat("+");
		screen.add(other);
		screen.concat("+");
		screen.add(other);
		screen.concat("(");
		assertThat(screen.getExpression().length(), is(equalTo(18)));
		screen.add(other);
		assertThat(screen.getExpression().length(), is(equalTo(18)));
	}
	
	@Test 
	public void should_not_reuse_expression_if_passes_screen_size_with_long_number() {
		Screen other = new Screen();
		other.concat("2");
		other.concat("+");
		other.concat("4");
		other.concat("+");
		other.concat("4");
		screen.concat("4");
		screen.concat("+");
		screen.add(other);
		screen.concat("+");
		screen.add(other);
		screen.concat("(");
		assertThat(screen.getExpression().length(), is(equalTo(18)));
		other = new Screen();
		other.concat("2.2314568");
		screen.add(other);
		assertThat(screen.getExpression().length(), is(equalTo(18)));
	}
	
	@Test
	public void should_not_evaluate_after_error() {
		screen.concat("1");
		screen.concat("/");
		screen.concat("0");
		screen.evaluate(new AngleUnit("Radianos", 1));
		assertThat(screen.getPhase(), is(equalTo(2)));
		String error = screen.getExpression();
		screen.evaluate(new AngleUnit("Radianos", 1));
		assertThat(screen.getExpression(), is(equalTo(error)));
	}
}
