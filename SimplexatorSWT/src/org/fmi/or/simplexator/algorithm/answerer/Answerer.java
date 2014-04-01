package org.fmi.or.simplexator.algorithm.answerer;

import java.util.Queue;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.Fraction;

public class Answerer {
	private SimplexTable table;
	// private ProblemType problemType;
	
	private Vector<Vector<Fraction>> vertices;
	private Vector<Vector<Fraction>> directions;
	
	private Queue<SimplexTable> queue;
	
	public Answerer(Vector<Fraction> optimalAnswer, SimplexTable table /*, ProblemType problemType*/) {
		this.table = table;
		//this.problemType = problemType;
		
		this.vertices = new Vector<Vector<Fraction>>();
		vertices.add(optimalAnswer);
		this.directions = new Vector<Vector<Fraction>>();
		
		//this.queue = new Queue<SimplexTable>();
	}
	
	public void findAllAnswers() {
		
	}
}
 