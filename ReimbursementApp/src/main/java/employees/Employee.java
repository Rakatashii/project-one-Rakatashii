package employees;

import dao.EmployeeDAO;

public class Employee {
	private String username, password;
	private String firstname, lastname;
	private int employeeID;
	public int numReimbursements;
	
	EmployeeDAO employeeDAO = new EmployeeDAO();
	
	public Employee(String username, String password, String firstname, String lastname) {
		this.employeeID = employeeDAO.getMaxEmployeeID();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.numReimbursements = 0;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public int getNumReimbursements() {
		return numReimbursements;
	}

	public void setNumReimbursements(int numReimbursements) {
		this.numReimbursements = numReimbursements;
	}
}
