package org.fmi.or.simplexator.service.serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.fmi.or.simplexator.algorithm.computation.SimplexMethodSolver;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.answerqueue.AnswerQueue;
import org.fmi.or.simplexator.answerqueue.IterationQueue;
import org.fmi.or.simplexator.answerqueue.ProblemConversionQueue;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolutionResponse {
	private Problem problem;
	@JsonProperty("problems")
	List<TransformationStep> serializableProblemSteps;
	List<String> messageLog;
	List<IterationStep> iterations;
	List<PossibleAnswer> answers;
	String latexResult;

	public SolutionResponse(Problem problem) {
		this.problem = problem;
		this.serializableProblemSteps = new ArrayList<>();
		this.messageLog = new ArrayList<>();
		this.iterations = new ArrayList<>();
	}

	public void solve() {
		// conversion
		SimplexMethodSolver solver = new SimplexMethodSolver();
		Locale locale = new Locale("bg", "BG");
		ProblemConversionQueue pcq = new ProblemConversionQueue(locale);

		IterationQueue iterq = new IterationQueue(locale);
		AnswerQueue ansq = new AnswerQueue(locale);
		LaTeXBuilder builder = new LaTeXBuilder();
		solver.solveProblem(problem, pcq, iterq, ansq, builder);
		// TODO Fix this sublist

		List<Problem> steps = pcq.getProblemSteps().subList(0, 3);
		for (Problem problem : steps) {
			serializableProblemSteps.add(new TransformationStep(problem));
		}
		this.messageLog.addAll(pcq.localizeMessages());
		this.iterations = iterq.getProblemSteps();
		
		this.messageLog.addAll(iterq.localizeMessages());

		this.answers = ansq.getAnswers();
		this.messageLog.addAll(ansq.localizeMessages());
		this.latexResult = builder.toString();
	}

	public List<TransformationStep> getSerializableProblemSteps() {
		return serializableProblemSteps;
	}

	public void setSerializableProblemSteps(
			List<TransformationStep> serializableProblemSteps) {
		this.serializableProblemSteps = serializableProblemSteps;
	}

	public List<String> getMessageLog() {
		return messageLog;
	}

	public void setMessageLog(List<String> messages) {
		this.messageLog = messages;
	}

	public List<IterationStep> getIterations() {
		return iterations;
	}

	public void setIterations(List<IterationStep> iterations) {
		this.iterations = iterations;
	}

	public List<PossibleAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<PossibleAnswer> answers) {
		this.answers = answers;
	}

	public String getLatexResult() {
		return latexResult;
	}

	public void setLatexResult(String latexResult) {
		this.latexResult = latexResult;
	}

}
