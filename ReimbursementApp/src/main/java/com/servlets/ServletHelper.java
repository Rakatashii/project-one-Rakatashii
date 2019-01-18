package com.servlets;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletHelper {
	String fullUrl;
	
	public synchronized void printAttributes(String methName, HttpSession hs) {
		System.out.println(methName + " logged: " + (String) hs.getAttribute("logged_in"));
		System.out.println(methName + " username: " + (String) hs.getAttribute("username"));
		System.out.println(methName + " password: " + (String) hs.getAttribute("password"));
		System.out.println(methName + " remember: " + (String) hs.getAttribute("remember_employee"));
		if (hs.getAttribute("home") != null) System.out.println(methName + " home: " + (String) hs.getAttribute("home"));
		if (hs.getAttribute("logout") != null) System.out.println(methName + " logout: " + (String) hs.getAttribute("logout"));
		if (hs.getAttribute("contact") != null) System.out.println(methName + " contact: " + (String) hs.getAttribute("contact"));
	}
	public synchronized <T extends ServletInterface> String getParams(T servlet, boolean append) {
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
	public synchronized String getAttributes(HttpSession session) {
		String attrString = "";
		Enumeration<String> attributeNames;
		if (session != null) attributeNames = session.getAttributeNames();
		else return "";
		int i = 0;
		if (attributeNames != null) {
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
		}
		return attrString;
	}
	public synchronized <T extends ServletInterface> String getFullUrl(T servlet, HttpSession session) {
		ArrayList<String> params = servlet.getParams();
		String url = servlet.getUrl();
		fullUrl = url;
		System.out.println("fullUrl(1) = " + fullUrl);
		if (session != null) fullUrl += ((getAttributes(session).length() <= 1) ? "" : "?" + getAttributes(session));
		if (params != null && params.size() > 0) {
			fullUrl += ((session != null && getAttributes(session).length() <= 1) ? "?" + getParams(servlet, true) : "&" + getParams(servlet, true));
		}
		System.out.println("fullUrl(1) = " + fullUrl);
		return fullUrl;
	}
	public synchronized <T extends ServletInterface> void clearSession(T servlet, HttpSession session) {
		Enumeration<String> attributeNames = session.getAttributeNames();
		int i = 0;
		while (attributeNames.hasMoreElements()) {
			String name = attributeNames.nextElement();
			session.removeAttribute(name);
		}
	}
	public synchronized void addParam(List<String> params, String key, String value) {
		String new_param = key + "=" + value;
		// Check For Duplicates And Update New To New KVString
		boolean duplicateKV = false;
		for (String p : params) {
			if (key.equals(p.split("=")[0])) {
				duplicateKV = true;
				System.out.println("duplicate param!");
				System.out.println("---old param = " + p);
				System.out.println("-----new key = " + key);
				System.out.println("-----new val = " + value);
				params.remove(p);
				params.add(new_param);
				return;
			}
		}
		if (!duplicateKV) params.add(new_param);
	}
	
	public synchronized void printParameters(HttpSession session) {
		Enumeration<String> attributeNames;
		if (session != null) attributeNames = session.getAttributeNames();
		else {
			return;
		}
		if (attributeNames != null) {
			System.out.println("Custom Parameters:");
			while (attributeNames.hasMoreElements()) {
				String name = attributeNames.nextElement();
				String value = (String) session.getAttribute(name);
				if (value != null) {
					System.out.println("PName: " + name + " - Value: " + value);
				}
			}
		} 
	}
	public synchronized void printRequestParameters(HttpServletRequest request) {
		Enumeration<String> requestParameterNames;
		if (request != null) requestParameterNames = request.getParameterNames();
		else {
			return;
		}
		if (requestParameterNames != null) {
			System.out.println("Request Parameters:");
			while (requestParameterNames.hasMoreElements()) {
				String name = requestParameterNames.nextElement();
				if (name != null) System.out.print("RPName: " + name); 
				String value = (String) request.getParameter(name);
				if (value != null) {
					System.out.println(" - Value: " + value + "\n");
				}
			}
		} 
	}
	public synchronized void printRequestAttributes(HttpServletRequest request) {
		Enumeration<String> requestAttributeNames;
		if (request != null) requestAttributeNames = request.getAttributeNames();
		else {
			return;
		}
		if (requestAttributeNames != null) {
			System.out.println("Attributes:");
			while (requestAttributeNames.hasMoreElements()) {
				String name = requestAttributeNames.nextElement();
				String value = (String) request.getAttribute(name);
				if (value != null) {
					System.out.println("PName: " + name + " - Value: " + value);
				}
			}
		} 
	}
}
