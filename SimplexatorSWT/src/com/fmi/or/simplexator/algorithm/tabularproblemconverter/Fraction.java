package com.fmi.or.simplexator.algorithm.tabularproblemconverter;

public class Fraction {
	private int numerator;
	private int denominator;

	public static final Fraction M = new Fraction(7_000_003);
	public static final Fraction ZERO = new Fraction(0);
	
	private int gcd() {
		while (numerator > 1 && denominator > 1) {
			if (numerator > denominator) {
				numerator %= denominator;
			} else
				denominator %= numerator;
		}
		return Math.max(numerator, denominator);

	}

	private void reduce() {
		int gcd = gcd();
		numerator /= gcd;
		denominator /= gcd;
	}

	private int compareTo(Fraction other) {
		Fraction check = this.subtract(other);

		return check.numerator;

	}

	public Fraction(int num, int denom) {
		numerator = num;
		denominator = denom;
		reduce();

	}

	public Fraction(int num) {
		numerator = num;
		denominator = 1;
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

}