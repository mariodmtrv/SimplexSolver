package org.fmi.or.simplexator.visualization;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.custom.ScrolledComposite;

public class Main {

	protected Shell shell;
	private Text restrictionNumericValue;
    private Problem problem;
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main window = new Main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */

	public void open() {
		Display display = Display.getDefault();

		shell = new Shell();
	/*	Composite simplexTableInstance = new Composite(shell, SWT.NONE);
		simplexTableInstance.setFont(SWTResourceManager.getFont("Segoe UI", 14,
				SWT.NORMAL));
		simplexTableInstance.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		FormData fd_simplexTableInstance = new FormData();
		fd_simplexTableInstance.bottom = new FormAttachment(100, -10);
		fd_simplexTableInstance.left = new FormAttachment(0, 62);
		fd_simplexTableInstance.top = new FormAttachment(0, 26);
		fd_simplexTableInstance.right = new FormAttachment(0, 402);
		simplexTableInstance.setLayoutData(fd_simplexTableInstance);

		visualizeXLabelInstance(simplexTableInstance,0,0,1);
		// TableColumn tc= new TableColumn(table, SWT.NONE);
		shell.open();
		shell.layout();
*/
		/*InitialRestrictions in=new InitialRestrictions();
		in.createProblemEnvironment();*/

		ProblemConversion conversion=new ProblemConversion();
	
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}

	/**
	 * Create contents of the window.
	 * 
	 * @param parent
	 */
	protected void visualizeXLabelInstance(Composite parent, Integer xCoord,
			Integer yCoord, Integer index) {
		Composite xInstance = new Composite(parent, SWT.NONE);
		xInstance.setBounds(39, 41, 40, 35);

		Label xLabel = new Label(xInstance, SWT.NONE);
		xLabel.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD
				| SWT.ITALIC));
		xLabel.setBounds(4, 7, 15, 25);
		xLabel.setText("X");

		Label xIndexLabel = new Label(xInstance, SWT.NONE);
		xIndexLabel.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD
				| SWT.ITALIC));
		xIndexLabel.setBounds(20, 17, 15, 25);
		xIndexLabel.setText(index.toString());

		Label xSignLabel = new Label(xInstance, SWT.NONE);
		xSignLabel.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD
				| SWT.ITALIC));
		xSignLabel.setText("+");
		xSignLabel.setBounds(20, 0, 50, 25);
	}
}