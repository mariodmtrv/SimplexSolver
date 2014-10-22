package org.fmi.or.simplexator.algorithm.answerer;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class Answer {
	private Vector<Vector<Fraction>> vertices;
	private Vector<Vector<Fraction>> directions;

	private Vector<Variable> variables;
	private boolean isK;
	private boolean isM;

	public Answer(Problem problem) {
		super();
		this.vertices = new Vector<Vector<Fraction>>();
		this.directions = new Vector<Vector<Fraction>>();
		this.setInfoForProblem(problem);
	}

	public Answer(Vector<Fraction> optimalAnswer1, Problem problem) {
		this.vertices = new Vector<Vector<Fraction>>();
		vertices.add(optimalAnswer1);
		this.directions = new Vector<Vector<Fraction>>();
		this.setInfoForProblem(problem);
	}

	public Answer(SimplexTable simtable, Problem problem) {
		this.vertices = new Vector<Vector<Fraction>>();
		this.directions = new Vector<Vector<Fraction>>();

		Vector<Fraction> optimalAnswer1 = new Vector<Fraction>();
		for (int i = 0; i < simtable.getVarCount(); i++) {
			optimalAnswer1.add(Fraction.ZERO);
		}

		for (int i = 0; i < simtable.getRestrictionsCount(); i++) {
			Fraction value = simtable.getRightSideValue(i);
			int index = simtable.getBasisIndeces().get(i);
			optimalAnswer1.set(index, value);
		}

		vertices.add(optimalAnswer1);
		this.setInfoForProblem(problem);
	}

	private void setInfoForProblem(Problem problem) {
		this.variables = new Vector<Variable>();
		for (int i = 0; i < problem.getVarCount(); i++) {
			this.variables.add(problem.getVarByIndex(i));
		}
	}

	public void pushVertex(Vector<Fraction> v) {
		vertices.add(v);
	}

	public void pushDirection(Vector<Fraction> d) {
		directions.add(d);
	}

	public Vector<Vector<Fraction>> getVertices() {
		return vertices;
	}

	public Vector<Vector<Fraction>> getDirections() {
		return directions;
	}

	public Vector<Variable> getVariables() {
		return variables;
	}

	public boolean isK() {
		return isK;
	}

	public boolean isM() {
		return isM;
	}

	public boolean equals(Object obj) {
		Answer other = (Answer) obj;
		if (this == other) {
			return true;
		}
		return this.vertices.equals(other.getVertices())
				&& this.directions.equals(other.getDirections());
	}
}
