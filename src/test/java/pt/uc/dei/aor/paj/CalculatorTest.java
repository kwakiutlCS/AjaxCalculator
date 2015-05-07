package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
	private Calculator calc;
	
	@Before
	public void init() {
		calc = new Calculator();
	}
	
//	@Test
//	public void should_perfom_simple_arithmetic_correctly() {
//		calc.setExpression("1+1");
//		calc.calcExp();
//		assertThat(calc.getExpression(), is(equalTo("2.0")));
//	}
//	
	@Test
	public void should_prevent_division_by_zero() {
		calc.setExpression("1/0");
		calc.calcExp();
		assertThat(calc.getExpression(), is(equalTo("Division by zero!")));
	}
	
	
}
