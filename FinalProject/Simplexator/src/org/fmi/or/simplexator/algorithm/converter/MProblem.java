package org.fmi.or.simplexator.algorithm.converter;

import java.util.Locale;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.answerer.AnswerSearcher;
import org.fmi.or.simplexator.answerqueue.ProblemConversionQueue;
import org.fmi.or.simplexator.service.serializable.TransformationStep;

public class MProblem extends Problem {
	public MProblem(Problem other) {
		super(other);
		this.isK = true;
		this.isM = false;
	}

	public void convertToMProblem() {
		ProblemConversionQueue problemConversionQueue = new ProblemConversionQueue(
				new Locale("bg", "BG"));
		convertToMProblem(problemConversionQueue);
	}

	public void convertToMProblem(ProblemConversionQueue queue) {
		this.isM = true;
		queue.addMessage("ConvertToM.explainBasis");
		int uniquenessMap[] = getUniquenessMap();
		boolean MVarNeeded[] = new boolean[restrictionsCount];
		for (int i = 0; i < MVarNeeded.length; i++) {
			MVarNeeded[i] = true;
		}

		for (int i = 0; i < uniquenessMap.length; i++) {
			if (uniquenessMap[i] != -1)
				MVarNeeded[uniquenessMap[i]] = false;
		}
		boolean shouldConvertToM = false;
		for (int i = 0; i < MVarNeeded.length; i++) {
			if (MVarNeeded[i] == true) {
				shouldConvertToM = true;
				break;
			}
		}

		queue.addMessage("ConvertToM.introduction");
		if (shouldConvertToM) {
			// should convert
			for (int i = 0; i < MVarNeeded.length; i++) {
				if (MVarNeeded[i]) {
					this.varCount++;
					int newVarIndex = ++(this.maxIndex);
					Variable mVar = new Variable(new Fraction(1), newVarIndex);
					// the m var is with coefficient 1 in the changed equation
					addVarToRestriction(i, mVar);
					// with m cofficient in the zfunction
					zfunction.add(new Variable(Fraction.M, newVarIndex));
					// and zero in the rest(table shape consistency, bitch!)
					Variable zeroMVar = new Variable(Fraction.ZERO,
							this.maxIndex);
					for (int j = 0; j < restrictionsCount; j++) {
						if (restrictions.get(j).getVarCount() < zfunction
								.size()) {
							addVarToRestriction(j, zeroMVar);
						}
					}
					
					queue.addMessage("STEP");
					this.hasNegativePart.clear();
				    for (int varInd = 0; varInd < varCount; varInd++) {
						this.hasNegativePart.add(false);
					}
					queue.addProblemStep(this);
					queue.addMessage("ConvertToM.explainAddingMVars");

				} else {
					// now doby is a free equation
				}
			}
		} else {

			// no convertion
			queue.addMessage("ConvertToM.mProblemWasGiven");
		}
		
		this.hasNegativePart.clear();
	    for (int varInd = 0; varInd < varCount; varInd++) {
			this.hasNegativePart.add(false);
		}
		queue.addProblemStep(this);
		queue.addMessage("ConvertToM.conclusion");
		queue.addMessage("STEP");
	}

	/**
	 * uniquenessMap[i]: -1 => is not unique (or is unique but the coefficient
	 * is <0) n => restriction where is unique (and coefficient >0)
	 * 
	 * */
	public int[] getUniquenessMap() {
		int uniquenessPosition[] = new int[this.varCount];
		for (int i = 0; i < uniquenessPosition.length; i++) {
			uniquenessPosition[i] = -1;
		}

		Variable[][] restrictionsTable = new Variable[getRestrictionsCount()][getVarCount()];
		for (int i = 0; i < getRestrictionsCount(); i++) {
			Restriction currentRestriction = getRestriction(i);
			restrictionsTable[i] = currentRestriction.getVariables();
		}

		for (int varPos = 0; varPos < varCount; varPos++) {
			for (int restrIndex = 0; restrIndex < restrictionsCount; restrIndex++) {
				if (restrictionsTable[restrIndex][varPos].getCoefficient()
						.isHigherThan(Fraction.ZERO)) {
					if (uniquenessPosition[varPos] != -1) {
						uniquenessPosition[varPos] = -1;
						break;
					} else {
						uniquenessPosition[varPos] = restrIndex;
					}
				}
			}
		}
		return uniquenessPosition;
	}

	/**
	 * @returns which restr should contain m
	 * */
	public Vector<Integer> getMResrictions() {
		Vector<Integer> indices = new Vector<>();
		//

		return indices;
	}

}
