bool unboundnessCriterion(vector<vector<Fraction>> &table, int j, int sizeB)
{
	for(int i=0; i<sizeB; ++i)
	{
		if(table[i][j] >= 0)
			return false;
	}
	return true;
}

int optimalityCriterion(vector<vector<Fraction>> &table)
// @return: i = index of variable that will be excluded from basis;
// 			-1 = criterion is successfull - optimality reached, values of all variables are non-negative.
{
	int index = -1;
	Fraction minValue = 0;
	Fraction minValueM = 0;
	for(int i=1; i<table[table.size()-1].size() - 1; ++i)
	{
		if(table[table.size()-1][i] <= 0
			&&
			(table[table.size()-1][i] < minValueM
				||
				(table[table.size()-1][i] == minValueM && table[table.size()-2][i] < minValue)
				)
			)
		{
			index = i;
			minValue = table[table.size()-2][i];
			minValueM = table[table.size()-1][i];
		}
	}
	return index;
}

Fraction rectangleRule(pair<int,int> keyElementCoords, int i, int j, vector<vector<Fraction> > &table)
{
	int p = keyElementCoords.first;
	int q = keyElementCoords.second;
	Fraction keyElement = table[p][q];

	if(p==i && q==j)
		return Fraction(1);
	if(p==i)
		return table[i][j] / table[p][q];
	if(q==j)
		return Fraction(0);

	return table[i][j] - table[i][q]*table[p][j]/keyElement;
}

Answer simplexMethod(const AbstractProblem &Problem)
{
	// 1. initialize
	vector<vector<Fraction>> table(Problem.basis.size() + 2, vector<Fraction(Problem.varCount + 1));

	___fillCoefficientsAtTop();
	___fillCoefficientsLeft();

	// 2. initial fill
	for(int i=0; i<Problem.varCount; ++i)
	{
		for(int j=0; j<Problem.varCount; ++j)
		{
			table[i][j] = Problem.restrictions[i][j].coef;
			___fillCell(i,j); // OR: ___fillInitialMatrix();
		}
	}

	// 3. evaluate variables' edges
	for(int j=0; j<table[0].size()-1; ++j)
	{
		Fraction sumC = 0, sumM = 0;
		if(Problem.zfunction[j].coef == M)
			sumM += Problem.zfunction[j].coef;
		else
			sumC += Problem.zfunction[j].coef;
		for(int i=0; i<table.size()-2; ++i)
		{
			if(Problem.zfunction[Problem.basis[i]].coef == M)
				sumM -= Problem.zfunction[Problem.basis[i]].coef;
			else
				sumC -= Problem.zfunction[Problem.basis[i]].coef;
		}
		table[table.size()-2][j] = sumC;
		table[table.size()-1][j] = sumM;
		___showEvaluatingVarEdge(j);
	}

	// 4. check criteria
	int indexOptimal = optimalityCriterion(table);
	while(indexOptimal != -1 && unboundnessCriterion(table,indexOptimal,Problem.basis.size()) == false)
	{
		vector<vector<Fraction>> oldTable = table;
		// 5. change basis - 1 in, 1 out
		int newBaseVar = indexOptimal;
		int indexOutVar = 0;
		int minRel = INT_MAX;
		for(int i=0; i<Problem.varCount; ++i)
		{
			if(oldTable[i][newBaseVar] > 0 && minRel > oldTable[i][oldTable[i].size()-1] / oldTable[i][newBaseVar])
			{
				minRel = oldTable[i][oldTable[i].size()-1] / oldTable[i][newBaseVar];
				indexOutVar = i;
			}
		}
		pair<int,int> keyElementCoords(indexOutVar, newBaseVar);
		Fracton keyElement = oldTable[indexOutVar][newBaseVar];
		___highlightKeyElement();
		___showTheChangeInBasis();
		___drawNewTable();

		// 6. fill key element's row
		for(int j=0; j<table[keyElementCoords.first].size(); ++j)
			table[keyElementCoords.first][j] = oldTable[keyElementCoords.first][j] / keyElement;
		___fillRowOfKeyElement();

		// 7. fill base elements' columns
		// TODO !!!
		___fillColumnsOfBaseVariables();

		// 8. fill rest of the table by rectangle rule
		for(int i=0; i<table.size(); ++i)
		{
			for(int j=0; j<table[i].size(); ++j)
			{
				table[i][j] = rectangleRule(keyElementCoords, i, j, oldTable);
				___showRectangleRule();
			}
		}

		indexOptimal = optimalityCriterion(table);
	}

// check criterions

	// 9. process the answer - a) write it down, b) is it unique, c) find others, d) K or M Problem
	// TODO: who will post-process it to final solution?
	Answer result(M,vector<Fraction>(Problem.varCount,0));

	for(int j=0; j<table[table.size()-1].size(); ++j)
	{
		if(table[table.size()-1][j] != 0)
		{
			result.type = K;
			break;
		}
	}
	___printConclusionAboutMOrK();

	for(int i=0; i<table.size(); ++i)
		result.x[Problem.basis[i]] = table[i][table.size()-1];
	___showOptimalSolutionFound();

// TODO: check for and return ALL solutions !!!

//	for(int j=0; j<table.size(); ++j)
//	{
//		if(table[table.size()-2][j] == 0 && table[table.size()-1][j] == 0 /* i ne e ot bazisa, i ne e POS ili NEG*/)
//		{
//			___declareMoreSolutions();
//			for(int i=0, int positiveExists = false, Fraction minRel = INT_MAX, int relIndex = -1; i<table.size()-2; ++i)
//			{
//				if(table[i][j] > 0)
//				{
//					positiveExists = true;
//					if(minRel > table[i][table[i].size()-1])
//					{
//						minRel =
//					}
//				}
//			}
//
//			result.solution = ;
//			___showEdge2OrDirection(D_or_Y);
//			result.y = ;
//		}
//		___showMoreSolutions();
//	}

	return result;
}

enum ProblemType { K, M };

class Answer
{
	ProblemType type;
	vector<Fraction> x;
	//vector y;

	Answer(ProblemType t = M,
		vector<Fraction> _x = vector<Fraction>(Problem.varCount,0)) : type(t), solution(s), x(_x) {}

	//bool isUnique() const { return y.empty(); }
};
