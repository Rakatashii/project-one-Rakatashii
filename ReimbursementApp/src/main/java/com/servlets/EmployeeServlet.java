package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static EmployeeService employeeService = new EmployeeService();
	private Employee loggedInEmployee = null;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PrintWriter output = response.getWriter();
		response.sendRedirect("http://localhost:8080/Reimbursements/home.html");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		PrintWriter output = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession(true);
		if (employeeService.verifyLoginInfo(username, password)) {
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			//request.getRequestDispatcher("ReimbursementServlet").forward(request,  response);
			response.sendRedirect("http://localhost:8080/Reimbursements/employee_view.html");
		} else {
			response.sendRedirect("http://localhost:8080/Reimbursements/EmployeeServlet");
		}
	}

}
