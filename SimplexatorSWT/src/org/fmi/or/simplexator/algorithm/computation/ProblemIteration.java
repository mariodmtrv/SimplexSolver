package org.fmi.or.simplexator.algorithm.computation;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;

public class ProblemIteration {

	public ProblemIteration(SimplexTable simplexTable) {
		this.simplexTable = simplexTable;
	}

	private SimplexTable simplexTable;

	public void makeIteration(Pair<Integer, Integer> keyElementCoords) {
		// refill table
		// UI.fillKeyElementRowAndExplain();
		// UI.explainThatTheColumnsOfBaseElemsAreWithZeroesOnlyAndOne1();
		// UI.showTheRectangleRule();

		// the logic in the code will be different than the one on paper
		// (because of different data structures used, i.e. the simplex table is
		// split in several variables),
		// however this is hidden from the user

		Fraction keyElement = new Fraction(simplexTable.table.getElement(
				keyElementCoords.first, keyElementCoords.second));

		// fill Table table
		for (int i = 0; i < simplexTable.getRestrictionsCount(); i++) {
			for (int j = 0; j < simplexTable.getVarCount(); j++) {
				Fraction setValue = rectangleRule(keyElementCoords, i, j);
				simplexTable.table.setElement(i, j, setValue);
			}
		}

		// fill costs
		for (int j = 0; j < simplexTable.getVarCount(); j++) {
			Fraction setValue = rectangleRuleSpecialCase(keyElementCoords, simplexTable.getRestrictionsCount(), j);
			simplexTable.numCost.setElement(j,setValue);
			
			setValue = rectangleRuleSpecialCase(keyElementCoords, simplexTable.getRestrictionsCount()+1, j);
			simplexTable.MCost.setElement(j,setValue);
		}

		// fill right side vector
		for (int i = 0; i < simplexTable.getRestrictionsCount(); i++) {
			Fraction setValue = rectangleRuleSpecialCase(keyElementCoords, i, simplexTable.getVarCount());
			simplexTable.rightSideValues.setElement(i,setValue);
		}

		// fill Z-value
		Fraction setValue = rectangleRuleSpecialCase(keyElementCoords, simplexTable.getRestrictionsCount(), simplexTable.getVarCount());
		simplexTable.resultNumValue = setValue;
		setValue = rectangleRuleSpecialCase(keyElementCoords, simplexTable.getRestrictionsCount()+1, simplexTable.getVarCount());
		simplexTable.resultMValue = setValue;
	}

	private Fraction rectangleRule(Pair<Integer, Integer> keyElementCoords,
			int i, int j) {
		int p = keyElementCoords.getFirst();
		int q = keyElementCoords.getSecond();
		Fraction keyElement = simplexTable.table.getElement(p, q);

		if (p == i && q == j)
			return new Fraction(1);
		else if (p == i)
			return simplexTable.table.getElement(i, j).divide(
					simplexTable.table.getElement(p, q));
		else if (q == j)
			return new Fraction(0);

		return simplexTable.table.getElement(i, j)
				.subtract(
						simplexTable.table.getElement(i, q).multiply(
								simplexTable.table.getElement(p, j).divide(
										keyElement)));
	}
	
	// helps fill these elements of the simplex table that are separate data structures, not part of the Table-type var
	private Fraction rectangleRuleSpecialCase(Pair<Integer, Integer> keyElementCoords,
			int i, int j) {
		int p = keyElementCoords.getFirst();
		int q = keyElementCoords.getSecond();
		Fraction keyElement = simplexTable.table.getElement(p, q);
		
		if(j==simplexTable.getVarCount() && i>=simplexTable.getRestrictionsCount()) {
			// fill Z-value
			
		}
		else if(j==simplexTable.getVarCount()) {
			// fill right side vector
			
		}
		else if(i>=simplexTable.getRestrictionsCount()) {
			// fill costs
			
		}
		return rectangleRule(keyElementCoords,i,j);
	}
}
