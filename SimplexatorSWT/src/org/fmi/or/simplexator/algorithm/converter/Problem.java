package org.fmi.or.simplexator.algorithm.converter;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Problem extends AbstractProblem {
	private Optimum optimum;
	private Vector<Boolean> hasNegativePart;

	public Problem(Problem other) {
		this(other.zfunction, other.restrictions, other.optimum);
	}

	public Problem(List<Variable> zfunction, Vector<Restriction> restrictions,
			Optimum optimum) {
		this.optimum = optimum;
		this.zfunction = zfunction;
		this.restrictions = restrictions;
		this.restrictionsCount = restrictions.size();
		this.varCount = zfunction.size();
		setToMinimum();
		// UI.printMinimum();
		setRightSidesToPositive();
		// UI.printToPositive();
		processNegativeParts();
		// UI.printNegativeSides();
		setToEquations();
		// UI.printEquation();
	}

	private void setToMinimum() {
		if (optimum != Optimum.MINIMUM) {
			optimum = Optimum.MINIMUM;
			for (Variable variable : zfunction) {
				variable.changeSign();
			}
		}
	}

	private void setRightSidesToPositive() {
		for (Restriction restriction : restrictions) {
			restriction.rightSideToPositive();
		}
	}

	private void setToEquations() {
		for (Restriction restriction : restrictions) {
			Variable newVar = restriction.setToEquation();
			if (newVar != null) {
				Variable toAdd = new Variable(Fraction.ZERO, newVar.getIndex());
				zfunction.add(toAdd);
				// we add with zero to keep table shape consistency
				for (int i=0; i<restrictions.size();i++) {
					if (restrictions.get(i).getVarCount() < zfunction.size()) {
						addVarToRestriction(i, toAdd);
					}
					else{
						// we are at the restriction already set to equation
					}
				}
			}
		}

		// this.varCount=
	}

	public void addVarToRestriction(int restrIndex, Variable var) {
		Restriction updated = restrictions.get(restrIndex);
		updated.addVariable(var);
		restrictions.set(restrIndex, updated);
	}

	private void processNegativeParts() {
		Iterator<Variable> zfuncIter = zfunction.iterator();
		for (Boolean hnp : hasNegativePart) {
			if (hnp == true) {
				varCount++;
				// add a zfunction variable
				Variable variable = (Variable) zfuncIter;
				List<Variable> varSigned = variable.bipartize();
				int varIndex = 0;
				zfuncIter.remove();
				zfunction.addAll(varIndex, varSigned);

				// change the variable in the restrictions
				for (int restrictionIndex = 0; restrictionIndex < restrictionsCount; restrictionIndex++) {
					Restriction changedRestriction = restrictions
							.get(restrictionIndex);
					changedRestriction.bipartizeVariable(varIndex);
					restrictions.set(restrictionIndex, changedRestriction);
				}
			}

		}

	}
}
