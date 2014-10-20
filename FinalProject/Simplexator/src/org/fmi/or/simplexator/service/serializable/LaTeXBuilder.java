package org.fmi.or.simplexator.service.serializable;

import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.MProblem;

public class LaTeXBuilder {

	private StringBuilder latex;

	public LaTeXBuilder() {
		this.latex = new StringBuilder();
	}

	public void beginDocument() {
		// StringBuilder latex = new StringBuilder();
		latex.append("\\documentclass[a4paper]{article}" + "\\begin{document}");
		// return latex.toString();
	}

	public void endDocument() {
		// StringBuilder latex = new StringBuilder();
		latex.append("\\end{document}");
		// return latex.toString();
	}

	// TODO
	public void problemToSring() {
		// return null;
	}

	public void preambleForSimplexTable(MProblem problem) {
		// StringBuilder latex = new StringBuilder();

		int numberOfColumns = problem.getVarCount() + 3;
		latex.append("\\[" + "\\begin{array}{|*{" + numberOfColumns + "}{c|}}");

		latex.append("\\hline &  ");
		for (int j = 0; j < problem.getVarCount(); j++) {
			latex.append("&" + problem.getVarByIndex(j).toString() + " ");
		}

		latex.append("&\\overline{\\mathbf{x}}_B\\\\"
				+ "\\cline{3-"
				+ numberOfColumns
				+ "}"
				+ "\\raisebox{6pt}[0pt][0pt]{$\\mathbf{B}$}&\\raisebox{6pt}[0pt][0pt]{$\\mathbf{c}_B$}");
		for (int i = 0; i < problem.getVarCount(); i++) {
			latex.append("&" + problem.getZfunctionVariables()[i].toString()
					+ " ");
		}
		latex.append("& 0 \\\\");

		// return latex.toString();
	}

	public void appendixForSimplexTable() {
		// StringBuilder latex = new StringBuilder();
		latex.append("\\end{array}" + "\\]");
		// return latex.toString();
	}

	public void iterationToString(SimplexTable simtable,
			Pair<Integer, Integer> key) {
		// StringBuilder latex = new StringBuilder();

		latex.append("\\hline");
		for (int i = 0; i < simtable.getBasisSize(); i++) {
			latex.append(simtable.getBasis().get(i).toString() + "  ");
			latex.append("&"
					+ simtable.getZfunctionCoefficients().get(i).toString());

			for (int j = 0; j < simtable.getVarCount(); j++) {
				if (i == key.getFirst() && j == key.getSecond()) {
					// this is the key element => highlight it
					latex.append("&\\mbox{\\fbox{$"
							+ simtable.getSimplexTableItem(i, j) + "$}}");
				} else {
					latex.append("& " + simtable.getSimplexTableItem(i, j)
							+ " ");
				}
			}

			latex.append("&" + simtable.getRightSideValue(i).toString()
					+ "\\\\");
		}

		latex.append("\\hline" + "\\overline{\\mathbf{c}} &  ");
		for (int j = 0; j < simtable.getVarCount(); j++) {
			latex.append("& ");
			latex.append(simtable.getNumCost(j).toString());
			if (!simtable.getMCost(j).equals(Fraction.M)) {
				latex.append(simtable.getMCost(j).toString() + "M ");
			}
		}
		latex.append("& ");
		latex.append(simtable.getResultNumValue().toString());
		if (!simtable.getResultMValue().equals(Fraction.M)) {
			latex.append(simtable.getResultMValue().toString() + "M ");
		}

		latex.append("\\hline");
		// return latex.toString();
	}

	// TODO
	public void answerToString() {
		// return null;
	}

	public String toString() {
		return latex.toString();
	}

}