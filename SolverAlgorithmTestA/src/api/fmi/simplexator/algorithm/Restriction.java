package api.fmi.simplexator.algorithm;

import java.util.List;

public class Restriction {
	private List<Variable> variables;
	private EquationSign sign;
	private Fraction rightSide;

	public Restriction(List<Variable> variables, EquationSign sign,
			Fraction rightSide) {
		super();
		this.variables = variables;
		this.sign = sign;
		this.rightSide = rightSide;
	}

	public void rightSideToPositive() {
		if (!(rightSide.isPositive())) {
			rightSide.changeSign();

			for (Variable var : variables) {
				var.changeSign();
			}
			// traditional *(-1) rule
			sign = sign.revert();

		}
	}

	public void bipartizeVariable(int index) {
		List<Variable> varSigned = variables.get(index).bipartize();
		variables.remove(index);
		variables.addAll(index, varSigned);
	}

}
