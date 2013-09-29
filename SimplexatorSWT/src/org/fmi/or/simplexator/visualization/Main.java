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

public class Main {

	protected Shell shell;
	private Text coefficient;
	private Text coefficient1;
	private Text restriction1Coefficient;
	private Text restrictionCoefficient;
	private Text restrictionNumericValue;

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
	protected void createProblemEnvironment() {

		shell = new Shell();
		shell.setSize(550, 400);
		shell.setText("Ограничени�?");

		Group restrictionsGroup = new Group(shell, SWT.NONE);
		restrictionsGroup.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		restrictionsGroup
				.setText("\u041E\u0433\u0440\u0430\u043D\u0438\u0447\u0435\u043D\u0438\u044F");
		restrictionsGroup.setBounds(40, 40, 340, 180);

		Label restrictionsCountLabel = new Label(restrictionsGroup, SWT.NONE);
		restrictionsCountLabel.setFont(SWTResourceManager.getFont("Segoe UI",
				13, SWT.NORMAL));
		restrictionsCountLabel.setBounds(42, 141, 163, 28);
		restrictionsCountLabel
				.setText("\u0411\u0440\u043E\u0439 \u043E\u0433\u0440\u0430\u043D\u0438\u0447\u0435\u043D\u0438\u044F");

		final Combo restrictionsCountCombo = new Combo(restrictionsGroup,
				SWT.NONE);
		restrictionsCountCombo.setFont(SWTResourceManager.getFont("Segoe UI",
				13, SWT.NORMAL));
		restrictionsCountCombo.setBounds(229, 138, 91, 23);
		restrictionsCountCombo.setItems(new String[] { "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "10" });
		restrictionsCountCombo.select(1);

		Label variablesCountLabel = new Label(restrictionsGroup, SWT.NONE);
		variablesCountLabel.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		variablesCountLabel.setBounds(42, 95, 163, 28);
		variablesCountLabel
				.setText("\u0411\u0440\u043E\u0439 \u043F\u0440\u043E\u043C\u0435\u043D\u043B\u0438\u0432\u0438");

		final Combo variablesCountCombo = new Combo(restrictionsGroup, SWT.NONE);
		variablesCountCombo.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		variablesCountCombo.setBounds(229, 92, 91, 23);
		variablesCountCombo.setItems(new String[] { "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "10" });
		variablesCountCombo.select(1);

		ToolBar extremumType = new ToolBar(restrictionsGroup, SWT.FLAT
				| SWT.RIGHT);
		extremumType.setFont(SWTResourceManager.getFont("Segoe UI", 14,
				SWT.NORMAL));
		extremumType.setBounds(229, 41, 101, 38);
		ToolItem extremumTypeMax = new ToolItem(extremumType, SWT.RADIO);
		extremumTypeMax.setText("MAX");

		final ToolItem extremumTypeMin = new ToolItem(extremumType, SWT.RADIO);
		extremumTypeMin.setText("MIN");

		Label extremumTypeLabel = new Label(restrictionsGroup, SWT.NONE);
		extremumTypeLabel.setFont(SWTResourceManager.getFont("Segoe UI", 14,
				SWT.NORMAL));
		extremumTypeLabel.setBounds(38, 48, 167, 30);
		extremumTypeLabel
				.setText("\u0422\u044A\u0440\u0441\u0435\u043D \u0435\u043A\u0441\u0442\u0440\u0435\u043C\u0443\u043C");

		Button btnNext = new Button(shell, SWT.NONE);
		btnNext.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Boolean isMinimum = extremumTypeMin.getSelection();
				Integer varCount = Integer.parseInt(variablesCountCombo
						.getText());
				Integer restrCount = Integer.parseInt(restrictionsCountCombo
						.getText());
				createCountsWindow(isMinimum, varCount, restrCount);
			}
		});
		btnNext.setBounds(314, 265, 110, 35);
		btnNext.setText("\u041F\u0440\u043E\u0434\u044A\u043B\u0436\u0438");

		shell.open();
		shell.layout();
	}

	public void open() {
		Display display = Display.getDefault();

		shell = new Shell();
		shell.setLayout(new FormLayout());

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
		 createProblemEnvironment();
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

	protected void visualizeRestriction(int variablesCount, int restrictionIndex) {
		Composite restrictionComposite = new Composite(shell, SWT.NONE);
		restrictionComposite.setBounds(80, 90 + 65 * (restrictionIndex - 1),
				230 + 110 * (variablesCount - 1), 65);

		Composite restrictionX1Composite = new Composite(restrictionComposite,
				SWT.NONE);
		restrictionX1Composite.setBounds(10, 15, 109, 45);

		restriction1Coefficient = new Text(restrictionX1Composite, SWT.BORDER);
		restriction1Coefficient.setFont(SWTResourceManager.getFont("Segoe UI",
				13, SWT.NORMAL));
		restriction1Coefficient.setBounds(20, 5, 50, 30);

		Label restrictionX1 = new Label(restrictionX1Composite, SWT.NONE);
		restrictionX1.setText("X");
		restrictionX1.setFont(SWTResourceManager.getFont("Segoe UI", 14,
				SWT.BOLD));
		restrictionX1.setBounds(75, 5, 15, 25);

		Label restrictionX1Index = new Label(restrictionX1Composite, SWT.NONE);
		restrictionX1Index.setText("1");
		restrictionX1Index.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		restrictionX1Index.setBounds(90, 20, 27, 34);

		for (Integer i = 2; i <= variablesCount; i++) {
			Composite restrictionXComposite = new Composite(
					restrictionComposite, SWT.NONE);
			restrictionXComposite.setBounds(120 + 105 * (i - 2), 15, 105, 45);

			restrictionCoefficient = new Text(restrictionXComposite, SWT.BORDER);
			restrictionCoefficient.setFont(SWTResourceManager.getFont(
					"Segoe UI", 13, SWT.NORMAL));
			restrictionCoefficient.setBounds(20, 5, 50, 30);

			Label restrictionX = new Label(restrictionXComposite, SWT.NONE);
			restrictionX.setText("X");
			restrictionX.setFont(SWTResourceManager.getFont("Segoe UI", 14,
					SWT.BOLD));
			restrictionX.setBounds(75, 5, 15, 25);

			Label restrictionXIndex = new Label(restrictionXComposite, SWT.NONE);
			restrictionXIndex.setText(i.toString());
			restrictionXIndex.setFont(SWTResourceManager.getFont("Segoe UI",
					13, SWT.NORMAL));
			restrictionXIndex.setBounds(90, 20, 30, 30);
			Label plusRestr = new Label(restrictionXComposite, SWT.NONE);
			plusRestr.setFont(SWTResourceManager.getFont("Segoe UI", 14,
					SWT.BOLD));
			plusRestr.setBounds(0, 3, 20, 30);
			plusRestr.setText("+");

		}
		Composite restrictionRightSide = new Composite(restrictionComposite,
				SWT.NONE);
		restrictionRightSide.setBounds(105 + 110 * (variablesCount - 1), 15,
				155, 45);

		Combo restrictionSign = new Combo(restrictionRightSide, SWT.NONE);
		restrictionSign.setFont(SWTResourceManager.getFont("Segoe UI", 14,
				SWT.NORMAL));
		restrictionSign.setBounds(10, 5, 50, 25);

		restrictionNumericValue = new Text(restrictionRightSide, SWT.BORDER);
		restrictionNumericValue.setFont(SWTResourceManager.getFont("Segoe UI",
				13, SWT.NORMAL));
		restrictionNumericValue.setBounds(65, 5, 50, 32);
	}

	protected void createCountsWindow(boolean isMinimum, int variablesCount,
			int restrictionsCount) {
		shell.dispose();
		shell = new Shell();
		shell.setSize(370 + variablesCount * 110, 140 + restrictionsCount * 90);
		shell.setText("Ограничени�?");
		visualizeFunction(isMinimum, variablesCount);
		for (Integer i = 1; i <= restrictionsCount; i++) {
			visualizeRestriction(variablesCount, i);
		}
		Button btnToTable = new Button(shell, SWT.NONE);
		btnToTable.setFont(SWTResourceManager.getFont("Segoe UI", 14,
				SWT.NORMAL));
		btnToTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {

			}
		});
		btnToTable.setBounds(200 + variablesCount * 110,
				50 + restrictionsCount * 90, 120, 35);
		btnToTable.setText("Продължи");

		shell.open();
		shell.layout();
	}

	protected void visualizeFunction(boolean isMinimum, int variablesCount) {
		String extremumText;
		if (isMinimum) {
			extremumText = "min z = ";
		} else {
			extremumText = "max z = ";
		}

		Label lblExtremum = new Label(shell, SWT.NONE);
		lblExtremum.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD
				| SWT.ITALIC));
		lblExtremum.setBounds(20, 35, 75, 30);
		lblExtremum.setText(extremumText);

		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(87, 30, 109, 45);

		coefficient1 = new Text(composite_1, SWT.BORDER);
		coefficient1.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		coefficient1.setBounds(20, 5, 50, 30);

		Label label = new Label(composite_1, SWT.NONE);
		label.setText("X");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		label.setBounds(75, 5, 15, 25);

		Label label_1 = new Label(composite_1, SWT.NONE);
		label_1.setText("1");
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		label_1.setBounds(90, 20, 27, 34);

		for (Integer i = 2; i <= variablesCount; i++) {

			Composite composite = new Composite(shell, SWT.NONE);
			composite.setBounds(200 + 110 * (i - 2), 30, 109, 45);

			coefficient = new Text(composite, SWT.BORDER);
			coefficient.setFont(SWTResourceManager.getFont("Segoe UI", 13,
					SWT.NORMAL));
			coefficient.setBounds(20, 5, 50, 30);

			Label lblVarLabel = new Label(composite, SWT.NONE);
			lblVarLabel.setBounds(75, 5, 15, 25);
			lblVarLabel.setFont(SWTResourceManager.getFont("Segoe UI", 14,
					SWT.BOLD));
			lblVarLabel.setText("X");

			Label lblIndexLabel = new Label(composite, SWT.NONE);
			lblIndexLabel.setText(i.toString());
			lblIndexLabel.setBounds(90, 20, 27, 34);
			lblIndexLabel.setFont(SWTResourceManager.getFont("Segoe UI", 13,
					SWT.NORMAL));

			Label plus = new Label(composite, SWT.NONE);
			plus.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
			plus.setBounds(0, 3, 20, 30);
			plus.setText("+");
		}

	}
}