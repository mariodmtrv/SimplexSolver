package org.fmi.or.simplexator.service.serializable;

import java.util.ArrayList;
import java.util.List;

public class PossibleAnswer {
	private List<String[]> directions;
	private List<String[]> points;

	public PossibleAnswer(PossibleAnswer answer) {
		this.directions = answer.directions;
		this.points = answer.points;
	}

	public PossibleAnswer() {
		super();
		this.directions = new ArrayList<>();
		this.points = new ArrayList<>();
	}

	public void addDirection(String[] direction) {
		this.directions.add(direction);
	}

	public void addPoint(String[] point) {
		this.points.add(point);
	}
}
