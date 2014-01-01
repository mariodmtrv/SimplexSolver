package org.fmi.or.simplexator.visualization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class UIController {
	LinkedList<UIContent> data;
	ListIterator<UIContent> current;

	public UIController() {
		this.data = new LinkedList<>();
	}

	public void addContent(String message, Destination destination) {
		if (destination == Destination.LOG) {
			data.add(new UIContent("\\mbox{" + message + "}\n", destination));
		} else {
			data.add(new UIContent(message, destination));
		}
		current = data.listIterator();
	}

	/**
	 * @return <code>null</code> if the list contains no next element
	 * */
	public UIContent getNext() {
		if (current.hasNext()) {
			return current.next();
		}
		return null;
	}

	/**
	 * @return <code>null</code> if the list contains no previous element
	 * */
	public UIContent getPrevious() {
		if (current.hasPrevious()) {
			return current.previous();
		}
		return null;
	}

	public LinkedList<UIContent> getContent() {
		return data;
	}
}
