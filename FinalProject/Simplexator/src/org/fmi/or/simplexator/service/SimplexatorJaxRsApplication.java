package org.fmi.or.simplexator.service;

import org.restlet.Context;
import org.restlet.ext.jaxrs.JaxRsApplication;

public class SimplexatorJaxRsApplication extends JaxRsApplication {

	public SimplexatorJaxRsApplication(Context context) {
		super(context);
		this.add(new SimplexatorApplication());
	}
}