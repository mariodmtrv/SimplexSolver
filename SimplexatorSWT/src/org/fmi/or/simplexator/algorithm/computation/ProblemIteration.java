/*package org.fmi.or.simplexator.algorithm.computation;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class ProblemIteration {

	public ProblemIteration(SimplexTable simplexTable) {
		this.simplexTable = simplexTable;
	}

	private SimplexTable simplexTable;

	public void makeIteration(Pair<Integer, Integer> keyElementCoords) {
		SimplexTable newSimplexTable = new SimplexTable();
		
		// fill keyElem's row
		for(int j=0; j <= simplexTable.getVarCount(); j++) {
			Fraction value = rectangleRule(keyElementCoords, keyElementCoords.getFirst(), j);
			newSimplexTable.setSimplexTableItem(keyElementCoords.getFirst(), j, value);
		}
		// UI.highlightKeyElemRow(keyElementCoords);
		
		// fill basis' vars columns
		for (int basisVarIndex : simplexTable.basisIndeces) {
			for(int i=0; i <= simplexTable.getVarCount()+1; i++) {
				Fraction value = rectangleRule(keyElementCoords, i, basisVarIndex);
				newSimplexTable.setSimplexTableItem(i, basisVarIndex, value);
			}
			// UI.highlightBasisVarColumn(i);
		}
		
		// fill rest
		for(int i=0; i<=simplexTable.getVarCount()+1; i++) {
			if(i == keyElementCoords.getFirst()) {
				// we have already filled the keyElem's row
				continue;
			}
			for(int j=0; j<=simplexTable.getRestrictionsCount(); j++) {
				if(isIndexOfBasisVar(j)) {
					// we have already filled the columns of the vars in the basis
					continue;
				}
				Fraction value = rectangleRule(keyElementCoords, i, j);
				newSimplexTable.setSimplexTableItem(i, j, value);
				// UI.fillTableElement(i,j);
			}
		}
	}
	
	private void fillTable(Pair<Integer, Integer> keyElementCoords) {

	}
	
	private boolean isIndexOfBasisVar(int i) {
		for (int basisVarIndex : simplexTable.basisIndeces) {
			if(i == basisVarIndex)
				return true;
		}
		return false;
	}
	
	private Fraction rectangleRule(Pair<Integer, Integer> keyElementCoords,
			int i, int j) {
		int p = keyElementCoords.getFirst();
		int q = keyElementCoords.getSecond();
		Fraction keyElement = simplexTable.getSimplexTableItem(p, q);

		if (p == i && q == j)
			return new Fraction(1);
		else if (p == i)
			return simplexTable.getSimplexTableItem(i, j).divide(
					simplexTable.getSimplexTableItem(p, q));
		else if (q == j)
			return new Fraction(0);

		return simplexTable.getSimplexTableItem(i, j)
				.subtract(
						simplexTable.getSimplexTableItem(i, q).multiply(
								simplexTable.getSimplexTableItem(p, j).divide(
										keyElement)));
	}
	
	private void visualizeTable() {
		
	}
	
	/*
	public void makeIteration(Pair<Integer, Integer> keyElementCoords) {
		// refill table
		// UI.fillKeyElementRowAndExplain(array,row,content);
		// UI.explainThatTheColumnsOfBaseElemsAreWithZeroesOnlyAndOne1();
		// UI.showTheRectangleRuleMessage();

		// the logic in the code will be different than the one on paper
		// (because of different data structures used, i.e. the simplex table is
		// split in several variables),
		// however this is hidden from the user

		Fraction keyElement = new Fraction(simplexTable.table.getElement(
				keyElementCoords.getFirst(), keyElementCoords.getSecond()));

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
			simplexTable.numCost.setElementAt(setValue,j);
			
			setValue = rectangleRuleSpecialCase(keyElementCoords, simplexTable.getRestrictionsCount()+1, j);
			simplexTable.MCost.setElementAt(setValue,j);
		}

		// fill right side vector
		for (int i = 0; i < simplexTable.getRestrictionsCount(); i++) {
			Fraction setValue = rectangleRuleSpecialCase(keyElementCoords, i, simplexTable.getVarCount());
			simplexTable.rightSideValues.setElementAt(setValue,i);
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
*/