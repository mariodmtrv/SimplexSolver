package org.fmi.or.simplexator.algorithm.computation;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class SimplexTable {
	
	public Table table;

	public Vector<Variable> basis;
	public Vector<Fraction> zfunctionCoefficients;

	public Vector<Fraction> numCost;
	public Vector<Fraction> MCost;
	
	public Vector<Fraction> rightSideValues;
	public Fraction resultNumValue;
	public Fraction resultMValue;
	
	public int getVarCount(){
		return numCost.size();
	}
	public int getRestrictionsCount(){
		return basis.size();
	}
}

