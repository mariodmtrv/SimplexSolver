// pretty print the coefficient
function printCoef(c, isFirst=false)
{
	var sign = c < 0 ? "-" : (isFirst ? "" : "+");
	var value = (Math.abs(c) == 1 || c == 0) ? "" : Math.abs(c).toString();
	return sign + value;
}


// pretty print the sign
function printEquationSign(s)
{
	if(s=="EQ")
		return "=";
	else if (s=="GTE")
		return ">=";
	else if (s=="LTE")
		return "<=";
}


// print next problem
function printProblem()
{
	var currentProblem = problemManager.getCurrentProblem();

	$("#problemDescription").append("<div></div>");
	var problemDiv = $("#problemDescription div:last");
	problemDiv.append("<span>" + currentProblem.extremum + " Z = </span>");

	// print Z-function
	problemDiv.append("<table><tr></tr></table>");
	$(problemDiv).find("table tr").append("<td>" + printCoef(currentProblem.zFuncCoefs[0], true) + currentProblem.variables[0] + "</td>");
	for (var i = 1; i < currentProblem.variables.length; i++)
	{
		$(problemDiv).find("table tr").append("<td>" + printCoef(currentProblem.zFuncCoefs[i]) + currentProblem.variables[i] + "</td>");
	};

	// print restrictions
	problemDiv.append("<table></table>");
	var restrictionsTable = $(problemDiv).find("table:last");
	for (r in currentProblem.restrictions)
	{
		$(restrictionsTable).append("<tr></tr>");
		var row = $(restrictionsTable).find("tr:last");
		row.append("<td>" + printCoef(currentProblem.restrictions[r].coefs[0], true) + currentProblem.variables[0] + "</td>");
		for (var i = 1; i < currentProblem.variables.length; i++)
		{
			row.append("<td>" + printCoef(currentProblem.restrictions[r].coefs[i]) + currentProblem.variables[i] + "</td>");
		};
		row.append("<td>" + printEquationSign(currentProblem.restrictions[r].sign) + "</td><td>" + currentProblem.restrictions[r].rightSide + "</td>");
	}

	// print xi >= 0
	problemDiv.append("<span>" + currentProblem.nonNegativeVars.join(", ") + " >= 0</span>");
}


// load the empty template of the simplex table
function makeBlankSimplexTable()
{
	// move old table to previous tables and clear highlighting
	$("#problemTable").find("tr, td, th").css('background', "");
	var currentIteration = problemManager.getCurrentIteration();
	highlightAndEmphasizeCell("mainTable", "red", currentIteration.keyElemCoords[0], currentIteration.keyElemCoords[1] - 1);
	$("#previousTable").append("<div></div>");
	var newDiv = $("#previousTable > div:last");
	$("#problemTable").children().clone().appendTo(newDiv);
	
	$("#next").toggle();

	// load the simplex table template and fill the data for the current iteration
	$("#problemTable").load("templates/simplex_table_template.html", function(){

		for (var v in problemManager.variables)
		{
			$("#problemTable .mainTableHeaderRow").append("<th>" + problemManager.variables[v] + "<br/>" + problemManager.zFuncCoefs[v] + "</th>");
		}

		for (var i = 0; i < problemManager.numRestrictions; i++)
		{
			$("#problemTable .basisTable").append("<tr><td><br></td><td><br></td></tr>");
			$("#problemTable .mainTable").append("<tr></tr>");
			$("#problemTable .rightSideTable").append("<tr><td></td></tr>");

			for (var j = 0; j < problemManager.numVars; j++)
			{
				$("#problemTable .mainTable tr:last").append("<td></td>");
			};
		};

		for (var i = 0; i < problemManager.numVars; i++)
		{
			$("#problemTable .costsTable tr:first").append("<td></td>");
			$("#problemTable .costsTable tr:last").append("<td></td>");
		};

		$("#next").toggle();
	});
}


function fillBasis()
{
	var currentIteration = problemManager.getCurrentIteration();

	for (var i = 0; i < currentIteration.basis.length; i++)
	{
		$("#problemTable .basisTable tr").eq(i + 1).children("td:first").html(currentIteration.basis[i]);
		$("#problemTable .basisTable tr").eq(i + 1).children("td:last").html(currentIteration.basisCoefs[i]);
	}

	highlightAll("basisTable", "yellow");
}


function fillMainTable()
{
	unHighlight("basisTable");

	var currentIteration = problemManager.getCurrentIteration();

	for (var i = 0; i < currentIteration.table.length; i++)
	{
		var row = $("#problemTable .mainTable tr").eq(i + 1);
		for (var j = 0; j < currentIteration.table[i].length; j++)
		{
			$(row).children("td").eq(j).html(currentIteration.table[i][j]);
		}
	}

	highlightAll("mainTable", "cyan");
}


function fillCosts()
{
	unHighlight("mainTable");

	var currentIteration = problemManager.getCurrentIteration();

	for (var i = 0; i < currentIteration.costs.length; i++)
	{
		for (var j = 0; j < currentIteration.costs[i].length; j++)
		{
			$("#problemTable .costsTable tr").eq(i).children("td").eq(j).html(currentIteration.costs[i][j]);
		}
	}

	highlightAll("costsTable", "red");
	// TODO: columns highlight on hover
}


function fillRightSideVector()
{
	unHighlight("costsTable");

	var currentIteration = problemManager.getCurrentIteration();

	for (var i = 0; i < currentIteration.rightSide.length; i++)
	{
		$("#problemTable .rightSideTable tr").eq(i + 1).children("td").html(currentIteration.rightSide[i]);
	}

	highlightColumn("basisTable", "yellow", 2);
	highlightAll("rightSideTable", "cyan");
}


function fillRightSideValue()
{
	unHighlight("basisTable");
	unHighlight("rightSideTable");

	var currentIteration = problemManager.getCurrentIteration();

	$("#problemTable .rightSideValueTable td:first").html(currentIteration.numValue);
	$("#problemTable .rightSideValueTable td:last").html(currentIteration.MValue);

	highlightColumn("basisTable", "yellow", 2);
	highlightAll("rightSideTable", "cyan");
	highlightAll("rightSideValueTable", "red");
}


function fillBasisColumns()
{
	unHighlight("basisTable");

	var currentIteration = problemManager.getCurrentIteration();

	var column_indexes = currentIteration.basis.map(problemManager.getVarIndex);
	for (var i = 0; i < currentIteration.table.length; i++)
	{
		var row = $("#problemTable .mainTable tr").eq(i + 1);
		for(var j in column_indexes)
		{
			$(row).children("td").eq(column_indexes[j]).html(currentIteration.table[i][column_indexes[j]]);

			highlightColumn("mainTable", "violet", column_indexes[j] + 1);
			highlightColumn("costsTable", "violet", column_indexes[j] + 1 + 2);
		}
	}

	for(var j in column_indexes)
	{
		$("#problemTable .costsTable tr:first td").eq(column_indexes[j]).html(currentIteration.costs[0][column_indexes[j]]);
		$("#problemTable .costsTable tr:last td").eq(column_indexes[j]).html(currentIteration.costs[1][column_indexes[j]]);
	}
}


function fillKeyElementRow()
{
	unHighlight("mainTable");
	unHighlight("costsTable");

	var currentIteration = problemManager.getCurrentIteration();

	var keyElementRow = currentIteration.keyElemCoords[0];
	highlightRow("mainTable", "violet", keyElementRow);
	highlightRow("rightSideTable", "violet", keyElementRow);
	for (var j = 0; j < currentIteration.table[keyElementRow - 1].length; j++)	// indexes of key element start at 1
	{
		$("#problemTable .mainTable tr").eq(keyElementRow).children("td").eq(j).html(currentIteration.table[keyElementRow - 1][j]);
	};

	$("#problemTable .rightSideTable tr").eq(keyElementRow).children("td:first").html(currentIteration.rightSide[keyElementRow - 1]);
}


// fill rest of the table (via rectangular rule)
function fillTableRectangleRule()
{
	unHighlight("mainTable");
	unHighlight("rightSideTable");

	var currentIteration = problemManager.getCurrentIteration();

	var keyElementRow = currentIteration.keyElemCoords[0];
	var column_indexes = currentIteration.basis.map(problemManager.getVarIndex);

	for (var i = 0; i < currentIteration.table.length; i++)
	{
		if(i + 1 == keyElementRow)
			continue;

		// fill main table
		for (var j = 0; j < currentIteration.table[i].length; j++)
		{
			if(column_indexes.indexOf(j) != -1)
				continue;

			$("#problemTable .mainTable tr").eq(i + 1).children("td").eq(j).html(currentIteration.table[i][j]);
			highlightCell("mainTable", "cyan", i + 1, j);
		};

		// fill right side column
		$("#problemTable .rightSideTable tr").eq(i + 1).children("td:first").html(currentIteration.rightSide[i]);
		highlightRow("rightSideTable", "cyan", i + 1);
	};

	// fill costs
	for (var i = 0; i < currentIteration.costs.length; i++)
	{
		for (var j = 0; j < currentIteration.costs[i].length; j++)
		{
			if(column_indexes.indexOf(j) != -1)
				continue;

			$("#problemTable .costsTable tr").eq(i).children("td").eq(j).html(currentIteration.costs[i][j]);
			highlightCell("costsTable", "cyan", i, j);
		}
	}

	// fill right side value
	$("#problemTable .rightSideValueTable td:first").html(currentIteration.numValue);
	$("#problemTable .rightSideValueTable td:last").html(currentIteration.MValue);
	highlightCell("rightSideValueTable", "cyan", 0, 0);
	highlightCell("rightSideValueTable", "cyan", 1, 0);

	// TODO: cells highlight on hover
}


function checkOptimalityCriterion()
{
	unHighlight("basisTable");
	unHighlight("mainTable");
	unHighlight("rightSideTable");
	unHighlight("rightSideValueTable");
	unHighlight("costsTable");

	var currentIteration = problemManager.getCurrentIteration();

	if(currentIteration.newKeyElemCoords.length == 0)
	{
		// TODO: handle special outcomes (no negative costs, no positvie cells in the column)
		return;
	}

	highlightColumn("costsTable", "green", currentIteration.newKeyElemCoords[1] + 2);
}


function checkUnboundednessCriterion()
{
	//unHighlight("costsTable");

	var currentIteration = problemManager.getCurrentIteration();

	if(currentIteration.newKeyElemCoords.length == 0)
	{
		// TODO: handle special outcomes (no negative costs, no positvie cells in the column)
		return;
	}

	//highlightColumn("costsTable", "green", currentIteration.newKeyElemCoords[1] + 2);
	highlightColumn("mainTable", "green", currentIteration.newKeyElemCoords[1]);
	highlightAll("rightSideTable", "cyan");
}


function findKeyElement()
{
	unHighlight("costsTable");
	unHighlight("mainTable");
	unHighlight("rightSideTable");

	var currentIteration = problemManager.getCurrentIteration();

	highlightColumn("mainTable", "green", currentIteration.newKeyElemCoords[1]);
	highlightColumn("costsTable", "green", currentIteration.newKeyElemCoords[1] + 2);
	highlightRow("basisTable", "green", currentIteration.newKeyElemCoords[0]);
	highlightRow("mainTable", "green", currentIteration.newKeyElemCoords[0]);
	highlightRow("rightSideTable", "green", currentIteration.newKeyElemCoords[0]);
}


// print next answer
function printAnswer()
{
	var currentAnswer = problemManager.getCurrentAnswer();
	var currentProblem = problemManager.getProblemForCurrentAnswer();

	$("#answers").append("<div></div>");
	var div = $("#answers div:last");
	$(div).load("templates/answers_grid_template.html", function(){
		$(div).find(".points").append("<tr></tr>");
		for (var v in currentProblem.variables)
		{
			$(div).find(".points tr:last").append("<th>" + currentProblem.variables[v] + "</th>");
		};

		for(var i = 0; i < currentAnswer.points.length; i++)
		{
			$(div).find(".points").append("<tr></tr>");
			for(var j = 0; j < currentAnswer.points[i].length; j++)
			{
				$(div).find(".points tr:last").append("<td>" + currentAnswer.points[i][j] + "</td>");
			}
		}

		if(currentAnswer.directions.length == 0)
			return;

		// print direction vectors (non-empty list)

		$(div).find(".directions").append("<tr></tr>");

		for (var v in currentProblem.variables)
		{
			$(div).find(".directions tr:last").append("<th>" + currentProblem.variables[v] + "</th>");
		};

		for(var i = 0; i < currentAnswer.directions.length; i++)
		{
			$(div).find(".directions").append("<tr></tr>");
			for(var j = 0; j < currentAnswer.directions[i].length; j++)
			{
				$(div).find(".directions tr:last").append("<td>" + currentAnswer.directions[i][j] + "</td>");
			}
		}
	});
}
