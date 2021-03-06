// pretty print the coefficient
function printCoef(c, isFirst)
{
	 var sign = (c[2] == "-") ? "" : (isFirst ? "" : "+");
	 var value = (c == "\\(1\\)" || c == "\\(-1\\)") ? (c == "\\(-1\\)" ? "\\(-\\)" : "") : c;
	 return sign + value;
}


// pretty print the sign
function printEquationSign(s)
{
	if(s=="EQ")
		return "\\( = \\)";
	else if (s=="GTE")
		return "\\( \\geq \\)";
	else if (s=="LTE")
		return "\\( \\leq \\)";
}


// print next problem
function printProblem()
{
	var currentProblem = problemManager.getCurrentProblem();

	$("#problemDescription div:last").css('float', 'left');
	$("#problemDescription").append("<div></div>");
	var problemDiv = $("#problemDescription div:last");
	$(problemDiv).css('margin', '5px 20px');
	$(problemDiv).css('min-width', '30%');
	var z = "\\(Z" + (currentProblem.isM ? "_M" : (currentProblem.isK ? "_K" : "")) + "\\)";
	problemDiv.append("<span>" + currentProblem.extremum + " " + z + " = </span>");

	// print Z-function
	problemDiv.append("<table><tr></tr></table>");
	$(problemDiv).find("table tr").append("<td>" + printCoef(currentProblem.zFuncCoefs[0], true) + " " + currentProblem.variables[0] + "</td>");
	for (var i = 1; i < currentProblem.variables.length; i++)
	{
		$(problemDiv).find("table tr").append("<td>" + printCoef(currentProblem.zFuncCoefs[i], false) + " " + currentProblem.variables[i] + "</td>");
	};

	// print restrictions
	problemDiv.append("<table></table>");
	var restrictionsTable = $(problemDiv).find("table:last");
	for (r in currentProblem.restrictions)
	{
		$(restrictionsTable).append("<tr></tr>");
		var row = $(restrictionsTable).find("tr:last");

		if(currentProblem.restrictions[r].coefs[0] != "\\(0\\)")
		{
			row.append("<td>" + printCoef(currentProblem.restrictions[r].coefs[0], true) + " " + currentProblem.variables[0] + "</td>");
		}
		else
		{
			row.append("<td></td>");
		}

		for (var i = 1; i < currentProblem.variables.length; i++)
		{
			if(currentProblem.restrictions[r].coefs[i] != "\\(0\\)")
			{
				row.append("<td>" + printCoef(currentProblem.restrictions[r].coefs[i], false) + " " + currentProblem.variables[i] + "</td>");
			}
			else
			{
				row.append("<td></td>");
			}
		};

		row.append("<td>" + printEquationSign(currentProblem.restrictions[r].sign) + "</td><td>" + currentProblem.restrictions[r].rightSide + "</td>");
	}

	// print xi >= 0
	if(currentProblem.nonNegativeVars.length > 0)
	{
		problemDiv.append("<span>" + currentProblem.nonNegativeVars.join(", ") + " \\(\\geq\\) 0</span>");
	}

	// print all messages related to this problem conversion step
	var currentMessage = problemManager.getCurrentMessage();
	while(currentMessage != "STEP") {
		$("#log #scrollable").prepend("<p>" + currentMessage + "</p>");
		currentMessage = problemManager.getCurrentMessage();
	}

	MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
}


// load the empty template of the simplex table
function makeBlankSimplexTable()
{
	// move old table to previous tables and clear highlighting
	$("#problemTable").find("tr, td, th").css('background', "");
	var currentIteration = problemManager.getCurrentIteration();
	highlightAndEmphasizeCell("mainTable", "red", currentIteration.keyElemCoords[0] + 1, currentIteration.keyElemCoords[1] + 1 - 1);
	$("#previousTable").append("<div></div>");
	var newDiv = $("#previousTable > div:last");
	$("#problemTable").children().clone().appendTo(newDiv);
	
	$("#next").toggle();

	// load the simplex table template and fill the data for the current iteration
	$("#problemTable").load("static/templates/simplex_table_template.html", function(){

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
		$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
		MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
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

	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
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

	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");

	highlightAll("mainTable", "cyan");
}


function fillCosts()
{
	unHighlight("rightSideTable");

	var currentIteration = problemManager.getCurrentIteration();

	for (var i = 0; i < currentIteration.costs.length; i++)
	{
		for (var j = 0; j < currentIteration.costs[i].length; j++)
		{
			var cell = $("#problemTable .costsTable tr").eq(i).children("td").eq(j);
			$(cell).html(currentIteration.costs[i][j]);

			// on hover: highlight the columns used for the calculation of the cell value
			$(cell).addClass("hoverEnabled");
			$(cell).hover(function() {
				highlightColumn("basisTable", "yellow", 2);
				var index = $(".hoverEnabled").index($(this));
				highlightColumn("mainTable", "yellow", index % problemManager.numVars + 1);
			}, function() {
				unHighlight("basisTable");
				unHighlight("mainTable");
			});
		}
	}

	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
	
	highlightAll("costsTable", "red");
}


function fillRightSideVector()
{
	unHighlight("mainTable");

	var currentIteration = problemManager.getCurrentIteration();

	for (var i = 0; i < currentIteration.rightSide.length; i++)
	{
		$("#problemTable .rightSideTable tr").eq(i + 1).children("td").html(currentIteration.rightSide[i]);
	}

	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");

	highlightAll("rightSideTable", "cyan");
}


function fillRightSideValue()
{
	$(".hoverEnabled").off('mouseenter mouseleave');
	$(".hoverEnabled").removeClass("hoverEnabled");
	unHighlight("costsTable");

	var currentIteration = problemManager.getCurrentIteration();

	$("#problemTable .rightSideValueTable td:first").html(currentIteration.numValue);
	$("#problemTable .rightSideValueTable td:last").html(currentIteration.MValue);

	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");

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

	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
}


function fillKeyElementRow()
{
	unHighlight("mainTable");
	unHighlight("costsTable");

	var currentIteration = problemManager.getCurrentIteration();

	var keyElementRow = currentIteration.keyElemCoords[0] + 1;
	highlightRow("mainTable", "violet", keyElementRow);
	highlightRow("rightSideTable", "violet", keyElementRow);
	for (var j = 0; j < currentIteration.table[keyElementRow - 1].length; j++)	// indexes of key element start at 1
	{
		$("#problemTable .mainTable tr").eq(keyElementRow).children("td").eq(j).html(currentIteration.table[keyElementRow - 1][j]);
	};

	$("#problemTable .rightSideTable tr").eq(keyElementRow).children("td:first").html(currentIteration.rightSide[keyElementRow - 1]);

	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
}


// helper function for fillTableRectangleRule();
// on hover: highlight the cells from the previous table that give the value in the selected cell
function showRectangleRuleHover(cell, cellCoords, keyElemCoords)
{
	$(cell).addClass("hoverEnabled");

	var i = cellCoords[0];
	var j = cellCoords[1];
	var p = keyElemCoords[0];
	var q = keyElemCoords[1];
	var keyElement = $("#previousTable div:last .mainTable tr").eq(p + 1).children("td").eq(q);

	if($(cell).closest("table").hasClass("mainTable")) {
		var cell_iq = $("#previousTable div:last .mainTable tr").eq(i + 1).children("td").eq(q);
		var cell_pj = $("#previousTable div:last .mainTable tr").eq(p + 1).children("td").eq(j);
		var previous = $("#previousTable div:last .mainTable tr").eq(i + 1).children("td").eq(j);
	}
	else if($(cell).closest("table").hasClass("rightSideTable")) {
		var cell_iq = $("#previousTable div:last .mainTable tr").eq(i + 1).children("td").eq(q);
		var cell_pj = $("#previousTable div:last .rightSideTable tr").eq(p + 1).children("td").eq(j);
		var previous = $("#previousTable div:last .rightSideTable tr").eq(i + 1).children("td").eq(j);
	}
	else if ($(cell).closest("table").hasClass("costsTable")) {
		var cell_iq = $("#previousTable div:last .costsTable tr").eq(i).children("td").eq(q);
		var cell_pj = $("#previousTable div:last .mainTable tr").eq(p + 1).children("td").eq(j);
		var previous = $("#previousTable div:last .costsTable tr").eq(i).children("td").eq(j);
	}
	else if ($(cell).closest("table").hasClass("rightSideValueTable")) {
		var cell_iq = $("#previousTable div:last .costsTable tr").eq(i).children("td").eq(q);
		var cell_pj = $("#previousTable div:last .rightSideTable tr").eq(p + 1).children("td").eq(j);
		var previous = $("#previousTable div:last .rightSideValueTable tr").eq(i).children("td").eq(j);
	};

	$(cell).hover(function() {
		$(cell_iq).css('background', "yellow");
		$(cell_pj).css('background', "yellow");
		$(previous).css('background', "cyan");
	}, function() {
		$(cell_iq).css('background', "");
		$(cell_pj).css('background', "");
		$(previous).css('background', "");
	});	
}


// fill rest of the table (via rectangular rule)
function fillTableRectangleRule()
{
	unHighlight("mainTable");
	unHighlight("rightSideTable");

	var currentIteration = problemManager.getCurrentIteration();

	var keyElementRow = currentIteration.keyElemCoords[0] + 1;
	var column_indexes = currentIteration.basis.map(problemManager.getVarIndex);

	var cell;
	for (var i = 0; i < currentIteration.table.length; i++)
	{
		if(i + 1 == keyElementRow)
			continue;

		// fill main table
		for (var j = 0; j < currentIteration.table[i].length; j++)
		{
			if(column_indexes.indexOf(j) != -1)
				continue;

			cell = $("#problemTable .mainTable tr").eq(i + 1).children("td").eq(j);
			$(cell).html(currentIteration.table[i][j]);
			showRectangleRuleHover(cell, [i, j], currentIteration.keyElemCoords);
			highlightCell("mainTable", "cyan", i + 1, j);
		};

		// fill right side column
		cell = $("#problemTable .rightSideTable tr").eq(i + 1).children("td:first");
		$(cell).html(currentIteration.rightSide[i]);
		showRectangleRuleHover(cell, [i, 0], currentIteration.keyElemCoords);
		highlightRow("rightSideTable", "cyan", i + 1);
	};

	// fill costs
	for (var i = 0; i < currentIteration.costs.length; i++)
	{
		for (var j = 0; j < currentIteration.costs[i].length; j++)
		{
			if(column_indexes.indexOf(j) != -1)
				continue;

			cell = $("#problemTable .costsTable tr").eq(i).children("td").eq(j);
			$(cell).html(currentIteration.costs[i][j]);
			showRectangleRuleHover(cell, [i, j], currentIteration.keyElemCoords);
			highlightCell("costsTable", "cyan", i, j);
		}
	}

	// fill right side value
	cell = $("#problemTable .rightSideValueTable td:first");
	cell.html(currentIteration.numValue);
	showRectangleRuleHover(cell, [0, 0], currentIteration.keyElemCoords);
	cell = $("#problemTable .rightSideValueTable td:last");
	cell.html(currentIteration.MValue);
	showRectangleRuleHover(cell, [1, 0], currentIteration.keyElemCoords);
	highlightCell("rightSideValueTable", "cyan", 0, 0);
	highlightCell("rightSideValueTable", "cyan", 1, 0);

	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
}


function checkOptimalityCriterion()
{
	unHighlight("basisTable");
	unHighlight("mainTable");
	unHighlight("rightSideTable");
	unHighlight("rightSideValueTable");
	unHighlight("costsTable");
	$(".hoverEnabled").off('mouseenter mouseleave');
	$(".hoverEnabled").removeClass("hoverEnabled");

	var currentIteration = problemManager.getCurrentIteration();

	if(currentIteration.newKeyElemCoords[1] == -1)
	{
		$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
		$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
		return;
	}

	highlightColumn("costsTable", "lime", currentIteration.newKeyElemCoords[1] + 1 + 2);

	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
}


function checkUnboundednessCriterion()
{
	//unHighlight("costsTable");

	var currentIteration = problemManager.getCurrentIteration();

	if(currentIteration.newKeyElemCoords[1] == -1)
	{
		return;
	}

	if(currentIteration.newKeyElemCoords[0] == -1)
	{
		$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
		$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
		$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
		return;
	}

	//highlightColumn("costsTable", "lime", currentIteration.newKeyElemCoords[1] + 1 + 2);
	highlightColumn("mainTable", "lime", currentIteration.newKeyElemCoords[1] + 1);
	highlightAll("rightSideTable", "cyan");

	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
	$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
}


function findKeyElement()
{
	unHighlight("costsTable");
	unHighlight("mainTable");
	unHighlight("rightSideTable");

	var currentIteration = problemManager.getCurrentIteration();

	highlightColumn("mainTable", "lime", currentIteration.newKeyElemCoords[1] + 1);
	highlightColumn("costsTable", "lime", currentIteration.newKeyElemCoords[1] + 1 + 2);
	highlightRow("basisTable", "lime", currentIteration.newKeyElemCoords[0] + 1);
	highlightRow("mainTable", "lime", currentIteration.newKeyElemCoords[0] + 1);
	highlightRow("rightSideTable", "lime", currentIteration.newKeyElemCoords[0] + 1);
}


// print next answer
function printAnswer()
{
	var currentAnswer = problemManager.getCurrentAnswer();

	if(!currentAnswer)
		return;

	$("#answers").append("<div></div>");
	var div = $("#answers div:last");
	$(div).load("static/templates/answers_grid_template.html", function(){
		$(div).prepend("<p>Answer to " + (currentAnswer.isM ? "M-" : (currentAnswer.isK ? "K-" : "original ")) + "problem</p>");
		$(div).find(".points").append("<caption>Vertices:</caption>");
		$(div).find(".points").append("<tr></tr>");
		for (var v in currentAnswer.variables)
		{
			$(div).find(".points tr:last").append("<th>" + currentAnswer.variables[v] + "</th>");
		};

		for(var i = 0; i < currentAnswer.points.length; i++)
		{
			$(div).find(".points").append("<tr></tr>");
			for(var j = 0; j < currentAnswer.points[i].length; j++)
			{
				$(div).find(".points tr:last").append("<td>" + currentAnswer.points[i][j] + "</td>");
			}
		}

		$("#log #scrollable").prepend("<p>" + problemManager.getCurrentMessage() + "</p>");
		MathJax.Hub.Queue(["Typeset",MathJax.Hub]);

		if(currentAnswer.directions.length == 0)
			return;

		// print direction vectors (non-empty list)
		$(div).find(".directions").append("<caption>Directions:</caption>");
		$(div).find(".directions").append("<tr></tr>");
		for (var v in currentAnswer.variables)
		{
			$(div).find(".directions tr:last").append("<th>" + currentAnswer.variables[v] + "</th>");
		};

		for(var i = 0; i < currentAnswer.directions.length; i++)
		{
			$(div).find(".directions").append("<tr></tr>");
			for(var j = 0; j < currentAnswer.directions[i].length; j++)
			{
				$(div).find(".directions tr:last").append("<td>" + currentAnswer.directions[i][j] + "</td>");
			}
		}

		MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
	});
}
