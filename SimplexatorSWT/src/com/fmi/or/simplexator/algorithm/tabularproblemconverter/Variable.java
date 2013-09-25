package com.fmi.or.simplexator.algorithm.tabularproblemconverter;

import java.util.List;

public class Variable {
	private Fraction coefficient;
	private int index;
	private VariableType type;
	
	public Variable(Fraction coefficient, int index, VariableType type) {
		super();
		this.coefficient = coefficient;
		this.index = index;
		this.type = type;
	}

	public Variable(Fraction coefficient, int index) {
		super();
		this.coefficient = coefficient;
		this.index = index;
		this.type = VariableType.NORMAL;
	}

	public void changeSign() {
		coefficient.changeSign();
	}

	@SuppressWarnings("null")
	public List<Variable> bipartize() {
		List<Variable> result = null;
		result.add(new Variable(this.coefficient, this.index,
				VariableType.POSITIVE));
		Fraction negativeCoef = this.coefficient;
		negativeCoef.changeSign();
		result.add(new Variable(negativeCoef, this.index, VariableType.NEGATIVE));
		return result;
	}
	public Fraction getCoefficient(){
		return coefficient;
	}
}
