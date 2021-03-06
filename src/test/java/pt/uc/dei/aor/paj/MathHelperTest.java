package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.number.IsCloseTo.closeTo;
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
	
	@Test
	public void should_separate_digits_from_constants_with_multiplication() {
		entries.add("e");
		MathHelper.concat(entries, "3", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("e*3")));
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
	@Test
	public void should_separate_dot_from_constants_with_multiplication() {
		entries.add("pi");
		MathHelper.concat(entries, ".", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("pi*0.")));
	}
	
	@Test
	public void should_not_concat_dot_after_science_notation() {
		entries.add("3");
		MathHelper.concat(entries, "E", 0);
		MathHelper.concat(entries, ".", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3E")));
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
		MathHelper.concat(entries, "sinh(", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3sinh(")));
	}
	
	@Test
	public void should_add_functions_correctly_after_evaluation() {
		entries.add("3");
		MathHelper.concat(entries, "atanh(", 1);
		assertThat(MathHelper.formExpression(entries), is(equalTo("atanh(3)")));
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
	
	@Test
	public void should_separate_constants_and_functions_with_multiplication() {
		entries.add("pi");
		MathHelper.concat(entries, "acos(", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("pi*acos(")));
	}
	
	// constants tests
	@Test
	public void should_add_constant_correctly() {
		entries.add("3");
		MathHelper.concat(entries, "pi", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3pi")));
		assertThat(entries.size(), is(equalTo(2)));
	}
	
	@Test
	public void should_separate_constants_with_multiplication() {
		entries.add("3");
		MathHelper.concat(entries, "pi", 0);
		MathHelper.concat(entries, "e", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3pi*e")));
	}
	
	@Test
	public void should_not_allow_constant_after_E() {
		entries.add("3");
		MathHelper.concat(entries, "E", 0);
		MathHelper.concat(entries, "pi", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3E")));
	}
	
	// unuary operator tests
	@Test
	public void should_unuary_operators_correctly() {
		entries.add("3");
		MathHelper.concat(entries, "!", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3!")));
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
	
	@Test
	public void should_not_concat_digit_after_unuary() {
		entries.add("5");
		MathHelper.concat(entries, "^2", 0);
		MathHelper.concat(entries, "6", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5^2")));
	}
	
	@Test
	public void should_not_concat_dot_after_unuary() {
		entries.add("5");
		MathHelper.concat(entries, "^2", 0);
		MathHelper.concat(entries, ".", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5^2")));
	}
	
	@Test
	public void should_accept_unuary_after_parenthesis() {
		entries.add("(");
		MathHelper.concat(entries, "5", 0);
		MathHelper.concat(entries, "+", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.concat(entries, "^2", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("(5+3)^2")));
	}
	
	// scientific notation tests
	@Test
	public void should_concat_science_notation_correctly() {
		entries.add("3");
		MathHelper.concat(entries, "E", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3E")));
	}
	
	@Test
	public void should_not_concat_science_notation_redundantly() {
		entries.add("3");
		MathHelper.concat(entries, "E", 0);
		MathHelper.concat(entries, "4", 0);
		MathHelper.concat(entries, "E", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3E4")));
	}
	
	@Test
	public void should_not_concat_science_notation_after_operator() {
		entries.add("3");
		MathHelper.concat(entries, "+", 0);
		MathHelper.concat(entries, "E", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3+")));
	}
	
	@Test
	public void should_not_concat_science_notation_after_unuary() {
		entries.add("3");
		MathHelper.concat(entries, "^2", 0);
		MathHelper.concat(entries, "E", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3^2")));
	}
	
	@Test
	public void should_not_concat_science_notation_after_evaluation() {
		entries.add("3");
		MathHelper.concat(entries, "E", 1);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3")));
	}
	
	@Test
	public void should_remove_dot_if_concat_science_notation() {
		entries.add("7.");
		MathHelper.concat(entries, "E", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("7E")));
	}
	
	@Test
	public void should_not_add_dot_after_science_notation() {
		entries.add("3");
		MathHelper.concat(entries, "E", 0);
		MathHelper.concat(entries, "4", 0);
		MathHelper.concat(entries, ".", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("3E4")));
	}
	
	// parenthesis tests
	@Test
	public void should_replace_zero_when_start_with_parenthesis() {
		entries.add("0");
		MathHelper.concat(entries, "(", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("(")));
	}
	
	@Test
	public void should_add_left_parenthesis_correctly() {
		entries.add("5");
		MathHelper.concat(entries, "(", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5(")));
	}
	
	@Test
	public void should_add_left_parenthesis_after_zero() {
		entries.add("5");
		MathHelper.concat(entries, "+", 0);
		MathHelper.concat(entries, "0", 0);
		MathHelper.concat(entries, "(", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5+(")));
	}
	
	@Test
	public void should_add_left_parenthesis_after_dot() {
		entries.add("5");
		MathHelper.concat(entries, "+", 0);
		MathHelper.concat(entries, ".", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5+0.")));
		MathHelper.concat(entries, "(", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5+(")));
	}
	
	@Test
	public void should_add_left_parenthesis_correctly_after_error() {
		entries.add("5");
		MathHelper.concat(entries, "(", 2);
		assertThat(MathHelper.formExpression(entries), is(equalTo("(")));
	}
	
	@Test
	public void should_add_left_parenthesis_correctly_after_result() {
		entries.add("5");
		MathHelper.concat(entries, "(", 1);
		assertThat(MathHelper.formExpression(entries), is(equalTo("(")));
	}
	
	@Test
	public void should_add_right_parenthesis_correctly() {
		entries.add("5");
		MathHelper.concat(entries, "mod", 0);
		MathHelper.concat(entries, "(", 0);
		MathHelper.concat(entries, "1", 0);
		MathHelper.concat(entries, "+", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, ")", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5mod(1+3)")));
	}
	
	@Test
	public void should_not_add_parenthesis_without_argument() {
		entries.add("5");
		MathHelper.concat(entries, "^", 0);
		MathHelper.concat(entries, "(", 0);
		MathHelper.concat(entries, ")", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5^(")));
	}
	
	@Test
	public void should_not_add_parenthesis_after_function() {
		entries.add("5");
		MathHelper.concat(entries, "-", 0);
		MathHelper.concat(entries, "tan(", 0);
		MathHelper.concat(entries, ")", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5-tan(")));
	}
	
	@Test
	public void should_not_add_parenthesis_that_dont_close_another() {
		entries.add("5");
		MathHelper.concat(entries, ")", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5")));
	}
	
	@Test
	public void should_not_add_parenthesis_that_dont_close_another_or_function() {
		entries.add("5");
		MathHelper.concat(entries, "(", 0);
		MathHelper.concat(entries, "cos(", 0);
		MathHelper.concat(entries, "(", 0);
		MathHelper.concat(entries, "2", 0);
		MathHelper.concat(entries, "pi", 0);
		MathHelper.concat(entries, "+", 0);
		MathHelper.concat(entries, "50", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.concat(entries, "*", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.concat(entries, "/", 0);
		MathHelper.concat(entries, "5", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.concat(entries, ")", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5(cos((2pi+50)*3)/5)")));
	}
	
	@Test
	public void should_not_close_parenthesis_after_operator() {
		entries.add("(");
		MathHelper.concat(entries, "5", 0);
		MathHelper.concat(entries, "*", 0);
		MathHelper.concat(entries, ")", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("(5*")));
	}
	
	// +/- tests
	@Test
	public void should_negate_correctly() {
		entries.add("5");
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(2)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("-5")));
	}
	
	@Test
	public void should_ignore_zero_when_symmetric_clicked() {
		entries.add("0");
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(1)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("0")));
	}
	
	@Test
	public void should_negate_correctly_with_parenthesis() {
		entries.add("5");
		MathHelper.concat(entries, "/", 0);
		MathHelper.concat(entries, "tanh(", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.concat(entries, "+/-", 0);
		//assertThat(entries.size(), is(equalTo(8)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("5/(-tanh(3))")));
	}
	
	@Test
	public void should_ignore_with_operators() {
		entries.add("5");
		MathHelper.concat(entries, "/", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(2)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("5/")));
	}
	
	@Test
	public void should_ignore_with_unuary() {
		entries.add("5");
		MathHelper.concat(entries, "^2", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(2)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("5^2")));
	}
	
	@Test
	public void should_ignore_with_functions() {
		entries.add("5");
		MathHelper.concat(entries, "cos(", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(2)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("5cos(")));
	}
	
	@Test
	public void should_ignore_with_science_notation() {
		entries.add("5");
		MathHelper.concat(entries, "E", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(2)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("5E")));
	}
	
	@Test
	public void should_ignore_with_dots() {
		entries.add("5");
		MathHelper.concat(entries, ".", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(1)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("5.")));
	}
	
	@Test
	public void should_ignore_with_open_parenthesis() {
		entries.add("5");
		MathHelper.concat(entries, "(", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(2)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("5(")));
	}
	
	@Test
	public void should_revert_to_original_if_double_negate() {
		entries.add("5");
		MathHelper.concat(entries, "+/-", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(1)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("5")));
	}
	
	@Test
	public void should_negate_composite_expression() {
		entries.add("6");
		MathHelper.concat(entries, "+", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(3)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("6-3")));
	}
	
	@Test
	public void should_revert_to_original_if_double_negate_composite() {
		entries.add("5");
		MathHelper.concat(entries, "*", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, "+/-", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(entries.size(), is(equalTo(3)));
		assertThat(MathHelper.formExpression(entries), is(equalTo("5*3")));
	}
	
	@Test
	public void should_negate_after_parenthesis() {
		entries.add("5");
		MathHelper.concat(entries, "*", 0);
		MathHelper.concat(entries, "sin(", 0);
		MathHelper.concat(entries, "9", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5*sin(-9")));
		assertThat(entries.size(), is(equalTo(5)));
	}
	
	@Test
	public void should_revert_functions() {
		entries.add("asin(");
		MathHelper.concat(entries, "5", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.concat(entries, "+/-", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("asin(5)")));
		assertThat(entries.size(), is(equalTo(3)));
	}
	
	@Test
	public void should_revert_functions_after_operator() {
		entries.add("5");
		MathHelper.concat(entries, "*", 0);
		MathHelper.concat(entries, "sqrt(", 0);
		MathHelper.concat(entries, "5", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.concat(entries, "+/-", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5*(-sqrt(5))")));
		MathHelper.concat(entries, "+/-", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("5*sqrt(5)")));
		assertThat(entries.size(), is(equalTo(5)));
	}
	
	
	
	// getLastExpression tests
	@Test 
	public void should_return_null_for_empty_entries() {
		entries.clear();
		assertThat(MathHelper.getLastExpression(entries), is(equalTo(0)));
	}
	
	@Test
	public void should_return_simple_numbers() {
		entries.add("3");
		entries.add("5");
		entries.add("-");
		entries.add("4");
		assertThat(MathHelper.getLastExpression(entries), is(equalTo(1)));
	}
	
	@Test
	public void should_return_decimal_numbers() {
		entries.add("3");
		entries.add("5");
		entries.add("-");
		entries.add("4.3");
		assertThat(MathHelper.getLastExpression(entries), is(equalTo(1)));
	}
	
	@Test
	public void should_return_scientific_notation_numbers() {
		entries.add("3");
		entries.add("5");
		entries.add("-");
		entries.add("4.3");
		entries.add("E");
		entries.add("3");
		assertThat(MathHelper.getLastExpression(entries), is(equalTo(3)));
	}
	
	@Test
	public void should_return_complex_expressions() {
		entries.add("3");
		entries.add("5");
		entries.add("-");
		entries.add("(");
		entries.add("7");
		entries.add("/");
		entries.add("9");
		entries.add(")");
		assertThat(MathHelper.getLastExpression(entries), is(equalTo(5)));
	}
	
	@Test
	public void should_return_functions() {
		entries.add("3");
		entries.add("5");
		entries.add("-");
		entries.add("cos(");
		entries.add("8");
		entries.add("-");
		entries.add("(");
		entries.add("7");
		entries.add("/");
		entries.add("9");
		entries.add(")");
		entries.add(")");
		assertThat(MathHelper.getLastExpression(entries), is(equalTo(9)));
	}
	
	@Test
	public void should_accept_last_expression_with_constants() {
		entries.add("3");
		entries.add("5");
		entries.add("-");
		entries.add("5");
		entries.add("pi");
		assertThat(MathHelper.getLastExpression(entries), is(equalTo(2)));
	}
	
	// evaluation tests
	@Test
	public void should_evaluate_cosh_correctly() {
		entries.add("cosh(");
		MathHelper.concat(entries, "1", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.evaluate(entries);
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(1.54308063, 0.0001)));
	}
	
	@Test
	public void should_evaluate_sinh_correctly() {
		entries.add("sinh(");
		MathHelper.concat(entries, "1", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.evaluate(entries);
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(1.17520119, 0.0001)));
	}
	
	@Test
	public void should_evaluate_tanh_correctly() {
		entries.add("tanh(");
		MathHelper.concat(entries, "1", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.evaluate(entries);
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(0.76159416, 0.0001)));
	}
	
	@Test
	public void should_evaluate_acosh_correctly() {
		entries.add("1.54308063");
		MathHelper.concat(entries, "acosh(", 1);
		MathHelper.evaluate(entries);
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(1, 0.0001)));
	}
	
	@Test
	public void should_evaluate_asinh_correctly() {
		entries.add("1.17520119");
		MathHelper.concat(entries, "asinh(", 1);
		MathHelper.evaluate(entries);
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(1, 0.0001)));
	}
	
	@Test
	public void should_evaluate_atanh_correctly() {
		entries.add("0.76159416");
		MathHelper.concat(entries, "atanh(", 1);
		MathHelper.evaluate(entries);
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(1, 0.0001)));
	}
	
	@Test
	public void should_evaluate_degrees_correctly() {
		entries.add("sin(");
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, "0", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.evaluate(entries, new AngleUnit("Graus", Math.PI/180));
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(0.5, 0.0001)));
	}
	
	@Test
	public void should_evaluate_grads_correctly() {
		entries.add("cosh(");
		MathHelper.concat(entries, "5", 0);
		MathHelper.concat(entries, "0", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.evaluate(entries, new AngleUnit("Grados", Math.PI/200));
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(1.32460909 , 0.0001)));
	}
	
	@Test
	public void should_evaluate_inverse_degrees_correctly() {
		entries.add("0.6557942");
		MathHelper.concat(entries, "atanh(", 1);
		MathHelper.evaluate(entries, new AngleUnit("Graus", Math.PI/180));
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(45, 0.0001)));
	}
	
	@Test
	public void should_evaluate_inverse_grads_correctly() {
		entries.add("0.70710678");
		MathHelper.concat(entries, "acos(", 1);
		MathHelper.evaluate(entries, new AngleUnit("Grados", Math.PI/200));
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(50, 0.0001)));
	}
	
	// phase 1 tests
	@Test
	public void should_not_change_phase_by_clicking_illegal_button() {
		entries.add("5");
		assertThat(MathHelper.concat(entries, "E", 1), is(equalTo(false)));
	}
	
	// helper functions test
	@Test
	public void should_format_integer_number_correctly() {
		assertThat(MathHelper.formatExpression("2.0"), is(equalTo("2")));
	}
	
	@Test
	public void should_limit_screen_size() {
		entries.add("1234123412341234123412");
		MathHelper.concat(entries, "5", 0);
		assertThat(entries.get(0).length(), is(equalTo(22)));
	}
	
	@Test
	public void should_limit_screen_size_with_functions() {
		entries.add("12341234123412341234");
		MathHelper.concat(entries, "atanh(", 0);
		assertThat(entries.size(), is(equalTo(1)));
	}
	
	@Test
	public void should_accept_22_chars_screen() {
		entries.add("123412341234123412341");
		MathHelper.concat(entries, "4", 0);
		assertThat(entries.get(0).length(), is(equalTo(22)));
	}
	
	// auto complete tests
	@Test
	public void should_auto_complete_correctly() {
		entries.add("sin(");
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, "0", 0);
		MathHelper.evaluate(entries, new AngleUnit("Graus", Math.PI/180));
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(0.5, 0.001)));
	}
	
	@Test
	public void should_auto_complete_complex_expressions() {
		entries.add("sin(");
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, "(", 0);
		MathHelper.concat(entries, "7", 0);
		MathHelper.concat(entries, "+", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.evaluate(entries, new AngleUnit("Graus", Math.PI/180));
		assertThat(Double.valueOf(entries.get(0)), is(closeTo(0.5, 0.001)));
	}
	
	// test extra functions 
	@Test 
	public void should_evaluate_any_log() {
		entries.add("logb(");
		MathHelper.concat(entries, "1024", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "2", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		assertThat(MathHelper.formExpression(entries), is(equalTo("10")));
	}
	
	@Test
	public void should_accept_logb_as_function() {
		assertThat(MathHelper.isFunction("logb("), is(equalTo(true)));
	}
	
	
	@Test 
	public void should_evaluate_any_root() {
		entries.add("root(");
		MathHelper.concat(entries, "32", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "5", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		assertThat(MathHelper.formExpression(entries), is(equalTo("2")));
	}
	
	@Test
	public void should_accept_root_as_function() {
		assertThat(MathHelper.isFunction("root("), is(equalTo(true)));
	}
	
	
	@Test
	public void should_accept_factorial() {
		entries.add("4");
		MathHelper.concat(entries, "!", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		assertThat(MathHelper.formExpression(entries), is(equalTo("24")));
	}
	
	@Test
	public void should_accept_expression_factorial() {
		entries.add("(");
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, "+", 0);
		MathHelper.concat(entries, "2", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.concat(entries, "!", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("(3+2)!")));
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		assertThat(MathHelper.formExpression(entries), is(equalTo("120")));
	}
	
	@Test 
	public void should_identify_factorial_as_unuary() {
		assertThat(MathHelper.isUnuaryOperator("!"), is(equalTo(true)));
	}
	

	@Test
	public void should_evaluate_comb_correctly() {
		entries.add("comb(");
		MathHelper.concat(entries, "6", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		assertThat(MathHelper.formExpression(entries), is(equalTo("20")));
	}
	
	@Test
	public void should_evaluate_comb_of_expression_correctly() {
		entries.add("4");
		MathHelper.concat(entries, "comb(", 0);
		MathHelper.concat(entries, "(", 0);
		MathHelper.concat(entries, "6", 0);
		MathHelper.concat(entries, "*", 0);
		MathHelper.concat(entries, "12", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "7", 0);
		MathHelper.concat(entries, "0", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		assertThat(MathHelper.formExpression(entries), is(equalTo("10224")));
	}
	
	@Test
	public void should_evaluate_large_comb() {
		entries.add("0");
		MathHelper.concat(entries, "comb(", 0);
		MathHelper.concat(entries, "7", 0);
		MathHelper.concat(entries, "2", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "1", 0);
		MathHelper.concat(entries, "2", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		String exp = MathHelper.formExpression(entries);
		assertThat(exp.substring(0, 7), is(equalTo("1.53632")));
		assertThat(exp.substring(exp.length()-3, exp.length()), is(equalTo("E13")));
	}
	
	
	@Test
	public void should_accept_comb_as_function() {
		assertThat(MathHelper.isFunction("comb("), is(equalTo(true)));
	}
	

	@Test
	public void should_evaluate_permutations_correctly() {
		entries.add("0");
		MathHelper.concat(entries, "perm(", 0);
		MathHelper.concat(entries, "8", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		assertThat(MathHelper.formExpression(entries), is(equalTo("336")));
	}
	
	@Test
	public void should_evaluate_perm_of_expression_correctly() {
		entries.add("0");
		MathHelper.concat(entries, "perm(", 0);
		MathHelper.concat(entries, "4", 0);
		MathHelper.concat(entries, "*", 0);
		MathHelper.concat(entries, "2", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "6", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		assertThat(MathHelper.formExpression(entries), is(equalTo("20160")));
	}
	
	@Test
	public void should_evaluate_large_perm() {
		entries.add("0");
		MathHelper.concat(entries, "perm(", 0);
		MathHelper.concat(entries, "7", 0);
		MathHelper.concat(entries, "2", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "1", 0);
		MathHelper.concat(entries, "2", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		String exp = MathHelper.formExpression(entries);
		assertThat(exp.substring(0, 7), is(equalTo("7.35903")));
		assertThat(exp.substring(exp.length()-3, exp.length()), is(equalTo("E21")));
	}
	
	@Test
	public void should_evaluate_large_perm2() {
		entries.add("0");
		MathHelper.concat(entries, "perm(", 0);
		MathHelper.concat(entries, "7", 0);
		MathHelper.concat(entries, "2", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, "6", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		String exp = MathHelper.formExpression(entries);
		assertThat(exp.substring(0, 7), is(equalTo("1.64611")));
		assertThat(exp.substring(exp.length()-3, exp.length()), is(equalTo("E62")));
	}
	
	@Test
	public void should_evaluate_large_perm3() {
		entries.add("0");
		MathHelper.concat(entries, "perm(", 0);
		MathHelper.concat(entries, "6", 0);
		MathHelper.concat(entries, "5", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, "2", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		String exp = MathHelper.formExpression(entries);
		assertThat(exp.substring(0, 7), is(equalTo("9.49827")));
		assertThat(exp.substring(exp.length()-3, exp.length()), is(equalTo("E53")));
	}
	
	@Test
	public void should_evaluate_large_perm4() {
		entries.add("0");
		MathHelper.concat(entries, "perm(", 0);
		MathHelper.concat(entries, "5", 0);
		MathHelper.concat(entries, "0", 0);
		MathHelper.concat(entries, "6", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "5", 0);
		MathHelper.concat(entries, "0", 0);
		MathHelper.concat(entries, "5", 0);
		MathHelper.evaluate(entries, new AngleUnit("Radianos", 1));
		String exp = MathHelper.formExpression(entries);
		assertThat(exp, is(equalTo("limit exceeded")));
	}
	
	@Test
	public void should_evaluate_large_get_comb() {
		assertThat(MathHelper.getComb(65, 32, 33), is(equalTo(3.6097142170081326E18)));
	}
	
	@Test
	public void should_evaluate_large_get_comb2() {
		assertThat(MathHelper.getComb(506, 505, 1), is(equalTo(506.)));
	}
	
	@Test
	public void should_perm_as_function() {
		assertThat(MathHelper.isFunction("perm("), is(equalTo(true)));
	}
	
	
	// comma test
	@Test
	public void should_accept_comma() {
		entries.add("logb(");
		MathHelper.concat(entries, "1024", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "2", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("logb(1024,2")));
	}
	
	// infinity test
	@Test
	public void should_show_infinity_as_excess_capacity() {
		entries.add("2");
		MathHelper.concat(entries, "E", 0);
		MathHelper.concat(entries, "200", 0);
		MathHelper.concat(entries, "^2", 0);
		MathHelper.evaluate(entries, new AngleUnit("Graus", Math.PI/180));
		assertThat(MathHelper.formExpression(entries), is(equalTo("limit exceeded")));

	}
	
	@Test
	public void should_show_minus_infinity_as_invalid() {
		entries.add("log(");
		MathHelper.concat(entries, "0", 0);
		MathHelper.evaluate(entries, new AngleUnit("Graus", Math.PI/180));
		assertThat(MathHelper.formExpression(entries), is(equalTo("invalid arguments")));
	}
	
	@Test
	public void should_show_nan_as_invalid() {
		entries.add("log(");
		MathHelper.concat(entries, "3", 0);
		MathHelper.concat(entries, "+/-", 0);
		MathHelper.evaluate(entries, new AngleUnit("Graus", Math.PI/180));
		assertThat(MathHelper.formExpression(entries), is(equalTo("invalid arguments")));
	}
	
	// comma functions test
	@Test
	public void should_recognize_root_as_a_comma_accepting_function() {
		assertThat(MathHelper.acceptsComma("root("), is(equalTo(true)));
	}
	
	@Test
	public void should_recognize_comb_as_a_comma_accepting_function() {
		assertThat(MathHelper.acceptsComma("comb("), is(equalTo(true)));
	}
	
	@Test
	public void should_recognize_perm_as_a_comma_accepting_function() {
		assertThat(MathHelper.acceptsComma("perm("), is(equalTo(true)));
	}
	
	@Test
	public void should_recognize_logb_as_a_comma_accepting_function() {
		assertThat(MathHelper.acceptsComma("logb("), is(equalTo(true)));
	}
	
	@Test 
	public void should_reuse_comma_functions_correctly_after_result() {
		entries.add("4");
		MathHelper.concat(entries, "root(", 1);
		assertThat(MathHelper.formExpression(entries), is(equalTo("root(4")));
	}
	
	@Test 
	public void should_reuse_comma_functions_correctly_after_result2() {
		entries.add("4");
		MathHelper.concat(entries, "logb(", 1);
		assertThat(MathHelper.formExpression(entries), is(equalTo("logb(4")));
	}
	
	
	@Test
	public void should_not_accept_comma_inside_normal_function() {
		entries.add("log(");
		MathHelper.concat(entries, "4", 0);
		MathHelper.concat(entries, ",", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("log(4")));
	}
	
	@Test
	public void should_not_accept_comma_outside_function() {
		entries.add("4");
		MathHelper.concat(entries, ",", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("4")));
	}
	
	@Test
	public void should_not_accept_duplicated_comma() {
		entries.add("logb(");
		MathHelper.concat(entries, "4", 0);
		MathHelper.concat(entries, ",", 0);
		MathHelper.concat(entries, "4", 0);
		MathHelper.concat(entries, ",", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("logb(4,4")));
	}
	
	@Test
	public void should_not_accept_comma_with_open_parenthesis() {
		entries.add("perm(");
		MathHelper.concat(entries, "4", 0);
		MathHelper.concat(entries, "(", 0);
		MathHelper.concat(entries, "4", 0);
		MathHelper.concat(entries, ",", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("perm(4(4")));
	}
	
	@Test
	public void should_accept_comma_after_parenthesis_in_function() {
		entries.add("0");
		MathHelper.concat(entries, "comb(", 0);
		MathHelper.concat(entries, "(", 0);
		MathHelper.concat(entries, "6", 0);
		MathHelper.concat(entries, "*", 0);
		MathHelper.concat(entries, "12", 0);
		MathHelper.concat(entries, ")", 0);
		MathHelper.concat(entries, ",", 0);
		assertThat(MathHelper.formExpression(entries), is(equalTo("comb((6*12),")));
	}
	
	// format history result
	@Test
	public void should_show_result_with_2_decimal_places() {
		assertThat(MathHelper.formatDecimalNumber("1.4567"), is(equalTo("1.45")));
	}
	
	@Test
	public void should_show_result_with_2_decimal_places_no_dot() {
		assertThat(MathHelper.formatDecimalNumber("1"), is(equalTo("1")));
	}
	
	@Test
	public void should_show_result_with_2_decimal_places_science_notation() {
		assertThat(MathHelper.formatDecimalNumber("1.23456E23"), is(equalTo("1.23E23")));
	}
	
	@Test
	public void should_show_result_with_2_decimal_places_science_notation_not_dot() {
		assertThat(MathHelper.formatDecimalNumber("1E23"), is(equalTo("1E23")));
	}

	
}
