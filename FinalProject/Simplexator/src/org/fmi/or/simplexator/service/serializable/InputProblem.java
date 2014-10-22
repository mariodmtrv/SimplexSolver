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
		int varsCount = Integer.parseInt(numVars);
		int restrCount = Integer.parseInt(numRestrictions);
		List<Variable> zfunction = new LinkedList<Variable>();
		for (int varIndex = 0; varIndex < varsCount; varIndex++) {
			zfunction.add(new Variable(new Fraction(zfunc.get(varIndex)),
					varIndex + 1));
		}

		Vector<Restriction> restrictions = new Vector<Restriction>();

		for (int restrIndex = 0; restrIndex < restrCount; restrIndex++) {
			List<String> restrictionString = this.restrictions.get(restrIndex);
			List<Variable> restrictionVars = new LinkedList<Variable>();
			for (int varIndex = 0; varIndex < varsCount; varIndex++) {
				restrictionVars.add(new Variable(new Fraction(restrictionString
						.get(varIndex)), varIndex + 1));
			}
			Restriction transformedRestriction = new Restriction(
					restrictionVars, EquationSign.toSign(restrictionString
							.get(varsCount)), new Fraction(
							restrictionString.get(varsCount + 1)));
			restrictions.add(transformedRestriction);
		}
		Optimum optimum = Optimum.fromString(type);
		Vector<Boolean> hasNegativePart = new Vector<>();
		for (String isPos : isPositive) {
			if (isPos.equals("true")) {
				hasNegativePart.add(false);
			} else if (isPos.equals("false")) {
				hasNegativePart.add(true);
			}
		}
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
