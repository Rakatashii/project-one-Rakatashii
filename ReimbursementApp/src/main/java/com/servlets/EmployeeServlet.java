package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.EmployeeDAO;
import employees.Employee;
import services.EmployeeService;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/login")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static EmployeeService employeeService = new EmployeeService();
	private Employee loggedInEmployee = null;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = response.getWriter();
		output.write("GET");
		
		response.sendRedirect("http://localhost:8080/Reimbursements/home.html");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		
		if (employeeService.verifyLoginInfo(username, password)) {
			loggedInEmployee = employeeService.getLoggedInEmployee();
			
			//request.getRequestDispatcher("ReimbursementServlet").forward(request,  response);
			response.sendRedirect("http://localhost:8080/Reimbursements/employee_view.html");
			if (loggedInEmployee != null) {
				//System.out.println("employee's username is " + loggedInEmployee.getUsername());
				//System.out.println("employee's password is " + loggedInEmployee.getPassword());
			}
		} else {
			response.sendRedirect("http://localhost:8080/Reimbursements/EmployeeServlet");
		}
		//request.getRequestDispatcher("http://localhost:8080/Reimbursements/employee_view.html").forward(request, response);
		
		//Employee newEmployee = new Employee(1, "Jayson", "skjdcn", "jay", "casper", 0);
		//new EmployeeDAO().addEmployee(newEmployee);
	}

}
