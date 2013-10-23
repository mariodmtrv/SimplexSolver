package org.fmi.or.simplexator.algorithm.computation;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class SimplexTable {
	
	public Table table;

	public Vector<Variable> basis;
	public Vector<Integer> basisIndeces;
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
	
	public Fraction getSimplexTableItem(int i, int j) {
		if(j > getVarCount() || i > getRestrictionsCount()+1) {
			// out of bounds
			return null;
		}
		
		if(j < getVarCount() && i < getRestrictionsCount()) {
			// get a value from the Table table
			return table.getElement(i, j);
		}
		
		if(j == getVarCount() && i >= getRestrictionsCount()) {
			// get Z-costs
			if(i == getRestrictionsCount()) {
				return resultNumValue;
			}
			else {
				return resultMValue;
			}
		}
		
		if(j == getVarCount()) {
			// get right side vector
			return rightSideValues.get(i);
		}
		
		if(i >= getRestrictionsCount()) {
			// get costs
			if(i == getRestrictionsCount()) {
				return numCost.get(j);
			}
			else {
				return MCost.get(j);
			}
		}
		
		return null;
	}
	
	public void setSimplexTableItem(int i, int j, Fraction value) {
		if(j < getVarCount() && i < getRestrictionsCount()) {
			// set a value from the Table table
			table.setElement(i, j, value);
		}
		
		if(j == getVarCount() && i >= getRestrictionsCount()) {
			// set Z-costs
			if(i == getRestrictionsCount()) {
				resultNumValue = value;
			}
			else {
				resultMValue = value;
			}
		}
		
		if(j == getVarCount()) {
			// set right side vector
			rightSideValues.setElementAt(value, i);
		}
		
		if(i >= getRestrictionsCount()) {
			// set costs
			if(i == getRestrictionsCount()) {
				numCost.setElementAt(value, j);
			}
			else {
				MCost.setElementAt(value, j);
			}
		}
	}
}

