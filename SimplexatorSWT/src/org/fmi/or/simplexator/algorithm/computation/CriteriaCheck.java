package org.fmi.or.simplexator.algorithm.computation;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.computation.Pair;

public class CriteriaCheck {
	/*private SimplexTable simplexTable;

	public CriteriaCheck(SimplexTable simplexTable) {
		this.simplexTable = simplexTable;
	}
*/
	/*
	 * this function is being called when solving the big problem;
	 * @return: simplexTable ready for next iteration,
	 * else - null;
	 */
	/*private SimplexTable check() {
		int toExclude = optimalityCriterion();
		if(toExclude == -1) {
			// optimality reached
			// UI.alertForAnswerReached();
			return null; // ??????????????????????
		}
		else {
			if(changeBasis(toExclude) == false) {
				// min Z = -infinity
				// UI.alertForUnboundedness();
				return null;
			}
			// no special case, we should continue to the next iteration
			return simplexTable; // ??????????????????????
		}
	}
	
	private boolean unboundednessCriterion(int j) {
		for (int i = 0; i < simplexTable.basis.size(); ++i) {
			if (simplexTable.table.getElement(i, j).isEqualOrHigher(
					Fraction.ZERO))
				return false;
		}
		return true;
	}

	private int optimalityCriterion()
	// @return: i = index of variable that will be included in the basis;
	// -1 = criterion is successful - optimality reached, values of all
	// variables are non-negative.
	{
		int index = -1;
		Fraction minValueNum = Fraction.ZERO;
		Fraction minValueM = Fraction.ZERO;
		for (int i = 0; i < simplexTable.getVarCount(); ++i) {
			if (simplexTable.MCost.get(i).isLowerOrEqual(Fraction.ZERO)
					&& (simplexTable.MCost.get(i).isLowerThan(minValueM) || (simplexTable.MCost
							.get(i).isEqualTo(minValueM) && simplexTable.numCost
							.get(i).isLowerThan(minValueNum)))) {
				index = i;
				minValueNum = simplexTable.numCost.get(i);
				minValueM = simplexTable.MCost.get(i);
			}
		}
		return index;
	}

	private boolean changeBasis(int indexOptimal) {
		int newBaseVar = indexOptimal; // index in the list of all vars
		int indexOutVar = -1; // index in the list of basis vars
		Fraction minRel = new Fraction(Integer.MAX_VALUE);
		
		// UI.explainProcessOfVarChanging();
		for(int i=0; i<simplexTable.basis.size(); ++i) {
			if(simplexTable.table.getElement(i,newBaseVar).isHigherThan(Fraction.ZERO)
					&& minRel.isHigherThan(simplexTable.rightSideValues.get(i).divide(simplexTable.table.getElement(i,newBaseVar)))) {
				// TODO: be sure that Blend's (Bill's) rule is OK here
				minRel = simplexTable.rightSideValues.get(i).divide(simplexTable.table.getElement(i,newBaseVar));
				indexOutVar = i;
			}
		}
		
		if(indexOutVar == -1) {
			return false;
		}
		
		// TODO: move somewhere else these 4 lines
		// TODO: figure out how to pass to the next iteration the key element's coords
		Pair<Integer,Integer> keyElementCoords = new Pair<Integer,Integer>(indexOutVar, newBaseVar);
		Fraction keyElement = simplexTable.table.getElement(indexOutVar,newBaseVar);
		// Ui.highlightKeyElement();
		// Ui.drawNewTable();
		
		return true;
	}*/
}
