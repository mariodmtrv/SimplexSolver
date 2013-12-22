package org.fmi.or.simplexator.visualization;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.core.internal.runtime.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
	Composite problem;
	Canvas canvas;
	public ProblemConversion(){
		open();
	}
	Frame frame;
	private static LinkedList<Pair<String,Destination>> messages;
	/**
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		shell=new Shell();
	/*	
	//	canvas=new org.eclipse.swt.widgets.Canvas(parent, style));
		Composite composite = new Composite(shell, SWT.EMBEDDED);
composite.setBounds(0, 0, 500, 500);
	    frame = SWT_AWT.new_Frame(composite);
	    frame.setBounds(10, 10, 200, 300);
		canvas=new Canvas();
		//canvas.setBackground(java.awt.Color.RED);
		canvas.setBounds(10, 10, 200, 300);
		/*problem = new Composite(shell, SWT.NONE);
		problem.setBounds(67, 10, 317, 64);
		ScrolledComposite messageLog = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		messageLog.setBounds(67, 145, 317, 85);
		messageLog.setExpandVertical(true);
		frame.add(canvas);*/
		//printToProblem("Hello");
		shell.open();
		shell.layout();
	}
	private void appendToFile(){
		
	}
	public void getNext(){
		
	}
	public void getPrevious(){
		
	}
	
	private void printToProblem(String message) {
		TeXFormula formula = new TeXFormula(message);
		TeXIcon icon = formula
				.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
		BufferedImage image = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		canvas=new Canvas();
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
		icon.paintIcon(canvas, g2, 0, 0);
		
		File outputfile = new File("image.jpg");
	
		
		Graphics g = canvas.getGraphics();
		
		g.drawImage(image,0,0,null);
		try {
			ImageIO.write(image, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void printToConsole(){
		
	}
	
}
