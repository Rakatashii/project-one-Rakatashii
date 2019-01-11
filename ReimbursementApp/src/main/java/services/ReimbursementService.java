package services;

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
	
	public void addReimbursement(Reimbursement reimbursement) {
		EmployeeService employeeService = new EmployeeService();
		this.employee = employeeService.getLoggedInEmployee();
	
		if (employee != null && reimbursement != null) {
			this.reimbursement = reimbursement;
			int employeeID = employee.getEmployeeID();
			this.reimbursement.setEmployeeID(employeeID);
			int reimbursementID = reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID);
			this.reimbursement.setReimbursementID(reimbursementID);
			
			reimbursementDAO.addReimbursement(this.reimbursement);
			employee.setNumReimbursements(reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID));
			employeeDAO.updateEmployee(employee);
			
			System.out.println(this.reimbursement);
		} else {
			reimbursement = null;
			System.out.println(this.reimbursement);
		}
	}
	
	public void addImage(Image image) {
		if (this.reimbursement != null && image != null) {
			this.image = image;
			this.image.setImageID(reimbursement.getReimbursementID());
			this.image.uploadImageLocally();
			imageDAO.addImage(this.image);
			System.out.println(this.image);
		}
	}
}
