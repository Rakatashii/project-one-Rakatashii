package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.EmployeeDAO;
import employees.Employee;
import reimbursements.Reimbursement;
import services.ReimbursementService;

@WebServlet(urlPatterns = { "/views/manager_select.html/" })
@MultipartConfig
public class ManagerSelect extends HttpServlet implements ServletInterface{
	private static final long serialVersionUID = 1L;
	ManagerLogin managerLogin;
	
	protected final static String url = "/Reimbursements/views/404.html";
	private ArrayList<String> params = new ArrayList<>();
	private String fullUrl;
	ServletHelper servletHelper = new ServletHelper();
	
	private Employee currentEmployee;
       
    public ManagerSelect() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(false);
		
		if (session != null && session.getAttribute("manager_logged_in") != null) {
			servletHelper.printAttributes("MS#GET: ", session);
			fullUrl = servletHelper.getFullUrl(this, session);
			
			/* Handle Get All Employees By User/ID Here or Ajax? */
			
			/*
			ReimbursementService reimbursementService = new ReimbursementService();
			ArrayList<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();
			
			String json = new Gson().toJson(reimbursements);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(json);
		    */
			
			EmployeeDAO employeeDAO = new EmployeeDAO();
			ArrayList<Employee> employees = employeeDAO.getAllEmployees();
			
			String json = new Gson().toJson(employees);
			
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.write(json);
			
			response.sendRedirect(fullUrl); // Need? Problems?
			return;
		} else if (session == null || session.getAttribute("manager_logged_in") == null) {
			System.out.println("PARAMS: ");
			servletHelper.printParameters(session);
			fullUrl = ManagerLogin.url + "?" + servletHelper.getParams(this, false);

			request.setAttribute("submission_response", "You Must Log In First");
			request.setAttribute("submission_response_type", "login_error");
			
			RequestDispatcher rd = request.getRequestDispatcher("/ManagerLogin");
			rd.forward(request, response);
		} else {
			System.out.println("Session Not NULL && LoggedIN NOT NULL");
			fullUrl = ManagerLogin.url + "?" + servletHelper.getParams(this, false);

			request.setAttribute("submission_response", "You Must Log In");
			request.setAttribute("submission_response_type", "login_error");
			
			RequestDispatcher rd = request.getRequestDispatcher("/ManagerLogin");
			rd.forward(request, response);
		} 

		/*
		HttpSession session = request.getSession();
		response.sendRedirect(servletHelper.getFullUrl(this, session));
		*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(false);
		
		if (request.getParameter("selected_employee_id") != null) {
			//currentEmployee = new Employee(Integer.parseInt(""))
			// Need Some way to set the selected id - otherwise, just going to get "selected_employee_id"
		}
		
		//TODO finish rows in JS
		/*
		ReimbursementService reimbursementService = new ReimbursementService();
		ArrayList<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();
		
		String json = new Gson().toJson(reimbursements);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	    */
	    
		//fullUrl = servletHelper.getFullUrl(this,  session);
		//response.sendRedirect(fullUrl);
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
