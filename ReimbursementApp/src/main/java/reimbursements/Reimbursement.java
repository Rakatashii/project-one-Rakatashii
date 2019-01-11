package reimbursements;

import java.io.File;

import employees.Employee;

public class Reimbursement {
	private int employeeID, reimbursementID;
	private String item, description, comments;
	private double amount;
	private Employee employee;
	
	public Reimbursement(int customerID, int reimbursementID, String item, String description, double amount, String comments) {
		super();
		this.reimbursementID = -1;
		this.item = item;
		this.description = description;
		this.comments = comments;
		this.amount = amount;
	}
	public Reimbursement(int reimbursementID, String item, String description, double amount, String comments) {
		super();
		this.reimbursementID = reimbursementID;
		this.item = item;
		this.description = description;
		this.comments = comments;
		this.amount = amount;
	}
	public Reimbursement(String item, String description, double amount, String comments) {
		super();
		this.reimbursementID = -1;
		this.item = item;
		this.description = description;
		this.comments = comments;
		this.amount = amount;
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
		return "Reimbursement [employeeID=" + employeeID + ", reimbursementID=" + reimbursementID + ", item=" + item + ", description="
				+ description + ", amount=" + amount + ", comments=" + comments + "]";
	}
}
