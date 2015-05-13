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
		angleList.setChosen("Grados");
		assertThat(angleList.getAngle().getFactor(), Matchers.is(Matchers.equalTo(Math.PI/200)));
	}
	
	@Test
	public void should_return_correct_factor_for_degrees() {
		angleList.setChosen("Graus");
		assertThat(angleList.getAngle().getFactor(), Matchers.is(Matchers.equalTo(Math.PI/180)));
	}
	
	@Test
	public void should_return_correct_factor_for_radians() {
		angleList.setChosen("Radianos");
		assertThat(angleList.getAngle().getFactor(), Matchers.is(Matchers.equalTo(1.)));
	}
}
