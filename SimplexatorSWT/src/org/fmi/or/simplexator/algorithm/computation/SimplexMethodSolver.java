package org.fmi.or.simplexator.algorithm.computation;

import java.util.List;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;


public class SimplexMethodSolver {
	
	private void solveProblem() {
		/*
		 * void main()
		 * {
		 * 	getInputFromUser;
		 * 	makeProblem;
		 * 	convertToK;
		 * 	convertToM;
		 * 	table = makeFirstIteration(MProblem);
		 * 	while(!(table = checkCriteriaAndChangeBasis(table)))
		 * 	{
		 * 		table = makeIteration(table);
		 * 	}
		 * 	// now "table" has the first optimal answer
		 * 	findAllAnswers(table);
		 * 	return to Zlateva & Chernogorov;
		 * }
		 */
		
		/*
		 * void main()
		 * {
		 * 	userData = getInputFromUser;
		 * 	Problem p = makeProblem(userData);
		 * 	Problem kp = convertToK(p);
		 * 	MProblem mp = convertToM(kp);
		 * 	SimplexTable simtable = problemInitialization(mp);
		 * 	while(	(basisChange = checkCriteriaAndFindNewBasis(simtable)) != invalid	)
		 * 	{
		 * 		simtable = makeIteration(simtable, basisChange);
		 * 	}
		 * 	// now "simtable" has the first optimal answer
		 * 	Answer result = findAllAnswers(simtable);
		 * 	// "result" holds the solution (is there one) and all possible optimal answers
		 *  Answer realanswer = convertToPrimaryProblem(result);
		 *  // find the answer to the problem that the user orignally input
		 * 	return to Zlateva & Chernogorov;
		 * }
		 */
		
		/*
		 * void main()
		 * {
		 * 	userData = getInputFromUser;
		 * 	Problem p = makeProblem(userData);
		 * 	Problem kp = convertToK(p);
		 * 	MProblem mp = convertToM(kp);
		 * 	SimplexTable simtable = problemInitialization(mp);
		 * 	while(	(basisChange = checkCriteriaAndFindNewBasis(simtable)) != invalid	)
		 * 	{
		 * 		simtable = makeIteration(simtable, basisChange);
		 * 	}
		 * 	// now "simtable" has the first optimal answer
		 * 	AnswerSearcher result = findAllAnswers(simtable);
		 * 	// "result" holds the solution (is there one) and all possible optimal answers
		 *  AnswerConverter realanswer = convert(result);
		 *  // find the answer to the problem that the user orignally input
		 * 	return to Zlateva & Chernogorov;
		 * }
		 */
	}
	
}
