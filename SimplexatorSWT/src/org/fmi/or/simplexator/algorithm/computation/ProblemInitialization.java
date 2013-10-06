package org.fmi.or.simplexator.algorithm.computation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class ProblemInitialization {
	private MProblem problem;
	private Table table;

	private Vector<Variable> basis;
	private Vector<Fraction> zfunctionCoefficients;

	private Vector<Fraction> numCost;
	private Vector<Fraction> MCost;

	private boolean isBasisValid(Vector<Variable> initialBasis) {
		if (initialBasis.size() != problem.getRestrictionsCount()) {
			// Ui.throwTooFewVariablesMessage("You need 3 vars you added 1");
			return false;
		}

		Variable[][] restrictionsTable = new Variable[problem
				.getRestrictionsCount()][problem.getVarCount()];
		for (int i = 0; i < problem.getRestrictionsCount(); i++) {
			Restriction currentRestriction = problem.getRestriction(i);
			restrictionsTable[i] = currentRestriction.getVariables();
		}

		Vector<Variable> initialBasisDraft = initialBasis;
		int initialBasisIndex = 0;

		for (int i = 0; i < restrictionsTable.length; i++) {
			for (int j = 0; j < restrictionsTable[i].length; j++) {
				for (int b = 0; b < initialBasisDraft.size(); b++) {
					boolean foundBasisVarInCurrentRow = false;
					// TODO: Question: restrictionsTable[i][j].getCoefficient()
					// > 0 ili ==1 ?????????????
					if (restrictionsTable[i][j].getCoefficient().isHigherThan(
							Fraction.ZERO)
							&& restrictionsTable[i][j].equals(initialBasisDraft
									.get(b))) {
						if (!foundBasisVarInCurrentRow)
							foundBasisVarInCurrentRow = true;
						else
							return false;

						// order basis in proper order
						// (initialbasis[n] is the variable that comes from the
						// n-th restriction)
						initialBasis.set(initialBasisIndex, initialBasisDraft.get(b));
						initialBasisIndex++;

						// now check the column
						int nonzeroes = 0;
						for (int col = 0; col < restrictionsTable.length; col++) {
							if (!restrictionsTable[col][j].getCoefficient()
									.isEqualTo(Fraction.ZERO)) {
								nonzeroes++;
							}
						}
						if (nonzeroes > 1) {
							return false;
							// Ui.throwWrongBasisSet("The vars do not form basis");
						}
						break;
					}
				}
			}
		}
		return true;
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
		List<Variable> basisFromUI = new Vector<>();
		Collections.sort(basisFromUI);
		return (Vector<Variable>) basisFromUI;
	}

	public ProblemInitialization(MProblem problem) {
		this.problem = problem;
		initializeZfunction();
		setInitialBasis();
		table = new Table(problem.getVarCount(), basis.size());

		// solveProblem(); // where to put this function ???????????????
	}

	private void initializeZfunction() {
		zfunctionCoefficients = new Vector<Fraction>(problem.getVarCount());
		Variable[] vars = problem.getZfunctionVariables();
		for (int j = 0; j < problem.getVarCount(); j++) {
			zfunctionCoefficients.add(j, vars[j].getCoefficient());
		}
	}

	private void initializeTable() {
		for (int i = 0; i < problem.getRestrictionsCount(); i++) {
			Fraction[] tableRow = new Fraction[problem.getVarCount() + 1];
			// the vars + vector b (right sides)
			Restriction restriction = problem.getRestriction(i);
			Variable[] vars = restriction.getVariables();
			for (int j = 0; j < problem.getVarCount(); j++) {

				tableRow[j] = vars[j].getCoefficient();
			}
			// after the vars
			tableRow[problem.getVarCount()] = restriction.getRightSide();
			table.setRow(i, tableRow);
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
			for (int i = 0; i < basis.size(); ++i) {

				if (basis.get(i).getCoefficient().isEqualTo(Fraction.M)
						|| table.getElement(i,j).isEqualTo(Fraction.M))
					sumM = sumM.subtract(basis.get(i).getCoefficient()
							.multiply(table.getElement(i,j)));
				else
					sumC = sumC.subtract(basis.get(i).getCoefficient()
							.multiply(table.getElement(i,j)));
			}
			numCost.add(j, sumC);
			MCost.add(j, sumM);
		}

	}
}
