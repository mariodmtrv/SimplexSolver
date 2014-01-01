package org.fmi.or.simplexator.visualization;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.core.internal.runtime.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.fmi.or.simplexator.algorithm.computation.Pair;
import org.fmi.or.simplexator.algorithm.converter.EquationSign;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Optimum;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ProblemConversion {
	protected Shell shell;
	private Display display;
	private Label problem;
	private Label log;
	private UIController uiController;

	/**
	 * @wbp.parser.entryPoint
	 */
	public ProblemConversion() {
		display = Display.getCurrent();
		shell = new Shell(display);
		shell.setSize(732, 558);

		problem = new Label(shell, SWT.BORDER | SWT.CENTER);
		problem.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		problem.setBounds(50, 50, 512, 208);

		Button nextButton = new Button(shell, SWT.NONE);
		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getNext();
			}
		});
		nextButton.setBounds(487, 465, 75, 25);
		nextButton.setText("\u0421\u043B\u0435\u0434\u0432\u0430\u0449\u0430");

		Button previousButton = new Button(shell, SWT.NONE);
		previousButton
				.setText("\u041F\u0440\u0435\u0434\u0438\u0448\u043D\u0430");
		previousButton.setBounds(412, 465, 75, 25);

		Button printFile = new Button(shell, SWT.NONE);
		printFile.setBounds(602, 22, 75, 25);
		printFile.setText("New Button");

		log = new Label(shell, SWT.BORDER | SWT.CENTER);
		log.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		log.setBounds(50, 287, 512, 132);
		previousButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getPrevious();
			}
		});
		open();
	}

	private static LinkedList<Pair<String, Destination>> messages;

	private Problem test() {
		List<Variable> zfunction = new LinkedList<Variable>();
		zfunction.add(new Variable(new Fraction(2, 7), 1));
		zfunction.add(new Variable(new Fraction(1, 4), 2));
		zfunction.add(new Variable(new Fraction(2), 3));

		Vector<Restriction> restrictions = new Vector<Restriction>();
		List<Variable> firstRestr = new LinkedList<Variable>();
		firstRestr.add(new Variable(new Fraction(1, 7), 1));
		firstRestr.add(new Variable(new Fraction(0), 2));
		firstRestr.add(new Variable(new Fraction(-1), 3));
		Restriction first = new Restriction(firstRestr, EquationSign.GTE,
				new Fraction(-1));

		List<Variable> secondRestr = new LinkedList<Variable>();
		secondRestr.add(new Variable(new Fraction(-1), 1));
		secondRestr.add(new Variable(new Fraction(0), 2));
		secondRestr.add(new Variable(new Fraction(-2), 3));
		Restriction second = new Restriction(secondRestr, EquationSign.LTE,
				new Fraction(-3, 5));

		List<Variable> thirdRestr = new LinkedList<Variable>();
		thirdRestr.add(new Variable(new Fraction(3), 1));
		thirdRestr.add(new Variable(new Fraction(1), 2));
		thirdRestr.add(new Variable(new Fraction(1), 3));
		Restriction third = new Restriction(thirdRestr, EquationSign.EQ,
				new Fraction(4));
		restrictions.add(first);
		restrictions.add(second);
		restrictions.add(third);

		Optimum optimum = Optimum.MINIMUM;
		Vector<Boolean> hasNegativePart = new Vector<>();
		hasNegativePart.add(true);
		hasNegativePart.add(false);
		hasNegativePart.add(false);
		Problem problem = new Problem(zfunction, restrictions, optimum,
				hasNegativePart);
		return problem;
	}

	public void open() {
		Problem p = test();
		p.convertToK();
		this.uiController = p.getUIControler();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private void writeToFile() {
		LinkedList<UIContent> content = uiController.getContent();
		ListIterator<UIContent> iterator = content.listIterator();
		while (iterator.hasNext()) {
		}
	}

	public void getNext() {
		printContent(uiController.getNext());
	}

	public void getPrevious() {
		printContent(uiController.getPrevious());
	}

	private void printContent(UIContent content) {
		if (content != null) {
			if (content.getDestination() == Destination.LOG) {
				printToDestination(content.getMessage(), log);
			} else if (content.getDestination() == Destination.WINDOW) {
				printToDestination(content.getMessage(), problem);
			}
		}
	}

	private void printToDestination(String latexText, Label destination) {
		TeXFormula formula = new TeXFormula(latexText);
		UUID id = UUID.randomUUID();
		String imageName = id + ".png";
		formula.createPNG(TeXConstants.STYLE_DISPLAY, 20, imageName,
				Color.white, Color.black);

		Image image = new Image(display, imageName);
		destination.setImage(image);
		File deleter = new File(imageName);
		deleter.delete();
	}
}
