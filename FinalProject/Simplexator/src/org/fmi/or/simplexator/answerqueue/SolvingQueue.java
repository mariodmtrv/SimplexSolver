package org.fmi.or.simplexator.answerqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class SolvingQueue {
	protected List<String> messageIdentifiers;
	protected ResourceBundle localizationBundle;

	public SolvingQueue(Locale locale) {
		localizationBundle = ResourceBundle.getBundle(
				"resources/ConversionMessages", locale);

		messageIdentifiers = new ArrayList<>();
	}

	public void addMessage(String messageIdentifier) {
		messageIdentifiers.add(messageIdentifier);
	}

	public List<String> localizeMessages() {
		List<String> localizedMessages = new ArrayList<>();
		for (String message : messageIdentifiers) {
			localizedMessages.add(localizationBundle.getString(message));
		}
		return localizedMessages;
	}
}
