package org.fmi.or.simplexator.algorithm.answerer;

import java.util.Vector;

import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.VariableType;

/**
 * Convert the answer of another problem obtained by the algorithm (oldAnswer)
 * to the answer of this problem (problem)
 */
public class AnswerConverter {
	private Problem problem;
	//private ProblemType problemType;
	private Answer oldAnswer;
	
	public AnswerConverter(Problem problem, /*ProblemType problemType,*/ Answer oldAnswer) {
		this.problem = problem;
		//this.problemType = problemType;
		this.oldAnswer = oldAnswer;
	}
	
	public Answer convert() {
		/*if(problemType == K)
			return convertMToK();
		return convertKToL();*/
		return null; // TODO
	}
	
	public Answer convertMToK() {
		Answer newAnswer = new Answer();
		
		
		// convert vertices
		for(int i = 0; i < oldAnswer.getVertices().size(); i++) {
			Vector<Fraction> newVertex = oldAnswer.getVertices().get(i);
			
			for(int j = problem.getVarCount(); j < newVertex.size(); j++) {
				if(!newVertex.get(j).isEqualTo(Fraction.ZERO)) {
					return null;
				}
			}
			
			newVertex.setSize(problem.getVarCount());
			newAnswer.pushVertex(newVertex);
		}
		
		
		// convert directions
		for(int i = 0; i < oldAnswer.getDirections().size(); i++) {
			Vector<Fraction> newDirection = oldAnswer.getDirections().get(i);
			
			for(int j = problem.getVarCount(); j < newDirection.size(); j++) {
				if(!newDirection.get(j).isEqualTo(Fraction.ZERO)) {
					return null;
				}
			}
			
			newDirection.setSize(problem.getVarCount());
			newAnswer.pushVertex(newDirection);
		}
		
		
		return newAnswer;
	}
	
	public Answer convertKToL() {
		Answer newAnswer = new Answer();
		
		
		// find variables added as "+" & "-"
		Vector<Integer> plusVars = new Vector<Integer>();
		
		for(int i=0; i < this.problem.getHasNegativePart().size(); i++) {
			if(this.problem.getHasNegativePart().get(i))
				plusVars.add(i);
		}
		
		// convert vertices
		for(int i = 0; i < oldAnswer.getVertices().size(); i++) {
			Vector<Fraction> newVertex = oldAnswer.getVertices().get(i);
			
			// unite variables added as "+" & "-"
			for(int j = 0; j < plusVars.size(); j++) {
				Fraction plusValue = newVertex.get(plusVars.get(j));
				Fraction minusValue = newVertex.get(plusVars.get(j) + 1);
				newVertex.setElementAt(plusValue.subtract(minusValue), plusVars.get(j));
				newVertex.remove(plusVars.get(j) + 1);
			}
			
			// cut out the variables added to balance the restrictions
			newVertex.setSize(problem.getVarCount());
			
			newAnswer.pushVertex(newVertex);
		}
		
		// convert directions
		for(int i = 0; i < oldAnswer.getDirections().size(); i++) {
			Vector<Fraction> newDirection = oldAnswer.getDirections().get(i);
			
			// unite variables added as "+" & "-"
			for(int j = 0; j < plusVars.size(); j++) {
				Fraction plusValue = newDirection.get(plusVars.get(j));
				Fraction minusValue = newDirection.get(plusVars.get(j) + 1);
				newDirection.setElementAt(plusValue.subtract(minusValue), plusVars.get(j));
				newDirection.remove(plusVars.get(j) + 1);
			}
			
			// cut out the variables added to balance the restrictions
			newDirection.setSize(problem.getVarCount());
			
			newAnswer.pushDirection(newDirection);
		}
		
		
		return newAnswer;
	}
}
