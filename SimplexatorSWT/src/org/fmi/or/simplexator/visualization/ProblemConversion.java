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
import java.util.LinkedList;
import java.util.UUID;

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
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class ProblemConversion {
	protected Shell shell;
	private Display display;
	private Label problem;

	public ProblemConversion() {
		display = Display.getCurrent();
		shell = new Shell(display);

		problem = new Label(shell, SWT.BORDER);
		problem.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		problem.setBounds(50, 50, shell.getSize().x/2, shell.getSize().y/2);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(83, 249, 385, 152);
		open();
	}

	private static LinkedList<Pair<String, Destination>> messages;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void open() {
	//printToProblem("a_1^+ Hello bihile (!shell.isDisposed()) \\hline  {if (!display.readAndDispatch())display.sleep();");
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private void appendToFile() {

	}

	public void getNext() {

	}

	public void getPrevious() {

	}

	private void printToConsole() {

	}

	private void printToProblem(String latexText) {
		TeXFormula formula = new TeXFormula(latexText);
		UUID id=UUID.randomUUID();
		String imageName=id+".png";
		formula.createPNG(TeXConstants.STYLE_DISPLAY, 20, imageName,
				Color.white, Color.black);

		Image image = new Image(display, imageName);
		problem.setImage(image);
		File f = new File(imageName);
		f.delete();
	}
}
