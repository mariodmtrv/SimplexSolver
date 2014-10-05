package org.fmi.or.simplexator.algorithm.converter;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.fmi.or.simplexator.answerqueue.AnswerQueue;

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
		// this.uiController = new UIController();
		this.optimum = optimum;
		this.zfunction = zfunction;
		this.restrictions = restrictions;
		this.restrictionsCount = restrictions.size();
		this.varCount = zfunction.size();
		this.hasNegativePart = hasNegativePart;
		this.maxIndex = zfunction.get(zfunction.size() - 1).getIndex();
	}

	public void convertToK() {
		AnswerQueue answerQueue = null;
		convertToK(answerQueue);
	}

	public void convertToK(AnswerQueue answerQueue) {
		// uiController.addContent(this.toString(), Destination.WINDOW);
		answerQueue.addMessage("allToMinimum");
		answerQueue.addProblemStep(this);
		this.optimum = Optimum.MINIMUM;
		answerQueue.addProblemStep(this);
		setToMinimum(answerQueue);

		setRightSidesToPositive();

		processNegativeParts();

		setToEquations();

	}

	private void setToMinimum(AnswerQueue answerQueue) {
		if (this.optimum != Optimum.MINIMUM) {
			// uiController.addContent("Ã�Å¸Ã‘â‚¬Ã�ÂµÃ�Â¾Ã�Â±Ã‘â‚¬Ã�Â°Ã�Â·Ã‘Æ’Ã�Â²Ã�Â°Ã�Â¼Ã�Âµ Ã�ÂºÃ‘Å Ã�Â¼ Ã�Â·Ã�Â°Ã�Â´Ã�Â°Ã‘â€¡Ã�Â° Ã�Â·Ã�Â° Ã�Â¼Ã�Â¸Ã�Â½Ã�Â¸Ã�Â¼Ã‘Æ’Ã�Â¼.\n",
			// Destination.LOG);
			// uiController.addContent("max(Z)=min(-Z)\n", Destination.LOG);
			this.optimum = Optimum.MINIMUM;
			for (Variable variable : this.zfunction) {
				variable.changeSign();
			}
			// uiController.addContent(this.toString(), Destination.WINDOW);
			return;
		}
		// uiController.addContent("Ã�Å¾Ã‘â‚¬Ã�Â¸Ã�Â³Ã�Â¸Ã�Â½Ã�Â°Ã�Â»Ã�Â½Ã�Â°Ã‘â€šÃ�Â° Ã�Â·Ã�Â°Ã�Â´Ã�Â°Ã‘â€¡Ã�Â° Ã�Âµ Ã�Â·Ã�Â° Ã�Â¼Ã�Â¸Ã�Â½Ã�Â¸Ã�Â¼Ã‘Æ’Ã�Â¼.",
		// Destination.LOG);
	}

	private void setRightSidesToPositive() {
		// uiController
		// .addContent(
		// "Ã�Â¢Ã‘â‚¬Ã�Â°Ã�Â½Ã‘ï¿½Ã‘â€žÃ�Â¾Ã‘â‚¬Ã�Â¼Ã�Â¸Ã‘â‚¬Ã�Â°Ã�Â¼Ã�Âµ Ã�Â²Ã‘ï¿½Ã�Â¸Ã‘â€¡Ã�ÂºÃ�Â¸ Ã�Â¾Ã�Â³Ã‘â‚¬Ã�Â°Ã�Â½Ã�Â¸Ã‘â€¡Ã�ÂµÃ�Â½Ã�Â¸Ã‘ï¿½ Ã‘ï¿½ Ã�Â¾Ã‘â€šÃ‘â‚¬Ã�Â¸Ã‘â€ Ã�Â°Ã‘â€šÃ�ÂµÃ�Â»Ã�Â½Ã�Â¸ Ã�Â´Ã�ÂµÃ‘ï¿½Ã�Â½Ã�Â¸ Ã‘ï¿½Ã‘â€šÃ‘â‚¬Ã�Â°Ã�Â½Ã�Â¸ Ã�Â² Ã‘â€šÃ�Â°Ã�ÂºÃ�Â¸Ã�Â²Ã�Â° Ã‘ï¿½ Ã�Â¿Ã�Â¾Ã�Â»Ã�Â¾Ã�Â¶Ã�Â¸Ã‘â€šÃ�ÂµÃ�Â»Ã�Â½Ã�Â¸.",
		// Destination.LOG);
		for (Restriction restriction : restrictions) {
			restriction.rightSideToPositive();
		}
		// uiController.addContent(this.toString(), Destination.WINDOW);
	}

	private void setToEquations() {
		// uiController
		// .addContent(
		// "Ã�â€”Ã�Â° Ã�Â²Ã‘ï¿½Ã‘ï¿½Ã�ÂºÃ�Â¾ Ã�Â½Ã�ÂµÃ‘â‚¬Ã�Â°Ã�Â²Ã�ÂµÃ�Â½Ã‘ï¿½Ã‘â€šÃ�Â²Ã�Â¾ Ã�Â² Ã�Â¾Ã�Â³Ã‘â‚¬Ã�Â°Ã�Â½Ã�Â¸Ã‘â€¡Ã�ÂµÃ�Â½Ã�Â¸Ã‘ï¿½Ã‘â€šÃ�Â° Ã�Â·Ã�Â°Ã�Â¼Ã�ÂµÃ‘ï¿½Ã‘â€šÃ�Â²Ã�Â°Ã�Â¼Ã�Âµ Ã‘ï¿½ Ã‘â‚¬Ã�Â°Ã�Â²Ã�ÂµÃ�Â½Ã‘ï¿½Ã‘â€šÃ�Â²Ã�Â¾, Ã�ÂºÃ�Â°Ã‘â€šÃ�Â¾ Ã�Â´Ã�Â¾Ã�Â±Ã�Â°Ã�Â²Ã‘ï¿½Ã�Â¼Ã�Âµ Ã�Â´Ã�Â¾Ã�Â¿Ã‘Å Ã�Â»Ã�Â½Ã�Â¸Ã‘â€šÃ�ÂµÃ�Â»Ã�Â½Ã�Â° Ã�Â¿Ã‘â‚¬Ã�Â¾Ã�Â¼Ã�ÂµÃ�Â½Ã�Â»Ã�Â¸Ã�Â²Ã�Â°.",
		// Destination.LOG);
		// uiController
		// .addContent(
		// "<Ã�Â»Ã‘ï¿½Ã�Â²Ã�Â°_Ã‘ï¿½Ã‘â€šÃ‘â‚¬Ã�Â°Ã�Â½Ã�Â°> >= <Ã�Â´Ã‘ï¿½Ã‘ï¿½Ã�Â½Ã�Â°_Ã‘ï¿½Ã‘â€šÃ‘â‚¬Ã�Â°Ã�Â½Ã�Â°>		Ã‘ï¿½Ã‘â€šÃ�Â°Ã�Â²Ã�Â°		<Ã�Â»Ã‘ï¿½Ã�Â²Ã�Â°_Ã‘ï¿½Ã‘â€šÃ‘â‚¬Ã�Â°Ã�Â½Ã�Â°> - \\x_k = <Ã�Â´Ã‘ï¿½Ã‘ï¿½Ã�Â½Ã�Â°_Ã‘ï¿½Ã‘â€šÃ‘â‚¬Ã�Â°Ã�Â½Ã�Â°>, Ã�ÂºÃ‘Å Ã�Â´Ã�ÂµÃ‘â€šÃ�Â¾ k Ã�Âµ Ã�Â½Ã�Â¾Ã�Â² Ã�Â¸Ã�Â½Ã�Â´Ã�ÂµÃ�ÂºÃ‘ï¿½ Ã�Â¸ \\x_k >= 0.",
		// Destination.LOG);
		// uiController
		// .addContent(
		// "<Ã�Â»Ã‘ï¿½Ã�Â²Ã�Â°_Ã‘ï¿½Ã‘â€šÃ‘â‚¬Ã�Â°Ã�Â½Ã�Â°> <= <Ã�Â´Ã‘ï¿½Ã‘ï¿½Ã�Â½Ã�Â°_Ã‘ï¿½Ã‘â€šÃ‘â‚¬Ã�Â°Ã�Â½Ã�Â°>		Ã‘ï¿½Ã‘â€šÃ�Â°Ã�Â²Ã�Â°		<Ã�Â»Ã‘ï¿½Ã�Â²Ã�Â°_Ã‘ï¿½Ã‘â€šÃ‘â‚¬Ã�Â°Ã�Â½Ã�Â°> + \\x_k = <Ã�Â´Ã‘ï¿½Ã‘ï¿½Ã�Â½Ã�Â°_Ã‘ï¿½Ã‘â€šÃ‘â‚¬Ã�Â°Ã�Â½Ã�Â°>, Ã�ÂºÃ‘Å Ã�Â´Ã�ÂµÃ‘â€šÃ�Â¾ k Ã�Âµ Ã�Â½Ã�Â¾Ã�Â² Ã�Â¸Ã�Â½Ã�Â´Ã�ÂµÃ�ÂºÃ‘ï¿½ Ã�Â¸ \\x_k >= 0.",
		// Destination.LOG);
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

		// uiController
		// .addContent(
		// "Ã�â€”Ã�Â° Ã�Â²Ã‘ï¿½Ã‘ï¿½Ã�ÂºÃ�Â° Ã�Â¿Ã‘â‚¬Ã�Â¾Ã�Â¼Ã�ÂµÃ�Â½Ã�Â»Ã�Â¸Ã�Â²Ã�Â° \\x_i, Ã�Â·Ã�Â° Ã�ÂºÃ�Â¾Ã‘ï¿½Ã‘â€šÃ�Â¾ Ã�Â½Ã‘ï¿½Ã�Â¼Ã�Â° Ã�Â¾Ã�Â³Ã‘â‚¬Ã�Â°Ã�Â½Ã�Â¸Ã‘â€¡Ã�ÂµÃ�Â½Ã�Â¸Ã�Âµ Ã�Â·Ã�Â° Ã�Â½Ã�ÂµÃ�Â¾Ã‘â€šÃ‘â‚¬Ã�Â¸Ã‘â€ Ã�Â°Ã‘â€šÃ�ÂµÃ�Â»Ã�Â½Ã�Â¾Ã‘ï¿½Ã‘â€š, Ã‘ï¿½ Ã�Â·Ã�Â°Ã�Â¼Ã�ÂµÃ‘ï¿½Ã‘â€šÃ�Â²Ã�Â°Ã�Â¼Ã�Âµ Ã‘ï¿½ 2 Ã�Â¿Ã‘â‚¬Ã�Â¾Ã�Â¼Ã�ÂµÃ�Â½Ã�Â»Ã�Â¸Ã�Â²Ã�Â¸ \\x_i^+ Ã�Â¸ \\x_i^-, Ã�ÂºÃ�Â¾Ã�Â¸Ã‘â€šÃ�Â¾ Ã‘ï¿½Ã�Â° Ã�Â½Ã�ÂµÃ�Â¾Ã‘â€šÃ‘â‚¬Ã�Â¸Ã‘â€ Ã�Â°Ã‘â€šÃ�ÂµÃ�Â»Ã�Â½Ã�Â¸.",
		// Destination.LOG);
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
		// uiController.addContent(this.toString(), Destination.WINDOW);
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

	public Optimum getOptimum() {
		return optimum;
	}

	public void setOptimum(Optimum optimum) {
		this.optimum = optimum;
	}

	public Integer getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(Integer maxIndex) {
		this.maxIndex = maxIndex;
	}

	public void setHasNegativePart(Vector<Boolean> hasNegativePart) {
		this.hasNegativePart = hasNegativePart;
	}

}
