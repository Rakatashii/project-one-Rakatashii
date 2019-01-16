package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import managers.Manager;
import services.EmployeeService;

@WebServlet(urlPatterns = { "/views/all_reimbursements.html/" })
public class AllReimbursements extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static String url = "/Reimbursements/views/all_reimbursements.html";
       
    public AllReimbursements() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(url);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // TODO get all reimbursements, get all images - test
		String json = new Gson().toJson(new Manager("Jake", "Dog"));
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
}
