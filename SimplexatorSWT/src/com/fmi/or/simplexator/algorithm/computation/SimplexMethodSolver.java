package com.fmi.or.simplexator.algorithm.computation;

import java.util.List;
import java.util.Vector;

import com.fmi.or.simplexator.algorithm.tabularproblemconverter.Fraction;
import com.fmi.or.simplexator.algorithm.tabularproblemconverter.MProblem;
import com.fmi.or.simplexator.algorithm.tabularproblemconverter.Restriction;
import com.fmi.or.simplexator.algorithm.tabularproblemconverter.Variable;

public class SimplexMethodSolver {

	
	private Vector<Vector<Fraction>> oldTable;
	private Vector<Fraction> oldNumCost;
	private Vector<Fraction> oldMCost;

/*	

	private void solveProblem() {
		initializeTable();
		// Ui.printTable(table,zfunc);
		calculateInitialCosts();
		// Ui.printCosts();

		int indexOptimal = optimalityCriterion();
		while (indexOptimal != -1
				&& unboundednessCriterion(indexOptimal) == false) {
			changeBasis(indexOptimal);
			// Ui.animationChangeBasis();
			reFillTable(indexOptimal);
		}

		// processAnswer();
	}

	
	private boolean unboundednessCriterion(int j) {
		for (int i = 0; i < basis.size(); ++i) {
			if (table.get(i).get(j).isEqualOrHigher(Fraction.ZERO))
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
		for (int i = 0; i < problem.getVarCount(); ++i) {
			if (MCost.get(i).isLowerOrEqual(Fraction.ZERO)
					&& (MCost.get(i).isLowerThan(minValueM) || (MCost.get(i)
							.isEqualTo(minValueM) && numCost.get(i)
							.isLowerThan(minValueNum)))) {
				index = i;
				minValueNum = numCost.get(i);
				minValueM = MCost.get(i);
			}
		}
		return index;
	}

	private Fraction rectangleRule(Pair<Integer, Integer> keyElementCoords,
			int i, int j, Vector<Vector<Fraction>> table) {
		int p = keyElementCoords.getFirst();
		int q = keyElementCoords.getSecond();
		Fraction keyElement = table.get(p).get(q);

		if (p == i && q == j)
			return new Fraction(1);
		else if (p == i)
			return table.get(i).get(j).divide(table.get(p).get(q));
		else if (q == j)
			return new Fraction(0);

		return table
				.get(i)
				.get(j)
				.subtract(
						table.get(i)
								.get(q)
								.multiply(
										table.get(p).get(j).divide(keyElement)));
	}
	
	private void changeBasis(int indexOptimal) {
		oldTable = table;
		oldNumCost = numCost;
		oldMCost = MCost;
		
		int newBaseVar = indexOptimal;
		int indexOutVar = -1;
		Fraction minRel = new Fraction(Integer.MAX_VALUE);
		for(int i=0; i<problem.getVarCount(); ++i)
		{
			if(oldTable.get(i).get(newBaseVar).isHigherThan(Fraction.ZERO) && minRel.isHigherThan(oldTable.get(i).get(oldTable.get(i).size()-1).divide(oldTable.get(i).get(newBaseVar))))
			{
				minRel = oldTable.get(i).get(oldTable.get(i).size()-1).divide(oldTable.get(i).get(newBaseVar));
				indexOutVar = i;
			}
		}
		if(indexOutVar == -1) {
			// Ui.noOptSolutions();
			// the end!
		}
		
		Pair<Integer,Integer> keyElementCoords(indexOutVar, newBaseVar);
		Fracton keyElement = oldTable[indexOutVar][newBaseVar];
		// Ui.highlightKeyElement();
		// Ui.drawNewTable();
	}

	private void reFillTable(int indexOptimal) {
		// !!!!!!!!!!!! fill key element's row
		for (int j = 0; j < table[keyElementCoords.first].size(); ++j)
			table[keyElementCoords.first][j] = oldTable[keyElementCoords.first][j]
					/ keyElement;
		// Ui.fillRowOfKeyElement();

		// !!!!!!!!!!!! fill base elements' columns
		for (int i = 0; i < basis.size(); i++) { 
			for (int j = 0; j < table.get(i).size(); i++) {
				// ...
			}
			// numCost[?] = MCost[?] = 0;
		}
		// Ui.fillColumnsOfBaseVariables();

		// !!!!!!!!!!!! fill rest of the table by rectangle rule
		for (int i = 0; i < table.size(); ++i) {
			for (int j = 0; j < table.get(i).size(); ++j) {
				table[i][j] = rectangleRule(keyElementCoords, i, j, oldTable);
				// Ui.showRectangleRule();
			}
		}

		indexOptimal = optimalityCriterion();
	}
	*/
}
