package org.fmi.or.simplexator.answerqueue.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.EquationSign;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Optimum;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;
import org.fmi.or.simplexator.answerqueue.ProblemConversionQueue;
import org.fmi.or.simplexator.service.serializable.SolutionResponse;
import org.junit.Test;

public class TestProblemConversion {

	@Test
	public void test() {
		List<Variable> zfunction = new LinkedList<Variable>();
		zfunction.add(new Variable(new Fraction(1), 1));
		zfunction.add(new Variable(new Fraction(3), 2));
		zfunction.add(new Variable(new Fraction(1), 3));
		zfunction.add(new Variable(new Fraction(-2), 4));

		Vector<Restriction> restrictions = new Vector<Restriction>();
		List<Variable> firstRestr = new LinkedList<Variable>();
		firstRestr.add(new Variable(new Fraction(5), 1));
		firstRestr.add(new Variable(new Fraction(3), 2));
		firstRestr.add(new Variable(new Fraction(-1), 3));
		firstRestr.add(new Variable(new Fraction(-2), 4));
		Restriction first = new Restriction(firstRestr, EquationSign.EQ,
				new Fraction(-8));

		List<Variable> secondRestr = new LinkedList<Variable>();
		secondRestr.add(new Variable(new Fraction(-2), 1));
		secondRestr.add(new Variable(new Fraction(-2), 2));
		secondRestr.add(new Variable(new Fraction(0), 3));
		secondRestr.add(new Variable(new Fraction(0), 4));
		Restriction second = new Restriction(secondRestr, EquationSign.GTE,
				new Fraction(5));

		restrictions.add(first);
		restrictions.add(second);

		Optimum optimum = Optimum.MAXIMUM;
		Vector<Boolean> hasNegativePart = new Vector<>();
		hasNegativePart.add(false);
		hasNegativePart.add(true);
		hasNegativePart.add(false);
		hasNegativePart.add(false);
		Problem problem = new Problem(zfunction, restrictions, optimum,
				hasNegativePart);
		Locale locale = new Locale("bg", "BG");
		ProblemConversionQueue answerQueue = new ProblemConversionQueue(locale);
		problem.convertToK(answerQueue);
		SolutionResponse response = new SolutionResponse(problem);
		response.solve();
	}

	@Test
	public void test2() {

	}

}
