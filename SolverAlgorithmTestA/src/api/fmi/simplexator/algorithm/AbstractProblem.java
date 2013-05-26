package api.fmi.simplexator.algorithm;

import java.util.List;
import java.util.Vector;

public abstract class AbstractProblem {
    protected int varCount;
	protected int restrictionsCount;
	protected List<Variable> zfunction;
	protected Vector<Restriction> restrictions;
	
	
}
