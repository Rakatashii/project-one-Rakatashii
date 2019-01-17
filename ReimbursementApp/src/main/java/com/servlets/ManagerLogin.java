package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managers.Manager;
import services.ManagerService;

@WebServlet("/views/manager_login.html/")
@MultipartConfig
public class ManagerLogin extends HttpServlet implements ServletInterface{
	private static final long serialVersionUID = 1L;
	private static ManagerService managerService = new ManagerService();
	private Manager loggedInManager = null;
	ManagerSelect managerSelect;
	
	protected final static String url = "/Reimbursements/views/manager_login.html";
	protected ArrayList<String> params = new ArrayList<>();
	private String fullUrl;
	ServletHelper servletHelper = new ServletHelper();
	
	private String username, password, rememberManager, home, logout, contact, managerLoggedIn;
	
    public ManagerLogin() {
        managerSelect = new ManagerSelect();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(true);
		fullUrl = servletHelper.getFullUrl(this, session);
		System.out.println("fullUrl = " + fullUrl);
		
		if (request.getAttribute("submission_response") != null) {
			System.out.println("login_error? " + request.getAttribute("submission_response"));
			servletHelper.addParam(params, "submission_response", (String) request.getAttribute("submission_response"));
			if (request.getAttribute("submission_response_type") != null) {
				servletHelper.addParam(params, "submission_response_type", (String) request.getAttribute("submission_response_type"));
				fullUrl = url + "?" + servletHelper.getParams(this, false);
				session.invalidate();
				this.params.clear();
				response.sendRedirect(fullUrl);
				return;
			}
		} else System.out.println("managerLoginServlet does not have submission_response");

		servletHelper.printAttributes("ML#GET(Top)", session);
		
		if (session.getAttribute("home") != null) {
			System.out.println("->HOME");
			session.removeAttribute("home");
			
			if (session.getAttribute("remember_manager") != null && session.getAttribute("remember_manager").equals("true")){
				if (session.getAttribute("manager_logged_in") != null) {
					if (session.getAttribute("logout") != null) session.setAttribute("logout", null);
					managerSelect.doGet(request, response);
					return;
				}
			} else {
				session.setAttribute("logout", "true");
			}
		}
		if (session.getAttribute("logout") != null) {
			System.out.println("->LOGOUT");

			servletHelper.clearSession(this, session);
			
			session.invalidate();
			session = request.getSession(true);
			servletHelper.printAttributes("ML#GET(Invalid): ", session);
			fullUrl = servletHelper.getFullUrl(this, session);
			ManagerService.logoutManager();
			response.sendRedirect(fullUrl);
			return;
		} else if (session.getAttribute("remember_manager") == null || session.getAttribute("remember_manager").equals("false")) {
			session.invalidate();
			session = request.getSession(true);
			servletHelper.printAttributes("ML#GET(Invalid): ", session);
			fullUrl = servletHelper.getFullUrl(this, session);
			ManagerService.logoutManager();
			response.sendRedirect(fullUrl);
			return;
		}

		if (session.getAttribute("remember_manager") != null) session.setAttribute("remember_manager", null);
		System.out.println("--Redirecting To Login");
		fullUrl = servletHelper.getFullUrl(this, session);
		response.sendRedirect(fullUrl);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(true);
		
		if (session.getAttribute("username") == null && session.getAttribute("password") == null) {
			username = request.getParameter("username");
			password = request.getParameter("password");
			rememberManager = request.getParameter("remember_manager");
			if (rememberManager == null) {
				rememberManager = "false";
			}
		} else {
			username = (String) session.getAttribute("username");
			password = (String) session.getAttribute("password");
			rememberManager = (String) session.getAttribute("remember_manager");
		}
		
		home = request.getParameter("home");
		if (home != null) {
			home = "true";
			session.setAttribute("home", home);
			doGet(request, response);
			return;
		} 
		logout = request.getParameter("logout");
		if (logout != null) {
			logout = "true";
			session.setAttribute("logout", logout);
			doGet(request, response);
			return;
		} 
		
		if (managerService.verifyLoginInfo(username, password)) {
			if (username != null && password != null) { 
				managerLoggedIn = "true";
				session.setAttribute("manager_logged_in", "true");
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				session.setAttribute("remember_manager",  rememberManager);
				
				servletHelper.printAttributes("ML#POST: ", session);
			}
			
			System.out.println("Manager Verified");
			managerSelect.doGet(request, response);

			return;
		} else {
			System.out.println("Unable To Verify Manager With Username " + username + " And Password " + password);
			session.setAttribute("remember_manager",  "false");
			response.sendRedirect(servletHelper.getFullUrl(this, session));
		}
	}

	@Override
	public ArrayList<String> getParams() {
		return params;
	}

	@Override
	public String getUrl() {
		return url;
	}
	
	void printParameters(HttpSession session) {
		Enumeration<String> attributeNames;
		if (session != null) attributeNames = session.getAttributeNames();
		else {
			return;
		}
		if (attributeNames != null) {
			System.out.println("PARAMETERS:");
			while (attributeNames.hasMoreElements()) {
				String name = attributeNames.nextElement();
				String value = (String) session.getAttribute(name);
				if (value != null) {
					System.out.println("Name: " + name + " - Value: " + value);
				}
			}
		} 
	}
}
