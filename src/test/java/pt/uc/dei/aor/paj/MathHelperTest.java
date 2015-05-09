package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MathHelperTest {
	
	// operator tests
	@Test
	public void should_concat_operation_to_number() {
		List<String> entries = new ArrayList<>();
		entries.add("3.14");
		MathHelper.concat(entries, "+");
		assertThat(entries.get(entries.size()-1), is(equalTo("+")));
	}
	
	@Test
	public void should_replace_operations() {
		List<String> entries = new ArrayList<>();
		entries.add("3.14");
		entries.add("*");
		MathHelper.concat(entries, "+");
		assertThat(entries.get(entries.size()-1), is(equalTo("+")));
		assertThat(entries.get(entries.size()-2), is(not(equalTo("*"))));
	}
	
	// digit tests
	
	
	// dot tests
	@Test
	public void should_not_concat_dot_to_decimal_number() {
		List<String> entries = new ArrayList<>();
		entries.add("3.14");
		MathHelper.concat(entries, ".");
		assertThat(entries.get(entries.size()-1), is(equalTo("3.14")));
	}
	
	@Test
	public void should_concat_zero_if_dot_follows_operation() {
		List<String> entries = new ArrayList<>();
		entries.add("3.14");
		entries.add("+");
		MathHelper.concat(entries, ".");
		assertThat(entries.get(entries.size()-1), is(equalTo("0.")));
	}
	
	@Test
	public void should_concat_dot_to_number() {
		List<String> entries = new ArrayList<>();
		entries.add("3");
		MathHelper.concat(entries, ".");
		assertThat(entries.get(entries.size()-1), is(equalTo("3.")));
	}
	
	
}
