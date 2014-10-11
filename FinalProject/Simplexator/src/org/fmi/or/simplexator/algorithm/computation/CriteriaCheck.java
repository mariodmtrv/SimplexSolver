package org.fmi.or.simplexator.algorithm.computation;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.answerqueue.IterationQueue;
import org.fmi.or.simplexator.answerqueue.ProblemConversionQueue;

public class CriteriaCheck {
	private SimplexTable simplexTable;
	private Pair<Integer,Integer> keyElementCoords;

	public CriteriaCheck(SimplexTable simplexTable) {
		this.simplexTable = simplexTable;
	}

	/*
	 * this function is being called when solving the big problem;
	 * @return: Pair = <varToGetOut, varToGetIn> => ProblemIteration continues and knows how to change basis; 
	 * else: <-1,-1> => optimality reached, no more iterations;
	 * 		 <-1, varToGetIn> => unbounded, no more iterations;
	 */
	public Pair<Integer,Integer> checkCriteriaAndFindNewBasis(IterationQueue queue) {
		int toInclude = optimalityCriterion();
		if(toInclude == -1) {
			// optimality reached
			queue.addMessage("CriteriaCheck.optimalityCriterionSuccess");
			queue.addMessage("CriteriaCheck.endLoopMessage");
			return new Pair<Integer, Integer>(-1, -1);
		}
		else {
			queue.addMessage("CriteriaCheck.optimalityCriterionFailed");
			
			if(findNewBasis(toInclude) == false) {
				// min Z = -infinity
				queue.addMessage("CriteriaCheck.unboundednessCriterionSuccess");
				queue.addMessage("CriteriaCheck.endLoopMessage");
				queue.addMessage("Answer.KAndLUnbounded");
				return new Pair<Integer, Integer>(-1, toInclude);
			}
			// no special case, we should continue to the next iteration
			queue.addMessage("CriteriaCheck.unboundednessCriterionFailed");
			queue.addMessage("CriteriaCheck.newIteration");
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
		
		this.keyElementCoords = new Pair<Integer,Integer>(indexOutVar, newBaseVar);
		// Fraction keyElement = simplexTable.getTableElement(indexOutVar, newBaseVar);
		
		return true;
	}
}
