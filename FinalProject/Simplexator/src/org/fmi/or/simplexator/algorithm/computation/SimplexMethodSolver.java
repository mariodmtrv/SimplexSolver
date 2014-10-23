package org.fmi.or.simplexator.algorithm.computation;

import org.fmi.or.simplexator.algorithm.answerer.Answer;
import org.fmi.or.simplexator.algorithm.answerer.AnswerConverter;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.answerqueue.AnswerQueue;
import org.fmi.or.simplexator.answerqueue.IterationQueue;
import org.fmi.or.simplexator.answerqueue.ProblemConversionQueue;
import org.fmi.or.simplexator.service.serializable.LaTeXBuilder;


public class SimplexMethodSolver {
	
	/**
	 * public int solveProblem(Problem p, ProblemConversionQueue pcq, IterationQueue iterq, AnswerQueue ansq)
	 * 
	 * Solves the problem using the Simplex Method and fills the queues.
	 *
	 * @param p 		input problem
	 * @param pcq		empty queue for problem conversions
	 * @param iterq		empty queue for problem iterations
	 * @param ansq		empty queue for answers
	 * @param latex		for generating the LaTeX file
	 * 
	 * @return          0, if optimal solution was found
	 * 					-1, if problem has no solution
	 * 					-2, if problem is unbounded
	 */
	public int solveProblem(Problem p, ProblemConversionQueue pcq, IterationQueue iterq, AnswerQueue ansq, LaTeXBuilder latex) {
		//Problem p = new Problem(zfunction, restrictions, optimum,
		//		hasNegativePart);
		
		Problem kp = new Problem(p);
		kp.convertToK(pcq);
		
		MProblem mp = new MProblem(kp);
		mp.convertToMProblem(pcq);
		
		latex.beginDocument();
		latex.preambleForSimplexTable(mp);
		
		// solve:
		ProblemInitialization mProblemInit = new ProblemInitialization(mp);
		SimplexTable simtable = mProblemInit.makeFirstIteration(iterq);
		
		CriteriaCheck critCheck = new CriteriaCheck(simtable);
		Pair<Integer, Integer> keyElementCoords = critCheck
				.checkCriteriaAndFindNewBasis(iterq);
		
		latex.iterationToString(simtable, keyElementCoords);
		
		ProblemIteration mProblemIter;
		while (keyElementCoords.getFirst() != -1) {
			mProblemIter = new ProblemIteration(mp, simtable,
					keyElementCoords);
			
			simtable = mProblemIter.makeIteration(iterq);
			
			critCheck = new CriteriaCheck(simtable);
			keyElementCoords = critCheck.checkCriteriaAndFindNewBasis(iterq);
			
			latex.iterationToString(simtable, keyElementCoords);
		}
		
		latex.appendixForSimplexTable();
		latex.endDocument();
		
		iterq.setNewKeyElementCoordsForLastIteration(keyElementCoords);
		
		// handle answers:
		if(keyElementCoords.getFirst() == -1 && keyElementCoords.getFirst() == -1) {
			// optimal answer found
			
			Answer mAnswer = new Answer(simtable, mp);
			ansq.addAnswer(mAnswer);
			
			AnswerConverter mtok = new AnswerConverter(kp, mAnswer);
			Answer kAnswer = mtok.convertMToK(ansq);
			
			if(kAnswer == null) {
				// invalid M-answer => problem has no solution
				return -1;
			}
			else {
				AnswerConverter ktol = new AnswerConverter(p, kAnswer);
				Answer lAnswer = ktol.convertKToL(ansq);
			}
		}
		else {
			// M is unbounded
			return -2;
		}
		
		return 0;
	}
	
}
