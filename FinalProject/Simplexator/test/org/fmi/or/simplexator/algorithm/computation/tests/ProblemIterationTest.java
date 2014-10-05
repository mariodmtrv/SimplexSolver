package org.fmi.or.simplexator.algorithm.computation.tests;

import static org.junit.Assert.*;

import org.fmi.or.simplexator.algorithm.computation.CriteriaCheck;
import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.algorithm.computation.ProblemInitialization;
import org.fmi.or.simplexator.algorithm.computation.ProblemIteration;
import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.tests.MProblemConversionTest;
import org.junit.Test;

public class ProblemIterationTest {

	@Test
	public void testMakeIterationMinimum() {
		MProblemConversionTest t=new MProblemConversionTest();
		Problem p=t.testCanonicalProblemMinimum();
		MProblem mProblem=new MProblem(p);
		mProblem.convertToMProblem();
		
		ProblemInitialization mProblemInit = 
				new ProblemInitialization(mProblem);
		SimplexTable simtable = mProblemInit.makeFirstIteration();
		
		// now test...
		CriteriaCheck critCheck = new CriteriaCheck(simtable);
		Pair<Integer,Integer> keyElementCoords = critCheck.checkCriteriaAndFindNewBasis();
		while(keyElementCoords.getFirst() != -1) {
			ProblemIteration mProblemIter = new ProblemIteration(mProblem, simtable, keyElementCoords);
			simtable = mProblemIter.makeIteration();
			critCheck = new CriteriaCheck(simtable);
			keyElementCoords = critCheck.checkCriteriaAndFindNewBasis();
		}
	}
	
	//@Test
	public void testMakeIterationMaximum() {
		MProblemConversionTest t=new MProblemConversionTest();
		Problem p=t.testCanonicalProblemMaximum();
		MProblem mProblem=new MProblem(p);
		mProblem.convertToMProblem();
		
		ProblemInitialization mProblemInit = 
				new ProblemInitialization(mProblem);
		SimplexTable simtable = mProblemInit.makeFirstIteration();
		
		// now test..
		CriteriaCheck critCheck = new CriteriaCheck(simtable);
		Pair<Integer,Integer> keyElementCoords = critCheck.checkCriteriaAndFindNewBasis();
		while(keyElementCoords.getFirst() != -1) {
			ProblemIteration mProblemIter = new ProblemIteration(mProblem, simtable, keyElementCoords);
			simtable = mProblemIter.makeIteration();
			critCheck = new CriteriaCheck(simtable);
			keyElementCoords = critCheck.checkCriteriaAndFindNewBasis();
		}
		
	}
	
}
