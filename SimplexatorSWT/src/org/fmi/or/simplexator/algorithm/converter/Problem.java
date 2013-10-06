package org.fmi.or.simplexator.algorithm.converter;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Problem extends AbstractProblem {
	private Optimum optimum;
	private Vector<Boolean> hasNegativePart;

	public Problem(List<Variable> zfunction,Vector<Restriction> restrictions, Optimum optimum){
		this.optimum=optimum;
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

	Iterator<Variable> zfuncIter = zfunction.iterator();

	private void processNegativeParts() {
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
