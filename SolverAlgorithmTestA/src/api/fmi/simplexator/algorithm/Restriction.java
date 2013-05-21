package api.fmi.simplexator.algorithm;

import java.util.Vector;

public class Restriction {
	private Vector<Variable> variables;
	private EquationSign sign;
	private Fraction rightSide;

	public Restriction(Vector<Variable> variables, EquationSign sign,
			Fraction rightSide) {
		super();
		this.variables = variables;
		this.sign = sign;
		this.rightSide = rightSide;
	}

}
