package org.fmi.or.simplexator.algorithm.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.fmi.or.simplexator.answerqueue.ProblemConversionQueue;

public class Problem extends AbstractProblem {

	private Optimum optimum;
	protected Vector<Boolean> hasNegativePart;
	protected Integer maxIndex;
	protected boolean isK;
	protected boolean isM;

	public Problem(Problem other) {
		this(other.zfunction, other.restrictions, other.optimum,
				other.hasNegativePart);
		this.isK = other.isK;
		this.isM = other.isM;
	}

	public Problem(List<Variable> zfunction,
			Vector<Restriction> otherRestrictions, Optimum optimum,
			Vector<Boolean> hasNegativePart) {
		this.optimum = optimum;
		this.restrictionsCount = otherRestrictions.size();
		this.varCount = zfunction.size();
		this.zfunction = new ArrayList<>();
		for (int i = 0; i < zfunction.size(); i++) {
			this.zfunction.add(zfunction.get(i));
		}
		this.restrictions = new Vector<>();
		for (int i = 0; i < otherRestrictions.size(); i++) {
			this.restrictions.add(new Restriction(otherRestrictions.get(i)));
		}

		this.hasNegativePart = new Vector<>();
		for (int i = 0; i < hasNegativePart.size(); i++) {
			this.hasNegativePart.add(hasNegativePart.get(i));
		}
		this.maxIndex = zfunction.get(zfunction.size() - 1).getIndex();
		this.isK = false;
		this.isM = false;
	}

	public void convertToK() {

		ProblemConversionQueue problemConversionQueue = new ProblemConversionQueue(
				new Locale("bg", "BG"));
		convertToK(problemConversionQueue);

	}

	public void convertToK(ProblemConversionQueue queue) {

		queue.addProblemStep(this);
		this.isK = true;
		queue.addMessage("ConvertToK.introduction");
		queue.addMessage("STEP");
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
			queue.addMessage("STEP");
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
		queue.addMessage("STEP");
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
						addVarToRestriction(i, toAdd);
					} else {
						// we are at the restriction already set to equation
					}
				}
				queue.addMessage("STEP");
				this.hasNegativePart = new Vector<>();
				for (int varInd = 0; varInd < this.varCount; varInd++) {
					this.hasNegativePart.add(false);
				}
				queue.addProblemStep(this);
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
		// Boolean processedOne = false;
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
				// processedOne = true;
				/*queue.addMessage("STEP");
				this.hasNegativePart = new Vector<>();
				for (int varInd = 0; varInd < this.varCount; varInd++) {
					this.hasNegativePart.add(false);
				}
				queue.addProblemStep(this);*/
			}

		}
		queue.addMessage("STEP");
		this.hasNegativePart = new Vector<>();
		for (int varInd = 0; varInd < this.varCount; varInd++) {
			this.hasNegativePart.add(false);
		}
		queue.addProblemStep(this);
		
		/*
		 * if (processedOne == false) {
		 * 
		 * queue.addProblemStep(this); }
		 */
	}

	private String getExtremal() {
		if (optimum == Optimum.MINIMUM) {
			return "{\\bf min {\\it z}}=";
		}
		return "{\\bf max {\\it z}}=";
	}

	@Override
	public String toString() {
		return "Problem [optimum=" + optimum + ", hasNegativePart="
				+ hasNegativePart + ", maxIndex=" + maxIndex + ", isK=" + isK
				+ ", isM=" + isM + ", varCount=" + varCount
				+ ", restrictionsCount=" + restrictionsCount + ", zfunction="
				+ zfunction + ", restrictions=" + restrictions + "]";
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

	public boolean isK() {
		return isK;
	}

	public void setK(boolean isK) {
		this.isK = isK;
	}

	public boolean isM() {
		return isM;
	}

	public void setM(boolean isM) {
		this.isM = isM;
	}

}
