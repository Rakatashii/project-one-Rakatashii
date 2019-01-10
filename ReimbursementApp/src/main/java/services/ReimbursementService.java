package services;

import dao.EmployeeDAO;
import dao.ReimbursementDAO;
import employees.Employee;
import reimbursements.Reimbursement;

public class ReimbursementService {
	private ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	
	public void addReimbursement(Reimbursement reimbursement) {
		EmployeeService employeeService = new EmployeeService();
		Employee loggedInEmployee = employeeService.getLoggedInEmployee();
		if (loggedInEmployee != null) {
			int employeeID = loggedInEmployee.getEmployeeID();
			reimbursement.setEmployeeID(employeeID);
			int itemID = reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID);
			reimbursement.setItemID(itemID);
			reimbursementDAO.addReimbursement(reimbursement);
			loggedInEmployee.setNumReimbursements(reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID));
			// TODO create update meth in employee DAO and update loggedInEmployee
		}
		System.out.println(reimbursement);
	}
	
	public void addImage() {
		
	}
}
