package org.fmi.or.simplexator.algorithm.answerer.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.answerer.Answer;
import org.fmi.or.simplexator.algorithm.answerer.AnswerConverter;
import org.fmi.or.simplexator.algorithm.computation.CriteriaCheck;
import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.algorithm.computation.ProblemInitialization;
import org.fmi.or.simplexator.algorithm.computation.ProblemIteration;
import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.EquationSign;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Optimum;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;
import org.fmi.or.simplexator.algorithm.converter.tests.MProblemConversionTest;
import org.fmi.or.simplexator.answerqueue.IterationQueue;
import org.junit.Test;

public class AnswererTest {

	@Test
	public void testSampleConversionMToKToL() {
		// define serializableProblemSteps
		List<Variable> zfunction = new LinkedList<Variable>();
		zfunction.add(new Variable(new Fraction(2), 1));
		zfunction.add(new Variable(new Fraction(1), 2));
		zfunction.add(new Variable(new Fraction(2), 3));

		Vector<Restriction> restrictions = new Vector<Restriction>();
		List<Variable> firstRestr = new LinkedList<Variable>();
		firstRestr.add(new Variable(new Fraction(1), 1));
		firstRestr.add(new Variable(new Fraction(0), 2));
		firstRestr.add(new Variable(new Fraction(-1), 3));
		Restriction first = new Restriction(firstRestr, EquationSign.LTE,
				new Fraction(-1));

		List<Variable> secondRestr = new LinkedList<Variable>();
		secondRestr.add(new Variable(new Fraction(-1), 1));
		secondRestr.add(new Variable(new Fraction(0), 2));
		secondRestr.add(new Variable(new Fraction(-2), 3));
		Restriction second = new Restriction(secondRestr, EquationSign.LTE,
				new Fraction(3));

		List<Variable> thirdRestr = new LinkedList<Variable>();
		thirdRestr.add(new Variable(new Fraction(3), 1));
		thirdRestr.add(new Variable(new Fraction(1), 2));
		thirdRestr.add(new Variable(new Fraction(1), 3));
		Restriction third = new Restriction(thirdRestr, EquationSign.EQ,
				new Fraction(4));
		restrictions.add(first);
		restrictions.add(second);
		restrictions.add(third);

		Optimum optimum = Optimum.MINIMUM;
		Vector<Boolean> hasNegativePart = new Vector<>();
		hasNegativePart.add(true);
		hasNegativePart.add(false);
		hasNegativePart.add(false);

		Problem p = new Problem(zfunction, restrictions, optimum,
				hasNegativePart);

		Problem kp = new Problem(p);
		kp.convertToK();

		MProblem mp = new MProblem(kp);
		mp.convertToMProblem();

		// define answers
		Vector<Fraction> mVector = new Vector<Fraction>();
		mVector.add(new Fraction(0, 1));
		mVector.add(new Fraction(1, 1));
		mVector.add(new Fraction(7, 1));
		mVector.add(new Fraction(0, 1));
		mVector.add(new Fraction(0, 1));
		mVector.add(new Fraction(2, 1));
		mVector.add(new Fraction(0, 1));
		Answer mAnswer = new Answer(mVector);

		Vector<Fraction> kVector = new Vector<Fraction>();
		kVector.add(new Fraction(0, 1));
		kVector.add(new Fraction(1, 1));
		kVector.add(new Fraction(7, 1));
		kVector.add(new Fraction(0, 1));
		kVector.add(new Fraction(0, 1));
		kVector.add(new Fraction(2, 1));
		Answer kAnswer = new Answer(kVector);

		Vector<Fraction> lVector = new Vector<Fraction>();
		lVector.add(new Fraction(-1, 1));
		lVector.add(new Fraction(7, 1));
		lVector.add(new Fraction(0, 1));
		Answer lAnswer = new Answer(lVector);

		// test:
		AnswerConverter mtok = new AnswerConverter(kp, mAnswer);
		Answer returnedMToK = mtok.convertMToK();
		if (!returnedMToK.equals(kAnswer)) {
			fail("");
		}

		AnswerConverter ktol = new AnswerConverter(p, kAnswer);
		Answer returnedKToL = ktol.convertKToL();
		if (!returnedKToL.equals(lAnswer)) {
			fail("");
		}
	}

	@Test
	public void testSeveralHasNegativeParts() {
		// define serializableProblemSteps
		List<Variable> zfunction = new LinkedList<Variable>();
		zfunction.add(new Variable(new Fraction(2), 1));
		zfunction.add(new Variable(new Fraction(1), 2));
		zfunction.add(new Variable(new Fraction(2), 3));

		Vector<Restriction> restrictions = new Vector<Restriction>();
		List<Variable> firstRestr = new LinkedList<Variable>();
		firstRestr.add(new Variable(new Fraction(1), 1));
		firstRestr.add(new Variable(new Fraction(0), 2));
		firstRestr.add(new Variable(new Fraction(-1), 3));
		Restriction first = new Restriction(firstRestr, EquationSign.LTE,
				new Fraction(-1));

		List<Variable> secondRestr = new LinkedList<Variable>();
		secondRestr.add(new Variable(new Fraction(-1), 1));
		secondRestr.add(new Variable(new Fraction(0), 2));
		secondRestr.add(new Variable(new Fraction(-2), 3));
		Restriction second = new Restriction(secondRestr, EquationSign.LTE,
				new Fraction(3));

		List<Variable> thirdRestr = new LinkedList<Variable>();
		thirdRestr.add(new Variable(new Fraction(3), 1));
		thirdRestr.add(new Variable(new Fraction(1), 2));
		thirdRestr.add(new Variable(new Fraction(1), 3));
		Restriction third = new Restriction(thirdRestr, EquationSign.EQ,
				new Fraction(4));
		restrictions.add(first);
		restrictions.add(second);
		restrictions.add(third);

		Optimum optimum = Optimum.MINIMUM;
		Vector<Boolean> hasNegativePart = new Vector<>();
		hasNegativePart.add(true);
		hasNegativePart.add(false);
		hasNegativePart.add(true); // several negative !!!

		Problem p = new Problem(zfunction, restrictions, optimum,
				hasNegativePart);

		Problem kp = new Problem(p);
		kp.convertToK();

		MProblem mp = new MProblem(kp);
		mp.convertToMProblem();

		// define answers
		// Warning!: Answers are fake
		Vector<Fraction> mVector = new Vector<Fraction>();
		mVector.add(new Fraction(0, 1));
		mVector.add(new Fraction(1, 1));
		mVector.add(new Fraction(2, 1));
		mVector.add(new Fraction(3, 1));
		mVector.add(new Fraction(4, 1));
		mVector.add(new Fraction(5, 1));
		mVector.add(new Fraction(6, 1));
		mVector.add(new Fraction(0, 1));
		Answer mAnswer = new Answer(mVector);

		Vector<Fraction> kVector = new Vector<Fraction>();
		kVector.add(new Fraction(0, 1));
		kVector.add(new Fraction(1, 1));
		kVector.add(new Fraction(2, 1));
		kVector.add(new Fraction(3, 1));
		kVector.add(new Fraction(4, 1));
		kVector.add(new Fraction(5, 1));
		kVector.add(new Fraction(6, 1));
		Answer kAnswer = new Answer(kVector);

		Vector<Fraction> lVector = new Vector<Fraction>();
		lVector.add(new Fraction(-1, 1));
		lVector.add(new Fraction(2, 1));
		lVector.add(new Fraction(-1, 1));
		Answer lAnswer = new Answer(lVector);

		// test:
		AnswerConverter mtok = new AnswerConverter(kp, mAnswer);
		Answer returnedMToK = mtok.convertMToK();
		if (!returnedMToK.equals(kAnswer)) {
			fail("");
		}

		AnswerConverter ktol = new AnswerConverter(p, kAnswer);
		Answer returnedKToL = ktol.convertKToL();
		if (!returnedKToL.equals(lAnswer)) {
			fail("");
		}
	}

	// @Test
	public void testNonZeroCoefInM() {
		// define serializableProblemSteps
		List<Variable> zfunction = new LinkedList<Variable>();
		zfunction.add(new Variable(new Fraction(2), 1));
		zfunction.add(new Variable(new Fraction(1), 2));
		zfunction.add(new Variable(new Fraction(2), 3));

		Vector<Restriction> restrictions = new Vector<Restriction>();
		List<Variable> firstRestr = new LinkedList<Variable>();
		firstRestr.add(new Variable(new Fraction(1), 1));
		firstRestr.add(new Variable(new Fraction(0), 2));
		firstRestr.add(new Variable(new Fraction(-1), 3));
		Restriction first = new Restriction(firstRestr, EquationSign.LTE,
				new Fraction(-1));

		List<Variable> secondRestr = new LinkedList<Variable>();
		secondRestr.add(new Variable(new Fraction(-1), 1));
		secondRestr.add(new Variable(new Fraction(0), 2));
		secondRestr.add(new Variable(new Fraction(-2), 3));
		Restriction second = new Restriction(secondRestr, EquationSign.LTE,
				new Fraction(3));

		List<Variable> thirdRestr = new LinkedList<Variable>();
		thirdRestr.add(new Variable(new Fraction(3), 1));
		thirdRestr.add(new Variable(new Fraction(1), 2));
		thirdRestr.add(new Variable(new Fraction(1), 3));
		Restriction third = new Restriction(thirdRestr, EquationSign.EQ,
				new Fraction(4));
		restrictions.add(first);
		restrictions.add(second);
		restrictions.add(third);

		Optimum optimum = Optimum.MINIMUM;
		Vector<Boolean> hasNegativePart = new Vector<>();
		hasNegativePart.add(true);
		hasNegativePart.add(false);
		hasNegativePart.add(false);

		Problem p = new Problem(zfunction, restrictions, optimum,
				hasNegativePart);

		Problem kp = new Problem(p);
		kp.convertToK();

		MProblem mp = new MProblem(kp);
		mp.convertToMProblem();

		// define answers
		Vector<Fraction> mVector = new Vector<Fraction>();
		mVector.add(new Fraction(0, 1));
		mVector.add(new Fraction(1, 1));
		mVector.add(new Fraction(7, 1));
		mVector.add(new Fraction(0, 1));
		mVector.add(new Fraction(0, 1));
		mVector.add(new Fraction(2, 1));
		mVector.add(new Fraction(5, 1)); // this is from M
		Answer mAnswer = new Answer(mVector);

		// test:
		AnswerConverter mtok = new AnswerConverter(kp, mAnswer);
		Answer returnedMToK = mtok.convertMToK();
		if (returnedMToK != null) {
			fail("");
		}
	}

	// @Test
	public void testMinimum() {
		MProblemConversionTest t = new MProblemConversionTest();
		Problem p = t.testCanonicalProblemMinimum();
		MProblem mProblem = new MProblem(p);
		mProblem.convertToMProblem();

		ProblemInitialization mProblemInit = new ProblemInitialization(mProblem);
		IterationQueue queue = new IterationQueue(new Locale("BG", "bg"));
		SimplexTable simtable = mProblemInit.makeFirstIteration(queue);

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
        IterationQueue queue= new IterationQueue(new Locale("BG", "bg"));
        SimplexTable simtable = mProblemInit.makeFirstIteration(queue);

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
