package org.fmi.or.simplexator.algorithm.converter;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Problem extends AbstractProblem {
	private Optimum optimum;
	private Vector<Boolean> hasNegativePart;
	protected Integer maxIndex;

	public Problem(Problem other) {
		this(other.zfunction, other.restrictions, other.optimum,
				other.hasNegativePart);
	}

	public Problem(List<Variable> zfunction, Vector<Restriction> restrictions,
			Optimum optimum, Vector<Boolean> hasNegativePart) {
		this.optimum = optimum;
		this.zfunction = zfunction;
		this.restrictions = restrictions;
		this.restrictionsCount = restrictions.size();
		this.varCount = zfunction.size();
		this.hasNegativePart = hasNegativePart;
		this.maxIndex = zfunction.get(zfunction.size() - 1).getIndex();
	}

	public void convertToK() {
		setToMinimum();
	
		setRightSidesToPositive();
	
		processNegativeParts();
		
		setToEquations();

	}

	private void setToMinimum() {
		if (this.optimum != Optimum.MINIMUM) {
		//	UI.printMessage("Преобразуваме към задача за минимум.\n");
		//UI.printMessage("max(Z)=min(-Z)\n");
			this.optimum = Optimum.MINIMUM;
			for (Variable variable : this.zfunction) {
				variable.changeSign();
			}
		//UI.printProblem(problem);
			return;
		}
		//UI.printMessage("Оригиналната задача е за минимум.");
	}

	private void setRightSidesToPositive() {
		//UI.printMessage("Трансформираме всички ограничения с отрицателни десни страни в такива с положителни.")
		for (Restriction restriction : restrictions) {
			restriction.rightSideToPositive();
		}
		//UI.printProblem(problem);
	}

	private void setToEquations() {
		//UI.printMessage("За всяко неравенство в ограниченията заместваме с равенство, като добавяме допълнителна променлива.");
		//UI.printMessage("<лява_страна> >= <дясна_страна>		става		<лява_страна> - \\x_k = <дясна_страна>, където k е нов индекс и \\x_k >= 0.");
		//UI.printMessage("<лява_страна> <= <дясна_страна>		става		<лява_страна> + \\x_k = <дясна_страна>, където k е нов индекс и \\x_k >= 0.");
		for (Restriction restriction : restrictions) {
			Variable newVar = restriction.setToEquation(maxIndex);
			if (newVar != null) {
				Variable toAdd = new Variable(Fraction.ZERO, newVar.getIndex());
				maxIndex++;
				varCount++;
				zfunction.add(toAdd);
				// we add with zero to keep table shape consistency
				for (int i = 0; i < restrictions.size(); i++) {
					if (restrictions.get(i).getVarCount() < zfunction.size()) {
						addVarToRestriction(i, toAdd);
					} else {
						// we are at the restriction already set to equation
					}
				}
			}
		}
	}

	public void addVarToRestriction(int restrIndex, Variable var) {
		Restriction updated = restrictions.get(restrIndex);
		updated.addVariable(var);
		restrictions.set(restrIndex, updated);
	}

	private void processNegativeParts() {
		
		//UI.printMessage("За всяка променлива \\x_i, за която няма
		//ограничение за неотрицателност, я заместваме с 2 променливи \\x_i^+ и \\x_i^-, които са неотрицателни.");
		int varsPassed = 0;
		Iterator<Variable> zfuncIter = zfunction.iterator();
		for (Boolean hnp : hasNegativePart) {
			Variable variable = (Variable) zfuncIter.next();
			varsPassed++;
			if (hnp == true) {
				varCount++;
				// add a zfunction variable
				List<Variable> varSigned = variable.bipartize();
				int varIndex = varsPassed - 1;
				zfuncIter.remove();
				zfunction.addAll(varIndex, varSigned);
				// one more var was added, we update the iterator
				zfuncIter = zfunction.iterator();
				varsPassed++;
				for (int i = 0; i < varsPassed; i++) {
					zfuncIter.next();
				}
				// change the variable in the restrictions
				for (int restrictionIndex = 0; restrictionIndex < restrictionsCount; restrictionIndex++) {
					Restriction changedRestriction = restrictions
							.get(restrictionIndex);
					changedRestriction.bipartizeVariable(varIndex);
					restrictions.set(restrictionIndex, changedRestriction);
				}
			}

		}
//UI.printProblem(problem);
	}
}
