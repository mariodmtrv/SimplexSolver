package org.fmi.or.simplexator.algorithm.computation.tests;

import static org.junit.Assert.*;

import org.fmi.or.simplexator.algorithm.computation.ProblemInitialization;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.tests.MProblemConversionTest;
import org.junit.Test;

public class ProblemInitializationTest {

	//@Test
	public void testProblemWithMinimum() {
		MProblemConversionTest t=new MProblemConversionTest();
		Problem p=t.testCanonicalProblemMinimum();
		MProblem mProblem=new MProblem(p);
		mProblem.convertToMProblem();
		
		ProblemInitialization mProblemInit = 
				new ProblemInitialization(mProblem);
		// now test...
		mProblemInit.makeFirstIteration();
		
		}
	@Test
	public void testProblemWithMaximum() {
		MProblemConversionTest t=new MProblemConversionTest();
		Problem p=t.testCanonicalProblemMaximum();
		MProblem mProblem=new MProblem(p);
		mProblem.convertToMProblem();
		
		ProblemInitialization mProblemInit = 
				new ProblemInitialization(mProblem);
		// now test...
		mProblemInit.makeFirstIteration();
		
		}
}
