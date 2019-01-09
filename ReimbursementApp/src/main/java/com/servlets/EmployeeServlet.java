package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDAO;
import employees.Employee;
import services.EmployeeService;

/**
 * Servlet implementation class CustomerServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static EmployeeService employeeService = new EmployeeService();
	private Employee loggedInEmployee = null;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = response.getWriter();
		output.write("GET");
		response.sendRedirect("http://localhost:8080/Reimbursements/");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		output.write("[ username: " + username + ", ");
		output.write("password: " + password + " ]");
		if (employeeService.verifyLoginInfo(username, password))
			loggedInEmployee = employeeService.getLoggedInEmployee();
		if (loggedInEmployee != null) {
			System.out.println("employee's username is " + loggedInEmployee.getUsername());
			System.out.println("employee's password is " + loggedInEmployee.getPassword());
			/*
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			//request.getRequestDispatcher("https://google.com").forward(request, response);
		} //else request.getRequestDispatcher("http:////localhost:8080//Reimbursements//").forward(request, response);
		else { 
			System.out.println("Employee is null.");
		}
		Employee newEmployee = new Employee(1, "Jayson", "skjdcn", "jay", "casper", 0);
		new EmployeeDAO().addEmployee(newEmployee);
	}

}
