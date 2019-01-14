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

import dao.EmployeeDAO;
import employees.Employee;
import services.EmployeeService;

@WebServlet("/login")
@MultipartConfig
public class EmployeeServlet extends HttpServlet implements ServletInterface {
	private static final long serialVersionUID = 1L;
	private static EmployeeService employeeService = new EmployeeService();
	private Employee loggedInEmployee = null;
	ReimbursementServlet reimbursementServlet;
	
	private final String url = "./views/home.html";
	private ArrayList<String> params = new ArrayList<>();
	private String fullUrl;
	ServletHelper servletHelper = new ServletHelper();
	
	private String username, password, remember, home, logout, contact, loggedIn;
	
    public EmployeeServlet() {
    	reimbursementServlet = new ReimbursementServlet();
    }
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(true);
		fullUrl = servletHelper.getFullUrl(this, session);
		//System.out.println("fullUrl = " + fullUrl);

		servletHelper.printAttributes("ES#GET(Top)", session);
		if (session.getAttribute("home") != null) {
			System.out.println("--home = " + session.getAttribute("home"));
			session.removeAttribute("home");
			
			if (session.getAttribute("remember_employee") != null && session.getAttribute("remember_employee").equals("true")){
				if (session.getAttribute("logged_in") != null) {
					if (session.getAttribute("logout") != null) session.setAttribute("logout", null);
					reimbursementServlet.doGet(request, response);
					return;
				}
			} else {
				session.setAttribute("logout", "true");
			}
		}
		if (session.getAttribute("logout") != null) {
			System.out.println("--logout = " + session.getAttribute("logout"));

			servletHelper.clearSession(this, session);
			
			session.invalidate();
			session = request.getSession(true);
			servletHelper.printAttributes("ES#GET(Invald): ", session);
			fullUrl = servletHelper.getFullUrl(this, session);
			EmployeeService.logoutEmployee();
			response.sendRedirect(fullUrl);
			return;
		} else if (session.getAttribute("remember_employee") == null || session.getAttribute("remember_employee").equals("false")) {
			session.invalidate();
			session = request.getSession(true);
			servletHelper.printAttributes("ES#GET(Invalid): ", session);
			fullUrl = servletHelper.getFullUrl(this, session);
			EmployeeService.logoutEmployee();
			response.sendRedirect(fullUrl);
			return;
		}

		if (session.getAttribute("remember_employee") != null) session.setAttribute("remember_employee", null);
		System.out.println("--Redirecting back to home.html");
		fullUrl = servletHelper.getFullUrl(this, session);
		response.sendRedirect(fullUrl);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(true);
		
		if (session.getAttribute("username") == null && session.getAttribute("password") == null) {
			System.out.println("Requesting Parameters...");
			username = request.getParameter("username");
			password = request.getParameter("password");
			remember = request.getParameter("remember_employee");
			if (remember == null) {
				remember = "false";
			}
		} else {
			System.out.println("Getting Attributes From Session...");
			username = (String) session.getAttribute("username");
			password = (String) session.getAttribute("password");
			remember = (String) session.getAttribute("remember_employee");
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
		
		if (employeeService.verifyLoginInfo(username, password)) {
			if (username != null && password != null) { 
				loggedIn = "true";
				session.setAttribute("logged_in", "true");
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				session.setAttribute("remember_employee",  remember);
				
				servletHelper.printAttributes("ES#POST: ", session);
			}
			
			System.out.println("Employee Verified");
			reimbursementServlet.doGet(request, response);
		} else {
			System.out.println("Unable To Verify Employee With Username " + username + " And Password " + password);
			session.setAttribute("remember_employee",  "false");
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
}
