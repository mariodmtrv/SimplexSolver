package org.fmi.or.simplexator.visualization;

import java.util.ArrayList;

/**
 * Defines an object to be visualized in the window
 * */
public class UIContent {
	private Destination destination;
	private String message;

	public UIContent(String message, Destination destination) {
		this.message = message;
		this.destination = destination;
	}

	public Destination getDestination() {
		return destination;
	}

	public String getMessage() {
		return message;
	}

}
