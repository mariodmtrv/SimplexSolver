package org.fmi.or.simplexator.service.serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.answerer.Answer;
import org.fmi.or.simplexator.algorithm.converter.Fraction;

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
	
	public PossibleAnswer(Answer problemAnswer) {
		// convert directions and vertices to List<String[]>
		
		this.directions = new ArrayList<>();
		this.points = new ArrayList<>();
		
		Vector<Vector<Fraction>> dirs = problemAnswer.getDirections();
		Vector<Vector<Fraction>> pts = problemAnswer.getVertices();
		
		for (int i = 0; i < dirs.size(); i++) {
			String[] currentDir = new String[dirs.get(i).size()];
			for (int j = 0; j < dirs.get(i).size(); j++) {
				currentDir[j] = dirs.get(i).get(j).toMathJaxString();
			}
			directions.add(currentDir);
		}
		
		for (int i = 0; i < pts.size(); i++) {
			String[] currentPt = new String[pts.get(i).size()];
			for (int j = 0; j < pts.get(i).size(); j++) {
				currentPt[j] = pts.get(i).get(j).toMathJaxString();
			}
			points.add(currentPt);
		}
	}

	public void addDirection(String[] direction) {
		this.directions.add(direction);
	}

	public void addPoint(String[] point) {
		this.points.add(point);
	}
}
