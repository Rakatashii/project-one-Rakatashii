package services;

import java.util.ArrayList;

import dao.EmployeeDAO;
import dao.ManagerDAO;
import employees.Employee;
import managers.Manager;

public class ManagerService {
	private ManagerDAO managerDAO;
	private static Manager loggedInManager;
	
	public boolean verifyLoginInfo(String username, String password) {
		managerDAO = new ManagerDAO();
		ArrayList<Manager> managers = null;
		if (managerDAO.getNumManagers() > 0) managers = managerDAO.getAllManagers();
		if (managers != null && !managers.isEmpty()) {
			for (Manager m : managers) {
				if (m.getUsername().equals(username) && m.getPassword().equals(password)) {
					loggedInManager = m;
					return true;
				}
			}
		}
		return false;
	}
	
	public static Manager getLoggedInManager() {
		return loggedInManager;
	}
	public static void logoutManager() {
		loggedInManager = null;
	}
	public Employee getSelectedEmployee(int id){
		EmployeeDAO employeeDAO = new EmployeeDAO();
		return employeeDAO.getEmployeeByID(id);
	}
}
