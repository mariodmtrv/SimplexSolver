package org.fmi.or.simplexator.algorithm.converter;

import java.util.List;
import java.util.Vector;

public class Restriction {
	private List<Variable> variables;
	private EquationSign sign;
	private Fraction rightSide;
    public Fraction getRightSide() {
		return rightSide;
	}
	public Variable[] getVariables(){
    	Variable[] vars=new Variable[variables.size()+1];
    	vars=variables.toArray(vars);
    	return vars;
    }
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
