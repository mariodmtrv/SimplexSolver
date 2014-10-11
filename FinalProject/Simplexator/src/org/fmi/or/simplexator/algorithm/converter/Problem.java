package org.fmi.or.simplexator.algorithm.converter;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.fmi.or.simplexator.answerqueue.ProblemConversionQueue;

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
		ProblemConversionQueue problemConversionQueue = new ProblemConversionQueue(new Locale("bg", "BG"));
		convertToK(problemConversionQueue);
	}

	public void convertToK(ProblemConversionQueue queue) {
		queue.addProblemStep(this);
		queue.addMessage("ConvertToK.introduction");
		setToMinimum(queue);

		setRightSidesToPositive(queue);

		processNegativeParts(queue);

		setToEquations(queue);
		queue.addMessage("ConvertToK.conclusion");

	}

	private void setToMinimum(ProblemConversionQueue queue) {

		queue.addMessage("ConvertToK.minimumNeeded");
		if (this.optimum != Optimum.MINIMUM) {
			queue.addMessage("ConvertToK.toMinimum");
			this.optimum = Optimum.MINIMUM;
			for (Variable variable : this.zfunction) {
				variable.changeSign();
			}
			queue.addProblemStep(this);
			return;
		} else {
			queue.addMessage("ConvertToK.alreadyMinimum");
		}

	}

	private void setRightSidesToPositive(ProblemConversionQueue queue) {
		queue.addMessage("ConvertToK.rightSidesToPositiveRule");
		for (Restriction restriction : restrictions) {
			restriction.rightSideToPositive();
		}
		queue.addProblemStep(this);
	}

	private void setToEquations(ProblemConversionQueue queue) {
		queue.addMessage("ConvertToK.toEquationsRule");
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
						queue.addProblemStep(this);
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

	private void processNegativeParts(ProblemConversionQueue queue) {

		queue.addMessage("ConvertToK.processNegativeParts");
		int varsPassed = 0;
		Iterator<Variable> zfuncIter = zfunction.iterator();
		Boolean processedOne = false;
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
				processedOne = true;
				queue.addProblemStep(this);
			}

		}
		if (processedOne == false) {
			queue.addMessage("ConvertToK.noNegativesAtRightSide");
			queue.addProblemStep(this);
		}
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
