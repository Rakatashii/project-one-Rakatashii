package com.servlets;

import java.io.IOException;
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

import employees.Employee;
import reimbursements.Reimbursement;
import services.EmployeeService;
import services.ManagerService;
import services.ReimbursementService;

@WebServlet(urlPatterns = { "/views/manager_resolve.html/" })
@MultipartConfig
public class ManagerResolve extends HttpServlet implements ServletInterface{
	private static final long serialVersionUID = 1L;
	protected static String url = "/Reimbursements/views/manager_resolve.html";
	private String fullUrl;
	private ArrayList<String> params = new ArrayList<>();
	private ServletHelper servletHelper = new ServletHelper();
	
    public ManagerResolve() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if (session != null && session.getAttribute("manager_logged_in") != null) {
			servletHelper.printAttributes("MR#GET: ", session);
			fullUrl = servletHelper.getFullUrl(this, session);
			response.sendRedirect(fullUrl);
			return;
		} else if (session == null || session.getAttribute("manager_logged_in") == null) {
			System.out.println("PARAMS: ");
			servletHelper.printParameters(session);
			fullUrl = ManagerLogin.url + "?" + servletHelper.getParams(this, false);

			request.setAttribute("submission_response", "You Must Log In");
			request.setAttribute("submission_response_type", "login_error");
			
			RequestDispatcher rd = request.getRequestDispatcher("/ManagerLogin");
			rd.forward(request, response);
			//session.invalidate(); ???
		} else {
			System.out.println("Session Not NULL && LoggedIN NOT NULL");
			fullUrl = ManagerLogin.url + "?" + servletHelper.getParams(this, false);

			request.setAttribute("submission_response", "You Must Log In");
			request.setAttribute("submission_response_type", "login_error");
			
			RequestDispatcher rd = request.getRequestDispatcher("/ManagerLogin");
			rd.forward(request, response);
		} 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		
		Employee employee = null;
		ArrayList<Reimbursement> reimbursements = new ArrayList<>();
		
		if (request.getParameter("selected_id") != null) {
			String selectedID = request.getParameter("selected_id");
			session.setAttribute("selected_id", selectedID);
			System.out.println("selected_id = " + selectedID);
			int id = Integer.parseInt(selectedID);
			employee = new ManagerService().getSelectedEmployee(id);
			if (employee != null) {
				ReimbursementService reimbursementService = new ReimbursementService();
				reimbursements = reimbursementService.getAllReimbursements(id);

				String json = new Gson().toJson(reimbursements);
			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(json);
				//fullUrl = servletHelper.getFullUrl(this,  session);
				//response.sendRedirect(fullUrl);
				//return;
			} else {
				System.out.println("Employee is null");
			}
		} else if (request.getAttribute("selected_id") != null) {
			System.out.println("selected_id = " + request.getAttribute("selected_id"));
		} else {
			System.out.println("selected_id is null");
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
