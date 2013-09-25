package com.fmi.or.simplexator.algorithm.computation;

import java.util.List;
import java.util.Vector;

import javax.management.MXBean;

import com.fmi.or.simplexator.algorithm.tabularproblemconverter.Fraction;
import com.fmi.or.simplexator.algorithm.tabularproblemconverter.MProblem;
import com.fmi.or.simplexator.algorithm.tabularproblemconverter.Restriction;
import com.fmi.or.simplexator.algorithm.tabularproblemconverter.Variable;

public class SimplexMethodSolver {
	private MProblem problem;
	private Vector<Vector<Fraction>> table;
	private Vector<Variable> basis;
	private Vector<Fraction> zfunctionCoefficients;

	private Vector<Fraction> numCost;
	private Vector<Fraction> MCost;

	private boolean isBasisValid(Vector<Variable> initialBasis) {
		// TODO checks size and if the vars are eligible

		// Ui.throwTooFewVariablesMessage("You need 3 vars you added 1");
		// Ui.throwWrongBasisSet("The vars do not form basis");
		return false;
	}

	private void setInitialBasis() {
		basis = getInitialBasis();
		while ((!isBasisValid(basis))) {
			basis = getInitialBasis();
		}
	}

	private Vector<Variable> getInitialBasis() {
		// TODO Gets the initial basis from the UI
		// Ui.getInitialVariables();
		return null;

	}

	public SimplexMethodSolver(MProblem problem) {
		this.problem = problem;
		initializeZfunction();
		setInitialBasis();
		table = new Vector<Vector<Fraction>>(basis.size());

		solveProblem();
	}

	private void initializeZfunction() {
		zfunctionCoefficients = new Vector<Fraction>(problem.getVarCount());
		Variable[] vars = problem.getZfunctionVariables();
		for (int j = 0; j < problem.getVarCount(); j++) {
			zfunctionCoefficients.add(j, vars[j].getCoefficient());
		}
	}

	private void solveProblem() {
		initializeTable();
		// Ui.printTable(table,zfunc);
		calculateInitialCosts();
		// Ui.printCosts();

	}

	private void initializeTable() {
		for (int i = 0; i < problem.getRestrictionsCount(); i++) {
			Vector<Fraction> tableRow = new Vector<>();
			Restriction restriction = problem.getRestriction(i);
			Variable[] vars = restriction.getVariables();
			for (int j = 0; j < problem.getVarCount(); j++) {

				tableRow.add(j, vars[j].getCoefficient());
			}
			tableRow.add(restriction.getRightSide());
			table.add(i, tableRow);
		}
	}

	private void calculateInitialCosts() {
		for (int j = 0; j < problem.getVarCount(); ++j) {
			Fraction sumC = Fraction.ZERO;
			Fraction sumM = Fraction.ZERO;
			// Ui.highlight(Zcoef[j]);
			if (zfunctionCoefficients.get(j).isEqualTo(Fraction.M))
				sumM = sumM.add(zfunctionCoefficients.get(j));
			else
				sumC = sumC.add(zfunctionCoefficients.get(j));
			// Ui.highlight(basisCoefs);
			// Ui.highlight(table[j]);
			// Ui.consolelog(zcoef[j] - skalarnoto proziv na
			// basisCoefs*table[j]);
			for (int i = 0; i < table.size(); ++i) {// TODO!!! make sure the
													// basis is in right order
													// listed (place perhaps in
													// "initializeTable")
													// sortirame po dadenite
													// promenlivi i namirame koq
													// v koe ograni4enie se
													// namira, i populvme
													// tablicata
													// sprqmo indexa na
													// promenlivata
				if (basis.get(i).getCoefficient().isEqualTo(Fraction.M)
						|| table.get(i).get(j).isEqualTo(Fraction.M))
					sumM = sumM.subtract(basis.get(i).getCoefficient()
							.multiply(table.get(i).get(j)));
				else
					sumC = sumC.subtract(basis.get(i).getCoefficient()
							.multiply(table.get(i).get(j)));
			}
			numCost.add(j, sumC);
			MCost.add(j, sumM);
		}

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
}
