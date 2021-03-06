package org.fmi.or.simplexator.answerqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.fmi.or.simplexator.algorithm.answerer.Answer;
import org.fmi.or.simplexator.service.serializable.PossibleAnswer;

public class AnswerQueue extends SolvingQueue {
	private List<PossibleAnswer> answers;

	public AnswerQueue(Locale locale) {
		super(locale);
		this.answers = new ArrayList<>();
	}

	public void addAnswer(PossibleAnswer problem) {
		PossibleAnswer answer = new PossibleAnswer(problem);
		answers.add(answer);
	}

	public void addAnswer(Answer problemAnswer) {
		if (problemAnswer == null) {
			return;
		}

		PossibleAnswer answer = new PossibleAnswer(problemAnswer);
		answers.add(answer);
	}

	public List<PossibleAnswer> getAnswers() {
		return answers;
	}

}
