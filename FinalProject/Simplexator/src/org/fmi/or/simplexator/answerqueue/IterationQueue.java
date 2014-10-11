package org.fmi.or.simplexator.answerqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.fmi.or.simplexator.algorithm.computation.Pair;
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
	
	// set "newKeyElementCoords" of the last iteration pushed
	public void setNewKeyElementCoordsForLastIteration(Pair<Integer, Integer> coords) {
		IterationStep lastStep = problemSteps.get(problemSteps.size() - 1);
		Integer[] newKeyElemCoords = new Integer[2];
		newKeyElemCoords[0] = coords.getFirst();
		newKeyElemCoords[1] = coords.getSecond();
		lastStep.setNewKeyElemCoords(newKeyElemCoords);
		problemSteps.set(problemSteps.size() - 1, lastStep);
	}
	
}
