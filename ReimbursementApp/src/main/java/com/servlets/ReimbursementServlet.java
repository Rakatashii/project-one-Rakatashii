package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		doGet(request, response);
	}

}
