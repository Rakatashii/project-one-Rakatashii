package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import employees.Employee;
import reimbursements.Reimbursement;
import services.EmployeeService;
import services.ReimbursementService;

public class ReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ReimbursementServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("ReimbursementServlet: " + (String) session.getAttribute("username"));
			System.out.println("ReimbursementServlet: " + (String) session.getAttribute("password"));
		} 
		response.sendRedirect("http://localhost:8080/Reimbursements/employee_view.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = response.getWriter();
		
		//EmployeeService employeeService = new EmployeeService();
		ReimbursementService reimbursementService = new ReimbursementService();
		
		String item = request.getParameter("item");
		String description = request.getParameter("description");
		String amountString = request.getParameter("amount");
		double amount = 0.0;
		if (amountString != null && amountString.length() > 0 && amountString.matches("[//d]+")) 
			amount = Double.parseDouble(amountString);
		String comments = request.getParameter("comments");
		if (comments == null || comments.length() == 0) {
			comments = "null";
		}
		//Employee loggedInEmployee = employeeService.getLoggedInEmployee();
		
		Reimbursement reimbursement = new Reimbursement(item, description, amount, comments);
		reimbursementService.addReimbursement(reimbursement);
		response.sendRedirect("http://localhost:8080/Reimbursements/ReimbursementServlet");
	}

}
