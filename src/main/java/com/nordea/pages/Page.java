package com.nordea.pages;

import java.util.HashMap;
import java.util.Map;

public class Page {

	private Map<Class<?>, Object> pages = new HashMap<Class<?>, Object>();

	@SuppressWarnings("unchecked")
	public <T> T getpage(Class<T> page) throws InstantiationException, IllegalAccessException {
		Object pageobj = null;
		System.out.println("Inside Page");
		if (pageobj == null) {
			pages.put(page, page.newInstance());
		}
		pageobj = pages.get(page);
		return (T) pageobj;

	}

}
