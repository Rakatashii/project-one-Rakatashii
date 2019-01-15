package services;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dao.EmployeeDAO;
import dao.ImageDAO;
import dao.ReimbursementDAO;
import employees.Employee;
import images.Image;
import reimbursements.Reimbursement;

public class ReimbursementService {
	Employee employee = null;
	Reimbursement reimbursement = null;
	Image image = null;
	
	private ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	private ImageDAO imageDAO = new ImageDAO();
	
	private String reimbursementDAOResponse = null, imageDAOResponse = null;
	boolean validReimbursement = true, validImage = true;
	String buttonType = "success";
	
	public boolean addReimbursement(Reimbursement reimbursement) {
		// EmployeeService employeeService = new EmployeeService();
		this.employee = EmployeeService.getLoggedInEmployee();
		
		if (this.employee != null && reimbursement != null) {
			this.reimbursement = reimbursement;
			int employeeID = employee.getEmployeeID();
			this.reimbursement.setEmployeeID(employeeID);
			int reimbursementID = reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID);
			this.reimbursement.setReimbursementID(reimbursementID);
			
			if (this.reimbursement.getAmount() < 0.0) {
				reimbursementDAOResponse = "Amount Must Be Positive.";
				validReimbursement = false;
				buttonType = "error";
				return false;
			}
			
			if (validReimbursement) reimbursementDAOResponse = reimbursementDAO.addReimbursement(this.reimbursement);
			if (reimbursementDAOResponse != null) System.out.println("reimbursementDAOResponse: " + reimbursementDAOResponse);
			employee.setNumReimbursements(reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID));
			employeeDAO.updateEmployee(employee);
			
			System.out.println("IN ReimbursementService: " + this.reimbursement);
			reimbursementDAOResponse = "Your Submission Was Successful!";
			
			return true;
		} else {
			reimbursementDAOResponse = "Your Request Could Not Be Processed.";
			buttonType = "error";
			return false;
		}
	}
	
	public void addImage(Image image) {
		if (this.reimbursement != null && image != null) {
			
			this.image = image;
			
			this.image.setEmployeeID(reimbursement.getEmployeeID());
			this.image.setReimbursementID(reimbursement.getReimbursementID());
			
			/* NEED THIS IN EITHER CASE... */
			imageDAOResponse = imageDAO.addImage(this.image);
			if (imageDAOResponse != null) {
				System.out.println("imageDAOResponse: " + imageDAOResponse);
			}
			else buttonType = "error";
			/* KEEP THIS TO TEST DAO#addImage AND FILE UPLOAD W THAT INFORMATION */
			this.image.uploadLocalFile();
			System.out.println("IN ReimbursementService: " + this.image);
			
			/* KEEP THIS TO TEST DAO#getImage AND FILE UPLOAD W THAT INFORMATION */
			//Image newImage = imageDAO.getImage(reimbursement.getEmployeeID(), reimbursement.getReimbursementID());
			//newImage.uploadLocalFile();
			//System.out.println(this.image);
		}
	}
	
	public String getResponse() {
		if (imageDAOResponse != null) {
			buttonType = "error";
			return ((imageDAOResponse.contains("_")) ? imageDAOResponse.replace('_', ' ') : imageDAOResponse);
		}
		else {
			
			if (reimbursementDAOResponse.toLowerCase().contains("success") == false && reimbursementDAOResponse.toLowerCase().contains("submit") == false) {
				System.out.println("!response.contains(\"successful\"): " + reimbursementDAOResponse.toLowerCase());
				buttonType = "error";
			} else buttonType = "success";
			return ((reimbursementDAOResponse.contains("_")) ? reimbursementDAOResponse.replace('_', ' ') : reimbursementDAOResponse);
		}
	}
	
	public String getButtonType() {
		return buttonType;
	}
	
	public double convertAmountToDouble(String amountString) {
		Pattern pattern = Pattern.compile("(-?(([0-9]+)|(([0-9]{1,})(\\.?)([0-9]+?))|(([0-9]+?)(\\.?)([0-9]{1,}))))");
		Matcher match = pattern.matcher(amountString);
		double val = -1;
		boolean negative = (amountString.charAt(0) == '-') ? true : false;
    	if (match.find() == false) {
			return -1;
		} else {
			val = Double.parseDouble(amountString);
		}
    	if (negative && val > 0) val *= -1;
    	return val;
	}
}
