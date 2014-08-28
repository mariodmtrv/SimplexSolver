package org.fmi.or.simplexator.algorithm.answerer;

import java.util.Vector;
import org.fmi.or.simplexator.algorithm.converter.Fraction;


public class Answer {
	//private ProblemType problemType;
	private Vector<Vector<Fraction>> vertices;
	private Vector<Vector<Fraction>> directions;
	
	public Answer() {
		super();
		this.vertices = new Vector<Vector<Fraction>>();
		this.directions = new Vector<Vector<Fraction>>();
	}

	public Answer(Vector<Fraction> optimalAnswer1) {
		this.vertices = new Vector<Vector<Fraction>>();
		vertices.add(optimalAnswer1);
		this.directions = new Vector<Vector<Fraction>>();
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
}
