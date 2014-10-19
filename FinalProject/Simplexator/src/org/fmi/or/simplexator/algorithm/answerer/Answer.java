package org.fmi.or.simplexator.algorithm.answerer;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
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
	
	public Answer(SimplexTable simtable) {
		this.vertices = new Vector<Vector<Fraction>>();
		this.directions = new Vector<Vector<Fraction>>();
		
		Vector<Fraction> optimalAnswer1 = new Vector<Fraction>();
		for(int i=0; i < simtable.getVarCount(); i++) {
			optimalAnswer1.add(Fraction.ZERO);
		}
		
		for(int i=0; i < simtable.getRestrictionsCount(); i++) {
			Fraction value = simtable.getRightSideValue(i);
			int index = simtable.getBasisIndeces().get(i);
			optimalAnswer1.set(index, value);
		}
		
		vertices.add(optimalAnswer1);
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
	
	public boolean equals(Object obj) {
		Answer other = (Answer) obj;
	    if(this == other) { 
	        return true;
	    }
	    return this.vertices.equals(other.getVertices()) && this.directions.equals(other.getDirections());
	}
}
