package org.fmi.or.simplexator.algorithm.answerer;

import java.util.Queue;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.Fraction;

/**
 * Performs breadth-first search (BFS) 
 * on the vertices of the feasible region set (the polytope)
 * until it discovers all optimal answers
 */
public class AnswerSearcher {
	private SimplexTable table;
	// private ProblemType problemType;
	private Answer answer;
	private Queue<SimplexTable> queue;
	
	public AnswerSearcher(Vector<Fraction> optimalAnswer1, SimplexTable table /*, ProblemType problemType*/) {
		this.table = table;
		//this.problemType = problemType;
		this.answer = new Answer(optimalAnswer1);
		//this.queue = new Queue<SimplexTable>(); // make Vector ?
	}
	
	public Answer findAllAnswers() {
		/*
		 * add vertex to answer & dequeue it from queue
		 * foreach q in queue:
		 *     if q is vertex => enqueue
		 *     else if q is direction => add to answer
		 *     [else continue...]
		 * return answer
		 */
		return this.answer; // TODO
	}
}
 