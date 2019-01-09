package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.util.PSQLException;

import employees.Employee;
import database.DBConnection;

public class EmployeeDAO {
	private Connection connection;
	private PreparedStatement ps;
	//final Logger log = Logger.getLogger(EmployeeDAO.class);
	
	public boolean addEmployee(Employee employee) {
		String tableName = "employees";
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  employee.getEmployeeID());
			ps.setString(2, employee.getUsername());
			ps.setString(3, employee.getPassword());
			ps.setString(4, employee.getFirstname());
			ps.setString(5,  employee.getLastname());
			ps.setInt(6, employee.getNumReimbursements());
		
			if (ps.executeUpdate() != 0) {
				//log.debug("Inserted Into " + tableName + " Values(" + employee.getEmployeeID() + ", " + employee.getUsername() + ", ... )");
				ps.close();
				return true;
			} else {
				ps.close();
				//log.debug("Failed To Insert Into " + tableName + " Employee With " + employee.getEmployeeID() + ", username = " + employee.getUsername() + ", ... ");
				//log.debug("\t" + "ps was closed.");
				//e.printStackTrace(); System.out.println();
				return false;
			} 
		} catch (PSQLException e) {
			//log.debug("Failed To Insert Into " + tableName + " Employee With " + employee.getEmployeeID() + ", username = " + employee.getUsername() + ", ... ");
			//log.debug("\t" + e.getLocalizedMessage());
			//e.printStackTrace(); System.out.println();
			return false;
		}
		catch (SQLException e) {
			//log.debug("Failed To Insert Into " + tableName + " Employee With " + employee.getEmployeeID() + ", username = " + employee.getUsername() + ", ... ");
			//log.debug("\t" + e.getLocalizedMessage());
			//e.printStackTrace(); System.out.println();
			return false;
		}
	}
	public int getNumEmployees() {
		String tableName = "employees";
		try {
			
			connection = DBConnection.getConnection();
			if (connection == null) {
				System.out.println("Connection is Null!");
				Thread.sleep(2000);
			}
			String sql = "SELECT COUNT(*) AS count FROM " + tableName + ";";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			statement.close(); rs.close();
			//log.debug("Current Count For " + tableName + " Is " + count);
			return count;
		} catch (SQLException e) {
			//log.debug("Could Not Get Count For Table " + tableName);
			e.printStackTrace(); System.out.println();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int getMaxEmployeeID() {
		String tableName = "employees";
		int maxID = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT MAX(employee_id) FROM " + tableName + ";";
		
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			
			if (rs.next() == true) maxID = rs.getInt(1);
			else return 0;
			
			statement.close(); rs.close();
			//log.debug("MaxID in table " + tableName + " = " + maxID);
			return maxID;
		} catch (SQLException e) {
			//log.debug("Could not get maxID from table " + tableName);
			e.printStackTrace(); System.out.println();
		}
		return maxID;
	}
	public ArrayList<Employee> getAllEmployees() {
		String tableName = "employees";
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY employee_id;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				Employee employee = new Employee(rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
				employees.add(employee);
				System.out.println(employee);
			}
			statement.close(); rs.close();
			//log.debug("Got employees ArraLiys With Size = " + employees.size());
			return employees;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return employees;
	}
	public Employee findEmployeeByID(int employee_id) {
		String tableName = "employees";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE employee_id = " + employee_id + ";";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			Employee employee = null;
			if (rs.next()) 
				employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5), rs.getInt(6));
			//log.debug("Employee Found In Table " + tableName + " with employee_id = " + 
						//employee.getEmployeeID() + " and username = " + employee.getUsername());
			return employee;
		} catch (SQLException e) {
			//log.debug("Employee Not Found In " + tableName + " With employee_id = " + employee_id);
			//log.debug("\t" + e.getLocalizedMessage()); 
		}
		return null;
	}
	public boolean checkIfEmployeeExists(int id, boolean fromSampleTable) {
		String tableName = "employees";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE employee_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				ps.close(); rs.close();
				// log.debug("Employee With ID = " + id + " Exists In Table " + tableName);
				return true;
			} else {
				ps.close(); rs.close();
				// log.debug("Employee With ID = " + id + " Does Not Exists In Table " + tableName);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return false;
	}
}
