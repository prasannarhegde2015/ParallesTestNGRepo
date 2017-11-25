package com.nordea.workflows;

import java.util.HashMap;
import java.util.Map;

public class Workflow {

	private Map<Class<?>, Object> workflows = new HashMap<Class<?>, Object>();

	@SuppressWarnings("unchecked")
	public <T> T getworkflow(Class<T> workflow) throws InstantiationException, IllegalAccessException {
		Object wkflwobj = null;
		// System.out.println("Inside Workflow")
		if (wkflwobj == null) {
			workflows.put(workflow, workflow.newInstance());
		}
		wkflwobj = workflows.get(workflow);
		return (T) wkflwobj;

	}

}
