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
import images.Image;
import reimbursements.Reimbursement;
import services.EmployeeService;
import services.ReimbursementService;

@WebServlet("/expenses")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB 
				 maxFileSize=1024*1024*5, //4MB
				 maxRequestSize=1024*1024*50) // 10MB
public class ReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ReimbursementServlet() {
        super();
    }
    
    private static String getTheSubmittedFileName(Part part) {
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
		} else {
			System.out.println("Session is null");
			response.sendRedirect("http://localhost:8080/Reimbursements/view/EmployeeServlet");
		}
		response.sendRedirect("http://localhost:8080/Reimbursements/views/employee_view.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		PrintWriter output = response.getWriter();

		ReimbursementService reimbursementService = new ReimbursementService();
		
		String expense = request.getParameter("expense");
		String source = request.getParameter("source");
		String amountString = request.getParameter("amount");
		
		double amount;
		if (amountString != null && amountString.matches("[ \\d]+\\.?[ \\d]+")){
			amount = Double.parseDouble(amountString);
		} else amount = 0.0;
		
		String comments = request.getParameter("comments");
		if (comments == null || comments.length() == 0) {
			comments = "null";
		}

		Reimbursement reimbursement = new Reimbursement(expense, source, amount, comments);
		//reimbursementService.addReimbursement(reimbursement);

	    Part filePart = request.getPart("image");
	    if (filePart != null) System.out.println("SUBMITTED NAME: " + filePart.getSubmittedFileName());
	    String fileName = null;
	    InputStream fileContent = null;
	    if (filePart != null) {
	    	System.out.println("Response Contain Image File!");
	    	fileContent = filePart.getInputStream();
	    	fileName = Paths.get(getTheSubmittedFileName(filePart)).toString();
	    	System.out.println("File Name: " + fileName);
	    	
	    	Image image = new Image(fileName, fileContent);
	    	System.out.println("IN ReimbursementServlet: " + image);
	    	
	    	reimbursementService.addReimbursement(reimbursement);
	    	reimbursementService.addImage(image);
	    } else {
	    	reimbursementService.addReimbursement(reimbursement);
	    	System.out.println("File Does NOT Contain Image File!");
	    }

	    doGet(request, response);
		//response.sendRedirect("http://localhost:8080/Reimbursements/ReimbursementServlet");
	}

}
