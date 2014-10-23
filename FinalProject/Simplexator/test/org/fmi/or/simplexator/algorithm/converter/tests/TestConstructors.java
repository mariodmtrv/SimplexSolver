package org.fmi.or.simplexator.algorithm.converter.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.EquationSign;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Optimum;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;
import org.junit.Test;

public class TestConstructors {

	@Test
	public void test() {
		Variable var = new Variable(new Fraction(3, 4), 1);
		Variable var2 = new Variable(var);
		Restriction r = new Restriction(new ArrayList<>(Arrays.asList(var)),
				EquationSign.EQ, new Fraction(3));
		Restriction r2 = new Restriction(r);
		Problem p = new Problem(new ArrayList<>(Arrays.asList(var)),
				new Vector<>(Arrays.asList(r)), Optimum.MAXIMUM, new Vector<>(
						Arrays.asList(true)));
		Problem p2 = new Problem(p);
	}
}
