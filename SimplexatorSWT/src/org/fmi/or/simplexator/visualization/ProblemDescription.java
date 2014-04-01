package org.fmi.or.simplexator.visualization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.fmi.or.simplexator.algorithm.converter.EquationSign;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Optimum;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class ProblemDescription {
	protected Shell shell;
	private ArrayList<Text> zFuncCoefs;
	private ArrayList<Text> rightSideValues;
	private ArrayList<Combo> equationSigns;
	private ArrayList<ArrayList<Text>> restrictionCoefficients;
	private ArrayList<Button> hasNoNegativePart;
	private Text restrictionNumericValue;
	private Integer restrictionsCount;
	private Integer variablesCount;
	private Boolean isMinimum;

	public ProblemDescription(boolean isMinimum, int variablesCount,
			int restrictionsCount) {
		zFuncCoefs = new ArrayList<>();
		this.restrictionsCount = restrictionsCount;
		this.variablesCount = variablesCount;
		this.isMinimum = isMinimum;
		restrictionCoefficients = new ArrayList<>(restrictionsCount);
		rightSideValues = new ArrayList<>(restrictionsCount);
		hasNoNegativePart = new ArrayList<>(variablesCount);
		equationSigns = new ArrayList<Combo>();
	}

	private List<Variable> addZFunction() {
		LinkedList<Variable> vars = new LinkedList<>();
		for (int i = 0; i < variablesCount; i++) {
			Variable var = new Variable(new Fraction(zFuncCoefs.get(i)
					.getText()), i + 1);
			vars.add(var);
		}
		return vars;
	}

	private Optimum getOptimum() {
		if (isMinimum) {
			return Optimum.MINIMUM;
		}
		return Optimum.MAXIMUM;
	}

	private Problem createProblem() {
		List<Variable> zfunction = addZFunction();
		Vector<Restriction> restrictions = new Vector<>();
		for (int restrIndex = 0; restrIndex < restrictionsCount; restrIndex++) {
			Restriction current = getRestriction(restrIndex);
			restrictions.add(current);
		}
		Vector<Boolean> hasNegativePart = getNegativeParts();
		
		Problem problem = new Problem(zfunction, restrictions, getOptimum(),
				hasNegativePart);
		return problem;
	}
private Vector<Boolean> getNegativeParts(){
	Vector<Boolean> result=new Vector<>();
	for(Button isNonNegative:hasNoNegativePart){
		result.add(!isNonNegative.getSelection());
		}
	return result;
}
	private EquationSign getEquationSign(Combo combo) {
		String sign = combo.getText();
		switch (sign) {
		case ">=":
			return EquationSign.GTE;
		case "<=":
			return EquationSign.LTE;
		case "=":
			return EquationSign.EQ;
		}
		return null;
	}

	private Restriction getRestriction(int restrIndex) {
		List<Variable> variables = new LinkedList<>();
		ArrayList<Text> coefficients = restrictionCoefficients.get(restrIndex);
		int index = 1;
		for (Text coef : coefficients) {
			variables.add(new Variable(new Fraction(coef.getText()), index));
			index++;
		}

		EquationSign sign = getEquationSign(equationSigns.get(restrIndex));
		Fraction rightSide = new Fraction(rightSideValues.get(restrIndex)
				.getText());
		Restriction current = new Restriction(variables, sign, rightSide);
		return current;
	}

	public void createCountsWindow() {
		restrictionCoefficients = new ArrayList<ArrayList<Text>>(
				restrictionsCount);
		shell = new Shell();

		shell.setSize(370 + variablesCount * 110, 140 + restrictionsCount * 90);
		shell.setText("РћРіСЂР°РЅРёС‡РµРЅРёпїЅ?");
		visualizeFunction(isMinimum, variablesCount);
		for (Integer i = 1; i <= restrictionsCount; i++) {
			visualizeRestriction(variablesCount, i);
		}
		visualizeNonNegatives();
		Button btnToTable = new Button(shell, SWT.NONE);
		btnToTable.setFont(SWTResourceManager.getFont("Segoe UI", 14,
				SWT.NORMAL));
		btnToTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			
//				ProblemConversion conversion=new ProblemConversion();
			
				shell.dispose();
			}
		});
		btnToTable.setBounds(300 + variablesCount * 110,
				50 + restrictionsCount * 90, 120, 35);
		btnToTable.setText("РџСЂРѕРґСЉР»Р¶Рё");

		shell.open();
		shell.layout();
	}

	private Text restrictionX1Visualization(int restrictionIndex,
			Composite restrictionComposite) {
		Composite restrictionX1Composite = new Composite(restrictionComposite,
				SWT.NONE);
		restrictionX1Composite.setBounds(10, 15, 109, 45);
		Text restriction1Coefficient = new Text(restrictionX1Composite,
				SWT.BORDER);
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
		return restriction1Coefficient;

	}

	protected void visualizeRestriction(int variablesCount, int restrictionIndex) {
		ArrayList<Text> curRestrCoefs = new ArrayList<>();

		Composite restrictionComposite = new Composite(shell, SWT.NONE);
		restrictionComposite.setBounds(80, 90 + 65 * (restrictionIndex - 1),
				230 + 110 * (variablesCount - 1), 65);

		Text restrictionCoefficient;

		curRestrCoefs.add(restrictionX1Visualization(restrictionIndex,
				restrictionComposite));

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
			curRestrCoefs.add(restrictionCoefficient);
		}
		restrictionCoefficients.add(curRestrCoefs);
		Composite restrictionRightSide = new Composite(restrictionComposite,
				SWT.NONE);

		restrictionRightSide.setBounds(105 + 110 * (variablesCount - 1), 15,
				155, 45);

		Combo restrictionSign = new Combo(restrictionRightSide, SWT.NONE);
		restrictionSign.setItems(new String[] { "<=", "=", ">=" });
		restrictionSign.select(1);
		restrictionSign.setFont(SWTResourceManager.getFont("Segoe UI", 14,
				SWT.NORMAL));
		restrictionSign.setBounds(10, 5, 60, 25);

		restrictionNumericValue = new Text(restrictionRightSide, SWT.BORDER);
		restrictionNumericValue.setFont(SWTResourceManager.getFont("Segoe UI",
				13, SWT.NORMAL));
		restrictionNumericValue.setBounds(75, 5, 50, 32);
		rightSideValues.add(restrictionNumericValue);
		equationSigns.add(restrictionSign);
	}

	private void visualizeNonNegatives() {

		
		Group nonNegatives = new Group(shell, SWT.NONE);
		
		nonNegatives.setBounds(45, 65 * (restrictionsCount) + 88,
				150 + 110 * (variablesCount - 1), 25);
		nonNegatives.setText("РќEРћРўР Р�Р¦РђРўР•Р›РќР�");
	
		for (int index = 1; index <= variablesCount; index++) {
			Button check = new Button(nonNegatives, SWT.CHECK);
			check.setBounds(120+ 110 * (index-1), 5, 20, 20);
			check.setSize(40, 40);
			check.pack();
			hasNoNegativePart.add(check);
		}

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

		Composite composite1 = new Composite(shell, SWT.NONE);
		composite1.setBounds(87, 30, 109, 45);
		Text coefficient1 = new Text(composite1, SWT.BORDER);
		coefficient1 = new Text(composite1, SWT.BORDER);
		coefficient1.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		coefficient1.setBounds(20, 5, 50, 30);

		Label label = new Label(composite1, SWT.NONE);
		label.setText("X");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		label.setBounds(75, 5, 15, 25);

		Label label1 = new Label(composite1, SWT.NONE);
		label1.setText("1");
		label1.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		label1.setBounds(90, 20, 27, 34);

		zFuncCoefs.add(coefficient1);
		for (Integer i = 2; i <= variablesCount; i++) {

			Composite composite = new Composite(shell, SWT.NONE);
			composite.setBounds(200 + 110 * (i - 2), 30, 109, 45);
			Text coefficient = new Text(composite, SWT.BORDER);
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
			zFuncCoefs.add(coefficient);
		}

	}
}
