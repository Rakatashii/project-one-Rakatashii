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
	
	Employee currentEmployee;
	
    public ManagerResolve() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("GET");
		servletHelper.printRequestParameters(request);
		servletHelper.printRequestAttributes(request);
		
		if (request.getParameter("go_back") != null) {
			request.removeAttribute("approved");
			request.removeAttribute("denied");
			response.sendRedirect("../ManagerSelect");
			return;
		} else if (request.getParameter("goto_view_reimbursements") != null) {
			request.removeAttribute("approved");
			request.removeAttribute("denied");
			doPost(request, response);
			return;
		}
		
		if (currentEmployee != null) {
			EmployeeService employeeService = new EmployeeService();
			if (request.getParameter("approved") != null) {
				String[] fields = request.getParameter("approved").split("_");
				int rid = Integer.parseInt(fields[0]);
				employeeService.approve(currentEmployee.getEmployeeID(), rid);
			} else System.out.println("No Parameter \"Approved\"");
			if (request.getParameter("denied") != null) {
				String[] fields = request.getParameter("denied").split("_");
				int rid = Integer.parseInt(fields[0]);
				employeeService.deny(currentEmployee.getEmployeeID(), rid);
			} else System.out.println("No Parameter \"Denied\"");
		}
		if (currentEmployee != null) {
			EmployeeService employeeService = new EmployeeService();
			if (request.getAttribute("approved") != null) {
				String[] fields = ((String) request.getAttribute("approved")).split("_");
				int rid = Integer.parseInt(fields[0]);
				employeeService.approve(currentEmployee.getEmployeeID(), rid);
			} else System.out.println("No Parameter \"Approved\"");
			if (request.getAttribute("denied") != null) {
				String[] fields = ((String) request.getAttribute("denied")).split("_");
				int rid = Integer.parseInt(fields[0]);
				employeeService.deny(currentEmployee.getEmployeeID(), rid);
			} else System.out.println("No Parameter \"Denied\"");
		}
		
		HttpSession session = request.getSession(false);
		
		if (session != null && session.getAttribute("manager_logged_in") != null) {
			servletHelper.printAttributes("MR#GET: ", session);
			fullUrl = servletHelper.getFullUrl(this, session);
			//doPost(request, response);
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
		
		System.out.println("POST");
		servletHelper.printRequestParameters(request);
		servletHelper.printRequestAttributes(request);
		
		if (currentEmployee != null) {
			EmployeeService employeeService = new EmployeeService();
			if (request.getParameter("approved") != null) {
				String[] fields = request.getParameter("approved").split("_");
				int rid = Integer.parseInt(fields[0]);
				employeeService.approve(currentEmployee.getEmployeeID(), rid);
			} else System.out.println("No Parameter \"Approved\"");
			if (request.getParameter("denied") != null) {
				String[] fields = request.getParameter("denied").split("_");
				int rid = Integer.parseInt(fields[0]);
				employeeService.deny(currentEmployee.getEmployeeID(), rid);
			} else System.out.println("No Parameter \"Deny\"");
		} 
		if (currentEmployee != null) {
			EmployeeService employeeService = new EmployeeService();
			if (request.getAttribute("approved") != null) {
				String[] fields = ((String) request.getAttribute("approved")).split("_");
				int rid = Integer.parseInt(fields[0]);
				employeeService.approve(currentEmployee.getEmployeeID(), rid);
			} else System.out.println("No Parameter \"Approved\"");
			if (request.getAttribute("denied") != null) {
				String[] fields = ((String) request.getAttribute("denied")).split("_");
				int rid = Integer.parseInt(fields[0]);
				employeeService.deny(currentEmployee.getEmployeeID(), rid);
			} else System.out.println("No Parameter \"Denied\"");
		}
		
		HttpSession session = request.getSession(false);
		
		ArrayList<Reimbursement> reimbursements = new ArrayList<>();
		
		System.out.println("in MR#POST");
		if (session.getAttribute("selected_id") != null || request.getParameter("selected_id") != null) {
			String selectedID = ((request.getParameter("selected_id") == null) ? (String) session.getAttribute("selected_id") : request.getParameter("selected_id"));
			session.setAttribute("selected_id", selectedID);
			System.out.println("selected_id = " + selectedID);
			int id = Integer.parseInt(selectedID);
			System.out.println("int = " + id);
			currentEmployee = new ManagerService().getSelectedEmployee(id);
			if (currentEmployee != null) {
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
