package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsTest {
	
	private Statistics stats;
	
	@Before
	public void init() {
		stats = new Statistics();
		
	}
	
	@Test
	public void should_add_correctly()  {
		List<String> entries = Arrays.asList(new String[]{"5.3","+","4","-","sin(","(", "4","+","4",")",")"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Addition")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_number_function()  {
		List<String> entries = Arrays.asList(new String[]{"5.3","*","4","sin(","5",")"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_constant_function()  {
		List<String> entries = Arrays.asList(new String[]{"5.3","*","pi","sin(","5",")"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_number_constant()  {
		List<String> entries = Arrays.asList(new String[]{"5.3","*","4","pi"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_number_parenthesis()  {
		List<String> entries = Arrays.asList(new String[]{"5.3","*","4","(","4",")"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_constant_parenthesis()  {
		List<String> entries = Arrays.asList(new String[]{"5.3","*","pi","(","5",")"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_constant_constant()  {
		List<String> entries = Arrays.asList(new String[]{"5.3","*","pi","e"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_constant_number()  {
		List<String> entries = Arrays.asList(new String[]{"5.3","*","pi","4"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_parenthesis_number()  {
		List<String> entries = Arrays.asList(new String[]{"(","5.3","E","4","pi",")", "4"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_parenthesis_constant()  {
		List<String> entries = Arrays.asList(new String[]{"(","5.3","*","pi",")", "e"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_parenthesis_function()  {
		List<String> entries = Arrays.asList(new String[]{"(","5.3","*","pi",")", "asinh(", "0.5", ")"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_add_implicitly_correctly_parenthesis_parenthesis()  {
		List<String> entries = Arrays.asList(new String[]{"(","5.3","*","pi",")", "(", "0.5", ")"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Multiplication")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test
	public void should_recognize_square_as_exponent() {
		List<String> entries = Arrays.asList(new String[]{"5.3","+","pi","^2", "-", "0.5", "^2"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Exponent")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(2)));
	}
	
	@Test 
	public void should_increment_sin_count_when_used() {
		List<String> entries = Arrays.asList(new String[]{"sin(","30"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Sine")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(1)));
	}
	
	@Test 
	public void should_increment_root_count_when_used() {
		List<String> entries = Arrays.asList(new String[]{"root(","30"});
		stats.add(entries);
		
		assertThat(stats.getStats().get(0).getDescription(), is(equalTo("Root")));
		assertThat(stats.getStats().get(0).getCounter(), is(equalTo(1)));
	}
}
