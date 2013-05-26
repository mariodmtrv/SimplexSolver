package api.fmi.simplexator.algorithm;

import java.util.List;

public class Variable {
	Fraction coefficient;
	int index;
	VariableType type;
	
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
}
