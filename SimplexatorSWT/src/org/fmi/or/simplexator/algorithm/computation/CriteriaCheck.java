package org.fmi.or.simplexator.algorithm.computation;

import org.fmi.or.simplexator.algorithm.converter.Fraction;

public class CriteriaCheck {
	private SimplexTable simplexTable;
	
	public CriteriaCheck(SimplexTable simplexTable) {
		this.simplexTable = simplexTable;
	}
	
	private boolean unboundednessCriterion(int j) {
		for (int i = 0; i < simplexTable.basis.size(); ++i) {
			if (simplexTable.table.getElement(i,j).isEqualOrHigher(Fraction.ZERO))
				return false;
		}
		return true;
	}

	private int optimalityCriterion()
	// @return: i = index of variable that will be excluded from basis;
	// -1 = criterion is successful - optimality reached, values of all
	// variables are non-negative.
	{
		int index = -1;
		Fraction minValueNum = Fraction.ZERO;
		Fraction minValueM = Fraction.ZERO;
		for (int i = 0; i < simplexTable.getVarCount(); ++i) {
			if (simplexTable.MCost.get(i).isLowerOrEqual(Fraction.ZERO)
					&& (simplexTable.MCost.get(i).isLowerThan(minValueM) || (simplexTable.MCost.get(i)
							.isEqualTo(minValueM) && simplexTable.numCost.get(i)
							.isLowerThan(minValueNum)))) {
				index = i;
				minValueNum = simplexTable.numCost.get(i);
				minValueM = simplexTable.MCost.get(i);
			}
		}
		return index;
	}
	
	private void changeBasis(int indexOptimal) {
		
		//TODO: think again!
		/*int newBaseVar = indexOptimal;
		int indexOutVar = -1;
		Fraction minRel = new Fraction(Integer.MAX_VALUE);
		for(int i=0; i<simplexTable.getVarCount(); ++i)
		{
			if(simplexTable.table.getElement(i,newBaseVar).isHigherThan(Fraction.ZERO) && minRel.isHigherThan(table.get(i).get(table.get(i).size()-1).divide(table.get(i).get(newBaseVar))))
			{
				minRel = simplexTable.table.getElement(i, simplexTable.table.get(i).size()-1).divide(table.get(i).get(newBaseVar));
				indexOutVar = i;
			}
		}
		if(indexOutVar == -1) {
			// Ui.noOptSolutions();
			// the end!
		}
		
		Pair<Integer,Integer> keyElementCoords(indexOutVar, newBaseVar);
		Fracton keyElement = simplexTable.table.getElement(indexOutVar,newBaseVar);
		// Ui.highlightKeyElement();
		// Ui.drawNewTable();
	}
	*/	 
	}
}
