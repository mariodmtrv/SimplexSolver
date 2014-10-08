package org.fmi.or.simplexator.answerqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.fmi.or.simplexator.service.serializable.IterationStep;

public class IterationQueue extends SolvingQueue {
	List<IterationStep> problemSteps;

	public IterationQueue(Locale locale) {
		super(locale);
		this.problemSteps = new ArrayList<>();
	}

	public void addProblem(IterationStep step) {
		problemSteps.add(step);
	}

	public List<IterationStep> getProblemSteps() {
		return problemSteps;
	}

}
