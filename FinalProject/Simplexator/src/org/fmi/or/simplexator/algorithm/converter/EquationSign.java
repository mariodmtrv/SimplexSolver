package org.fmi.or.simplexator.algorithm.converter;

public enum EquationSign {
	LTE, EQ, GTE;
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
