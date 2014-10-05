function initializeStepSwitcher(problemManager)
{
	var stepSwitcher = new function(){
		var self = this;

		this.events = {
			// next actions to do: print next problem parameter
			0: printProblem,

			// next actions to do: print first iteration
			1: makeBlankSimplexTable,
			2: fillBasis,
			3: fillMainTable,
			4: fillCosts,
			5: fillRightSideVector,
			6: fillRightSideValue,
			7: checkOptimalityCriterion,
			8: checkUnboundednessCriterion,		// if iterations array is empty, then skip to printAnswer
			9: findKeyElement,

			// next actions to do: print next iteration
			10: makeBlankSimplexTable,
			11: fillBasis,
			12: fillBasisColumns,
			13: fillKeyElementRow,
			14: fillTableRectangleRule,
			15: checkOptimalityCriterion,
			16: checkUnboundednessCriterion,	// if iterations array is not empty, then go back to makeBlankSimplexTable
			17: findKeyElement,

			// next actions to do: print next answer
			18: printAnswer
		};

		this.step = 0;
		this.executeNextStep = function(){
			console.log("step = " + self.step + "; index = " + problemManager.index + ";")
			self.events[self.step]();
			MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
			self.updateStepAndIndex();
		};

		this.updateStepAndIndex = function(){
			if(self.step == 0 && problemManager.index < problemManager.problems.length - 1){
				problemManager.index += 1;
			}
			else if(self.step == 0 && problemManager.index == problemManager.problems.length - 1){
				self.step += 1;
				problemManager.index = 0;
			}
			else if((self.step == 8 || self.step == 16) && problemManager.index == problemManager.iterations.length - 1){
				self.step = 18;
				problemManager.index = 0;
			}
			else if(self.step == 9){
				self.step += 1;
				problemManager.index += 1;
			}
			else if(self.step == 17){
				self.step = 10;
				problemManager.index += 1;
			}
			else if(self.step == 18 && problemManager.index < problemManager.answers.length - 1){
				problemManager.index += 1;
			}
			else{
				self.step += 1;
			}
		};
	};

	$('#next').click(stepSwitcher.executeNextStep);
	return stepSwitcher;
}