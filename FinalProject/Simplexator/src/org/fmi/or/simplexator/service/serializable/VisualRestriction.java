package org.fmi.or.simplexator.service.serializable;

import java.util.ArrayList;
import java.util.List;

import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;

public class VisualRestriction {
	List<String> coefs;
	String sign;
	String rightSide;

	public VisualRestriction(List<String> coefs, String sign, String rightSide) {
		super();
		this.coefs = coefs;
		this.sign = sign;
		this.rightSide = rightSide;
	}

	public VisualRestriction(Restriction restriction) {
		this.rightSide = restriction.getRightSide().toMathJaxString();
		this.sign = restriction.getSign().toString();
		this.coefs = new ArrayList<>();
		Variable[] restrVars = restriction.getVariables();
		for (int i = 0; i < restrVars.length; i++) {
			this.coefs.add(restrVars[i].getCoefficient().toMathJaxString());
		}
	}

	public List<String> getCoefs() {
		return coefs;
	}

	public void setCoefs(List<String> coefs) {
		this.coefs = coefs;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRightSide() {
		return rightSide;
	}

	public void setRightSide(String rightSide) {
		this.rightSide = rightSide;
	}

}
