package com.fmi.or.simplexator.algorithm.tabularproblemconverter;

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
