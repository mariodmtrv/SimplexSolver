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
		queue.addMessage("mProblemConversion");
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

			// UI.printMessage("Ð�ÐµÐ¾Ð±Ñ…Ð¾Ð´Ð¸Ð¼Ð¾ Ðµ Ð´Ð° Ð¿Ñ€ÐµÐ¾Ð±Ñ€Ð°Ð·ÑƒÐ²Ð°Ð¼Ðµ ÐºÑŠÐ¼ Ðœ-Ð·Ð°Ð´Ð°Ñ‡Ð°, Ð·Ð°Ñ‰Ð¾Ñ‚Ð¾ Ð½Ñ�Ð¼Ð°Ð¼Ðµ Ð´Ð¾Ñ�Ñ‚Ð°Ñ‚ÑŠÑ‡Ð½Ð¾ Ð¿Ñ€Ð¾Ð¼ÐµÐ½Ð»Ð¸Ð²Ð¸, Ð·Ð° Ð´Ð° Ð¾Ð±Ñ€Ð°Ð·ÑƒÐ²Ð°Ð¼Ðµ Ð±Ð°Ð·Ð¸Ñ� (Ð½ÐµÐ¾Ð±Ñ…Ð¾Ð´Ð¸Ð¼Ð¸ Ñ�Ð° Ñ‚Ð¾Ð»ÐºÐ¾Ð²Ð° Ð±Ð°Ð·Ð¸Ñ�Ð½Ð¸ Ð¿Ñ€Ð¾Ð¼ÐµÐ½Ð»Ð¸Ð²Ð¸, ÐºÐ¾Ð»ÐºÐ¾Ñ‚Ð¾ Ñ�Ð° Ð¾Ð³Ñ€Ð°Ð½Ð¸Ñ‡ÐµÐ½Ð¸Ñ�Ñ‚Ð°).");
			// UI.printMessage("Ð’ Ñ�ÑŠÐ¾Ñ‚Ð²ÐµÑ‚Ð½Ð¸Ñ‚Ðµ Ð¾Ð³Ñ€Ð°Ð½Ð¸Ñ‡ÐµÐ½Ð¸Ñ� Ð´Ð¾Ð±Ð°Ð²Ñ�Ð¼Ðµ Ð½Ð¾Ð²Ð° Ð¿Ñ€Ð¾Ð¼ÐµÐ½Ð»Ð¸Ð²Ð°, ÐºÐ¾Ñ�Ñ‚Ð¾ Ðµ Ð½ÐµÐ¾Ñ‚Ñ€Ð¸Ñ†Ð°Ñ‚ÐµÐ»Ð½Ð° Ð¸ ÐºÐ¾ÐµÑ„Ð¸Ñ†Ð¸ÐµÐ½Ñ‚ÑŠÑ‚ Ð¹ Ð² Z-Ñ„ÑƒÐ½ÐºÑ†Ð¸Ñ�Ñ‚Ð° Ðµ Ðœ. Ð¢ÑƒÐº Ðœ Ðµ Ð½Ñ�ÐºÐ°ÐºÐ²Ð¾ Ñ„Ð¸ÐºÑ�Ð¸Ñ€Ð°Ð½Ð¾ ÐºÑ€Ð°Ð¹Ð½Ð¾ Ð¼Ð½Ð¾Ð³Ð¾ Ð³Ð¾Ð»Ñ�Ð¼Ð¾ Ñ‡Ð¸Ñ�Ð»Ð¾.");
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
					queue.addMessage("mProblemStepConversion");

				} else {
					// now doby is a free equation
				}
			}
		} else {

			// no convertion
			queue.addMessage("mProblemWasGiven");
		}
		queue.addProblemStep(this);
		queue.addMessage("mProblemReady");
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
