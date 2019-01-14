package reimbursements;

import java.io.File;

import dao.ReimbursementDAO;
import employees.Employee;

public class Reimbursement {
	private int employeeID, reimbursementID;
	private String expense, source, comments;
	private double amount;
	private Employee employee;
	private String status = "pending";
	
	ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
	
	public Reimbursement(int employeeID, int reimbursementID, String expense, String source, double amount, String comments) {
		super();
		this.employeeID = employeeID;
		this.reimbursementID = reimbursementID;
		this.expense = expense;
		this.source = source;
		this.amount = amount;
		this.comments = (comments != null) ? comments : "NONE";
	}
	public Reimbursement(int employeeID, String expense, String source, double amount, String comments) {
		super();
		this.employeeID = employeeID;
		this.reimbursementID = reimbursementDAO.getNumReimbursementsByEmployeeID(employeeID);
		this.expense = expense;
		this.source = source;
		this.amount = amount;
		this.comments = (comments != null) ? comments : "NONE";
	}
	public Reimbursement(String expense, String source, double amount, String comments) {
		super();
		this.employeeID = -1;
		this.reimbursementID = -1;
		this.expense = expense;
		this.source = source;
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

	public String getExpense() {
		return expense;
	}

	public void setExpense(String expense) {
		this.expense = expense;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Reimbursement [employeeID=" + employeeID + ", reimbursementID=" + reimbursementID + ", "
				+ "expense=" + (expense != null ? expense + ", " : "null")
				+ "source=" + (source != null ? source + ", " : "null") 
				+ "amount=" + amount + ", "
				+ "comments=" + (comments != null ? comments : "null") + "]";
	}
	
}
