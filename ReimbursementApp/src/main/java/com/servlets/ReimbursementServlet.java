package com.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import employees.Employee;
import reimbursements.Reimbursement;
import services.EmployeeService;
import services.ReimbursementService;

@WebServlet("/expenses")
@MultipartConfig
public class ReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// location to store file uploaded
	
    public ReimbursementServlet() {
        super();
    }
    
    private static String getTheSubmittedFileName(Part part) {
    	System.out.println("part.getHeader(\"content-disposition\"): " + part.getHeader("content-disposition").join(""));
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
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
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter output = response.getWriter();
		
		//EmployeeService employeeService = new EmployeeService();
		ReimbursementService reimbursementService = new ReimbursementService();
		
		String rItem = request.getParameter("item");
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
		
		Reimbursement reimbursement = new Reimbursement(rItem, description, amount, comments);
		reimbursementService.addReimbursement(reimbursement);
		
		//String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
	    Part filePart = request.getPart("image"); // Retrieves <input type="file" name="file">
	    String fileName = null;
	    InputStream fileContent = null;
	    if (filePart != null) {
	    	System.out.println("File is not empty!");
	    	fileContent = filePart.getInputStream();
	    	fileName = Paths.get(getTheSubmittedFileName(filePart)).toString(); // MSIE fix.
	    	//fileName = filePart.getSubmittedFileName();
	    	System.out.println("File name: " + fileName);
	    	System.out.println(fileContent.toString());
	    } else System.out.println("File IS empty.");

		response.sendRedirect("http://localhost:8080/Reimbursements/ReimbursementServlet");
	}

}
