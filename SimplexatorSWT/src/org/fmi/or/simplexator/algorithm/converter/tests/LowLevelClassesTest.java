package org.fmi.or.simplexator.algorithm.converter.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.fmi.or.simplexator.algorithm.converter.EquationSign;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;
import org.fmi.or.simplexator.algorithm.converter.VariableType;
import org.junit.Test;

public class LowLevelClassesTest {

	@Test
	public void testFractionDivisionAndComparison() {
		Fraction a = new Fraction(7, 14);
		Fraction b = new Fraction(10, 20);
		Fraction actual = a.divide(b);
		Fraction expected = new Fraction(1);
		assertTrue(actual.isEqualOrHigher(expected)
				&& actual.isLowerOrEqual(expected));
	}

	@Test
	public void testVariableBipartition() {
		List<Variable> bipartizedActual=new LinkedList<>();
		Fraction coef = new Fraction(7, 14);
		Variable var=new Variable(coef, 3);
		bipartizedActual=var.bipartize();
		Variable posExpected=new Variable(new Fraction(1,2), 3,VariableType.POSITIVE);
		Variable negExpected=new Variable(new Fraction(1,-2), 3,VariableType.NEGATIVE);
		assertTrue(posExpected.equals(bipartizedActual.get(0)));
		assertTrue(negExpected.equals(bipartizedActual.get(1)));
	}
	@Test
	public void testRestrictionPositivation(){
		List<Variable> variables=new LinkedList<Variable>();
		variables.add(new Variable(new Fraction(3, 7), 1));
		variables.add(new Variable(new Fraction(5, 9), 2));
		variables.add(new Variable(new Fraction(3, -9), 3));
		Restriction restriction=new Restriction(variables, EquationSign.GTE, new Fraction(-3));
		restriction.rightSideToPositive();
		Variable x3expected=new Variable(new Fraction(1,3), 3);
		assertTrue(x3expected.equals(restriction.getVariables()[2]));
	}
}
