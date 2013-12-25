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
	private SimplexTable simplexTable;

	private boolean isBasisValid(Vector<Variable> initialBasis) {
		if (initialBasis.size() != problem.getRestrictionsCount()) {
			// Ui.throwError("РќРµРѕР±С…РѕРґРёРјРё СЃР° problem.getRestrictionsCount()РїСЂРѕРјРµРЅР»РёРІРё, РІРёРµ РґРѕР±Р°РІРёС…С‚Рµ initialBasis.size()");
			return false;
		}

		Variable[][] restrictionsTable = new Variable[problem
				.getRestrictionsCount()][problem.getVarCount()];
		for (int i = 0; i < problem.getRestrictionsCount(); i++) {
			Restriction currentRestriction = problem.getRestriction(i);
			restrictionsTable[i] = currentRestriction.getVariables();
		}

		for(int i=0;i<initialBasis.size(); i++) {
			Variable toModify = initialBasis.get(i);
			int index = problem.getVarIndex(toModify);
			Variable newCoef = new Variable(problem.getZfunctionVariables()[index].getCoefficient(), toModify.getIndex(),toModify.getType());
			initialBasis.set(i, newCoef);
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
						Variable swap = initialBasisDraft.get(initialBasisIndex);
						initialBasis.set(initialBasisIndex,
								initialBasisDraft.get(b));
						initialBasis.set(b, swap);
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
							// Ui.throwError("Р�Р·Р±СЂР°РЅРёС‚Рµ РїСЂРѕРјРµРЅР»РёРІРё РЅРµ РѕР±СЂР°Р·СѓРІР°С‚ Р±Р°Р·РёСЃ");
						}
						break;
					}
				}
			}
		}
		return true;
	}

	private void setInitialBasis() {
		/* user inputs until the basis is correct */
		simplexTable.setBasis(getInitialBasis());
		while ((!isBasisValid(simplexTable.getBasis()))) {
			simplexTable.setBasis(getInitialBasis());
		}
		// we now have a valid basis; let's find the corresponding indeces
		for (int i = 0; i < simplexTable.getBasisSize(); i++) {
			int basisVarIndex = problem.getVarIndex(simplexTable.getBasis()
					.get(i));
			
			simplexTable.setBasisIndecesElementAt(basisVarIndex, i);
		}
	}

	private static Vector<Variable> setBasisFromUI() {
		Vector<Variable> vars = new Vector<>();
		//basis for problem with minimum
		/*vars.add(new Variable(new Fraction(1), 2));
		vars.add(new Variable(new Fraction(1), 5));
		vars.add(new Variable(new Fraction(1), 6));
		*/
		//basis for problem with maximum
		vars.add(new Variable(new Fraction(1), 3));
		vars.add(new Variable(new Fraction(1), 6));
		return vars;

	}

	private Vector<Variable> getInitialBasis() {
		// TODO Gets the initial basis from the UI
		// Ui.getInitialVariables();
		/*
		 * This is hardcoded to simulate basis from UI extraction
		 */
		List<Variable> basisFromUI = setBasisFromUI();

		Collections.sort(basisFromUI);
		return (Vector<Variable>) basisFromUI;
	}

	public ProblemInitialization(MProblem problem) {
		this.problem = problem;
		simplexTable = new SimplexTable(problem.getVarCount(),
				problem.getRestrictionsCount());
		initializeZfunction();
		setInitialBasis();
	}

	public SimplexTable makeFirstIteration() {
		initializeTable();
		// Ui.printTable(table,zfunc);
		calculateInitialCosts();
		// Ui.printCosts();

		return simplexTable;
	}

	private void initializeZfunction() {
		simplexTable.setZfunctionCoefficients(new Vector<Fraction>(problem
				.getVarCount()));
		Variable[] vars = problem.getZfunctionVariables();
		for (int j = 0; j < problem.getVarCount(); j++) {
			simplexTable.addZfunctionCoefficient(j, vars[j].getCoefficient());
		}
	}

	private void initializeTable() {
		for (int i = 0; i < problem.getRestrictionsCount(); i++) {
			Fraction[] tableRow = new Fraction[problem.getVarCount()];
			// the vars + vector b (right sides)
			Restriction restriction = problem.getRestriction(i);
			Variable[] vars = restriction.getVariables();
			for (int j = 0; j < problem.getVarCount(); j++) {

				tableRow[j] = vars[j].getCoefficient();
			}
			// after the vars
			simplexTable.setRightSideValue(i, restriction.getRightSide());
			simplexTable.setTableRow(i, tableRow);

			// TODO: fill in the formula
			// resultNumValue = ;
			// resultMValue = ;
		}
	}
private void calculateResultCosts(){
	Fraction numCost = new Fraction(Fraction.ZERO);
	Fraction MCost = new Fraction(Fraction.ZERO);
	for(int i=0;i<simplexTable.getBasisSize(); i++) {
		if (simplexTable.getBasis().get(i).getCoefficient()
				.isEqualTo(Fraction.M))
		{
			MCost=MCost.subtract(simplexTable.getRightSideValue(i));
		}
		else
		{
			numCost = numCost.subtract(simplexTable.getBasis().get(i)
					.getCoefficient()
					.multiply(simplexTable.getRightSideValue(i)));
		}
	}
	this.simplexTable.setResultMValue(MCost);
	this.simplexTable.setResultNumValue(numCost);
}
	private void calculateInitialCosts() {
		for (int j = 0; j < problem.getVarCount(); ++j) {
			Fraction sumC = Fraction.ZERO;
			Fraction sumM = Fraction.ZERO;
			// Ui.highlight(Zcoef[j]);
			if (simplexTable.getZfunctionCoefficients().get(j)
					.isEqualTo(Fraction.M))
				sumM = sumM.add(new Fraction(1));
			else
				sumC = sumC.add(simplexTable.getZfunctionCoefficients().get(j));
			// Ui.highlight(basisCoefs);
			// Ui.highlight(table[j]);
			// Ui.consolelog(zcoef[j] - skalarnoto proziv na
			// basisCoefs*table[j]);
			for (int i = 0; i < simplexTable.getBasisSize(); ++i) {

				if (simplexTable.getBasis().get(i).getCoefficient()
						.isEqualTo(Fraction.M)
						|| simplexTable.getTableElement(i, j).isEqualTo(
								Fraction.M))
					sumM = sumM.subtract(simplexTable.getTableElement(i, j));
				else
					sumC = sumC.subtract(simplexTable.getBasis().get(i)
							.getCoefficient()
							.multiply(simplexTable.getTableElement(i, j)));
			}
			simplexTable.addNumCost(j, sumC);
			simplexTable.addMCost(j, sumM);
			
			calculateResultCosts();
		}

	}
	
}
