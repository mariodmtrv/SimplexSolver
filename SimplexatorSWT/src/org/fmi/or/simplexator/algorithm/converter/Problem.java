package org.fmi.or.simplexator.algorithm.converter;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

//import org.fmi.or.simplexator.visualization.Destination;
//import org.fmi.or.simplexator.visualization.UIController;

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
//		this.uiController = new UIController();
		this.optimum = optimum;
		this.zfunction = zfunction;
		this.restrictions = restrictions;
		this.restrictionsCount = restrictions.size();
		this.varCount = zfunction.size();
		this.hasNegativePart = hasNegativePart;
		this.maxIndex = zfunction.get(zfunction.size() - 1).getIndex();
	}

	public void convertToK() {
//		uiController.addContent(this.toString(), Destination.WINDOW);
		setToMinimum();

		setRightSidesToPositive();

		processNegativeParts();

		setToEquations();

	}

	private void setToMinimum() {
		if (this.optimum != Optimum.MINIMUM) {
//			uiController.addContent("Преобразуваме към задача за минимум.\n",
//					Destination.LOG);
//			uiController.addContent("max(Z)=min(-Z)\n", Destination.LOG);
			this.optimum = Optimum.MINIMUM;
			for (Variable variable : this.zfunction) {
				variable.changeSign();
			}
//			uiController.addContent(this.toString(), Destination.WINDOW);
			return;
		}
//		uiController.addContent("Оригиналната задача е за минимум.",
//				Destination.LOG);
	}

	private void setRightSidesToPositive() {
//		uiController
//				.addContent(
//						"Трансформираме всички ограничения с отрицателни десни страни в такива с положителни.",
//						Destination.LOG);
		for (Restriction restriction : restrictions) {
			restriction.rightSideToPositive();
		}
//		uiController.addContent(this.toString(), Destination.WINDOW);
	}

	private void setToEquations() {
//		uiController
//				.addContent(
//						"За всяко неравенство в ограниченията заместваме с равенство, като добавяме допълнителна променлива.",
//						Destination.LOG);
//		uiController
//				.addContent(
//						"<лява_страна> >= <дясна_страна>		става		<лява_страна> - \\x_k = <дясна_страна>, където k е нов индекс и \\x_k >= 0.",
//						Destination.LOG);
//		uiController
//				.addContent(
//						"<лява_страна> <= <дясна_страна>		става		<лява_страна> + \\x_k = <дясна_страна>, където k е нов индекс и \\x_k >= 0.",
//						Destination.LOG);
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

//		uiController
//				.addContent(
//						"За всяка променлива \\x_i, за която няма ограничение за неотрицателност, я заместваме с 2 променливи \\x_i^+ и \\x_i^-, които са неотрицателни.",
//						Destination.LOG);
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
//		uiController.addContent(this.toString(), Destination.WINDOW);
	}

	public String toString() {
		StringBuilder pretty = new StringBuilder();
		pretty.append(getExtremal());
		pretty.append(zfunction.get(0).toString());
		for (int index = 1; index < zfunction.size(); index++) {
			if (zfunction.get(index).getCoefficient()
					.isEqualOrHigher(Fraction.ZERO)) {
				pretty.append("+");
			}
			pretty.append(zfunction.get(index).toString());

		}
		pretty.append("\\cr\\cr");

		for (Restriction restr : restrictions) {
			pretty.append(restr.toString());
		}
		return pretty.toString();
	}

	private String getExtremal() {
		if (optimum == Optimum.MINIMUM) {
			return "{\\bf min {\\it z}}=";
		}
		return "{\\bf max {\\it z}}=";
	}
}
