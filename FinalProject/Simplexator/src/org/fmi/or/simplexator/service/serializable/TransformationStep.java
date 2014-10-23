package org.fmi.or.simplexator.service.serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.tools.ant.taskdefs.rmic.KaffeRmic;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class TransformationStep {
	String extremum;
	Boolean isK;
	Boolean isM;
	List<String> variables;
	List<String> zFuncCoefs;
	List<VisualRestriction> restrictions;
	List<String> nonNegativeVars;

	public TransformationStep(String extremum, Boolean isK, Boolean isM,
			List<String> variables, List<String> zFuncCoefs,
			List<VisualRestriction> visualRestrictions,
			List<String> nonNegativeVars) {

		this.extremum = extremum;
		this.isK = isK;
		this.isM = isM;
		this.variables = variables;
		this.zFuncCoefs = zFuncCoefs;
		// this.restrictions = restrictions;
		this.nonNegativeVars = nonNegativeVars;
	}

	public TransformationStep(Problem problem) {

		this.extremum = problem.getOptimum().toString();
	
		this.variables = new ArrayList<>();
		for (int i = 0; i < problem.getVarCount(); i++) {
			this.variables.add(problem.getVarByIndex(i).toMathJaxString());
		}
		Variable[] zFuncVariables = problem.getZfunctionVariables();
		//System.out.println(zFuncVariables);
		this.zFuncCoefs = new ArrayList<>();
		for (Variable zFuncVar : zFuncVariables) {
			String zFuncCoef = zFuncVar.getCoefficient().toMathJaxString();
			//System.out.println(zFuncCoef);
			this.zFuncCoefs.add(zFuncCoef);
		}
		// add restrictions
		this.restrictions = new ArrayList<>();
		for (int restrIndex = 0; restrIndex < problem.getRestrictionsCount(); restrIndex++) {
			//System.out.println("RESTR: " + problem.getRestriction(restrIndex).getVariables().length);
			restrictions.add(new VisualRestriction(problem
					.getRestriction(restrIndex)));
		}
		this.nonNegativeVars = new ArrayList<>();
		Vector<Boolean> negativePartIndicators = problem.getHasNegativePart();
		//System.out.println("NONNEG: " + problem.getHasNegativePart().size());
		for (int i = 0; i < negativePartIndicators.size(); i++) {
			if (negativePartIndicators.get(i) == false)
				nonNegativeVars.add(problem.getVarByIndex(i).toMathJaxString());
		}
		//System.out.println("NONNEG - json: " + this.nonNegativeVars.size());
		this.isK=problem.isK();
	    this.isM=problem.isM();
	}

	public String getExtremum() {
		return extremum;
	}

	public void setExtremum(String extremum) {
		this.extremum = extremum;
	}

	public Boolean getIsK() {
		return isK;
	}

	public void setIsK() {
		this.isK = true;
	}

	public Boolean getIsM() {
		return isM;
	}

	public void setIsM(Boolean isM) {
		this.isM = isM;
	}

	public List<String> getVariables() {
		return variables;
	}

	public void setVariables(List<String> variables) {
		this.variables = variables;
	}

	public List<String> getzFuncCoefs() {
		return zFuncCoefs;
	}

	public void setzFuncCoefs(List<String> zFuncCoefs) {
		this.zFuncCoefs = zFuncCoefs;
	}

	public List<VisualRestriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<VisualRestriction> restrictions) {
		this.restrictions = restrictions;
	}

	public List<String> getNonNegativeVars() {
		return nonNegativeVars;
	}

	public void setNonNegativeVars(List<String> nonNegativeVars) {
		this.nonNegativeVars = nonNegativeVars;
	}
}
