package org.fmi.or.simplexator.service.serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.fmi.or.simplexator.algorithm.answerer.Answer;
import org.fmi.or.simplexator.algorithm.answerer.AnswerConverter;
import org.fmi.or.simplexator.algorithm.computation.CriteriaCheck;
import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.algorithm.computation.ProblemInitialization;
import org.fmi.or.simplexator.algorithm.computation.ProblemIteration;
import org.fmi.or.simplexator.algorithm.computation.SimplexTable;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.answerqueue.AnswerQueue;
import org.fmi.or.simplexator.answerqueue.IterationQueue;
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
		IterationQueue iterationQueue= new IterationQueue(new Locale("BG", "bg"));
		ProblemInitialization mProblemInit = new ProblemInitialization(mProblem);
		SimplexTable simtable = mProblemInit.makeFirstIteration(iterationQueue);
		messages.addAll(problemConversionQueue.localizeMessages());
		CriteriaCheck critCheck = new CriteriaCheck(simtable);
		Pair<Integer, Integer> keyElementCoords = critCheck
				.checkCriteriaAndFindNewBasis(queue);

		ProblemIteration mProblemIter;
		while (keyElementCoords.getFirst() != -1) {
			mProblemIter = new ProblemIteration(mp, simtable, keyElementCoords);

			simtable = mProblemIter.makeIteration(queue);

			critCheck = new CriteriaCheck(simtable);
			keyElementCoords = critCheck.checkCriteriaAndFindNewBasis(queue);
		}

		// handle answers:
		if (keyElementCoords.getFirst() == -1
				&& keyElementCoords.getFirst() == -1) {
			// optimal answer found

			Answer mAnswer = new Answer(simtable);

			AnswerConverter mtok = new AnswerConverter(kp, mAnswer);
			AnswerQueue mq = new AnswerQueue(new Locale("BG", "bg"));
			Answer kAnswer = mtok.convertMToK(mq);

			if (kAnswer == null) {
				// invalid M-answer => problem has no solution

			} else {
				AnswerConverter ktol = new AnswerConverter(p, kAnswer);
				AnswerQueue kq = new AnswerQueue(new Locale("BG", "bg"));
				Answer lAnswer = ktol.convertKToL(kq);
			}
		} else {
			// M is unbounded

		}
	}

}
