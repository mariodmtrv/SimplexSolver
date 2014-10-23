package org.fmi.or.simplexator.algorithm.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Restriction {
	private List<Variable> variables;
	private EquationSign sign;
	private Fraction rightSide;

	public Restriction(Restriction other) {
		this.sign = other.sign;
		this.rightSide = other.rightSide;
		this.variables = new ArrayList<>();
		for (int i = 0; i < other.variables.size(); i++) {
			this.variables.add(new Variable(other.variables.get(i)));
		}
	}

	public EquationSign getSign() {
		return sign;
	}

	public void setSign(EquationSign sign) {
		this.sign = sign;
	}

	public Fraction getRightSide() {
		return rightSide;
	}

	public int getVarCount() {
		return variables.size();
	}

	public Variable[] getVariables() {
		Variable[] vars = new Variable[variables.size()];
		vars = variables.toArray(vars);
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

	/**
	 * @return the variable to add to zfunction null if it is already equation
	 * */
	public Variable setToEquation(Integer maxIndex) {
		if (this.sign == EquationSign.EQ) {
			// nothing to change
			return null;
		} else if (this.sign == EquationSign.GTE) {
			this.sign = EquationSign.EQ;
			Variable newVar = new Variable(new Fraction(-1), maxIndex + 1);
			this.variables.add(newVar);
			return newVar;
		} else if (this.sign == EquationSign.LTE) {
			this.sign = EquationSign.EQ;
			Variable newVar = new Variable(new Fraction(1), maxIndex + 1);
			this.variables.add(newVar);
			return newVar;
		}
		return null;
	}

	public void bipartizeVariable(int index) {
		List<Variable> varSigned = variables.get(index).bipartize();
		variables.remove(index);
		variables.addAll(index, varSigned);
	}

	public void addVariable(Variable var) {
		variables.add(var);
	}
/*
	public String toString() {
		StringBuilder pretty = new StringBuilder();
		pretty.append(variables.get(0).toString());
		for (int index = 1; index < variables.size(); index++) {
			if (variables.get(index).getCoefficient()
					.isEqualOrHigher(Fraction.ZERO)) {
				pretty.append("+");
			}
			pretty.append(variables.get(index).toString());
		}
		pretty.append(getEquationSign());
		pretty.append(rightSide.toString());
		pretty.append("\\cr");
		return pretty.toString();

	}
*/
	private String getEquationSign() {
		if (sign == EquationSign.EQ) {
			return "=";
		}
		if (sign == EquationSign.GTE) {
			return "\\geq";
		}
		return "\\leq";
	}
}
