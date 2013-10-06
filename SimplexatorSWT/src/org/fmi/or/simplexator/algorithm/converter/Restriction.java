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
    public int getVarCount(){
    	return variables.size();
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
	/**
	 * @return the variable to add to zfunction
	 * null if it is already equation
	 * */
	public Variable setToEquation(){
		if(this.sign==EquationSign.EQ){
			// nothing to change
			return null;
		}
		else if(this.sign==EquationSign.GTE){
			this.sign=EquationSign.EQ;
			Variable newVar=new Variable(new Fraction(-1),variables.size()+1);
		this.variables.add(newVar);
			return newVar;
		}
		else if(this.sign==EquationSign.LTE){
			this.sign=EquationSign.EQ;
			Variable newVar=new Variable(new Fraction(1),variables.size()+1);
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
	public void addVariable(Variable var){
		variables.add(var);
	}

}
