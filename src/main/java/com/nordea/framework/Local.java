package com.nordea.framework;

import com.nordea.pages.Page;
import com.nordea.workflows.Workflow;

public class Local {

	private static Workflow wkf;

	public static Workflow workflows() {
		System.out.println("Inside LocalWKflow");
		wkf = new Workflow();
		return wkf;
	}

	private static Page pg;

	public static Page pages() {
		System.out.println("Inside LocalPage");
		pg = new Page();
		return pg;
	}

}
