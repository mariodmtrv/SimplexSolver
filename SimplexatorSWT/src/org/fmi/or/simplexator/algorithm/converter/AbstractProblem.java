package org.fmi.or.simplexator.algorithm.converter;

import java.util.Iterator;
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
	/**
	 * @return null if the variable was not found,
	 * index at which it is in the list otherwise 
	 * */
	public int getVarIndex(Variable v) {
		Iterator it=zfunction.iterator();
		int index = 0;
		while(it.hasNext()) {
			if(it==v) {
				return index;
			}
			index++;
		}
		return (Integer) null;
	}
}
