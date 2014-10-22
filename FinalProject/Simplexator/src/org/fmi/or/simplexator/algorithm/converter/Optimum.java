package org.fmi.or.simplexator.algorithm.converter;

public enum Optimum {
	MINIMUM, MAXIMUM;
	public String toString() {
		if (this == MAXIMUM)
			return "MAX";
		if (this == MINIMUM)
			return "MIN";
		return "UNDEFINED";
	}

	public static Optimum fromString(String strOpt) {
		switch (strOpt) {
		case "MIN": {
			return MINIMUM;
		}
		case "MAX": {
			return MAXIMUM;
		}
		default: {
			return null;
		}
		}
	}
}
