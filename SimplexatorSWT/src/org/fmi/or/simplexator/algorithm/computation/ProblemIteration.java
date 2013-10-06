package org.fmi.or.simplexator.algorithm.computation;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;

public class ProblemIteration {
	
	public void makeIterations(Table table) {
		/*
		private void refillTable(int indexOptimal) {
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
		}*/
		
		/*
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
	*/
	}
}
