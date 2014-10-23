package org.fmi.or.simplexator.service.serializable.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.service.serializable.InputProblem;
import org.junit.Test;

public class TestConvertProblem {
	private InputProblem createProblem() {
		InputProblem problem = new InputProblem();
		problem.setIsPositive(new ArrayList<String>(Arrays.asList("true",
				"false")));
		problem.setType("MAX");
		problem.setNumRestrictions("2");
		problem.setNumVars("2");
		List<List<String>> restrictions = new ArrayList<>();
		restrictions.add(new ArrayList<>(Arrays.asList("3", "5", "EQ", "5")));
		restrictions.add(new ArrayList<>(Arrays.asList("3", "5", "EQ", "5")));

		problem.setRestrictions(restrictions);
		List<String> zfunc = new ArrayList<>(Arrays.asList("3", "2", "4"));

		problem.setZfunc(zfunc);
		return problem;
	}

	@Test
	public void testConversion() {
		InputProblem problem = createProblem();

		Problem x = problem.getProblem();
		System.out.println(x.getVarCount());
		System.out.println(x.getRestrictionsCount());
		System.out.println(x.getHasNegativePart());
		System.out.println(x.getZfunctionVariables());
		x.convertToK();
	}

}
