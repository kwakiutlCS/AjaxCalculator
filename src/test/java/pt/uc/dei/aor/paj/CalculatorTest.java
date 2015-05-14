package pt.uc.dei.aor.paj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {
	
	@Mock
	private Statistics stat;
	@Mock
	private History hist;
	@Mock
	private Mode mode;
	@Mock
	private Screen screen;
	@Mock
	private AngleUnitList angleUnit;
	
	@InjectMocks
	private Calculator calc;
	
	
	@Test
	public void should_clear_screen_when_changes_mode_and_mode_1() {
		Mockito.when(mode.getMode()).thenReturn(1);
		calc.changeMode();
		Mockito.verify(screen).clear();
	}
	
	@Test
	public void should_not_clear_screen_when_changes_mode_and_mode_0() {
		Mockito.when(mode.getMode()).thenReturn(0);
		calc.changeMode();
		Mockito.verify(screen, Mockito.never()).clear();
	}
	
	@Test
	public void should_call_concat_with_normal_calculator() {
		Mockito.when(mode.getMode()).thenReturn(0);
		calc.add("+");
		Mockito.verify(screen).concat("+");
	}
	
	@Test
	public void should_call_concat_with_graph_calculator() {
		Mockito.when(mode.getMode()).thenReturn(2);
		Mockito.when(mode.isModeAdvanced()).thenReturn(true);
		calc.add("2");
		Mockito.verify(screen).graphConcat("2");
	}
	
	@Test
	public void should_call_screen_clone_when_calc_exp() {
		AngleUnit angle = new AngleUnit("Radianos", 1);
		Mockito.when(angleUnit.getAngle()).thenReturn(angle);
		calc.calcExp();
		Mockito.verify(screen).getClone();
	}
	
	@Test
	public void should_call_screen_evaluate_when_calc_exp() {
		AngleUnit angle = new AngleUnit("Radianos", 1);
		Mockito.when(angleUnit.getAngle()).thenReturn(angle);
		calc.calcExp();
		Mockito.verify(screen).evaluate(angle);
	}
	
	
}
