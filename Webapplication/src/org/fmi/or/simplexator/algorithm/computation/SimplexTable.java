package org.fmi.or.simplexator.algorithm.computation;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Variable;
import org.fmi.or.simplexator.algorithm.converter.VariableType;

public class SimplexTable {

	private Table table;

	private Vector<Variable> basis;
	private Vector<Integer> basisIndeces;
	private Vector<Fraction> zfunctionCoefficients;

	private Vector<Fraction> numCost;
	private Vector<Fraction> MCost;

	private Vector<Fraction> rightSideValues;
	private Fraction resultNumValue;
	private Fraction resultMValue;

	public SimplexTable(int varCount, int restrictionsCount) {
		table = new Table(varCount, restrictionsCount);
		basis = new Vector<Variable>();
		basisIndeces = new Vector<Integer>();
		zfunctionCoefficients = new Vector<Fraction>();
		numCost = new Vector<Fraction>();
		MCost = new Vector<Fraction>();
		rightSideValues = new Vector<Fraction>();
		for(int i=0;i<restrictionsCount;i++){
			basis.add(new Variable(new Fraction(Fraction.ZERO),0));
			basisIndeces.add(new Integer(0));
			rightSideValues.add(new Fraction(Fraction.ZERO));
		}
		for(int i=0;i<varCount;i++){
			zfunctionCoefficients.add(new Fraction(Fraction.ZERO));
			numCost.add(new Fraction(Fraction.ZERO));
			MCost.add(new Fraction(Fraction.ZERO));
		}
	}
	
	// blank SimplexTable copy constructor
	public SimplexTable(SimplexTable oldTable) {
		int varCount = oldTable.getVarCount();
		int restrictionsCount = oldTable.getRestrictionsCount();
		
		table = new Table(varCount, restrictionsCount);
		basis = new Vector<Variable>();
		basisIndeces = new Vector<Integer>();
		zfunctionCoefficients = new Vector<Fraction>();
		numCost = new Vector<Fraction>();
		MCost = new Vector<Fraction>();
		rightSideValues = new Vector<Fraction>();
		
		// fill the basis, basisIndeces and the zfunctionCoefficients with the copied values,
		// others leave blank
		Vector<Variable> oldBasis = oldTable.getBasis();
		Vector<Integer> oldBasisIndeces = oldTable.getBasisIndeces();
		Vector<Fraction> oldTableZFunctionCoefs = oldTable.getZfunctionCoefficients();
		
		for(int i=0;i<restrictionsCount;i++){
			basis.add(new Variable(oldBasis.get(i).getCoefficient(), oldBasis.get(i).getIndex(), oldBasis.get(i).getType()));
			basisIndeces.add(oldBasisIndeces.get(i));
			rightSideValues.add(new Fraction(Fraction.ZERO));
		}
		for(int i=0;i<varCount;i++){
			zfunctionCoefficients.add(oldTableZFunctionCoefs.get(i));
			numCost.add(new Fraction(Fraction.ZERO));
			MCost.add(new Fraction(Fraction.ZERO));
		}
	}

	public void setTableRow(int row, Fraction[] tableRow) {
		table.setRow(row, tableRow);
	}

	public Fraction getTableElement(int i, int j) {
		return table.getElement(i, j);
	}
	
	public void setTableElement(int i, int j, Fraction setValue) {
		table.setElement(i, j, setValue);
	}

	public Table getTable() {
		return table;
	}

	public int getVarCount() {
		return numCost.size();
	}

	public int getRestrictionsCount() {
		return basis.size();
	}

	public int getBasisSize() {
		return basis.size();
	}

	public Vector<Variable> getBasis() {
		return basis;
	}

	public void setBasis(Vector<Variable> basis) {
		this.basis = basis;
	}

	public Vector<Integer> getBasisIndeces() {
		return basisIndeces;
	}

	public void setBasisIndeces(Vector<Integer> basisIndeces) {
		this.basisIndeces = basisIndeces;
	}

	public Vector<Fraction> getZfunctionCoefficients() {
		return zfunctionCoefficients;
	}

	public void addZfunctionCoefficient(int pos, Fraction coef) {
		zfunctionCoefficients.add(pos, coef);
	}

	public void setZfunctionCoefficients(Vector<Fraction> zfunctionCoefficients) {
		this.zfunctionCoefficients = zfunctionCoefficients;
	}

	public Fraction getMCost(int i) {
		return MCost.get(i);
	}
	
	public Fraction getNumCost(int i) {
		return numCost.get(i);
	}
	
	public void addNumCost(int pos, Fraction sum) {
		numCost.set(pos, sum);
	}

	public void addMCost(int pos, Fraction sum) {
		MCost.set(pos, sum);
	}
	
	public void setRightSideValue(int i, Fraction f) {
		rightSideValues.set(i,f);
	}
	public Fraction getRightSideValue(int i){
		return rightSideValues.get(i);
	}
	public Fraction getResultNumValue() {
		return resultNumValue;
	}

	public void setResultNumValue(Fraction resultNumValue) {
		this.resultNumValue = resultNumValue;
	}

	public Fraction getResultMValue() {
		return resultMValue;
	}

	public void setResultMValue(Fraction resultMValue) {
		this.resultMValue = resultMValue;
	}

	public Fraction getSimplexTableItem(int i, int j) {
		if (j > getVarCount() || i > getRestrictionsCount() + 1) {
			// out of bounds
			return null;
		}

		if (j < getVarCount() && i < getRestrictionsCount()) {
			// get a value from the Table table
			return table.getElement(i, j);
		}

		if (j == getVarCount() && i >= getRestrictionsCount()) {
			// get Z-costs
			if (i == getRestrictionsCount()) {
				return resultNumValue;
			} else {
				return resultMValue;
			}
		}

		if (j == getVarCount()) {
			// get right side vector
			return rightSideValues.get(i);
		}

		if (i >= getRestrictionsCount()) {
			// get costs
			if (i == getRestrictionsCount()) {
				return numCost.get(j);
			} else {
				return MCost.get(j);
			}
		}

		return null;
	}

	public void setSimplexTableItem(int i, int j, Fraction value) {
		if (j < getVarCount() && i < getRestrictionsCount()) {
			// set a value from the Table table
			table.setElement(i, j, value);
		}

		else if (j == getVarCount() && i >= getRestrictionsCount()) {
			// set Z-costs
			if (i == getRestrictionsCount()) {
				resultNumValue = value;
			} else {
				resultMValue = value;
			}
		}

		else if (j == getVarCount()) {
			// set right side vector
			rightSideValues.setElementAt(value, i);
		}

		else if (i >= getRestrictionsCount()) {
			// set costs
			if (i == getRestrictionsCount()) {
				numCost.setElementAt(value, j);
			} else {
				MCost.setElementAt(value, j);
			}
		}
	}

	public void setBasisIndecesElementAt(int basisVarIndex, int i) {
		basisIndeces.setElementAt(basisVarIndex, i);
		
	}
	
	public void setBasisElementAt(Variable basisVarIndex, int i) {
		basis.setElementAt(basisVarIndex, i);
	}
	
	public void setRightSideValuesElementAt(Fraction setValue, int i) {
		rightSideValues.setElementAt(setValue, i);
	}
	
	public void setNumCostElementAt(Fraction setValue, int j) {
		numCost.setElementAt(setValue, j);
	}
	
	public void setMCostElementAt(Fraction setValue, int j) {
		MCost.setElementAt(setValue, j);
	}
}
