package org.fmi.or.simplexator.algorithm.converter;

public class MProblem extends Problem {
	private void addMVariables() {

	}

	public MProblem(Problem problem) {
		varCount = problem.varCount;
		restrictionsCount = problem.restrictionsCount;
		zfunction = problem.zfunction;
		restrictions = problem.restrictions;

	}

}
