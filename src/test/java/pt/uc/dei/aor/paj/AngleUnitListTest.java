package pt.uc.dei.aor.paj;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class AngleUnitListTest {

	private AngleUnitList angleList;
	
	@Before
	public void init() {
		angleList = new AngleUnitList();
	}
	
	@Test
	public void should_return_correct_factor_for_grads() {
		angleList.setChosen("Grads");
		assertThat(angleList.getAngle().getFactor(), Matchers.is(Matchers.equalTo(Math.PI/200)));
	}
	
	@Test
	public void should_return_correct_factor_for_degrees() {
		angleList.setChosen("Degrees");
		assertThat(angleList.getAngle().getFactor(), Matchers.is(Matchers.equalTo(Math.PI/180)));
	}
	
	@Test
	public void should_return_correct_factor_for_radians() {
		angleList.setChosen("Radians");
		assertThat(angleList.getAngle().getFactor(), Matchers.is(Matchers.equalTo(1.)));
	}
}
