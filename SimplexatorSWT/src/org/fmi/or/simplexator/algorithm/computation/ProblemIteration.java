package org.fmi.or.simplexator.algorithm.computation;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class ProblemIteration {

	public ProblemIteration(MProblem problem, SimplexTable simplexTable, Pair<Integer,Integer> keyElementCoords) {
		this.problem = problem;
		this.simplexTable = simplexTable;
		this.keyElementCoords = keyElementCoords;
	}

	private MProblem problem; 
	private SimplexTable simplexTable;
	private Pair<Integer,Integer> keyElementCoords;

	// set the new basis for the newSimplexTable
	private void changeBasis() {
		int indexVarOut = this.keyElementCoords.getFirst();
		int indexVarIn = this.keyElementCoords.getSecond();
		Variable varIn = this.problem.getVarByIndex(indexVarIn);
		
		this.simplexTable.setBasisIndecesElementAt(indexVarIn, indexVarOut);
		this.simplexTable.setBasisElementAt(varIn, indexVarOut);
	}
	
	public void makeIteration() {
		// new table has same parameters as the other, but it is blank,
		// except for the zfunctionCoefs, basis and basisIndeces
		SimplexTable newSimplexTable = new SimplexTable(this.simplexTable);
		
		// change basis of newSimplexTable
		changeBasis();
		
		// fill keyElem's row
		for (int j = 0; j <= simplexTable.getVarCount(); j++) {
			Fraction value = rectangleRule(keyElementCoords,
					keyElementCoords.getFirst(), j);
			newSimplexTable.setSimplexTableItem(keyElementCoords.getFirst(), j,
					value);
		}
		// UI.highlightKeyElemRow(keyElementCoords);

		// fill basis' vars columns
		Vector<Integer> basisIndeces = simplexTable.getBasisIndeces();
		for (int basisVarIndex : basisIndeces) {
			for (int i = 0; i <= simplexTable.getVarCount() + 1; i++) {
				Fraction value = rectangleRule(keyElementCoords, i,
						basisVarIndex);
				newSimplexTable.setSimplexTableItem(i, basisVarIndex, value);
			}
			// UI.highlightBasisVarColumn(i);
		}

		// fill rest
		for (int i = 0; i <= simplexTable.getVarCount() + 1; i++) {
			if (i == keyElementCoords.getFirst()) {
				// we have already filled the keyElem's row
				continue;
			}
			for (int j = 0; j <= simplexTable.getRestrictionsCount(); j++) {
				if (isIndexOfBasisVar(j)) {
					// we have already filled the columns of the vars in the
					// basis
					continue;
				}
				Fraction value = rectangleRule(keyElementCoords, i, j);
				newSimplexTable.setSimplexTableItem(i, j, value);
				// UI.fillTableElement(i,j);
			}
		}
	}

	private void fillTable(Pair<Integer, Integer> keyElementCoords) {
		// TODO: remind me why we make that function ?????????????????????????????
	}

	private boolean isIndexOfBasisVar(int i) {
		Vector<Integer> basisIndeces = simplexTable.getBasisIndeces();
		for (int basisVarIndex : basisIndeces) {
			if (i == basisVarIndex)
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

		return simplexTable.getSimplexTableItem(i, j).subtract(
				simplexTable.getSimplexTableItem(i, q).multiply(
						simplexTable.getSimplexTableItem(p, j).divide(
								keyElement)));
	}

	private void visualizeTable() {
		// TODO: remind me why we make that function ?????????????????????????????
		// perhaps it's obsolete because we visualize everything when we compute it in makeIeration()
	}

	// everything down this line is obsolete ?????????????????????????????????????
	/*public void makeIteration(Pair<Integer, Integer> keyElementCoords) {
		// refill table
		// UI.fillKeyElementRowAndExplain(array,row,content);
		// UI.explainThatTheColumnsOfBaseElemsAreWithZeroesOnlyAndOne1();
		// UI.showTheRectangleRuleMessage();

		// the logic in the code will be different than the one on paper
		// (because of different data structures used, i.e. the simplex table is
		// split in several variables),
		// however this is hidden from the user

		Fraction keyElement = new Fraction(simplexTable.getTableElement(
				keyElementCoords.getFirst(), keyElementCoords.getSecond()));

		// fill Table table
		for (int i = 0; i < simplexTable.getRestrictionsCount(); i++) {
			for (int j = 0; j < simplexTable.getVarCount(); j++) {
				Fraction setValue = rectangleRule(keyElementCoords, i, j);
				simplexTable.setTableElement(i, j, setValue);
			}
		}

		// fill costs
		for (int j = 0; j < simplexTable.getVarCount(); j++) {
			Fraction setValue = rectangleRuleSpecialCase(keyElementCoords,
					simplexTable.getRestrictionsCount(), j);
			simplexTable.setNumCostElementAt(setValue,j);

			setValue = rectangleRuleSpecialCase(keyElementCoords,
					simplexTable.getRestrictionsCount() + 1, j);
			simplexTable.setMCostElementAt(setValue,j);
		}

		// fill right side vector
		for (int i = 0; i < simplexTable.getRestrictionsCount(); i++) {
			Fraction setValue = rectangleRuleSpecialCase(keyElementCoords, i,
					simplexTable.getVarCount());
			simplexTable.setRightSideValuesElementAt(setValue, i);
		}

		// fill Z-value
		Fraction setValue = rectangleRuleSpecialCase(keyElementCoords,
				simplexTable.getRestrictionsCount(), simplexTable.getVarCount());
		simplexTable.setResultNumValue(setValue);
		setValue = rectangleRuleSpecialCase(keyElementCoords,
				simplexTable.getRestrictionsCount() + 1,
				simplexTable.getVarCount());
		simplexTable.setResultMValue(setValue);
	}

	private Fraction rectangleRule(Pair<Integer, Integer> keyElementCoords,
			int i, int j) {
		int p = keyElementCoords.getFirst();
		int q = keyElementCoords.getSecond();
		Fraction keyElement = simplexTable.getTableElement(p, q);

		if (p == i && q == j)
			return new Fraction(1);
		else if (p == i)
			return simplexTable.getTableElement(i, j).divide(
					simplexTable.getTableElement(p, q));
		else if (q == j)
			return new Fraction(0);

		return simplexTable.getTableElement(i, j)
				.subtract(
						simplexTable.getTableElement(i, q).multiply(
								simplexTable.getTableElement(p, j).divide(
										keyElement)));
	}

	// helps fill these elements of the simplex table that are separate data
	// structures, not part of the Table-type var
	private Fraction rectangleRuleSpecialCase(
			Pair<Integer, Integer> keyElementCoords, int i, int j) {
		int p = keyElementCoords.getFirst();
		int q = keyElementCoords.getSecond();
		Fraction keyElement = simplexTable.getTableElement(p, q);

		if (j == simplexTable.getVarCount()
				&& i >= simplexTable.getRestrictionsCount()) {
			// fill Z-value

		} else if (j == simplexTable.getVarCount()) {
			// fill right side vector

		} else if (i >= simplexTable.getRestrictionsCount()) {
			// fill costs

		}
		return rectangleRule(keyElementCoords, i, j);
	}*/

}
