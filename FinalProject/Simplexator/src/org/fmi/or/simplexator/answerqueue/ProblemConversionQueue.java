package org.fmi.or.simplexator.answerqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.fmi.or.simplexator.algorithm.converter.Problem;

public class ProblemConversionQueue extends SolvingQueue {
	private List<Problem> problemSteps;

	public ProblemConversionQueue(Locale locale) {
		super(locale);
		problemSteps = new ArrayList<>();
	}

	public void addProblemStep(Problem problem) {
		Problem converted = new Problem(problem);

		problemSteps.add(converted);
	}

	public List<Problem> getProblemSteps() {
		return problemSteps;
	}

}
