package org.fmi.or.simplexator.algorithm.computation.tests;

import static org.junit.Assert.*;

import java.util.Locale;

import org.fmi.or.simplexator.algorithm.computation.ProblemInitialization;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.tests.MProblemConversionTest;
import org.fmi.or.simplexator.answerqueue.IterationQueue;
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
		IterationQueue queue= new IterationQueue(new Locale("BG", "bg"));
		mProblemInit.makeFirstIteration(queue);
		
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
		IterationQueue queue= new IterationQueue(new Locale("BG", "bg"));
		mProblemInit.makeFirstIteration(queue);
		
		}
}
