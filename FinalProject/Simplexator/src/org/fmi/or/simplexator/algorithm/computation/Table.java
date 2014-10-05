package org.fmi.or.simplexator.algorithm.computation;

import org.fmi.or.simplexator.algorithm.converter.Fraction;

public class Table {
	private Fraction table[][];

	public Table(int varCount, int restrictionsCount) {
		table = new Fraction[restrictionsCount][varCount];
		for(int i=0;i<restrictionsCount;i++){
			for (int j = 0; j < varCount; j++) {
				table[i][j]=new Fraction(Fraction.ZERO);
			}
		}
	}

	public Fraction getElement(int i, int j) {
		return table[i][j];
	}

	public void setElement(int i, int j, Fraction value) {
		table[i][j] = value;
	}

	public void setRow(int rowIndex, Fraction[] values) {
		if (values.length > table[rowIndex].length) {
			throw new IllegalStateException("Too many values entered");
		}
		for (int i = 0; i < table[rowIndex].length; i++) {
			table[rowIndex][i] = values[i];
		}
	}
	//public int getV
}
