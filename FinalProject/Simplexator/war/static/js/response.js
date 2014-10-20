function initializeProblemManager(responseFromServer)
{
	return problemManager = new function(){
		var self = this;

		var response = responseFromServer;
		this.problems = response.problems;
		this.iterations = response.iterations;
		this.answers = response.answers;
		this.messageLog = response.messageLog;

		// get parameters of M-Problem
		this.numVars = this.problems[2].variables.length;
		this.numRestrictions = this.problems[2].restrictions.length;
		this.variables = this.problems[2].variables;
		this.zFuncCoefs = this.problems[2].zFuncCoefs;

		// given a variable (string) return its index (integer)
		this.getVarIndex = function(variable){
			return self.variables.indexOf(variable);
		}

		// methods for iterating response
		this.index = 0;
		this.getCurrentProblem = function(){
			return self.problems[self.index];
		};
		this.getCurrentIteration = function(){
			return self.iterations[self.index];
		};
		this.getCurrentAnswer = function(){
			return self.answers[self.index];
		};

		this.getProblemForCurrentAnswer = function(){
			return self.problems[self.problems.length - 1 - self.index];
		};

		// methods for iterating the response message log
		this.msgIndex = 0;
		this.getCurrentMessage = function(){
			 var i = self.msgIndex;
			 self.msgIndex += 1;
			return self.messageLog[i];
		};
	};
}