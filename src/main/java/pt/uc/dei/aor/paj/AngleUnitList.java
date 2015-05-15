package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class AngleUnitList implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<AngleUnit> angles;
	private String chosen;
	
	public AngleUnitList() {
		angles = new LinkedList<>();
		angles.add(new AngleUnit("Radians", 1));
		angles.add(new AngleUnit("Degrees", Math.PI/180));
		angles.add(new AngleUnit("Grads", Math.PI/200));
		chosen = angles.get(0).getName();
	}

	public List<AngleUnit> getAngles() {
		return angles;
	}

	public String getChosen() {
		return chosen;
	}

	public void setChosen(String chosen) {
		this.chosen = chosen;
	}
	
	public AngleUnit getAngle() {
		for (AngleUnit a : angles) {
			if (a.getName().equals(chosen)) return a;
		}
		return null;
	}
	
}
