package org.fmi.or.simplexator.algorithm.converter;

import java.util.Vector;

public class MProblem extends Problem {
	public MProblem(Problem other) {
		super(other);
	}

	public void convertToMProblem() {
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
			// UI.printMessage(Converting to MProblem is required)
			for (int i = 0; i < MVarNeeded.length; i++) {
				if (MVarNeeded[i]) {
					int newVarIndex = zfunction.size() + 1;
					Variable mVar = new Variable(new Fraction(1), newVarIndex);
					// the m var is with coefficient 1 in the changed equation
					addVarToRestriction(i, mVar);
					// with m cofficient in the zfunction
					zfunction.add(new Variable(Fraction.M, newVarIndex));
					// and zero in the rest(table shape consistency, bitch!)
					Variable zeroMVar = new Variable(Fraction.ZERO,
							zfunction.size());
					for (int j = 0; j < restrictionsCount; j++) {
						if (restrictions.get(j).getVarCount() < zfunction
								.size()) {
							addVarToRestriction(j, zeroMVar);
						}
					}
				} else {
					// now doby is a free equation
				}
			}
		} else {
			// no convertion
		}
	}

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
				if (!restrictionsTable[restrIndex][varPos].getCoefficient()
						.isEqualTo(Fraction.ZERO)) {
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
	public Vector<Integer> getMRisrictions() {
		Vector<Integer> indices = new Vector<>();
		//

		return indices;
	}

}
