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
//			uiController.addContent("ÐŸÑ€ÐµÐ¾Ð±Ñ€Ð°Ð·ÑƒÐ²Ð°Ð¼Ðµ ÐºÑŠÐ¼ Ð·Ð°Ð´Ð°Ñ‡Ð° Ð·Ð° Ð¼Ð¸Ð½Ð¸Ð¼ÑƒÐ¼.\n",
//					Destination.LOG);
//			uiController.addContent("max(Z)=min(-Z)\n", Destination.LOG);
			this.optimum = Optimum.MINIMUM;
			for (Variable variable : this.zfunction) {
				variable.changeSign();
			}
//			uiController.addContent(this.toString(), Destination.WINDOW);
			return;
		}
//		uiController.addContent("ÐžÑ€Ð¸Ð³Ð¸Ð½Ð°Ð»Ð½Ð°Ñ‚Ð° Ð·Ð°Ð´Ð°Ñ‡Ð° Ðµ Ð·Ð° Ð¼Ð¸Ð½Ð¸Ð¼ÑƒÐ¼.",
//				Destination.LOG);
	}

	private void setRightSidesToPositive() {
//		uiController
//				.addContent(
//						"Ð¢Ñ€Ð°Ð½Ñ�Ñ„Ð¾Ñ€Ð¼Ð¸Ñ€Ð°Ð¼Ðµ Ð²Ñ�Ð¸Ñ‡ÐºÐ¸ Ð¾Ð³Ñ€Ð°Ð½Ð¸Ñ‡ÐµÐ½Ð¸Ñ� Ñ� Ð¾Ñ‚Ñ€Ð¸Ñ†Ð°Ñ‚ÐµÐ»Ð½Ð¸ Ð´ÐµÑ�Ð½Ð¸ Ñ�Ñ‚Ñ€Ð°Ð½Ð¸ Ð² Ñ‚Ð°ÐºÐ¸Ð²Ð° Ñ� Ð¿Ð¾Ð»Ð¾Ð¶Ð¸Ñ‚ÐµÐ»Ð½Ð¸.",
//						Destination.LOG);
		for (Restriction restriction : restrictions) {
			restriction.rightSideToPositive();
		}
//		uiController.addContent(this.toString(), Destination.WINDOW);
	}

	private void setToEquations() {
//		uiController
//				.addContent(
//						"Ð—Ð° Ð²Ñ�Ñ�ÐºÐ¾ Ð½ÐµÑ€Ð°Ð²ÐµÐ½Ñ�Ñ‚Ð²Ð¾ Ð² Ð¾Ð³Ñ€Ð°Ð½Ð¸Ñ‡ÐµÐ½Ð¸Ñ�Ñ‚Ð° Ð·Ð°Ð¼ÐµÑ�Ñ‚Ð²Ð°Ð¼Ðµ Ñ� Ñ€Ð°Ð²ÐµÐ½Ñ�Ñ‚Ð²Ð¾, ÐºÐ°Ñ‚Ð¾ Ð´Ð¾Ð±Ð°Ð²Ñ�Ð¼Ðµ Ð´Ð¾Ð¿ÑŠÐ»Ð½Ð¸Ñ‚ÐµÐ»Ð½Ð° Ð¿Ñ€Ð¾Ð¼ÐµÐ½Ð»Ð¸Ð²Ð°.",
//						Destination.LOG);
//		uiController
//				.addContent(
//						"<Ð»Ñ�Ð²Ð°_Ñ�Ñ‚Ñ€Ð°Ð½Ð°> >= <Ð´Ñ�Ñ�Ð½Ð°_Ñ�Ñ‚Ñ€Ð°Ð½Ð°>		Ñ�Ñ‚Ð°Ð²Ð°		<Ð»Ñ�Ð²Ð°_Ñ�Ñ‚Ñ€Ð°Ð½Ð°> - \\x_k = <Ð´Ñ�Ñ�Ð½Ð°_Ñ�Ñ‚Ñ€Ð°Ð½Ð°>, ÐºÑŠÐ´ÐµÑ‚Ð¾ k Ðµ Ð½Ð¾Ð² Ð¸Ð½Ð´ÐµÐºÑ� Ð¸ \\x_k >= 0.",
//						Destination.LOG);
//		uiController
//				.addContent(
//						"<Ð»Ñ�Ð²Ð°_Ñ�Ñ‚Ñ€Ð°Ð½Ð°> <= <Ð´Ñ�Ñ�Ð½Ð°_Ñ�Ñ‚Ñ€Ð°Ð½Ð°>		Ñ�Ñ‚Ð°Ð²Ð°		<Ð»Ñ�Ð²Ð°_Ñ�Ñ‚Ñ€Ð°Ð½Ð°> + \\x_k = <Ð´Ñ�Ñ�Ð½Ð°_Ñ�Ñ‚Ñ€Ð°Ð½Ð°>, ÐºÑŠÐ´ÐµÑ‚Ð¾ k Ðµ Ð½Ð¾Ð² Ð¸Ð½Ð´ÐµÐºÑ� Ð¸ \\x_k >= 0.",
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
//						"Ð—Ð° Ð²Ñ�Ñ�ÐºÐ° Ð¿Ñ€Ð¾Ð¼ÐµÐ½Ð»Ð¸Ð²Ð° \\x_i, Ð·Ð° ÐºÐ¾Ñ�Ñ‚Ð¾ Ð½Ñ�Ð¼Ð° Ð¾Ð³Ñ€Ð°Ð½Ð¸Ñ‡ÐµÐ½Ð¸Ðµ Ð·Ð° Ð½ÐµÐ¾Ñ‚Ñ€Ð¸Ñ†Ð°Ñ‚ÐµÐ»Ð½Ð¾Ñ�Ñ‚, Ñ� Ð·Ð°Ð¼ÐµÑ�Ñ‚Ð²Ð°Ð¼Ðµ Ñ� 2 Ð¿Ñ€Ð¾Ð¼ÐµÐ½Ð»Ð¸Ð²Ð¸ \\x_i^+ Ð¸ \\x_i^-, ÐºÐ¾Ð¸Ñ‚Ð¾ Ñ�Ð° Ð½ÐµÐ¾Ñ‚Ñ€Ð¸Ñ†Ð°Ñ‚ÐµÐ»Ð½Ð¸.",
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
	
	public Vector<Boolean> getHasNegativePart() {
		return hasNegativePart;
	}

}
