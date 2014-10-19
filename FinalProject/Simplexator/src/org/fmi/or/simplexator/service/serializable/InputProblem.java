package org.fmi.or.simplexator.service.serializable;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.EquationSign;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Optimum;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class InputProblem {
	/*
	 * {"type":"MIN", "numVars":"3", "numRestrictions":"4",
	 * "zfunc":["1/2","2/3","3/4"], "restrictions":[["1","2","3","GTE","4"],
	 * ["5","6","7","LTE","8"], ["9","10","11","EQ","12"],
	 * ["13","14","15","EQ","16"]], "isPositive":[true,false,true]}"
	 */
	String type;
	String numVars;
	String numRestrictions;
	List<String> zfunc;
	List<List<String>> restrictions;
	List<String> isPositive;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumVars() {
		return numVars;
	}

	public void setNumVars(String numVars) {
		this.numVars = numVars;
	}

	public String getNumRestrictions() {
		return numRestrictions;
	}

	public void setNumRestrictions(String numRestrictions) {
		this.numRestrictions = numRestrictions;
	}

	public List<String> getZfunc() {
		return zfunc;
	}

	public void setZfunc(List<String> zfunc) {
		this.zfunc = zfunc;
	}

	public List<List<String>> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<List<String>> restrictions) {
		this.restrictions = restrictions;
	}

	public List<String> getIsPositive() {
		return isPositive;
	}

	public void setIsPositive(List<String> isPositive) {
		this.isPositive = isPositive;
	}

	public Problem getProblem() {
		List<Variable> zfunction = new LinkedList<Variable>();
		zfunction.add(new Variable(new Fraction(2), 1));
		zfunction.add(new Variable(new Fraction(1), 2));
		zfunction.add(new Variable(new Fraction(2), 3));

		Vector<Restriction> restrictions = new Vector<Restriction>();
		List<Variable> firstRestr = new LinkedList<Variable>();
		firstRestr.add(new Variable(new Fraction(1), 1));
		firstRestr.add(new Variable(new Fraction(0), 2));
		firstRestr.add(new Variable(new Fraction(-1), 3));
		Restriction first = new Restriction(firstRestr, EquationSign.LTE,
				new Fraction(-1));
		Optimum optimum = Optimum.MINIMUM;
		Vector<Boolean> hasNegativePart = new Vector<>();
		hasNegativePart.add(true);
		hasNegativePart.add(false);
		hasNegativePart.add(false);
		Problem problem = new Problem(zfunction, restrictions, optimum,
				hasNegativePart);
		return problem;
	}

	@Override
	public String toString() {
		return "InputProblem [type=" + type + ", numVars=" + numVars
				+ ", numRestrictions=" + numRestrictions + ", zfunc=" + zfunc
				+ ", restrictions=" + restrictions + ", isPositive="
				+ isPositive + "]";
	}
}
