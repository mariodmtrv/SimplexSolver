// Based on problem parameters make form for user input
$(document).ready(function(){
  $("#nextCreate").on('click', function() {
  	var type = $("#type").val();
  	var numVars = $("#numVars").val();
  	var numRestrictions = $("#numRestrictions").val();

  	$("#problemParameters").toggle();
    $("#setProblem").toggle();

    $("#zTable").append("<tr></tr>");
    $("#zTable tr").append("<td>" + type + " Z = </td>");
    $("#zTable tr").append("<td><input/>x_1</td>");
    for(i=2; i <= numVars; i++){
        $("#zTable tr").append("<td>+<input/>x_" + i + "</td>");
    }

    for (var i = 0; i < numRestrictions; i++) {
      var row = $("#restrictTable").append("<tr></tr>");
      $("#restrictTable tr").eq(i).append("<td><input/>x_1</td>");
      for (var j = 2; j <= numVars; j++) {
        $("#restrictTable tr").eq(i).append("<td>+<input/>x_" + j + "</td>");
      };
      $("#restrictTable tr").eq(i).append("<td><select class=\"sign\"><option value=\"GTE\">>=</option><option value=\"LTE\"><=</option><option value=\"EQ\">=</option></select></td>");
      $("#restrictTable tr").eq(i).append("<td><input class=\"rightSide\"/></td>");
    };

    var checks = $("#checkPositive").append("<tr></tr>");
  	for (var i = 1; i <= numVars; i++) {
      checks.append("<input type='checkbox' name='isPositive_x" + i + "' value='isPositive_x" + i + "'>x_" + i + " >= 0");
    };
  });
});


var problemManager;
var stepSwitcher;


// Make & send JSON of the problem via AJAX to the server,
// also recieve response and initialize variables for the visualization
$(document).ready(function(){
  $("#nextSendProblem").on('click', function() {
    var problem = {};
    var jsonProblem = JSON.stringify(getProblemInfo(problem));

    // TODO: send AJAX
    var HARDCODED_RESPONSE = {
      problems: [
      {
        extremum: "MIN",
        isK: false,
        isM: false,
        variables: ["x1", "x2", "x3"],
        zFuncCoefs: ["2", "1", "2"],
        restrictions: [
          {
            coefs: ["1", "0", "-1"],
            sign: "LTE",
            rightSide: "-1"
          },
          {
            coefs: ["-1", "0", "-2"],
            sign: "LTE",
            rightSide: "3"
          },
          {
            coefs: ["3", "1", "1"],
            sign: "EQ",
            rightSide: "4"
          }
        ],
        nonNegativeVars: ["x2", "x3"]
      },
      {
        extremum: "MIN",
        isK: true,
        isM: false,
        variables: ["x1+", "x1-", "x2", "x3", "x4", "x5"],
        zFuncCoefs: ["2", "-2", "1", "2", "0", "0"],
        restrictions: [
          {
            coefs: ["-1", "1", "0", "1", "-1", "0"],
            sign: "EQ",
            rightSide: "1"
          },
          {
            coefs: ["-1", "1", "0", "-2", "0", "1"],
            sign: "EQ",
            rightSide: "3"
          },
          {
            coefs: ["3", "-3", "1", "1", "0", "0"],
            sign: "EQ",
            rightSide: "4"
          }
        ],
        nonNegativeVars: ["x1+", "x1-", "x2", "x3", "x4", "x5"]
      },
      {
        extremum: "MIN",
        isK: true,
        isM: true,
        variables: ["x1+", "x1-", "x2", "x3", "x4", "x5", "x6"],
        zFuncCoefs: ["2", "-2", "1", "2", "0", "0", "M"],
        restrictions: [
          {
            coefs: ["-1", "1", "0", "1", "-1", "0", "1"],
            sign: "EQ",
            rightSide: "1"
          },
          {
            coefs: ["-1", "1", "0", "-2", "0", "1", "0"],
            sign: "EQ",
            rightSide: "3"
          },
          {
            coefs: ["3", "-3", "1", "1", "0", "0", "0"],
            sign: "EQ",
            rightSide: "4"
          }
        ],
        nonNegativeVars: ["x1+", "x1-", "x2", "x3", "x4", "x5", "x6"]
      }
      ],
      iterations: [
      {
        basis: ["x6", "x5", "x2"],
        basisCoefs: ["M", "0", "1"],
        table: [
        ["-1", "1", "0", "1", "-1", "0", "1"],
        ["-1", "1", "0", "-2", "0", "1", "0"],
        ["3", "-3", "1", "1", "0", "0", "0"]
        ],
        rightSide: ["1", "3", "4"],
        costs: [
        ["-1", "1", "0", "1", "0", "0", "0"],
        ["1", "-1", "0", "-1", "1", "0", "0"]
        ],
        numValue: "-4",
        MValue: "-1",
        messageLog: [],
        keyElemCoords: [],
        newKeyElemCoords: [1, 2]
      },
      {
        basis: ["x1-", "x5", "x2"],
        basisCoefs: ["-2", "0", "1"],
        table: [
        ["-1", "1", "0", "1", "-1", "0", "1"],
        ["0", "0", "0", "-3", "1", "1", "-1"],
        ["0", "0", "1", "4", "-3", "0", "3"]
        ],
        rightSide: ["1", "2", "7"],
        costs: [
        ["0", "0", "0", "0", "1", "0", "-1"],
        ["0", "0", "0", "0", "0", "0", "1"]
        ],
        numValue: "-5",
        MValue: "0",
        messageLog: [],
        keyElemCoords: [1, 2],
        newKeyElemCoords: [-1, -1]
      }
      ],
      answers: [
      {
        points: [
        ["0", "1", "7", "0", "0", "2", "0"]
        ],
        directions: []
      },
      {
        points: [
        ["0", "1", "7", "0", "0", "2"]
        ],
        directions: []
      },
      {
        points: [
        ["-1", "7", "0"],
        ["1", "2", "3"],
        ["1", "2", "3"]
        ],
        directions: [["1", "2", "3"],["1", "2", "3"]]
      }
      ]
    };

    $("#setProblem").toggle();

    problemManager = initializeProblemManager(HARDCODED_RESPONSE);
    stepSwitcher = initializeStepSwitcher(problemManager);
  });
});


// Make JS-object of the problem the user entered
function getProblemInfo(problem) {
  problem.type = $("#type").val();
  problem.numVars = $("#numVars").val();
  problem.numRestrictions = $("#numRestrictions").val();

  problem.zfunc = [];
  $("#zTable tr td input").each(function() {
    problem.zfunc.push($(this).val());
  });

  problem.restrictions = [];
  $("#restrictTable tr").each(function() {
    restrRow = [];
    $("td", this).each(function() {
      if($(this).children().first().is("select")) {
        restrRow.push($("select", this).val());
      }
      else {
        restrRow.push($("input", this).val());
      }
    })
    problem.restrictions.push(restrRow);
  });

  problem.isPositive = [];
  $("#checkPositive input").each(function() {
    problem.isPositive.push($(this).is(':checked'));
  });

  return problem;
}