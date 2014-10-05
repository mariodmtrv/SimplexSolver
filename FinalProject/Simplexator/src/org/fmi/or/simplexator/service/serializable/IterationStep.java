package org.fmi.or.simplexator.service.serializable;

import java.util.List;

public class IterationStep {
	List<String> basis;
	List<String> basisCoefs;
	String table[][];
	List<String> rightSide;
	List<List<String>> data;
}
