package org.fmi.or.simplexator.algorithm.computation;

import java.util.List;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;


public class SimplexMethodSolver {

	
	private Vector<Vector<Fraction>> oldTable;
	private Vector<Fraction> oldNumCost;
	private Vector<Fraction> oldMCost;
	

	private void solveProblem() {
		/*
		 * void main()
		 * {
		 * 	getInputFromUser;
		 * 	makeProblem;
		 * 	convertToK;
		 * 	convertToM;
		 * 	table = makeFirstIteration(MProblem);
		 * 	while(!(nextBasis = checkCriteria(table)))
		 * 	{
		 * 		table = makeIterations(table,nextBasis);
		 * 	}
		 * 	// now "table" has the first optimal answer
		 * 	findAllAnswers(table);
		 * 	return to Zlateva & Chernogorov;
		 * }
		 */
	}
	
}
