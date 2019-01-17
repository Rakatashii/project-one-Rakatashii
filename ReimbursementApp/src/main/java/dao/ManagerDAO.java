package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.util.PSQLException;

import database.DBConnection;
import managers.Manager;

public class ManagerDAO {
	private Connection connection;
	private PreparedStatement ps;
	final private String tableName = "managers";
	
	public boolean addManager(Manager manager) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  manager.getManagerID());
			ps.setString(2, manager.getUsername());
			ps.setString(3, manager.getPassword());
			ps.setString(4, manager.getFirstname());
			ps.setString(5,  manager.getLastname());
		
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (PSQLException e) {
			e.printStackTrace(); System.out.println();
			return false;
		}
		catch (SQLException e) {
			e.printStackTrace(); System.out.println();
			return false;
		}
	}
	public boolean updateManager(Manager manager) {
		try {
			connection = DBConnection.getConnection();
			String sql = "UPDATE " + tableName + " SET "
				+ "manager_id=?, username=?, password=?, firstname=?, "
				+ "lastname=? WHERE manager_id=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  manager.getManagerID());
			ps.setString(2, manager.getUsername());
			ps.setString(3, manager.getPassword());
			ps.setString(4, manager.getFirstname());
			ps.setString(5,  manager.getLastname());
			
			ps.setInt(6, manager.getManagerID());
		
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (PSQLException e) {
			e.printStackTrace(); System.out.println();
			return false;
		}
		catch (SQLException e) {
			e.printStackTrace(); System.out.println();
			return false;
		}
	}
	public int getNumManagers() {
		int count = 0;
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				System.out.println("Connection is Null!");
			}
			String sql = "SELECT COUNT(*) AS count FROM " + tableName + ";";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			count = rs.getInt("count");
			statement.close(); rs.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		} 
		return count;
	}
	public int getMaxManagerID() {
		int maxID = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT MAX(manager_id) FROM " + tableName + ";";
		
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			
			if (rs.next() == true) maxID = rs.getInt(1);
			else return 0;
			
			statement.close(); rs.close();
			return maxID;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return maxID;
	}
	public ArrayList<Manager> getAllManagers() {
		ArrayList<Manager> managers = new ArrayList<>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY manager_id;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				Manager manager = new Manager(rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5));
				managers.add(manager);
				System.out.println(manager);
			}
			statement.close(); rs.close();
			return managers;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return managers;
	}
	public Manager getManagerByID(int manager_id) {
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE manager_id = " + manager_id + ";";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			Manager manager = null;
			if (rs.next()) 
				manager = new Manager(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5));
			return manager;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return null;
	}
	public boolean checkIfManagerExists(int id) {
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE manager_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				ps.close(); rs.close();
				return true;
			} else {
				ps.close(); rs.close();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return false;
	}
}
