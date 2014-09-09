package org.fmi.or.simplexator.algorithm.answerer.tests;

import org.fmi.or.simplexator.algorithm.computation.CriteriaCheck;
import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.algorithm.computation.ProblemInitialization;
import org.fmi.or.simplexator.algorithm.computation.ProblemIteration;
import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.tests.MProblemConversionTest;
import org.junit.Test;

public class AnswererTest {
	
	@Test
	public void testSampleConversionMToK() {
		// TODO
	}
	
	@Test
	public void testSampleConversionKToL() {
		// TODO
	}

	@Test
	public void testSeveralHasNegativeParts() {
		// TODO
	}

	@Test
	public void testNonZeroCoefInM() {
		// TODO
	}
	
	// @Test
	public void testMinimum() {
		MProblemConversionTest t = new MProblemConversionTest();
		Problem p = t.testCanonicalProblemMinimum();
		MProblem mProblem = new MProblem(p);
		mProblem.convertToMProblem();

		ProblemInitialization mProblemInit = new ProblemInitialization(mProblem);
		SimplexTable simtable = mProblemInit.makeFirstIteration();

		// now test...
		CriteriaCheck critCheck = new CriteriaCheck(simtable);
		Pair<Integer, Integer> keyElementCoords = critCheck
				.checkCriteriaAndFindNewBasis();
		ProblemIteration mProblemIter;
		while (keyElementCoords != null) {
			mProblemIter = new ProblemIteration(mProblem, simtable,
					keyElementCoords);
			simtable = mProblemIter.makeIteration();
			critCheck = new CriteriaCheck(simtable);
			keyElementCoords = critCheck.checkCriteriaAndFindNewBasis();
		}

		// now the new test...
		// TODO
	}

	// @Test
	public void testMaximum() {
		MProblemConversionTest t = new MProblemConversionTest();
		Problem p = t.testCanonicalProblemMaximum();
		MProblem mProblem = new MProblem(p);
		mProblem.convertToMProblem();

		ProblemInitialization mProblemInit = new ProblemInitialization(mProblem);
		SimplexTable simtable = mProblemInit.makeFirstIteration();

		// now test..
		CriteriaCheck critCheck = new CriteriaCheck(simtable);
		Pair<Integer, Integer> keyElementCoords = critCheck
				.checkCriteriaAndFindNewBasis();
		ProblemIteration mProblemIter;
		while (keyElementCoords != null) {
			mProblemIter = new ProblemIteration(mProblem, simtable,
					keyElementCoords);
			simtable = mProblemIter.makeIteration();
			critCheck = new CriteriaCheck(simtable);
			keyElementCoords = critCheck.checkCriteriaAndFindNewBasis();
		}

		// now the new test...
		// TODO
	}
}
