package org.fmi.or.simplexator.service.serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.fmi.or.simplexator.algorithm.computation.ProblemInitialization;
import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.answerqueue.ProblemConversionQueue;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolutionResponse {
	private Problem problem;
	@JsonProperty("problems")
	List<TransformationStep> serializableProblemSteps;
	List<String> messages;
	List<IterationStep> iterations;

	public SolutionResponse(Problem problem) {
		this.problem = problem;
		this.serializableProblemSteps = new ArrayList<>();
		this.messages = new ArrayList<>();
		this.iterations = new ArrayList<>();
	}

	public void solve() {
		// conversion
		ProblemConversionQueue problemConversionQueue = new ProblemConversionQueue(
				new Locale("bg", "BG"));
		problem.convertToK(problemConversionQueue);
		MProblem mProblem = new MProblem(problem);
		mProblem.convertToMProblem(problemConversionQueue);
		for (Problem problem : problemConversionQueue.getProblemSteps()) {
			serializableProblemSteps.add(new TransformationStep(problem));
		}
		ProblemInitialization mProblemInit = new ProblemInitialization(mProblem);
		SimplexTable simtable = mProblemInit.makeFirstIteration();
		messages.addAll(problemConversionQueue.localizeMessages());

	}

}
