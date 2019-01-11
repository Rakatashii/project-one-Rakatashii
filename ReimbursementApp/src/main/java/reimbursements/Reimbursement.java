package reimbursements;

import java.io.File;

import dao.ReimbursementDAO;
import employees.Employee;

public class Reimbursement {
	private int employeeID, reimbursementID;
	private String item, description, comments;
	private double amount;
	private Employee employee;
	
	ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
	
	public Reimbursement(int employeeID, int reimbursementID, String item, String description, double amount, String comments) {
		super();
		this.employeeID = employeeID;
		this.reimbursementID = reimbursementID;
		this.item = item;
		this.description = description;
		this.amount = amount;
		this.comments = (comments != null) ? comments : "NONE";
	}
	public Reimbursement(int employeeID, String item, String description, double amount, String comments) {
		super();
		this.employeeID = employeeID;
		this.reimbursementID = reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID);
		this.item = item;
		this.description = description;
		this.amount = amount;
		this.comments = (comments != null) ? comments : "NONE";
	}
	public Reimbursement(String item, String description, double amount, String comments) {
		super();
		this.employeeID = -1;
		this.reimbursementID = -1;
		this.item = item;
		this.description = description;
		this.amount = amount;
		this.comments = (comments != null) ? comments : "NONE";
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public int getReimbursementID() {
		return reimbursementID;
	}

	public void setReimbursementID(int reimbursementID) {
		this.reimbursementID = reimbursementID;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@Override
	public String toString() {
		return "Reimbursement [employeeID=" + employeeID + ", reimbursementID=" + reimbursementID + ", "
				+ "item=" + (item != null ? item + ", " : "null")
				+ "description=" + (description != null ? description + ", " : "null") 
				+ "amount=" + amount + ", "
				+ "comments=" + (comments != null ? comments : "null") + "]";
	}
	
}
