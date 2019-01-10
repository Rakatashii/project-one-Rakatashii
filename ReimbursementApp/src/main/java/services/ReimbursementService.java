package services;

import dao.ReimbursementDAO;
import employees.Employee;
import reimbursements.Reimbursement;

public class ReimbursementService {
	private ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
	
	public void addReimbursement(Reimbursement reimbursement) {
		EmployeeService employeeService = new EmployeeService();
		Employee loggedInEmployee = employeeService.getLoggedInEmployee();
		if (loggedInEmployee != null) {
			int employeeID = loggedInEmployee.getEmployeeID();
			reimbursement.setEmployeeID(employeeID);
			int itemID = reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID);
			reimbursement.setItemID(itemID);
			reimbursementDAO.addReimbursement(reimbursement);
		}
		System.out.println(reimbursement);
	}
	
	public void addImage() {
		
	}
}
