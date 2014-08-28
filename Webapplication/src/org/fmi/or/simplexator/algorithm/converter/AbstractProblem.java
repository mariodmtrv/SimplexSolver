package org.fmi.or.simplexator.algorithm.converter;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

//import org.fmi.or.simplexator.visualization.UIController;

public abstract class AbstractProblem {
	protected int varCount;
	protected int restrictionsCount;
	protected List<Variable> zfunction;
	protected Vector<Restriction> restrictions;
//	protected UIController uiController;

	public int getRestrictionsCount() {
		return restrictionsCount;
	}

	public Restriction getRestriction(int index) {
		return restrictions.elementAt(index);
	}

//	public UIController getUIControler() {
//		return uiController;
//	}

	public int getVarCount() {
		return varCount;
	}

	public Variable[] getZfunctionVariables() {
		/* investigate +1 case */
		Variable[] vars = new Variable[zfunction.size()];
		vars = zfunction.toArray(vars);
		return vars;
	}

	/**
	 * @return null if the variable was not found, index at which it is in the
	 *         list otherwise
	 * */
	public int getVarIndex(Variable v) {
		Iterator it = zfunction.iterator();
		int index = 0;
		Variable iterelem;
		while (it.hasNext()) {
			iterelem = (Variable) it.next();
			if (iterelem.compareTo(v) == 0) {
				return index;
			}

			index++;
		}
		return (Integer) null;
	}

	/**
	 * @return the variable listed under index i
	 * */
	public Variable getVarByIndex(int i) {
		return zfunction.get(i);
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Restriction z : restrictions) {
			result.append(z.toString());
		}
		return result.toString();
	}
}
