package org.fmi.or.simplexator.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.fmi.or.simplexator.algorithm.converter.EquationSign;
import org.fmi.or.simplexator.algorithm.converter.Fraction;
import org.fmi.or.simplexator.algorithm.converter.MProblem;
import org.fmi.or.simplexator.algorithm.converter.Optimum;
import org.fmi.or.simplexator.algorithm.converter.Problem;
import org.fmi.or.simplexator.algorithm.converter.Restriction;
import org.fmi.or.simplexator.algorithm.converter.Variable;
import org.fmi.or.simplexator.answerqueue.FileGenerator;
import org.fmi.or.simplexator.answerqueue.ProblemConversionQueue;
import org.fmi.or.simplexator.service.serializable.InputProblem;
import org.fmi.or.simplexator.service.serializable.TransformationStep;

@Path("/solve")
public class SimplexatorService {
	
	//@Produces("application/json")
	@POST
	@Path("/get-all-steps")
	@Consumes("application/json")
	public void test(InputProblem inputProblem){
		System.out.println(inputProblem.getProblem());
	}
	public ProblemConversionQueue getSteps() {
		ProblemConversionQueue queue = new ProblemConversionQueue(new Locale(
				"bg", "BG"));
		
		//problem.convertToK(queue);

		/*
		 * List<TransformationStep> steps = new ArrayList<>(); steps.add(new
		 * TransformationStep(queue.getProblemSteps().get(0))); steps.add(new
		 * TransformationStep(queue.getProblemSteps().get(1)));
		 */return queue;
	}

	@GET
	@Produces("application/json")
	@Path("/get-step")
	public TransformationStep getStep() {
		List<Variable> zfunction = new LinkedList<Variable>();
		zfunction.add(new Variable(new Fraction(2), 1));
		zfunction.add(new Variable(new Fraction(1), 2));
		zfunction.add(new Variable(new Fraction(2), 3));

		Vector<Restriction> restrictions = new Vector<Restriction>();
		List<Variable> firstRestr = new LinkedList<Variable>();
		firstRestr.add(new Variable(new Fraction(1), 1));
		firstRestr.add(new Variable(new Fraction(0), 2));
		firstRestr.add(new Variable(new Fraction(-1), 3));
		Restriction first = new Restriction(firstRestr, EquationSign.LTE,
				new Fraction(-1));

		List<Variable> secondRestr = new LinkedList<Variable>();
		secondRestr.add(new Variable(new Fraction(-1), 1));
		secondRestr.add(new Variable(new Fraction(0), 2));
		secondRestr.add(new Variable(new Fraction(-2), 3));
		Restriction second = new Restriction(secondRestr, EquationSign.LTE,
				new Fraction(3));

		List<Variable> thirdRestr = new LinkedList<Variable>();
		thirdRestr.add(new Variable(new Fraction(3), 1));
		thirdRestr.add(new Variable(new Fraction(1), 2));
		thirdRestr.add(new Variable(new Fraction(1), 3));
		Restriction third = new Restriction(thirdRestr, EquationSign.EQ,
				new Fraction(4));
		restrictions.add(first);
		restrictions.add(second);
		restrictions.add(third);

		Optimum optimum = Optimum.MINIMUM;
		Vector<Boolean> hasNegativePart = new Vector<>();
		hasNegativePart.add(true);
		hasNegativePart.add(false);
		hasNegativePart.add(false);
		Problem problem = new Problem(zfunction, restrictions, optimum,
				hasNegativePart);
		problem.convertToK();
		TransformationStep step = new TransformationStep(problem);
		return step;
	}

	/*
	 * @POST
	 * 
	 * @Consumes("application/json") public Response createProduct(Product
	 * product) { String result = "Product created : " + product; return
	 * Response.status(201).entity(result).build(); }
	 */
	@GET
	@Path("/latex")
	@Produces("text/plain")
	public String getLatexContent() {
		return "Latex content";
	}

}