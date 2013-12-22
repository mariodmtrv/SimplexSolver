package org.fmi.or.simplexator.algorithm.computation;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.computation.Pair;

public class CriteriaCheck {
	private SimplexTable simplexTable;
	private Pair<Integer,Integer> keyElementCoords;

	public CriteriaCheck(SimplexTable simplexTable) {
		this.simplexTable = simplexTable;
	}

	/*
	 * this function is being called when solving the big problem;
	 * @return: Pair = <varToGetOut, varToGetIn> => ProblemIteration continues and knows how to change basis; 
	 * else - null => no more iterations;
	 */
	public Pair<Integer,Integer> checkCriteriaAndFindNewBasis() {
		int toInclude = optimalityCriterion();
		if(toInclude == -1) {
			// optimality reached
			// UI.printMessage("Симплекс методът завършва успешно! Достигнахме до оптимално решение.");
			return null;
		}
		else {
			if(findNewBasis(toInclude) == false) {
				// min Z = -infinity
				// UI.printMessage("М-задачата е неограничена, т.е. min(Z)=-infinity.");
				return null;
			}
			// no special case, we should continue to the next iteration
			// UI.printMessage("Нито един критерий не е изпълнен, затова симплекс методът продължава със следващата итерация.");
			return this.keyElementCoords;
		}
	}
	
	private boolean unboundednessCriterion(int j) {
		// UI.highlightRows(restrCount & restrCount+1);
		// UI.printMessage("Проверяваме дали задачата е неограничена (чрез Критерия за Неограниченост).");
		// // UI.printMessage(---write_something_about_the_Unboundedness_Criterion_???---);
		for (int i = 0; i < simplexTable.getBasisSize(); ++i) {
			if (simplexTable.getTableElement(i,j).isEqualOrHigher(
					Fraction.ZERO))
				return false;
		}
		// UI.printMessage("Критерият за Неограниченост е в сила.");
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
			if (simplexTable.getMCost(i).isLowerOrEqual(Fraction.ZERO)
					&& (simplexTable.getMCost(i).isLowerThan(minValueM) || (simplexTable.getMCost(i).isEqualTo(minValueM) && simplexTable.getNumCost(i).isLowerThan(minValueNum)))) {
				// Blend's rule is OK here
				index = i;
				minValueNum = simplexTable.getNumCost(i);
				minValueM = simplexTable.getMCost(i);
			}
		}
		return index;
	}

	private boolean findNewBasis(int indexOptimal) {
		int newBaseVar = indexOptimal; // index in the list of all vars
		int indexOutVar = -1; // index in the list of basis vars
		Fraction minRel = new Fraction(Integer.MAX_VALUE);
		
		if(unboundednessCriterion(indexOptimal)) {
			return false;
		}
		
		// not unbounded => we can safely choose smallest fraction ( rightSideVector[k] / simplexTable[k][indexOptimal] )
		for(int i=0; i<simplexTable.getBasisSize(); ++i) {
			if(simplexTable.getTableElement(i, newBaseVar).isHigherThan(Fraction.ZERO)
					&& minRel.isHigherThan(simplexTable.getRightSideValue(i).divide(simplexTable.getTableElement(i,newBaseVar)))) {
				// Blend's rule is OK here
				minRel = simplexTable.getRightSideValue(i).divide(simplexTable.getTableElement(i,newBaseVar));
				indexOutVar = i;
			}
		}
		
		if(indexOutVar == -1) {
			return false;
		}
		
		// UI.printMessage(---explain_process_of_var_changing---);
		this.keyElementCoords = new Pair<Integer,Integer>(indexOutVar, newBaseVar);
		// Fraction keyElement = simplexTable.getTableElement(indexOutVar, newBaseVar);
		// UI.highlightElement(indexOutVar, newBaseVar);
		// UI.drawNewTable();
		
		return true;
	}
}
