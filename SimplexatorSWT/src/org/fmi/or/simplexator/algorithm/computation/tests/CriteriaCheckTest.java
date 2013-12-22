package org.fmi.or.simplexator.algorithm.computation.tests;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.computation.CriteriaCheck;
import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Variable;
import org.fmi.or.simplexator.algorithm.converter.VariableType;
import org.junit.Test;

public class CriteriaCheckTest {
	
	@Test
	public void testGoToNextIteration1() {
		SimplexTable simtable = new SimplexTable(7, 3);
		
		// set basis
		Vector<Variable> basis = new Vector<>();
		basis.add(new Variable(Fraction.M, 6, VariableType.NORMAL));
		basis.add(new Variable(new Fraction(0), 5, VariableType.NORMAL));
		basis.add(new Variable(new Fraction(1), 2, VariableType.NORMAL));
		simtable.setBasis(basis);
		// set basis indexes
		Vector<Integer> basisInd = new Vector<>();
		basisInd.add(6);
		basisInd.add(5);
		basisInd.add(2);
		simtable.setBasisIndeces(basisInd);
		
		// set zfunc-coefs
		Vector<Fraction> zfc = new Vector<>();
		zfc.add(new Fraction(2));
		zfc.add(new Fraction(-2));
		zfc.add(new Fraction(1));
		zfc.add(new Fraction(2));
		zfc.add(new Fraction(0));
		zfc.add(new Fraction(0));
		zfc.add(Fraction.M);
		simtable.setZfunctionCoefficients(zfc);
		
		// set numCosts
		simtable.setNumCostElementAt(new Fraction(-1), 0);
		simtable.setNumCostElementAt(new Fraction(1), 1);
		simtable.setNumCostElementAt(new Fraction(0), 2);
		simtable.setNumCostElementAt(new Fraction(1), 3);
		simtable.setNumCostElementAt(new Fraction(0), 4);
		simtable.setNumCostElementAt(new Fraction(0), 5);
		simtable.setNumCostElementAt(new Fraction(0), 6);
		// set MCosts
		simtable.setMCostElementAt(new Fraction(1), 0);
		simtable.setMCostElementAt(new Fraction(-1), 1);
		simtable.setMCostElementAt(new Fraction(0), 2);
		simtable.setMCostElementAt(new Fraction(-1), 3);
		simtable.setMCostElementAt(new Fraction(1), 4);
		simtable.setMCostElementAt(new Fraction(0), 5);
		simtable.setMCostElementAt(new Fraction(0), 6);
		
		// set RightSideValues
		simtable.setRightSideValue(0, new Fraction(1));
		simtable.setRightSideValue(1, new Fraction(3));
		simtable.setRightSideValue(2, new Fraction(4));
		
		// set result
		simtable.setResultNumValue(new Fraction(-4));
		simtable.setResultMValue(new Fraction(-1));
		
		// set table
		int i=0;
		simtable.setTableElement(i, 0, new Fraction(-1));
		simtable.setTableElement(i, 1, new Fraction(1));
		simtable.setTableElement(i, 2, new Fraction(0));
		simtable.setTableElement(i, 3, new Fraction(1));
		simtable.setTableElement(i, 4, new Fraction(-1));
		simtable.setTableElement(i, 5, new Fraction(0));
		simtable.setTableElement(i, 6, new Fraction(1));
		i=1;
		simtable.setTableElement(i, 0, new Fraction(-1));
		simtable.setTableElement(i, 1, new Fraction(1));
		simtable.setTableElement(i, 2, new Fraction(0));
		simtable.setTableElement(i, 3, new Fraction(-2));
		simtable.setTableElement(i, 4, new Fraction(0));
		simtable.setTableElement(i, 5, new Fraction(1));
		simtable.setTableElement(i, 6, new Fraction(0));
		i=2;
		simtable.setTableElement(i, 0, new Fraction(3));
		simtable.setTableElement(i, 1, new Fraction(-3));
		simtable.setTableElement(i, 2, new Fraction(1));
		simtable.setTableElement(i, 3, new Fraction(1));
		simtable.setTableElement(i, 4, new Fraction(0));
		simtable.setTableElement(i, 5, new Fraction(0));
		simtable.setTableElement(i, 6, new Fraction(0));
		
		
		// now test...
		CriteriaCheck cc = new CriteriaCheck(simtable);
		Pair<Integer,Integer> newBasis=cc.checkCriteriaAndFindNewBasis();
	}
	
	@Test
	public void testGoToNextIteration2() {
		SimplexTable simtable = new SimplexTable(7, 2);
		
		// set basis
		Vector<Variable> basis = new Vector<>();
		basis.add(new Variable(new Fraction(-1), 3, VariableType.NORMAL));
		basis.add(new Variable(Fraction.M, 6, VariableType.NORMAL));
		simtable.setBasis(basis);
		// set basis indexes
		Vector<Integer> basisInd = new Vector<>();
		basisInd.add(3);
		basisInd.add(6);
		simtable.setBasisIndeces(basisInd);
		
		// set zfunc-coefs
		Vector<Fraction> zfc = new Vector<>();
		zfc.add(new Fraction(-1));
		zfc.add(new Fraction(-3));
		zfc.add(new Fraction(3));
		zfc.add(new Fraction(-1));
		zfc.add(new Fraction(2));
		zfc.add(new Fraction(0));
		zfc.add(Fraction.M);
		simtable.setZfunctionCoefficients(zfc);
		
		// set numCosts
		simtable.setNumCostElementAt(new Fraction(-6), 0);
		simtable.setNumCostElementAt(new Fraction(-6), 1);
		simtable.setNumCostElementAt(new Fraction(6), 2);
		simtable.setNumCostElementAt(new Fraction(0), 3);
		simtable.setNumCostElementAt(new Fraction(4), 4);
		simtable.setNumCostElementAt(new Fraction(0), 5);
		simtable.setNumCostElementAt(new Fraction(0), 6);
		// set MCosts
		simtable.setMCostElementAt(new Fraction(2), 0);
		simtable.setMCostElementAt(new Fraction(2), 1);
		simtable.setMCostElementAt(new Fraction(-2), 2);
		simtable.setMCostElementAt(new Fraction(0), 3);
		simtable.setMCostElementAt(new Fraction(0), 4);
		simtable.setMCostElementAt(new Fraction(1), 5);
		simtable.setMCostElementAt(new Fraction(0), 6);
		
		// set RightSideValues
		simtable.setRightSideValue(0, new Fraction(8));
		simtable.setRightSideValue(1, new Fraction(5));
		
		// set result
		simtable.setResultNumValue(new Fraction(8));
		simtable.setResultMValue(new Fraction(-5));
		
		// set table
		int i=0;
		simtable.setTableElement(i, 0, new Fraction(-5));
		simtable.setTableElement(i, 1, new Fraction(-3));
		simtable.setTableElement(i, 2, new Fraction(3));
		simtable.setTableElement(i, 3, new Fraction(1));
		simtable.setTableElement(i, 4, new Fraction(2));
		simtable.setTableElement(i, 5, new Fraction(0));
		simtable.setTableElement(i, 6, new Fraction(0));
		i=1;
		simtable.setTableElement(i, 0, new Fraction(-2));
		simtable.setTableElement(i, 1, new Fraction(-2));
		simtable.setTableElement(i, 2, new Fraction(2));
		simtable.setTableElement(i, 3, new Fraction(0));
		simtable.setTableElement(i, 4, new Fraction(0));
		simtable.setTableElement(i, 5, new Fraction(-1));
		simtable.setTableElement(i, 6, new Fraction(1));
		
		
		// now test...
		CriteriaCheck cc = new CriteriaCheck(simtable);
		cc.checkCriteriaAndFindNewBasis();
	}
	

	
	@Test
	public void testOptimalReached1() {
		SimplexTable simtable = new SimplexTable(7, 3);
		// CHANGED: only non-negative values in Costs
		
		// set basis
		Vector<Variable> basis = new Vector<>();
		basis.add(new Variable(Fraction.M, 6, VariableType.NORMAL));
		basis.add(new Variable(new Fraction(0), 5, VariableType.NORMAL));
		basis.add(new Variable(new Fraction(1), 2, VariableType.NORMAL));
		simtable.setBasis(basis);
		// set basis indexes
		Vector<Integer> basisInd = new Vector<>();
		basisInd.add(6);
		basisInd.add(5);
		basisInd.add(2);
		simtable.setBasisIndeces(basisInd);
		
		// set zfunc-coefs
		Vector<Fraction> zfc = new Vector<>();
		zfc.add(new Fraction(2));
		zfc.add(new Fraction(-2));
		zfc.add(new Fraction(1));
		zfc.add(new Fraction(2));
		zfc.add(new Fraction(0));
		zfc.add(new Fraction(0));
		zfc.add(Fraction.M);
		simtable.setZfunctionCoefficients(zfc);
		
		// set numCosts
		simtable.setNumCostElementAt(new Fraction(-1), 0);
		simtable.setNumCostElementAt(new Fraction(1), 1);
		simtable.setNumCostElementAt(new Fraction(0), 2);
		simtable.setNumCostElementAt(new Fraction(1), 3);
		simtable.setNumCostElementAt(new Fraction(-5), 4); //
		simtable.setNumCostElementAt(new Fraction(2), 5); //
		simtable.setNumCostElementAt(new Fraction(0), 6);
		// set MCosts
		simtable.setMCostElementAt(new Fraction(1), 0);
		simtable.setMCostElementAt(new Fraction(3), 1); //
		simtable.setMCostElementAt(new Fraction(0), 2);
		simtable.setMCostElementAt(new Fraction(2), 3); //
		simtable.setMCostElementAt(new Fraction(1), 4);
		simtable.setMCostElementAt(new Fraction(0), 5);
		simtable.setMCostElementAt(new Fraction(0), 6);
		
		// set RightSideValues
		simtable.setRightSideValue(0, new Fraction(1));
		simtable.setRightSideValue(1, new Fraction(3));
		simtable.setRightSideValue(2, new Fraction(4));
		
		// set result
		simtable.setResultNumValue(new Fraction(-4));
		simtable.setResultMValue(new Fraction(-1));
		
		// set table
		int i=0;
		simtable.setTableElement(i, 0, new Fraction(-1));
		simtable.setTableElement(i, 1, new Fraction(1));
		simtable.setTableElement(i, 2, new Fraction(0));
		simtable.setTableElement(i, 3, new Fraction(1));
		simtable.setTableElement(i, 4, new Fraction(-1));
		simtable.setTableElement(i, 5, new Fraction(0));
		simtable.setTableElement(i, 6, new Fraction(1));
		i=1;
		simtable.setTableElement(i, 0, new Fraction(-1));
		simtable.setTableElement(i, 1, new Fraction(1));
		simtable.setTableElement(i, 2, new Fraction(0));
		simtable.setTableElement(i, 3, new Fraction(-2));
		simtable.setTableElement(i, 4, new Fraction(0));
		simtable.setTableElement(i, 5, new Fraction(1));
		simtable.setTableElement(i, 6, new Fraction(0));
		i=2;
		simtable.setTableElement(i, 0, new Fraction(3));
		simtable.setTableElement(i, 1, new Fraction(-3));
		simtable.setTableElement(i, 2, new Fraction(1));
		simtable.setTableElement(i, 3, new Fraction(1));
		simtable.setTableElement(i, 4, new Fraction(0));
		simtable.setTableElement(i, 5, new Fraction(0));
		simtable.setTableElement(i, 6, new Fraction(0));
		
		
		// now test...
		CriteriaCheck cc = new CriteriaCheck(simtable);
		cc.checkCriteriaAndFindNewBasis();
	}
	
	@Test
	public void testOptimalReached2() {
		SimplexTable simtable = new SimplexTable(7, 2);
		// CHANGED: only non-negative Costs
		
		// set basis
		Vector<Variable> basis = new Vector<>();
		basis.add(new Variable(new Fraction(-1), 3, VariableType.NORMAL));
		basis.add(new Variable(Fraction.M, 6, VariableType.NORMAL));
		simtable.setBasis(basis);
		// set basis indexes
		Vector<Integer> basisInd = new Vector<>();
		basisInd.add(3);
		basisInd.add(6);
		simtable.setBasisIndeces(basisInd);
		
		// set zfunc-coefs
		Vector<Fraction> zfc = new Vector<>();
		zfc.add(new Fraction(-1));
		zfc.add(new Fraction(-3));
		zfc.add(new Fraction(3));
		zfc.add(new Fraction(-1));
		zfc.add(new Fraction(2));
		zfc.add(new Fraction(0));
		zfc.add(Fraction.M);
		simtable.setZfunctionCoefficients(zfc);
		
		// set numCosts
		simtable.setNumCostElementAt(new Fraction(-6), 0);
		simtable.setNumCostElementAt(new Fraction(-6), 1);
		simtable.setNumCostElementAt(new Fraction(6), 2);
		simtable.setNumCostElementAt(new Fraction(0), 3);
		simtable.setNumCostElementAt(new Fraction(4), 4);
		simtable.setNumCostElementAt(new Fraction(0), 5);
		simtable.setNumCostElementAt(new Fraction(0), 6);
		// set MCosts
		simtable.setMCostElementAt(new Fraction(2), 0);
		simtable.setMCostElementAt(new Fraction(2), 1);
		simtable.setMCostElementAt(new Fraction(2), 2); //
		simtable.setMCostElementAt(new Fraction(0), 3);
		simtable.setMCostElementAt(new Fraction(0), 4);
		simtable.setMCostElementAt(new Fraction(1), 5);
		simtable.setMCostElementAt(new Fraction(0), 6);
		
		// set RightSideValues
		simtable.setRightSideValue(0, new Fraction(8));
		simtable.setRightSideValue(1, new Fraction(5));
		
		// set result
		simtable.setResultNumValue(new Fraction(8));
		simtable.setResultMValue(new Fraction(-5));
		
		// set table
		int i=0;
		simtable.setTableElement(i, 0, new Fraction(-5));
		simtable.setTableElement(i, 1, new Fraction(-3));
		simtable.setTableElement(i, 2, new Fraction(3));
		simtable.setTableElement(i, 3, new Fraction(1));
		simtable.setTableElement(i, 4, new Fraction(2));
		simtable.setTableElement(i, 5, new Fraction(0));
		simtable.setTableElement(i, 6, new Fraction(0));
		i=1;
		simtable.setTableElement(i, 0, new Fraction(-2));
		simtable.setTableElement(i, 1, new Fraction(-2));
		simtable.setTableElement(i, 2, new Fraction(2));
		simtable.setTableElement(i, 3, new Fraction(0));
		simtable.setTableElement(i, 4, new Fraction(0));
		simtable.setTableElement(i, 5, new Fraction(-1));
		simtable.setTableElement(i, 6, new Fraction(1));
		
		
		// now test...
		CriteriaCheck cc = new CriteriaCheck(simtable);
		cc.checkCriteriaAndFindNewBasis();
	}

	@Test
	public void testUnbounded1() {
		SimplexTable simtable = new SimplexTable(7, 3);
		// CHANGED: x1- variable has non-positive column
		
		// set basis
		Vector<Variable> basis = new Vector<>();
		basis.add(new Variable(Fraction.M, 6, VariableType.NORMAL));
		basis.add(new Variable(new Fraction(0), 5, VariableType.NORMAL));
		basis.add(new Variable(new Fraction(1), 2, VariableType.NORMAL));
		simtable.setBasis(basis);
		// set basis indexes
		Vector<Integer> basisInd = new Vector<>();
		basisInd.add(6);
		basisInd.add(5);
		basisInd.add(2);
		simtable.setBasisIndeces(basisInd);
		
		// set zfunc-coefs
		Vector<Fraction> zfc = new Vector<>();
		zfc.add(new Fraction(2));
		zfc.add(new Fraction(-2));
		zfc.add(new Fraction(1));
		zfc.add(new Fraction(2));
		zfc.add(new Fraction(0));
		zfc.add(new Fraction(0));
		zfc.add(Fraction.M);
		simtable.setZfunctionCoefficients(zfc);
		
		// set numCosts
		simtable.setNumCostElementAt(new Fraction(-1), 0);
		simtable.setNumCostElementAt(new Fraction(1), 1);
		simtable.setNumCostElementAt(new Fraction(0), 2);
		simtable.setNumCostElementAt(new Fraction(1), 3);
		simtable.setNumCostElementAt(new Fraction(0), 4);
		simtable.setNumCostElementAt(new Fraction(0), 5);
		simtable.setNumCostElementAt(new Fraction(0), 6);
		// set MCosts
		simtable.setMCostElementAt(new Fraction(1), 0);
		simtable.setMCostElementAt(new Fraction(-1), 1);
		simtable.setMCostElementAt(new Fraction(0), 2);
		simtable.setMCostElementAt(new Fraction(-1), 3);
		simtable.setMCostElementAt(new Fraction(1), 4);
		simtable.setMCostElementAt(new Fraction(0), 5);
		simtable.setMCostElementAt(new Fraction(0), 6);
		
		// set RightSideValues
		simtable.setRightSideValue(0, new Fraction(1));
		simtable.setRightSideValue(1, new Fraction(3));
		simtable.setRightSideValue(2, new Fraction(4));
		
		// set result
		simtable.setResultNumValue(new Fraction(-4));
		simtable.setResultMValue(new Fraction(-1));
		
		// set table
		int i=0;
		simtable.setTableElement(i, 0, new Fraction(-1));
		simtable.setTableElement(i, 1, new Fraction(0)); //
		simtable.setTableElement(i, 2, new Fraction(0));
		simtable.setTableElement(i, 3, new Fraction(1));
		simtable.setTableElement(i, 4, new Fraction(-1));
		simtable.setTableElement(i, 5, new Fraction(0));
		simtable.setTableElement(i, 6, new Fraction(1));
		i=1;
		simtable.setTableElement(i, 0, new Fraction(-1));
		simtable.setTableElement(i, 1, new Fraction(-1)); //
		simtable.setTableElement(i, 2, new Fraction(0));
		simtable.setTableElement(i, 3, new Fraction(-2));
		simtable.setTableElement(i, 4, new Fraction(0));
		simtable.setTableElement(i, 5, new Fraction(1));
		simtable.setTableElement(i, 6, new Fraction(0));
		i=2;
		simtable.setTableElement(i, 0, new Fraction(3));
		simtable.setTableElement(i, 1, new Fraction(-3));
		simtable.setTableElement(i, 2, new Fraction(1));
		simtable.setTableElement(i, 3, new Fraction(1));
		simtable.setTableElement(i, 4, new Fraction(0));
		simtable.setTableElement(i, 5, new Fraction(0));
		simtable.setTableElement(i, 6, new Fraction(0));
		
		
		// now test...
		CriteriaCheck cc = new CriteriaCheck(simtable);
		cc.checkCriteriaAndFindNewBasis();
	}
	
	@Test
	public void testUnbounded2() {
		SimplexTable simtable = new SimplexTable(7, 2);
		// CHANGED: x2- variable has non-positive column
		
		// set basis
		Vector<Variable> basis = new Vector<>();
		basis.add(new Variable(new Fraction(-1), 3, VariableType.NORMAL));
		basis.add(new Variable(Fraction.M, 6, VariableType.NORMAL));
		simtable.setBasis(basis);
		// set basis indexes
		Vector<Integer> basisInd = new Vector<>();
		basisInd.add(3);
		basisInd.add(6);
		simtable.setBasisIndeces(basisInd);
		
		// set zfunc-coefs
		Vector<Fraction> zfc = new Vector<>();
		zfc.add(new Fraction(-1));
		zfc.add(new Fraction(-3));
		zfc.add(new Fraction(3));
		zfc.add(new Fraction(-1));
		zfc.add(new Fraction(2));
		zfc.add(new Fraction(0));
		zfc.add(Fraction.M);
		simtable.setZfunctionCoefficients(zfc);
		
		// set numCosts
		simtable.setNumCostElementAt(new Fraction(-6), 0);
		simtable.setNumCostElementAt(new Fraction(-6), 1);
		simtable.setNumCostElementAt(new Fraction(6), 2);
		simtable.setNumCostElementAt(new Fraction(0), 3);
		simtable.setNumCostElementAt(new Fraction(4), 4);
		simtable.setNumCostElementAt(new Fraction(0), 5);
		simtable.setNumCostElementAt(new Fraction(0), 6);
		// set MCosts
		simtable.setMCostElementAt(new Fraction(2), 0);
		simtable.setMCostElementAt(new Fraction(2), 1);
		simtable.setMCostElementAt(new Fraction(-2), 2);
		simtable.setMCostElementAt(new Fraction(0), 3);
		simtable.setMCostElementAt(new Fraction(0), 4);
		simtable.setMCostElementAt(new Fraction(1), 5);
		simtable.setMCostElementAt(new Fraction(0), 6);
		
		// set RightSideValues
		simtable.setRightSideValue(0, new Fraction(8));
		simtable.setRightSideValue(1, new Fraction(5));
		
		// set result
		simtable.setResultNumValue(new Fraction(8));
		simtable.setResultMValue(new Fraction(-5));
		
		// set table
		int i=0;
		simtable.setTableElement(i, 0, new Fraction(-5));
		simtable.setTableElement(i, 1, new Fraction(-3));
		simtable.setTableElement(i, 2, new Fraction(-3)); //
		simtable.setTableElement(i, 3, new Fraction(1));
		simtable.setTableElement(i, 4, new Fraction(2));
		simtable.setTableElement(i, 5, new Fraction(0));
		simtable.setTableElement(i, 6, new Fraction(0));
		i=1;
		simtable.setTableElement(i, 0, new Fraction(-2));
		simtable.setTableElement(i, 1, new Fraction(-2));
		simtable.setTableElement(i, 2, new Fraction(-2)); //
		simtable.setTableElement(i, 3, new Fraction(0));
		simtable.setTableElement(i, 4, new Fraction(0));
		simtable.setTableElement(i, 5, new Fraction(-1));
		simtable.setTableElement(i, 6, new Fraction(1));
		
		
		// now test...
		CriteriaCheck cc = new CriteriaCheck(simtable);
		cc.checkCriteriaAndFindNewBasis();
		
	}
}
