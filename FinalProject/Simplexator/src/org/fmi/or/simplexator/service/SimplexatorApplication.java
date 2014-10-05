package org.fmi.or.simplexator.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class SimplexatorApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(SimplexatorService.class);
		return classes;
	}

}