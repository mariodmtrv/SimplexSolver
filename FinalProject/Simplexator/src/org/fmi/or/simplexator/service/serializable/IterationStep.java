package org.fmi.or.simplexator.service.serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.computation.Table;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class IterationStep {
	List<String> basis;
	List<String> basisCoefs;
	List<List<String>> table;
	List<String> rightSide;
	List<List<String>> costs;
	List<Integer> keyElemCoords;
	List<Integer> newKeyElemCoords;
	String numValue;
	String MValue;

	public List<Integer> getKeyElemCoords() {
		return keyElemCoords;
	}

	public void setKeyElemCoords(List<Integer> keyElemCoords) {
		this.keyElemCoords = keyElemCoords;
	}

	public void setKeyElemCoords(Pair<Integer, Integer> keyElemCoords) {
		this.keyElemCoords = new ArrayList<>();
		this.keyElemCoords.add(keyElemCoords.getFirst());
		this.keyElemCoords.add(keyElemCoords.getSecond());
	}

	public IterationStep(SimplexTable simplexTable) {
		Vector<Variable> tableBasis = simplexTable.getBasis();
		this.basis = new ArrayList<>();
		for (Variable basisVar : tableBasis) {
			basis.add(basisVar.toMathJaxString());
		}
		Vector<Fraction> tableBasisCoeficients = simplexTable
				.getZfunctionCoefficients();

		this.basisCoefs = new ArrayList<>();
		Vector<Integer> tableBasisIndices = simplexTable.getBasisIndeces();
		for (int i = 0; i < tableBasisIndices.size(); i++) {
			basisCoefs.add(tableBasisCoeficients.get(tableBasisIndices.get(i))
					.toMathJaxString());
		}

		this.table = new ArrayList<>();
		for (int i = 0; i < simplexTable.getRestrictionsCount(); i++) {
			List<String> row = new ArrayList<>();
			for (int j = 0; j < simplexTable.getVarCount(); j++) {
				row.add(simplexTable.getTableElement(i, j).toMathJaxString());
			}
			table.add(row);
		}
		this.costs = new ArrayList<>();
		List<String> numCosts = new ArrayList<>();
		List<String> mCosts = new ArrayList<>();
		for (int i = 0; i < simplexTable.getVarCount(); i++) {
			numCosts.add(simplexTable.getNumCost(i).toMathJaxString());
			mCosts.add(simplexTable.getMCost(i).toMathJaxString());
		}
		this.costs.add(numCosts);
		this.costs.add(mCosts);

		this.rightSide = new ArrayList<>();
		for (int i = 0; i < simplexTable.getRestrictionsCount(); i++) {
			rightSide.add(simplexTable.getRightSideValue(i).toMathJaxString());
		}

		this.keyElemCoords = new ArrayList<>();
		this.newKeyElemCoords = new ArrayList<>();

		this.numValue = simplexTable.getResultNumValue().toMathJaxString();
		this.MValue = simplexTable.getResultMValue().toMathJaxString();
	}

	public String getNumValue() {
		return numValue;
	}

	public void setNumValue(String numValue) {
		this.numValue = numValue;
	}

	public String getMValue() {
		return MValue;
	}

	public void setMValue(String mValue) {
		MValue = mValue;
	}

	public List<String> getBasis() {
		return basis;
	}

	public void setBasis(List<String> basis) {
		this.basis = basis;
	}

	public List<String> getBasisCoefs() {
		return basisCoefs;
	}

	public void setBasisCoefs(List<String> basisCoefs) {
		this.basisCoefs = basisCoefs;
	}

	public List<List<String>> getTable() {
		return table;
	}

	public void setTable(List<List<String>> table) {
		this.table = table;
	}

	public List<String> getRightSide() {
		return rightSide;
	}

	public void setRightSide(List<String> rightSide) {
		this.rightSide = rightSide;
	}

	public List<List<String>> getCosts() {
		return costs;
	}

	public void setCosts(List<List<String>> costs) {
		this.costs = costs;
	}

	public List<Integer> getNewKeyElemCoords() {
		return newKeyElemCoords;
	}

	public void setNewKeyElemCoords(List<Integer> newKeyElemCoords) {
		this.newKeyElemCoords = newKeyElemCoords;
	}

}
