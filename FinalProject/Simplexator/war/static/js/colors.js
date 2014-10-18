function highlightCell(tableName, color, rowIndex, columnIndex)
{
	var cell = $("#problemTable ." + tableName).find("tr").eq(rowIndex).find("td").eq(columnIndex);
	$(cell).css('background', color);
}
// NOTE: .find("tr") doesn't work if there are nested tables inside cells of the given table


function highlightAndEmphasizeCell(tableName, color, rowIndex, columnIndex)
{
	var cell = $("#problemTable ." + tableName).find("tr").eq(rowIndex).find("td").eq(columnIndex);
	$(cell).css('background', color);
	$(cell).css("font-weight", "bold");
	$(cell).css("text-decoration", "underline");
}


function highlightRow(tableName, color, rowIndex)
{
	var row = $("#problemTable ." + tableName).find("tr").eq(rowIndex);
	$(row).css('background', color);
}


function highlightColumn(tableName, color, columnIndex)
{
	var rows = $("#problemTable ." + tableName).find("tr");
	var column = $(rows).find(':nth-child(' + columnIndex + ')');
	$(column).css('background', color);
}


function highlightAll(tableName, color)
{
	$("#problemTable ." + tableName).find("tr").find("td, th").css('background', color);
}


function unHighlight(tableName)
{
	$("#problemTable ." + tableName).find("tr, td, th").css('background', "");
}