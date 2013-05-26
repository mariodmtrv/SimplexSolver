package com.fmi.simplexator.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import api.fmi.simplexator.algorithm.*;

public class ProblemDefinition {

	protected Shell problemWindow;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ProblemDefinition window = new ProblemDefinition();
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
		createContents();
		problemWindow.open();
		problemWindow.layout();
		while (!problemWindow.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		problemWindow = new Shell();
		problemWindow.setSize(450, 504);
		problemWindow.setText("Simplexator");
		problemWindow.setLayout(new FormLayout());
		Group restrictionsGroup = new Group(problemWindow, SWT.NONE);
		FormData fd_restrictionsGroup = new FormData();
		fd_restrictionsGroup.bottom = new FormAttachment(0, 238);
		fd_restrictionsGroup.right = new FormAttachment(0, 347);
		fd_restrictionsGroup.top = new FormAttachment(0, 36);
		fd_restrictionsGroup.left = new FormAttachment(0, 29);
		restrictionsGroup.setLayoutData(fd_restrictionsGroup);
		restrictionsGroup.setFont(SWTResourceManager.getFont("Segoe UI", 10,
				SWT.NORMAL));
		restrictionsGroup
				.setText("\u041E\u0433\u0440\u0430\u043D\u0438\u0447\u0435\u043D\u0438\u044F \u043D\u0430 \u0437\u0430\u0434\u0430\u0447\u0430\u0442\u0430");
		restrictionsGroup.setLayout(new FormLayout());

		ToolBar extremumType = new ToolBar(restrictionsGroup, SWT.FLAT
				| SWT.RIGHT);
		FormData fd_extremumType = new FormData();
		fd_extremumType.bottom = new FormAttachment(0, 51);
		fd_extremumType.right = new FormAttachment(0, 293);
		fd_extremumType.top = new FormAttachment(0, 16);
		fd_extremumType.left = new FormAttachment(0, 195);
		extremumType.setLayoutData(fd_extremumType);
		extremumType.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.BOLD));

		ToolItem extremumTypeMax = new ToolItem(extremumType, SWT.RADIO);
		extremumTypeMax.setText("MAX");

		ToolItem extremumTypeMin = new ToolItem(extremumType, SWT.RADIO);
		extremumTypeMin.setText("MIN");

		Label variablesCountLabel = new Label(restrictionsGroup, SWT.NONE);
		FormData fd_variablesCountLabel = new FormData();
		fd_variablesCountLabel.bottom = new FormAttachment(0, 91);
		fd_variablesCountLabel.right = new FormAttachment(0, 177);
		fd_variablesCountLabel.top = new FormAttachment(0, 65);
		fd_variablesCountLabel.left = new FormAttachment(0, 16);
		variablesCountLabel.setLayoutData(fd_variablesCountLabel);
		variablesCountLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));
		variablesCountLabel
				.setText("\u0411\u0440\u043E\u0439 \u043F\u0440\u043E\u043C\u0435\u043D\u043B\u0438\u0432\u0438");

		Combo variablesCount = new Combo(restrictionsGroup, SWT.NONE);
		FormData fd_variablesCount = new FormData();
		fd_variablesCount.right = new FormAttachment(0, 286);
		fd_variablesCount.top = new FormAttachment(0, 65);
		fd_variablesCount.left = new FormAttachment(0, 195);
		variablesCount.setLayoutData(fd_variablesCount);
		variablesCount.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));
		variablesCount.setItems(new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10" });
		variablesCount.select(1);

		Label restrictionsCountLabel = new Label(restrictionsGroup, SWT.NONE);
		FormData fd_restrictionsCountLabel = new FormData();
		fd_restrictionsCountLabel.bottom = new FormAttachment(0, 146);
		fd_restrictionsCountLabel.right = new FormAttachment(0, 177);
		fd_restrictionsCountLabel.top = new FormAttachment(0, 120);
		fd_restrictionsCountLabel.left = new FormAttachment(0, 16);
		restrictionsCountLabel.setLayoutData(fd_restrictionsCountLabel);
		restrictionsCountLabel
				.setText("\u0411\u0440\u043E\u0439 \u043E\u0433\u0440\u0430\u043D\u0438\u0447\u0435\u043D\u0438\u044F");
		restrictionsCountLabel.setFont(SWTResourceManager.getFont("Segoe UI",
				12, SWT.NORMAL));

		Combo restrictionsCount = new Combo(restrictionsGroup, SWT.NONE);
		FormData fd_restrictionsCount = new FormData();
		fd_restrictionsCount.right = new FormAttachment(0, 286);
		fd_restrictionsCount.top = new FormAttachment(0, 117);
		fd_restrictionsCount.left = new FormAttachment(0, 195);
		restrictionsCount.setLayoutData(fd_restrictionsCount);
		restrictionsCount.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));
		restrictionsCount.setItems(new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10" });
		restrictionsCount.select(1);

		Label extremumTypeLabel = new Label(restrictionsGroup, SWT.NONE);
		FormData fd_extremumTypeLabel = new FormData();
		fd_extremumTypeLabel.bottom = new FormAttachment(0, 59);
		fd_extremumTypeLabel.right = new FormAttachment(0, 177);
		fd_extremumTypeLabel.top = new FormAttachment(0, 16);
		fd_extremumTypeLabel.left = new FormAttachment(0, 16);
		extremumTypeLabel.setLayoutData(fd_extremumTypeLabel);
		extremumTypeLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.BOLD));
		extremumTypeLabel
				.setText("\u0422\u044A\u0440\u0441\u0435\u043D \u0435\u043A\u0441\u0442\u0440\u0435\u043C\u0443\u043C");

		Composite controls = new Composite(problemWindow, SWT.NONE);
		FormData fd_controls = new FormData();
		fd_controls.top = new FormAttachment(restrictionsGroup, 54);
		fd_controls.right = new FormAttachment(100, -10);
		fd_controls.bottom = new FormAttachment(0, 356);
		fd_controls.left = new FormAttachment(0, 183);
		controls.setLayoutData(fd_controls);
		controls.setLayout(null);

		final Optimum opt = optimumType(extremumTypeMax);
		final int varCount = Integer
				.parseInt((String) variablesCount.getText());
		
		final int restrCount = Integer.parseInt((String) restrictionsCount
				.getText());

		Button nextButton = new Button(controls, SWT.NONE);
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {

				specifyRestrictions(opt, varCount, restrCount);
			}

		});
		nextButton.setBounds(136, 10, 95, 31);
		nextButton.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));
		nextButton.setText("\u0421\u043B\u0435\u0434\u0432\u0430\u0449\u0430");

	}

	protected void restrictionsSpecification(Optimum opt) {
		/*
		 * MessageBox dialog = new MessageBox(problemWindow, SWT.ICON_QUESTION |
		 * SWT.OK| SWT.CANCEL); dialog.setText("My info");
		 * if(opt==Optimum.MAXIMUM) dialog.setMessage("MAX"); int returnCode =
		 * dialog.open();
		 */
	}

	private Optimum optimumType(ToolItem extremumTypeMax) {
		Optimum optimum;
		if (extremumTypeMax.isEnabled()) {
			optimum = Optimum.MAXIMUM;
		} else
			optimum = Optimum.MINIMUM;

		return optimum;
	}

	protected void specifyRestrictions(Optimum optimumType, Integer variablesCount,
			Integer restrictionsCount) {
		
		
	}

}
