package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class ExperimentalSet implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<ExperimentalPoint> points;
	private int nextId;
	@Inject
	private Data data;
	@Inject
	private Screen screen;
	
	public ExperimentalSet() {
		points = new ArrayList<>();
		nextId = 1;
	}
	
	public void addPoint(String s) {
		points.add(new ExperimentalPoint(s, nextId++));
		Collections.sort(points);
		data.clear();
		for (ExperimentalPoint p : points) {
			data.add(p.getX(), p.getY());
		}
		data.complete();
	}
	
	public String complete() {
		return "/graph";
	}
	
	public void removeAll() {
		points.clear();
		data.clear();
		data.complete();
	}
	
	public String remove(int id) {
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getId() == id) {
				points.remove(i);
				break;
			}
		}
		data.clear();
		data.complete();
		return null;
	}
	
	public String click(String s) {
		if (s.equals("del")) {
			removeAll();
		}
		else if (s.equals("add")) {
			if (screen.getEntries().size() == 3) {
				addPoint(screen.getExpression());
				screen.clear();
			}
		}
		else if (s.equals(",")) {
			screen.sepPoints();
		}
		else if (s.equals("draw")) {
			return complete();
		}
		return null;
	}
	
	public List<ExperimentalPoint> getPoints() { return points; }
}
