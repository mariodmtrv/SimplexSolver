package api.fmi.simplexator.algorithm.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import api.fmi.simplexator.algorithm.*;

public class FractionsCorrectness {
	@Test
	public void fractionsSubstraction() {
		Fraction a = new Fraction(3, 7);
		Fraction b = new Fraction(1);
		Fraction expected = new Fraction(-4, 7);
		Fraction actual = a.substraction(b);
		assertTrue(actual.equals(expected));
	}

	@Test
	public void fractionsDivision() {

		Fraction a = new Fraction(3, 7);
		Fraction b = new Fraction(9, 5);
		Fraction expected = new Fraction(5, 12);
		Fraction actual = a.divide(b);
		assertTrue(actual.equals(expected));
	}

}
