package org.fmi.or.simplexator.algorithm.converter;

import java.text.ParseException;
import java.util.regex.Pattern;

public class Fraction {
	private Integer numerator;
	private Integer denominator;

	public static final Fraction M = new Fraction(7_000_003);
	public static final Fraction ZERO = new Fraction(0);

	public Fraction(String text) throws NumberFormatException {
		// empty string
		if (text.equals("")) {
			numerator = 0;
			denominator = 1;
		}

		else {

			// single number
			if (!text.contains("/")) {
				numerator = Integer.parseInt(text);
				denominator = 1;
			}
			// fraction
			else {
				String[] st = text.split("/");
				numerator = Integer.parseInt(st[0]);
				denominator = Integer.parseInt(st[1]);
			}
		}
		reduce();
	}

	private int findGCD(int numerator, int denominator) {
		while (numerator > 0 && denominator > 0) {
			if (numerator > denominator) {
				numerator %= denominator;
			} else
				denominator %= numerator;
		}
		return Math.max(numerator, denominator);

	}

	private int toPositive(int num) {
		if (num < 0)
			return num * (-1);
		return num;
	}

	private void reduce() {
		int gcd = findGCD(toPositive(this.numerator),
				toPositive(this.denominator));
		this.numerator /= gcd;
		this.denominator /= gcd;
	}

	private int compareTo(Fraction other) {
		Fraction check = this.subtract(other);

		return check.numerator;

	}

	public Fraction(int num, int denom) {

		this.numerator = num;

		this.denominator = denom;
		if (denom < 0) {
			this.numerator *= (-1);
			this.denominator *= (-1);
		}
		reduce();

	}

	public Fraction(Fraction other) {
		this.numerator = other.numerator;
		this.denominator = other.denominator;
	}

	public Fraction(int num) {
		this.numerator = num;
		this.denominator = 1;
	}

	public Fraction add(Fraction other) {
		int num = this.numerator * other.denominator + this.denominator
				* other.numerator;

		int denom = this.denominator * other.denominator;

		Fraction result = new Fraction(num, denom);

		return result;
	}

	public void changeSign() {
		this.numerator *= -1;
	}

	public boolean isPositive() {
		return numerator > 0;
	}

	public Fraction subtract(Fraction other) {
		// a-b=a+(-b)
		Fraction negother = new Fraction(-other.numerator, other.denominator);
		Fraction result = this.add(negother);

		return result;
	}

	public Fraction multiply(Fraction other) {
		int num = this.numerator * other.numerator;
		int denom = this.denominator * other.denominator;

		Fraction result = new Fraction(num, denom);

		return result;
	}

	public Fraction divide(Fraction other) {
		// (a/b)/(c/d)=(a/b)*(d/c)
		Fraction revOther = new Fraction(other.denominator, other.numerator);
		Fraction result = this.multiply(revOther);

		return result;
	}

	public boolean isLowerThan(Fraction other) {
		return (this.compareTo(other) < 0);

	}

	public boolean isLowerOrEqual(Fraction other) {
		return (this.compareTo(other) <= 0);
	}

	public boolean isEqualTo(Fraction other) {
		return (this.compareTo(other) == 0);
	}

	public boolean isEqualOrHigher(Fraction other) {
		return (this.compareTo(other) >= 0);
	}

	public boolean isHigherThan(Fraction other) {
		return (this.compareTo(other) > 0);
	}


	public String toString() {
		if (this.numerator == 0) {
			return "0";
		}
		if (this.denominator == 1) {
			return this.numerator.toString();
		}
		if (this.numerator < 0) {
			return "-" + "\\frac{" + Math.abs(this.numerator) + "}{"
					+ this.denominator + "}";
		}
		return "\\frac{" + this.numerator + "}{" + this.denominator + "}";
	}

}