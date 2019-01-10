package reimbursements;

import java.io.File;

import employees.Employee;

public class Reimbursement {
	private int employeeID, itemID;
	private String item, description, comments;
	private double amount;
	private Employee employee;
	
	public Reimbursement(int customerID, int itemID, String item, String description, double amount, String comments) {
		super();
		this.item = item;
		this.description = description;
		this.comments = comments;
		this.amount = amount;
	}
	public Reimbursement(int itemID, String item, String description, double amount, String comments) {
		super();
		this.itemID = itemID;
		this.item = item;
		this.description = description;
		this.comments = comments;
		this.amount = amount;
	}
	public Reimbursement(String item, String description, double amount, String comments) {
		super();
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

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
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
		return "Reimbursement [employeeID=" + employeeID + ", itemID=" + itemID + ", item=" + item + ", description="
				+ description + ", comments=" + comments + ", amount=" + amount + ", employee=" + employee + "]";
	}
}
