package employees;

import dao.EmployeeDAO;

public class Employee {

	private String username, password;
	private String firstname, lastname;
	private int employeeID;
	public int numReimbursements;
	boolean rememberLoginInfo;
	
	EmployeeDAO employeeDAO = new EmployeeDAO();
	
	public Employee(String username, String password, String firstname, String lastname, int numReimbursements) {
		this.employeeID = employeeDAO.getMaxEmployeeID()+1;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.numReimbursements = numReimbursements;
		rememberLoginInfo = false;
	}
	public Employee(int employeeID, String username, String password, String firstname, String lastname, int numReimbursements) {
		this.employeeID = employeeID;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.numReimbursements = numReimbursements;
		rememberLoginInfo = false;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", username=" + username + ", password=" + password 
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", numReimbursements=" + numReimbursements
				+ "]";
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
		this.numReimbursements= numReimbursements;
	}
	public boolean isRememberLoginInfo() {
		return rememberLoginInfo;
	}
	public void setRememberLoginInfo(boolean rememberLoginInfo) {
		this.rememberLoginInfo = rememberLoginInfo;
	}
}
