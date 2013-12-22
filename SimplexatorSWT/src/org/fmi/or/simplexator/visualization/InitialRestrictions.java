package org.fmi.or.simplexator.visualization;

import org.eclipse.swt.widgets.Shell;

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

public class InitialRestrictions {
	private Shell shell;
	public InitialRestrictions() {

	}
	public void createProblemEnvironment() {

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
				
				ProblemDescription pd = new ProblemDescription(isMinimum, varCount, restrCount);
				pd.createCountsWindow();
				shell.dispose();
			}
		});
		btnNext.setBounds(314, 265, 110, 35);
		btnNext.setText("\u041F\u0440\u043E\u0434\u044A\u043B\u0436\u0438");

		shell.open();
		shell.layout();

	}

}
