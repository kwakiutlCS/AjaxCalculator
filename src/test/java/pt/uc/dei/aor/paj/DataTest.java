package pt.uc.dei.aor.paj;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataTest {

	private Data data;
	
	@Before
	public void init() {
		data = new Data();
	}
	
	@Test
	public void should_remove_points_when_clear() {
		data.add(1, 1);
		data.add(2, 2);
		data.setM(23);
		data.clear();
		Assert.assertThat(data.getM(), Matchers.is(Matchers.equalTo(0.)));
	}
	
	@Test
	public void should_compute_correct_regression() {
		data.add(1, 1);
		data.add(2, 2);
		data.complete();
		Assert.assertThat(data.getM(), Matchers.is(Matchers.equalTo(1.)));
		Assert.assertThat(data.getB(), Matchers.is(Matchers.equalTo(0.)));
		Assert.assertThat(data.getR2(), Matchers.is(Matchers.equalTo(1.)));
		
	}
}
