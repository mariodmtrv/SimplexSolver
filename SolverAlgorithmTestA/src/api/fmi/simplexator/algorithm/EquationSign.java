package api.fmi.simplexator.algorithm;

public enum EquationSign {
	LOWER, LOREQ, EQUAL, HOREQ, HIGHER;
	public EquationSign revert() {
		switch (this) {
		case LOWER: {
			return HIGHER;
		}
		case LOREQ: {
			return HOREQ;
		}
		case HOREQ: {
			return LOREQ;
		}
		case HIGHER: {
			return LOWER;
		}
		default: {
			return this;
		}
		}
	}
}
