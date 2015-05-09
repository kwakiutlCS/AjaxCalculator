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
	
	
	// evaluation tests
	
	// phase 1 tests
}
