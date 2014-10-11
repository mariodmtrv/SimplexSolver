package org.fmi.or.simplexator.algorithm.converter;

import java.util.Locale;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.answerer.AnswerSearcher;
import org.fmi.or.simplexator.answerqueue.ProblemConversionQueue;

public class MProblem extends Problem {
	public MProblem(Problem other) {
		super(other);
	}

	public void convertToMProblem() {
		ProblemConversionQueue problemConversionQueue = new ProblemConversionQueue(
				new Locale("bg", "BG"));
		convertToMProblem(problemConversionQueue);
	}

	public void convertToMProblem(ProblemConversionQueue queue) {
		//queue.addMessage("mProblemConversion");
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
		if (shouldConvertToM) {
			// should convert
			queue.addMessage("ConvertToM.introduction");
			
			// UI.printMessage("Ã�ï¿½Ã�ÂµÃ�Â¾Ã�Â±Ã‘â€¦Ã�Â¾Ã�Â´Ã�Â¸Ã�Â¼Ã�Â¾ Ã�Âµ Ã�Â´Ã�Â° Ã�Â¿Ã‘â‚¬Ã�ÂµÃ�Â¾Ã�Â±Ã‘â‚¬Ã�Â°Ã�Â·Ã‘Æ’Ã�Â²Ã�Â°Ã�Â¼Ã�Âµ Ã�ÂºÃ‘Å Ã�Â¼ Ã�Å“-Ã�Â·Ã�Â°Ã�Â´Ã�Â°Ã‘â€¡Ã�Â°, Ã�Â·Ã�Â°Ã‘â€°Ã�Â¾Ã‘â€šÃ�Â¾ Ã�Â½Ã‘ï¿½Ã�Â¼Ã�Â°Ã�Â¼Ã�Âµ Ã�Â´Ã�Â¾Ã‘ï¿½Ã‘â€šÃ�Â°Ã‘â€šÃ‘Å Ã‘â€¡Ã�Â½Ã�Â¾ Ã�Â¿Ã‘â‚¬Ã�Â¾Ã�Â¼Ã�ÂµÃ�Â½Ã�Â»Ã�Â¸Ã�Â²Ã�Â¸, Ã�Â·Ã�Â° Ã�Â´Ã�Â° Ã�Â¾Ã�Â±Ã‘â‚¬Ã�Â°Ã�Â·Ã‘Æ’Ã�Â²Ã�Â°Ã�Â¼Ã�Âµ Ã�Â±Ã�Â°Ã�Â·Ã�Â¸Ã‘ï¿½ (Ã�Â½Ã�ÂµÃ�Â¾Ã�Â±Ã‘â€¦Ã�Â¾Ã�Â´Ã�Â¸Ã�Â¼Ã�Â¸ Ã‘ï¿½Ã�Â° Ã‘â€šÃ�Â¾Ã�Â»Ã�ÂºÃ�Â¾Ã�Â²Ã�Â° Ã�Â±Ã�Â°Ã�Â·Ã�Â¸Ã‘ï¿½Ã�Â½Ã�Â¸ Ã�Â¿Ã‘â‚¬Ã�Â¾Ã�Â¼Ã�ÂµÃ�Â½Ã�Â»Ã�Â¸Ã�Â²Ã�Â¸, Ã�ÂºÃ�Â¾Ã�Â»Ã�ÂºÃ�Â¾Ã‘â€šÃ�Â¾ Ã‘ï¿½Ã�Â° Ã�Â¾Ã�Â³Ã‘â‚¬Ã�Â°Ã�Â½Ã�Â¸Ã‘â€¡Ã�ÂµÃ�Â½Ã�Â¸Ã‘ï¿½Ã‘â€šÃ�Â°).");
			// UI.printMessage("Ã�â€™ Ã‘ï¿½Ã‘Å Ã�Â¾Ã‘â€šÃ�Â²Ã�ÂµÃ‘â€šÃ�Â½Ã�Â¸Ã‘â€šÃ�Âµ Ã�Â¾Ã�Â³Ã‘â‚¬Ã�Â°Ã�Â½Ã�Â¸Ã‘â€¡Ã�ÂµÃ�Â½Ã�Â¸Ã‘ï¿½ Ã�Â´Ã�Â¾Ã�Â±Ã�Â°Ã�Â²Ã‘ï¿½Ã�Â¼Ã�Âµ Ã�Â½Ã�Â¾Ã�Â²Ã�Â° Ã�Â¿Ã‘â‚¬Ã�Â¾Ã�Â¼Ã�ÂµÃ�Â½Ã�Â»Ã�Â¸Ã�Â²Ã�Â°, Ã�ÂºÃ�Â¾Ã‘ï¿½Ã‘â€šÃ�Â¾ Ã�Âµ Ã�Â½Ã�ÂµÃ�Â¾Ã‘â€šÃ‘â‚¬Ã�Â¸Ã‘â€ Ã�Â°Ã‘â€šÃ�ÂµÃ�Â»Ã�Â½Ã�Â° Ã�Â¸ Ã�ÂºÃ�Â¾Ã�ÂµÃ‘â€žÃ�Â¸Ã‘â€ Ã�Â¸Ã�ÂµÃ�Â½Ã‘â€šÃ‘Å Ã‘â€š Ã�Â¹ Ã�Â² Z-Ã‘â€žÃ‘Æ’Ã�Â½Ã�ÂºÃ‘â€ Ã�Â¸Ã‘ï¿½Ã‘â€šÃ�Â° Ã�Âµ Ã�Å“. Ã�Â¢Ã‘Æ’Ã�Âº Ã�Å“ Ã�Âµ Ã�Â½Ã‘ï¿½Ã�ÂºÃ�Â°Ã�ÂºÃ�Â²Ã�Â¾ Ã‘â€žÃ�Â¸Ã�ÂºÃ‘ï¿½Ã�Â¸Ã‘â‚¬Ã�Â°Ã�Â½Ã�Â¾ Ã�ÂºÃ‘â‚¬Ã�Â°Ã�Â¹Ã�Â½Ã�Â¾ Ã�Â¼Ã�Â½Ã�Â¾Ã�Â³Ã�Â¾ Ã�Â³Ã�Â¾Ã�Â»Ã‘ï¿½Ã�Â¼Ã�Â¾ Ã‘â€¡Ã�Â¸Ã‘ï¿½Ã�Â»Ã�Â¾.");
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
		queue.addProblemStep(this);
		queue.addMessage("ConvertToM.conclusion");
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
