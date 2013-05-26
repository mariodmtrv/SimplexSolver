package api.fmi.simplexator.algorithm;

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
