package services;

import dao.EmployeeDAO;
import dao.ReimbursementDAO;
import employees.Employee;
import images.Image;
import reimbursements.Reimbursement;

public class ReimbursementService {
	Employee loggedInEmployee = null;
	Reimbursement reimbursement = null;
	Image image = null;
	
	private ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	
	public void addReimbursement(Reimbursement reimbursement) {
		EmployeeService employeeService = new EmployeeService();
		this.loggedInEmployee = employeeService.getLoggedInEmployee();
		this.reimbursement = reimbursement;
		if (loggedInEmployee != null) {
			int employeeID = this.loggedInEmployee.getEmployeeID();
			this.reimbursement.setEmployeeID(employeeID);
			int itemID = reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID);
			this.reimbursement.setItemID(itemID);
			
			reimbursementDAO.addReimbursement(this.reimbursement);
			loggedInEmployee.setNumReimbursements(reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID));
			// TODO create update meth in employee DAO and update loggedInEmployee
		} else {
			reimbursement = null;
		}
		
		System.out.println(reimbursement);
	}
	
	public void addImage(Image image) {
		if (this.reimbursement != null) {
			this.image = image;
			if (this.image.getItemID() == -1) this.image.setItemID(reimbursement.getItemID());
			image.uploadImageLocally();
		}
	}
}
