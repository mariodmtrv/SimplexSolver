package org.fmi.or.simplexator.algorithm.computation;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Variable;
import org.fmi.or.simplexator.answerqueue.IterationQueue;

public class ProblemIteration {

	public ProblemIteration(MProblem problem, SimplexTable oldSimplexTable, Pair<Integer,Integer> keyElementCoords) {
		this.problem = problem;
		this.oldSimplexTable = oldSimplexTable;
		this.keyElementCoords = keyElementCoords;
	}

	private MProblem problem;
	private SimplexTable oldSimplexTable;
	private Pair<Integer,Integer> keyElementCoords;

	// set the new basis for the newSimplexTable
	private void changeBasis(SimplexTable newSimplexTable) {
		int indexVarOut = this.keyElementCoords.getFirst();
		int indexVarIn = this.keyElementCoords.getSecond();
		Variable varIn = this.problem.getVarByIndex(indexVarIn);
		
		// UI.printMessage("Променливата \\this.problem.getVarByIndex(indexVarOut) излиза от базиса, а на нейно място влиза \\this.problem.getVarByIndex(indexVarIn).");
		newSimplexTable.setBasisIndecesElementAt(indexVarIn, indexVarOut);
		newSimplexTable.setBasisElementAt(varIn, indexVarOut);
	}
	
	public SimplexTable makeIteration(IterationQueue queue) {
		// new table has same parameters as the other, but it is blank,
		// except for the zfunctionCoefs, basis and basisIndeces
		SimplexTable newSimplexTable = new SimplexTable(this.oldSimplexTable);
		queue.addMessage("drawEmptyTable");
		
		// change basis of newSimplexTable
		changeBasis(newSimplexTable);
		queue.addMessage("Iteration.fillBasisInTable");
		
		// fill keyElem's row
		// UI.printMessage("Редът на ключовия елемент се преписва от старата таблица като преди това се разделя на стойността на ключовия елемент.");
		// UI.highlightElement(keyElementCoords.getFirst(), keyElementCoords.getSecond());
		for (int j = 0; j <= oldSimplexTable.getVarCount(); j++) {
			Fraction value = rectangleRule(keyElementCoords,
					keyElementCoords.getFirst(), j);
			newSimplexTable.setSimplexTableItem(keyElementCoords.getFirst(), j,
					value);
		}

		// fill basis' vars columns
		// UI.printMessage("Колоните на базисните променливи се попълват с нули и една единица на реда, отговарящ на позицията й вляво.");
		Vector<Integer> basisIndeces = oldSimplexTable.getBasisIndeces();
		for (int basisVarIndex : basisIndeces) {
			for (int i = 0; i <= oldSimplexTable.getRestrictionsCount() + 1; i++) {
				Fraction value = rectangleRule(keyElementCoords, i,
						basisVarIndex);
				newSimplexTable.setSimplexTableItem(i, basisVarIndex, value);
			}
			// UI.highlightColumns(simplexTable.getBasisIndecesElement(i));
		}
		
		queue.addMessage("Iteration.fillBasisColumns");
		queue.addMessage("Iteration.fillKeyRow");

		// fill rest
		// UI.printMessage("Останалите клетки попълваме по правилото на правоъгълника.");
		// UI.printMessage(---explain_rectangle_rule---);
		for (int i = 0; i <= oldSimplexTable.getRestrictionsCount() + 1; i++) {
			if (i == keyElementCoords.getFirst()) {
				// we have already filled the keyElem's row
				continue;
			}
			for (int j = 0; j <= oldSimplexTable.getVarCount(); j++) {
				if (isIndexOfBasisVar(j)) {
					// we have already filled the columns of the vars in the
					// basis
					continue;
				}
				Fraction value = rectangleRule(keyElementCoords, i, j);
				newSimplexTable.setSimplexTableItem(i, j, value);
				// UI.highlightTableElement(i,j);
			}
		}
		queue.addMessage("Iteration.fillTable");
		
		return newSimplexTable;
	}

	private boolean isIndexOfBasisVar(int i) {
		Vector<Integer> basisIndeces = oldSimplexTable.getBasisIndeces();
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
		Fraction keyElement = oldSimplexTable.getSimplexTableItem(p, q);

		if (p == i && q == j)
			return new Fraction(1);
		else if (p == i)
			return oldSimplexTable.getSimplexTableItem(i, j).divide(
					oldSimplexTable.getSimplexTableItem(p, q));
		else if (q == j)
			return new Fraction(0);

		return oldSimplexTable.getSimplexTableItem(i, j).subtract(
				oldSimplexTable.getSimplexTableItem(i, q).multiply(
						oldSimplexTable.getSimplexTableItem(p, j).divide(
								keyElement)));
	}

}
