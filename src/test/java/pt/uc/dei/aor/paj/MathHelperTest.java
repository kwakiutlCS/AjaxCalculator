package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MathHelperTest {
	private List<String> entries;
	@Before
	public void init() {
		entries = new ArrayList<>();
	}
	
	// operator tests
	@Test
	public void should_concat_operation_to_number() {
		entries.add("3.14");
		MathHelper.concat(entries, "+", 0);
		assertThat(entries.get(entries.size()-1), is(equalTo("+")));
	}
	
	@Test
	public void should_replace_operations() {
		entries.add("3.14");
		entries.add("*");
		MathHelper.concat(entries, "+", 0);
		assertThat(entries.get(entries.size()-1), is(equalTo("+")));
		assertThat(entries.get(entries.size()-2), is(not(equalTo("*"))));
	}
	
	@Test
	public void should_not_add_binary_after_open_parenthesis() {
		entries.add("0");
		MathHelper.concat(entries, "acos(", 0);
		MathHelper.concat(entries, "*", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("acos(")));
	}
	
	@Test
	public void should_remove_final_dot_click_operator() {
		entries.add("3");
		MathHelper.concat(entries, ".", 0);
		MathHelper.concat(entries, "+", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3+")));
	}
	
	
	// digit tests
	@Test
	public void should_concat_digit_to_another() {
		entries.add("3.14");
		entries.add("*");
		entries.add("3");
		MathHelper.concat(entries, "0", 0);
		assertThat(entries.get(entries.size()-1), is(equalTo("30")));
	}
	
	@Test
	public void should_concat_digit_to_operator() {
		entries.add("3.14");
		entries.add("*");
		MathHelper.concat(entries, "0", 0);
		assertThat(entries.get(entries.size()-1), is(equalTo("0")));
		assertThat(entries.get(entries.size()-2), is(equalTo("*")));
	}
	
	@Test
	public void should_omit_leading_zeros() {
		entries.add("3.14");
		entries.add("*");
		entries.add("0");
		MathHelper.concat(entries, "3", 0);
		assertThat(entries.get(entries.size()-1), is(equalTo("3")));
		assertThat(entries.get(entries.size()-2), is(equalTo("*")));
	}
	
	
	// dot tests
	@Test
	public void should_not_concat_dot_to_decimal_number() {
		entries.add("3.14");
		MathHelper.concat(entries, ".", 0);
		assertThat(entries.get(entries.size()-1), is(equalTo("3.14")));
	}
	
	@Test
	public void should_concat_zero_if_dot_follows_operation() {
		entries.add("3.14");
		entries.add("+");
		MathHelper.concat(entries, ".", 0);
		assertThat(entries.get(entries.size()-1), is(equalTo("0.")));
	}
	
	@Test
	public void should_concat_dot_to_number() {
		entries.add("3");
		MathHelper.concat(entries, ".", 0);
		assertThat(entries.get(entries.size()-1), is(equalTo("3.")));
	}
	
	
	// error tests
	@Test
	public void should_delete_error_when_digit_clicked() {
		entries.add("Division by zero!");
		MathHelper.concat(entries, "7", 2);
		assertThat(entries.get(0), is(equalTo("7")));
		assertThat(entries.size(), is(equalTo(1)));
	}
	
	@Test
	public void should_delete_error_when_dot_clicked() {
		entries.add("Division by zero!");
		MathHelper.concat(entries, ".", 2);
		assertThat(entries.get(0), is(equalTo("0.")));
		assertThat(entries.size(), is(equalTo(1)));
	}
	
	@Test
	public void should_ignore_operator_click_when_error() {
		entries.add("Division by zero!");
		MathHelper.concat(entries, "^", 2);
		assertThat(entries.get(0), is(equalTo("Division by zero!")));
		assertThat(entries.size(), is(equalTo(1)));
	}
	
	// form expression test
	@Test
	public void should_convert_entries_to_expression_correctly() {
		entries.add("0");
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, "+", 0);
		MathHelper.concat(entries, "4", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3+4")));
	}
	
	// format integer expression
	@Test
	public void should_represent_integer_values_without_dot() {
		entries.add("3");
		assertThat(MathHelper.formExpression(entries), is(equalTo("3")));
	}
	
	@Test
	public void should_represent_integer_values_without_dot_after_evaluation() {
		entries.add("3");
		entries.add("%");
		entries.add("2");
		MathHelper.evaluate(entries);
		assertThat(MathHelper.formExpression(entries), is(equalTo("1")));
	}
	
	// functions tests
	@Test
	public void should_add_functions_correctly() {
		entries.add("3");
		MathHelper.concat(entries, "sin(", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3sin(")));
	}
	
	@Test
	public void should_add_functions_correctly_after_evaluation() {
		entries.add("3");
		MathHelper.concat(entries, "atan(", 1);
		assertThat(MathHelper.formExpression(entries), is(equalTo("atan(3)")));
	}
	
	@Test
	public void should_add_functions_correctly_after_error() {
		entries.add("Division by zero!");
		MathHelper.concat(entries, "log(", 2);
		assertThat(MathHelper.formExpression(entries), is(equalTo("log(")));
	}
	
	@Test
	public void should_remove_zero_if_clicked_function() {
		entries.add("3.2");
		entries.add("^");
		entries.add("0");
		MathHelper.concat(entries, "sqrt(", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3.2^sqrt(")));
	}
	
	@Test
	public void should_remove_initial_zero_if_clicked_function() {
		entries.add("0");
		MathHelper.concat(entries, "cos(", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("cos(")));
	}
	
	@Test
	public void should_remove_final_dot_click_function() {
		entries.add("3");
		MathHelper.concat(entries, ".", 0);
		MathHelper.concat(entries, "log10(", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3log10(")));
	}
	
	
	
	// unuary operator tests
	@Test
	public void should_unuary_operators_correctly() {
		entries.add("3");
		MathHelper.concat(entries, "^2", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3^2")));
	}
	
	@Test
	public void should_not_add_unuary_after_open_parenthesis() {
		entries.add("0");
		MathHelper.concat(entries, "asin(", 0);
		MathHelper.concat(entries, "^2", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("asin(")));
	}
	
	@Test
	public void should_remove_final_dot_click_unuary() {
		entries.add("3");
		MathHelper.concat(entries, ".", 0);
		MathHelper.concat(entries, "^2", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3^2")));
	}
	
	// evaluation tests
	
	// phase 1 tests
	
	
	// helper functions test
	@Test
	public void should_format_integer_number_correctly() {
		assertThat(MathHelper.formatExpression("2.0"), is(equalTo("2")));
	}
}
