package org.fmi.or.simplexator.algorithm.converter;

import java.util.List;

public class Variable implements Comparable {

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

	public List<Variable> bipartize() {
		List<Variable> result = null;
		result.add(new Variable(this.coefficient, this.index,
				VariableType.POSITIVE));
		Fraction negativeCoef = this.coefficient;
		negativeCoef.changeSign();
		result.add(new Variable(negativeCoef, this.index, VariableType.NEGATIVE));
		return result;
	}

	public Fraction getCoefficient() {
		return coefficient;
	}

	public int getTypeIndex() {
		if (type == VariableType.NEGATIVE) {
			return -1;
		}
		if (type == VariableType.POSITIVE) {
			return 1;
		}
		return 0;
	}

	@Override
	public int compareTo(Object arg0) {
		Variable other = (Variable) arg0;
		if (this.index == other.index) {
			if (this.getTypeIndex() < other.getTypeIndex())
				return -1;
			if (this.getTypeIndex() > other.getTypeIndex())
				return 1;
			return 0;
		}
		return this.index - other.index;
	}

	@Override
	public boolean equals(Object obj) {
		return (this.index == ((Variable) obj).index)
				&& (this.getTypeIndex() == ((Variable) obj).getTypeIndex());
	}
	@Override
	public int hashCode(){
	final int MAX_INDEX=100;
		return MAX_INDEX*getTypeIndex()+index; 
	}
}
