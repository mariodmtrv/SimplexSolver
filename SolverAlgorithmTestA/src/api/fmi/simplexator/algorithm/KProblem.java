package api.fmi.simplexator.algorithm;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class KProblem {
	private Optimum optimum;
	private int varCount;
	private int restrictionsCount;
	private List<Variable> zfunction;
	private Vector<Boolean> hasNegativePart;
	private Vector<Restriction> restrictions;

	private void setToMinimum() {
		if (optimum != Optimum.MINIMUM) {
			optimum = Optimum.MINIMUM;
			for (Variable variable : zfunction) {
				variable.changeSign();
			}
		}
	}

	private void setRightSidesToPositive() {
		for (Restriction restriction : restrictions) {
			restriction.rightSideToPositive();
		}
	}

	Iterator<Variable> zfuncIter = zfunction.iterator();
	
	private void processNegativeParts() {
		for (Boolean hnp : hasNegativePart) {
			if(hnp==true){
				varCount++;
				
			}
		}

	}

}
