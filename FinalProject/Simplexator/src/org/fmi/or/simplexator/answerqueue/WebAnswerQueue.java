package org.fmi.or.simplexator.answerqueue;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.fmi.or.simplexator.algorithm.converter.Problem;

public class WebAnswerQueue extends AnswerQueue {
	List<String> messages;

	public WebAnswerQueue(Locale locale) {
		super(locale);
		// TODO Auto-generated constructor stub

	}

	public void generateLocalizedMessages() {
		this.messages = super.localizeMessages();
	}

	public List<String> getMessages() {
		return this.messages;
	}

}