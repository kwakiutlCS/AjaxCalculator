package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {
	
	@Mock
	private Statistic stat;
	@Mock
	private History hist;
	@Mock 
	private Statistics2 stat2;
	
	@InjectMocks
	private Calculator calc;
	
	@Before
	public void init() {
//		doNothing().when(stat).addStat(anyString());
//		doNothing().when(hist).addHist(anyString());
//		doNothing().when(hist).addEntry(anyString(), anyString(), Mockito.anyLong());
//		doNothing().when(stat2).add(anyString());
	}
	
	@Test
	public void should_perfom_simple_arithmetic_correctly() {
		calc.setExpression("1+1");
		calc.calcExp();
		assertThat(calc.getExpression(), is(equalTo("2.0")));
	}
	
	@Test
	public void should_perform_long_arithmetic_correctly() {
		calc.setExpression("1+6/3-6^2+3*7+6/3");
		calc.calcExp();
		assertThat(calc.getExpression(), is(equalTo("-10.0")));
	}
	
	@Test
	public void should_perform_trignometric_correctly() {
		calc.setExpression("sin(pi/2)");
		calc.calcExp();
		assertThat(calc.getExpression(), is(equalTo("1.0")));	
	}
	
	@Test
	public void should_perform_logarithm_correctly() {
		calc.setExpression("log(e^7)+log10(100000)");
		calc.calcExp();
		assertThat(calc.getExpression(), is(equalTo("12.0")));
	}

	@Test
	public void should_prevent_division_by_zero() {
		calc.setExpression("1/0");
		calc.calcExp();
		assertThat(calc.getExpression(), is(equalTo("Division by zero!")));
	}
	
	
}
