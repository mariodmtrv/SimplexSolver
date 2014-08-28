package org.fmi.or.simplexator.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

@Path("/solver")
public class SolutionController {

	@Context
	ServletContext context;

	@POST
	@Path("/solve-problem")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SolutionStep> produceStepList() {
		return null;
	}

	@GET
	@Path("/filter-problems")
	public void filterProblems(String request) {
		/*
		 * JsonElement jelement = new JsonParser().parse(request); JsonObject
		 * jobject = jelement.getAsJsonObject(); jobject =
		 * jobject.getAsJsonObject("data"); JsonArray jarray =
		 * jobject.getAsJsonArray("translations");
		 */

	}
}
