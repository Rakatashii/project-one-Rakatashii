package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.util.PSQLException;

import database.DBConnection;
import employees.Employee;
import reimbursements.Reimbursement;

public class ReimbursementDAO {
	private Connection connection;
	private PreparedStatement ps;
	//final Logger log = Logger.getLogger(EmployeeDAO.class);
	
	public boolean addReimbursement(Reimbursement reimbursement) {
		String tableName = "reimbursements";
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  reimbursement.getEmployeeID());
			ps.setInt(2,  reimbursement.getItemID());
			ps.setString(3, reimbursement.getItem());
			ps.setString(4, reimbursement.getDescription());
			ps.setDouble(5, reimbursement.getAmount());
			ps.setString(6, reimbursement.getComments());
			
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
			e.printStackTrace(); System.out.println();
			return false;
		}
		catch (SQLException e) {
			//log.debug("Failed To Insert Into " + tableName + " Employee With " + employee.getEmployeeID() + ", username = " + employee.getUsername() + ", ... ");
			//log.debug("\t" + e.getLocalizedMessage());
			e.printStackTrace(); System.out.println();
			return false;
		}
	}
	public int getNumReimbursements() {
		String tableName = "reimbursements";
		try {
			connection = DBConnection.getConnection();
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
		}
		return 0;
	}
	public int getNumReimbursementsByEmployeeID(int employee_id) {
		String tableName = "reimbursements";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) as count FROM " + tableName + " WHERE employee_id = " + employee_id;
		
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			statement.close(); rs.close();
			//log.debug("Current Count For " + tableName + " Is " + count);
			return count;
		} catch (SQLException e) {
			//log.debug("Could not get maxID from table " + tableName);
			e.printStackTrace(); System.out.println();
		}
		return 0;
	}
	public ArrayList<Reimbursement> getReimbursementsByEmployeeID(int employee_id) {
		String tableName = "reimbursements";
		ArrayList<Reimbursement> reimbursements = new ArrayList<>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE employee_id = " + employee_id + " ORDER BY employee_id;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				Reimbursement reimbursement = new Reimbursement(rs.getInt(1), rs.getInt(2), 
						rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getString(6));
				reimbursements.add(reimbursement);
				System.out.println(reimbursement);
			}
			statement.close(); rs.close();
			//log.debug("Got employees ArraLiys With Size = " + employees.size());
			return reimbursements;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return reimbursements;
	}
	public Employee getReimbursementByItemID(int employee_id) {
		String tableName = "reimbursements";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE employee_id = " + employee_id + " ORDER BY item_id";
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
			e.printStackTrace(); System.out.println();
		}
		return null;
	}
	public boolean checkIfReimbursementExists(int employee_id, int item_id) {
		String tableName = "employees";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE employee_id = ? AND item_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, employee_id);
			ps.setInt(2, item_id);
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
