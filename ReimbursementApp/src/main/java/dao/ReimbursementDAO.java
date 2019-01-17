package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.postgresql.util.PSQLException;

import database.DBConnection;
import employees.Employee;
import reimbursements.Reimbursement;

public class ReimbursementDAO {
	private Connection connection;
	private PreparedStatement ps;
	//final Logger log = Logger.getLogger(EmployeeDAO.class);
	
	public String addReimbursement(Reimbursement reimbursement) {
		String tableName = "reimbursements";
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  reimbursement.getEmployeeID());
			ps.setInt(2,  reimbursement.getReimbursementID());
			ps.setString(3, reimbursement.getExpense());
			ps.setString(4, reimbursement.getSource());
			ps.setDouble(5, reimbursement.getAmount());
			ps.setString(6, reimbursement.getComments());
			ps.setString(7, reimbursement.getRelativePath());
			ps.setString(8, reimbursement.getStatus());
			
			if (ps.executeUpdate() != 0) {
				ps.close();
				return "Your Request Has Been Submitted.";
			} else {
				ps.close();
				return "System Error. Please Contact An Administrator.";
			} 
		} catch (PSQLException e) {
			e.printStackTrace(); System.out.println();
			String error = e.getLocalizedMessage();
			Pattern pattern = Pattern.compile("(?<=ERROR:.{1}?)(.*)(?!.\\n|DETAIL:)");
	    	Matcher match = pattern.matcher(error.replace("\\",  "#"));
	    	if (match.find()) {
	    		System.out.println(match.group(0)); return match.group(0);
	    	}
	    	else return "Unable To Process Your Request.";
		}
		catch (SQLException e) {
			e.printStackTrace(); System.out.println();
			return "Unable To Process Your Request.";
		}
	}
	public void deleteReimbursementByID(int reimbursementID) {
		String tableName = "reimbursements";
		try {
			connection = DBConnection.getConnection();
			String sql = "DELETE FROM " + tableName + " WHERE reimbursement_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  reimbursementID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
	}
	public int getNumReimbursements() {
		String tableName = "reimbursements";
		int count = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM " + tableName + ";";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next())
				count = rs.getInt("count");
			statement.close(); rs.close();
			//log.debug("Current Count For " + tableName + " Is " + count);
			return count;
		} catch (SQLException e) {
			//log.debug("Could Not Get Count For Table " + tableName);
			e.printStackTrace(); System.out.println();
		}
		return count;
	}
	public int getNumReimbursementsByEmployeeID(int employee_id) {
		String tableName = "reimbursements";
		int count = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) as count FROM " + tableName + " WHERE employee_id = " + employee_id;
		
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) 
				count = rs.getInt("count");
			statement.close(); rs.close();
			//log.debug("Current Count For " + tableName + " Is " + count);
			return count;
		} catch (SQLException e) {
			//log.debug("Could not get maxID from table " + tableName);
			e.printStackTrace(); System.out.println();
		}
		return count;
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
				reimbursement.setRelativePath(rs.getString(7));
				reimbursement.setStatus(rs.getString(8));
				
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
	/*
	public Employee getReimbursementByExpenseID(int employee_id) {
		String tableName = "reimbursements";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE employee_id = " + employee_id + " ORDER BY expense_id";
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
	*/
	public boolean checkIfReimbursementExists(int employee_id, int expense_id) {
		String tableName = "employees";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE employee_id = ? AND expense_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, employee_id);
			ps.setInt(2, expense_id);
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
