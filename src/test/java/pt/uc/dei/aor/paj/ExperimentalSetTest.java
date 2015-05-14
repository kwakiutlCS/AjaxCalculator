package pt.uc.dei.aor.paj;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentalSetTest {

	@Mock
	private Data data;
	
	@InjectMocks
	private ExperimentalSet set;
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void should_increase_point_list_when_add() {
		int size = set.getPoints().size();
		set.addPoint("4,5");
		assertThat(set.getPoints().size(), Matchers.is(Matchers.equalTo(size+1)));
	}
	
	@Test
	public void should_contain_the_point_when_add() {
		set.addPoint("4,5");
		assertThat(set.getPoints().get(0).getX(), Matchers.is(Matchers.equalTo(4.)));
		assertThat(set.getPoints().get(0).getY(), Matchers.is(Matchers.equalTo(5.)));
	}
	
	@Test
	public void should_remove_all_points() {
		set.addPoint("4,5");
		set.addPoint("3,6");
		set.removeAll();
		assertThat(set.getPoints().size(), Matchers.is(Matchers.equalTo(0)));
	}
	
	@Test
	public void should_remove_1_points() {
		set.addPoint("4,5");
		set.addPoint("3,6");
		set.remove(1);
		assertThat(set.getPoints().size(), Matchers.is(Matchers.equalTo(1)));
	}
}
