package com.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
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
				 maxRequestSize=1024*1024*10) // 10MB
public class ReimbursementServlet extends HttpServlet implements ServletInterface{
	private static final long serialVersionUID = 1L;
	EmployeeServlet employeeServlet;
	
	private final String url = "./views/employee_view.html";
	private ArrayList<String> params = new ArrayList<>();
	private String fullUrl;
	ServletHelper servletHelper = new ServletHelper();
	
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

		if (servletHelper.getAttributes(session) != null) fullUrl = url + ((servletHelper.getAttributes(session).length() <= 1) ? "" : "?" + servletHelper.getAttributes(session));
		if (params.size() > 0) {
			fullUrl += ((servletHelper.getAttributes(session).length() <= 1) ? "?" + servletHelper.getParams(this, session, true) : "&" + servletHelper.getParams(this, session, true));
		}
		System.out.println("fullUrl = " + fullUrl);
		
		if (session.getAttribute("logged_in") != null) {
			servletHelper.printAttributes("RS#GET: ", session);
			response.sendRedirect(fullUrl);
		} else {
			System.out.println("Employee Is Not Logged In!");
			System.out.println("--Redirecting to EmployeeServlet");
			new EmployeeServlet().doGet(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		PrintWriter output = response.getWriter();
		
		HttpSession session = request.getSession(false);

		if (session.getAttribute("logged_in") != null) {
			servletHelper.printAttributes("RS#POST: ", session);
		} else {
			System.out.println("Employee Is Not Logged In!");
			System.out.println("--Redirecting to EmployeeServlet");
			new EmployeeServlet().doGet(request, response);
			return;
		}

		ReimbursementService reimbursementService = new ReimbursementService();
		
		String expense = request.getParameter("expense");
		String source = request.getParameter("source");
		String amountString = request.getParameter("amount");
		double amount = reimbursementService.convertAmountToDouble(amountString);
		
		String comments = request.getParameter("comments");
		if (comments == null || comments.length() == 0) {
			comments = "null";
		}

		Reimbursement reimbursement = new Reimbursement(expense, source, amount, comments);

	    Part filePart = request.getPart("image");
	    if (filePart != null) System.out.println("SUBMITTED NAME: " + "`" + filePart.getSubmittedFileName() + "`");
	    String fileName = null;
	    InputStream fileContent = null;
	    if (filePart != null && (filePart.getContentType().contains("png") 
	    		|| filePart.getContentType().contains("jpg") || filePart.getContentType().contains("jpeg"))) {
	    	System.out.println("content type: " + filePart.getContentType());
	    	System.out.println("size: " + filePart.getSize());
	    	System.out.println("name: " + filePart.getName());
	    	fileContent = filePart.getInputStream();
	    	fileName = Paths.get(getTheSubmittedFileName(filePart)).toString();
	    	System.out.println("File Name: " + fileName);
	    	
	    	Image image = new Image(fileName, fileContent);
	    	System.out.println("IN ReimbursementServlet: " + reimbursement);
	    	System.out.println("IN ReimbursementServlet: " + image);
	    	
	    	if (reimbursementService.addReimbursement(reimbursement))
	    		reimbursementService.addImage(image);

	    	System.out.println("The Request Contained An Image File.");
	    } else {
	    	System.out.println("IN ReimbursementServlet: " + reimbursement);
	    	reimbursementService.addReimbursement(reimbursement);
	    	System.out.println("The Request Does NOT Contain An Image File!");
	    }
	    
	    String submissionResponse = reimbursementService.getResponse();

    	servletHelper.addParam(params, "submission_response", submissionResponse);

    	String responseType = reimbursementService.getButtonType();
    	servletHelper.addParam(params, "submission_response_type", responseType);
    	
	    fullUrl = servletHelper.getFullUrl(this, session);
	    System.out.println("fullUrl: " + fullUrl);
	    response.sendRedirect(fullUrl);
	}

	@Override
	public synchronized ArrayList<String> getParams() {
		return params;
	}
	@Override
	public synchronized String getUrl() {
		return url;
	}
	
}
