package com.fmi.or.simplexator.algorithm.tabularproblemconverter;

public class MProblem extends AbstractProblem {
	private void addMVariables() {

	}

	public MProblem(Problem problem) {
		varCount = problem.varCount;
		restrictionsCount = problem.restrictionsCount;
		zfunction = problem.zfunction;
		restrictions = problem.restrictions;

	}
	
}
