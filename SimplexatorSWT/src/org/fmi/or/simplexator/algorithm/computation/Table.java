package org.fmi.or.simplexator.algorithm.computation;

import org.fmi.or.simplexator.algorithm.converter.Fraction;

public class Table {
	private Fraction table[][];

	public Table(int varCount, int restrictionsCount) {
		table = new Fraction[varCount][restrictionsCount];
	}

	public Fraction getElement(int i, int j) {
		return table[i][j];
	}

	public void setElement(int i, int j, Fraction value) {
		table[i][j] = value;
	}

	public void setRow(int rowIndex, Fraction[] values) {
		if (values.length > table.length) {
			throw new IllegalStateException("Too many values entered");
		}
		for (int i = 0; i < table.length; i++) {
			table[rowIndex][i] = values[i];
		}
	}
	//public int getV
}
