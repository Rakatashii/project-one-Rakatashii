package services;

import java.util.ArrayList;

import dao.EmployeeDAO;
import dao.ReimbursementDAO;
import employees.Employee;

public class EmployeeService {
	private EmployeeDAO employeeDAO;
	private static Employee loggedInEmployee;
	
	public boolean verifyLoginInfo(String username, String password) {
		employeeDAO = new EmployeeDAO();
		ArrayList<Employee> employees = null;
		if (employeeDAO.getNumEmployees() > 0) employees = employeeDAO.getAllEmployees();
		if (employees != null && employees.size() > 0) {
			for (Employee e : employees) {
				if (e.getUsername().equals(username) && e.getPassword().equals(password)) {
					loggedInEmployee = e;
					return true;
				}
			}
		}
		return false;
	}
	
	public static Employee getLoggedInEmployee() {
		return loggedInEmployee;
	}
	public static void logoutEmployee() {
		loggedInEmployee = null;
	}
	
	public void approve(int employeeID, int reimbursementID) {
		ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
		reimbursementDAO.updateStatus(employeeID,  reimbursementID,  "approved");
		System.out.println("Employee With ID " + employeeID + " Was APPROVED");
	}
	public void deny(int employeeID, int reimbursementID) {
		ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
		reimbursementDAO.updateStatus(employeeID,  reimbursementID,  "denied");
		System.out.println("Employee With ID " + employeeID + " Was DENIED");
	}
}
