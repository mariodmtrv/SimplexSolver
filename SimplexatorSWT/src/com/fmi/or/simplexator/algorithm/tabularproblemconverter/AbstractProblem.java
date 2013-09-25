package com.fmi.or.simplexator.algorithm.tabularproblemconverter;

import java.util.List;
import java.util.Vector;

public abstract class AbstractProblem {
    protected int varCount;
	protected int restrictionsCount;
	protected List<Variable> zfunction;
	public int getRestrictionsCount() {
		return restrictionsCount;
	}
	protected Vector<Restriction> restrictions;
	
	public Restriction getRestriction(int index){
		return restrictions.elementAt(index);
	}
	public int getVarCount() {
		return varCount;
	}
	
	public Variable[] getZfunctionVariables(){
    	Variable[] vars=new Variable[zfunction.size()+1];
    	vars=zfunction.toArray(vars);
    	return vars;
    }
}
