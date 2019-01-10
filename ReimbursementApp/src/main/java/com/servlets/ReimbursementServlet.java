package com.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import employees.Employee;
import reimbursements.Reimbursement;
import services.EmployeeService;
import services.ReimbursementService;

public class ReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";
	
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
		


		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) System.out.println("File Upload Request = true");
		else System.out.println("File Upload Request = false");
		
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }

        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);


        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = getServletContext().getRealPath("")+ File.separator + UPLOAD_DIRECTORY;

        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // parses the request's content to extract file data

            System.out.println(uploadPath);
            List<FileItem> formItems = upload.parseRequest((RequestContext)request);
            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // C:\tomcat\apache-tomcat-7.0.40\webapps\data\
                        // saves the file on disk
                        item.write(storeFile);
                        request.setAttribute("message","Upload has been done successfully!");
                        System.out.println("demo Success");
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message","There was an error: " + ex.getMessage());
            System.out.println("demo Fail: " +   ex.getMessage() );
        }
		/*
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload();

		// Parse the request
		FileItemIterator iter;
		try {
			iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
			    FileItemStream fileItem = iter.next();
			    String name = fileItem.getFieldName();
			    InputStream stream = ((ServletRequest) fileItem).getInputStream(); // Watch Cast On fileItem
			    if (fileItem.isFormField()) {
			        System.out.println("Form field " + name + " with value "
			            + Streams.asString(stream) + " detected.");
			    } else {
			        System.out.println("File field " + name + " with file name "
			            + fileItem.getName() + " detected.");
			        // Process the input stream
			    }
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("http://localhost:8080/Reimbursements/ReimbursementServlet");
		*/
	}

}
