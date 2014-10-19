package org.fmi.or.simplexator.service.serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class IterationStep {
	List<String> basis;
	List<String> basisCoefs;
	String table[][];
	List<String> rightSide;
	String[][] costs;
	Integer[] keyElemCoords;
	Integer[] newKeyElemCoords;
	
	public IterationStep(SimplexTable table){
		Vector<Variable> tableBasis = table.getBasis();
	this.basis = new ArrayList<>();
	//	for()
	}
	
	public IterationStep(List<String> basis, List<String> basisCoefs,
			String[][] table, List<String> rightSide, String[][] costs,
			Integer[] keyElemCoords, Integer[] newKeyElemCoords) {
		super();
		this.basis = basis;
		this.basisCoefs = basisCoefs;
		this.table = table;
		this.rightSide = rightSide;
		this.costs = costs;
		this.keyElemCoords = keyElemCoords;
		this.newKeyElemCoords = newKeyElemCoords;
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
	
	public String[][] getTable() {
		return table;
	}
	
	public void setTable(String[][] table) {
		this.table = table;
	}
	
	public List<String> getRightSide() {
		return rightSide;
	}
	
	public void setRightSide(List<String> rightSide) {
		this.rightSide = rightSide;
	}
	
	public String[][] getCosts() {
		return costs;
	}
	
	public void setCosts(String[][] costs) {
		this.costs = costs;
	}
	
	public Integer[] getKeyElemCoords() {
		return keyElemCoords;
	}
	
	public void setKeyElemCoords(Integer[] keyElemCoords) {
		this.keyElemCoords = keyElemCoords;
	}
	
	public Integer[] getNewKeyElemCoords() {
		return newKeyElemCoords;
	}
	
	public void setNewKeyElemCoords(Integer[] newKeyElemCoords) {
		this.newKeyElemCoords = newKeyElemCoords;
	}

}
