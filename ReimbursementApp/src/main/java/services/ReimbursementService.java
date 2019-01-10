package services;

import dao.ReimbursementDAO;
import employees.Employee;
import reimbursements.Reimbursement;

public class ReimbursementService {
	private ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
	
	public void addReimbursement(Reimbursement reimbursement) {
		EmployeeService employeeService = new EmployeeService();
		Employee loggedInEmployee = employeeService.getLoggedInEmployee();
		int itemID = reimbursementDAO.getNumReimbursements();
		reimbursement.setEmployeeID(loggedInEmployee.getEmployeeID());
		reimbursement.setItemID(itemID);
		System.out.println(reimbursement);
		reimbursementDAO.addReimbursement(reimbursement);
	}
	
	public void addImage() {
		
	}
}
