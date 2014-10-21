package org.fmi.or.simplexator.algorithm.answerer.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.EquationSign;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Optimum;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;
import org.fmi.or.simplexator.service.serializable.IterationStep;
import org.fmi.or.simplexator.service.serializable.SolutionResponse;
import org.junit.Test;

public class ResponseTest {

	@Test
	public void test() {
		SolutionResponse response = new SolutionResponse(createProblem());
		response.solve();
		List<IterationStep> x = response.getIterations();
		
	}
	private Problem createProblem() {
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
		Problem problem = new Problem(zfunction, restrictions, optimum,
				hasNegativePart);
		return problem;
	}

}
