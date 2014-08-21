package org.fmi.or.simplexator.algorithm.answerer;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Problem;

/**
 * Convert the answer obtained by the algorithm
 * to the answer of another problem (M -> K -> L)
 */
public class AnswerConverter {
	private Problem problem;
	//private ProblemType problemType;
	
	private Vector<Vector<Fraction>> oldVertices;
	private Vector<Vector<Fraction>> oldDirections;
	
	public AnswerConverter(Problem problem, /*ProblemType problemType,*/ Vector<Vector<Fraction>> vertices, Vector<Vector<Fraction>> directions) {
		this.problem = problem;
		//this.problemType = problemType;
		
		this.oldVertices = vertices;
		this.oldDirections = directions;
	}
	
	public Answer convert() {
		// select whether to call MToK or KToL
		return new Answer(null); // TODO
	}
	
	public Answer convertMToK() {
		return new Answer(null); // TODO
	}
	
	public Answer convertKToL() {
		return new Answer(null); // TODO
	}
}
