package org.fmi.or.simplexator.answerqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.fmi.or.simplexator.algorithm.converter.Problem;

public abstract class AnswerQueue {
	protected List<Problem> problemSteps;
	private List<String> messageIdentifiers;
	protected ResourceBundle localizationBundle;

	public AnswerQueue(Locale locale) {
		localizationBundle = ResourceBundle.getBundle(
				"resources/ConversionMessages", locale);
		problemSteps = new ArrayList<>();
		messageIdentifiers = new ArrayList<>();
	}

	public void addProblemStep(Problem problem) {
		Problem converted = new Problem(problem);
		problemSteps.add(converted);
	}

	public void addMessage(String messageIdentifier) {
		messageIdentifiers.add(messageIdentifier);
	}

	public List<Problem> getProblemSteps() {
		return problemSteps;
	}

	public List<String> localizeMessages() {
		List<String> localizedMessages = new ArrayList<>();
		for (String message : messageIdentifiers) {
			localizedMessages.add(localizationBundle.getString(message));
		}
		return localizedMessages;
	}

}
