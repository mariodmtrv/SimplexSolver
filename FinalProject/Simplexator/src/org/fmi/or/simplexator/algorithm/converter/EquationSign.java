package org.fmi.or.simplexator.algorithm.converter;

public enum EquationSign {
	LTE, EQ, GTE;
	public static EquationSign toSign(String sign) {
		switch (sign) {
		case "LTE": {
			return LTE;
		}
		case "GTE": {
			return GTE;
		}
		case "EQ": {
			return EQ;
		}
		default: {
			return null;
		}
		}
	}

	public EquationSign revert() {
		switch (this) {
		case LTE: {
			return GTE;
		}
		case GTE: {
			return LTE;
		}
		default: {
			return this;
		}
		}
	}
}
