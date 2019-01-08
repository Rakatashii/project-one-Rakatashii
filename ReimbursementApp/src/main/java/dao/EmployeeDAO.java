package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;

import com.reimbursementApp.ReimbursementApp;

import database.DBConnection;
import employees.Employee;

public class EmployeeDAO {
	private Connection connection;
	private PreparedStatement ps;
	final Logger log = Logger.getLogger(EmployeeDAO.class);
	
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
				log.debug("Inserted Into " + tableName + " Values(" + employee.getEmployeeID() + ", " + employee.getUsername() + ", ... )");
				ps.close();
				return true;
			} else {
				ps.close();
				log.debug("Failed To Insert Into " + tableName + " Employee With " + employee.getEmployeeID() + ", username = " + employee.getUsername() + ", ... ");
				log.debug("\t" + "ps was closed.");
				//e.printStackTrace(); System.out.println();
				return false;
			} 
		} catch (PSQLException e) {
			log.debug("Failed To Insert Into " + tableName + " Employee With " + employee.getEmployeeID() + ", username = " + employee.getUsername() + ", ... ");
			log.debug("\t" + e.getLocalizedMessage());
			//e.printStackTrace(); System.out.println();
			return false;
		}
		catch (SQLException e) {
			log.debug("Failed To Insert Into " + tableName + " Employee With " + employee.getEmployeeID() + ", username = " + employee.getUsername() + ", ... ");
			log.debug("\t" + e.getLocalizedMessage());
			//e.printStackTrace(); System.out.println();
			return false;
		}
	}
	public int getNumEmployees() {
		String tableName = "employees";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM " + tableName + ";";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			statement.close(); rs.close();
			log.debug("Current Count For " + tableName + " Is " + count);
			return count;
		} catch (SQLException e) {
			log.debug("Could Not Get Count For Table " + tableName);
			e.printStackTrace(); System.out.println();
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
			log.debug("MaxID in table " + tableName + " = " + maxID);
			return maxID;
		} catch (SQLException e) {
			log.debug("Could not get maxID from table " + tableName);
			e.printStackTrace(); System.out.println();
		}
		return maxID;
	}
}
