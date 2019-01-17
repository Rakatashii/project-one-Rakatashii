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

import dao.ReimbursementDAO;
import employees.Employee;
import managers.Manager;
import reimbursements.Reimbursement;
import services.EmployeeService;
import services.ReimbursementService;

@WebServlet(urlPatterns = { "/views/all_reimbursements.html/" })
@MultipartConfig
public class AllReimbursements extends HttpServlet implements ServletInterface{
	private static final long serialVersionUID = 1L;
	protected static String url = "/Reimbursements/views/all_reimbursements.html";
	private String fullUrl;
	private ArrayList<String> params = new ArrayList<>();
	ServletHelper servletHelper = new ServletHelper();
       
    public AllReimbursements() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if (session != null && session.getAttribute("logged_in") != null) {
			servletHelper.printAttributes("RS#GET: ", session);
			fullUrl = servletHelper.getFullUrl(this, session);
			response.sendRedirect(fullUrl);

		} else if (session == null || session.getAttribute("logged_in") == null) {
			System.out.println("PARAMS: " + params);
			fullUrl = EmployeeLogin.url + "?" + servletHelper.getParams(this, false);

			request.setAttribute("submission_response", "You Must Log In");
			request.setAttribute("submission_response_type", "login_error");
			
			RequestDispatcher rd = request.getRequestDispatcher("/EmployeeLogin");
			rd.forward(request, response);
		} else {
			System.out.println("Session Not NULL && LoggedIN NOT NULL");
			fullUrl = EmployeeLogin.url + "?" + servletHelper.getParams(this, false);

			request.setAttribute("submission_response", "You Must Log In");
			request.setAttribute("submission_response_type", "login_error");
			
			RequestDispatcher rd = request.getRequestDispatcher("/EmployeeLogin");
			rd.forward(request, response);
		} 
		fullUrl = servletHelper.getFullUrl(this,  session);
		response.sendRedirect(fullUrl);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		ReimbursementService reimbursementService = new ReimbursementService();
		ArrayList<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();
		
		String json = new Gson().toJson(reimbursements);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	    
		fullUrl = servletHelper.getFullUrl(this,  session);
		response.sendRedirect(fullUrl);
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
