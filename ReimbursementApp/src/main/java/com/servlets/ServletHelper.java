package com.servlets;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

public class ServletHelper {
	String fullUrl;
	
	public void printAttributes(String methName, HttpSession hs) {
		System.out.println(methName + " logged: " + (String) hs.getAttribute("logged_in"));
		System.out.println(methName + " username: " + (String) hs.getAttribute("username"));
		System.out.println(methName + " password: " + (String) hs.getAttribute("password"));
		System.out.println(methName + " remember: " + (String) hs.getAttribute("remember_employee"));
		if (hs.getAttribute("home") != null) System.out.println(methName + " home: " + (String) hs.getAttribute("home"));
		if (hs.getAttribute("logout") != null) System.out.println(methName + " logout: " + (String) hs.getAttribute("logout"));
		if (hs.getAttribute("contact") != null) System.out.println(methName + " contact: " + (String) hs.getAttribute("contact"));
	}
	public <T extends ServletInterface> String getParams(T servlet, HttpSession session, boolean append) {
		ArrayList<String> params = servlet.getParams();
		String paramString = "";
		if (servlet.getParams().size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				if (i == 0) {
					paramString += params.get(i);
				} else {
					paramString += "&" + params.get(i);
				}
			}
		}
		return paramString;
	}
	public String getAttributes(HttpSession session) {
		String attrString = "";
		Enumeration<String> attributeNames = session.getAttributeNames();
		int i = 0;
		while (attributeNames.hasMoreElements()) {
			String name = attributeNames.nextElement();
			String value = (String) session.getAttribute(name);
			if (value != null) {
				if (i == 0) {
					attrString += (name + "=" + value);
				} else {
					attrString += "&" + (name + "=" + value);
				}
				i++;
			}
		}
		return attrString;
	}
	public <T extends ServletInterface> String getFullUrl(T servlet, HttpSession session) {
		ArrayList<String> params = servlet.getParams();
		String url = servlet.getUrl();
		fullUrl = url;
		System.out.println("fullUrl(1) = " + fullUrl);
		fullUrl += ((getAttributes(session).length() <= 1) ? "" : "?" + getAttributes(session));
		if (params != null && params.size() > 0) {
			fullUrl += ((getAttributes(session).length() <= 1) ? "?" + getParams(servlet, session, true) : "&" + getParams(servlet, session, true));
		}
		System.out.println("fullUrl(1) = " + fullUrl);
		return fullUrl;
	}
	public <T extends ServletInterface> void clearSession(T servlet, HttpSession session) {
		Enumeration<String> attributeNames = session.getAttributeNames();
		int i = 0;
		while (attributeNames.hasMoreElements()) {
			String name = attributeNames.nextElement();
			session.removeAttribute(name);
		}
	}
}
