package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.postgresql.util.PSQLException;

import database.DBConnection;
import images.Image;
import reimbursements.Reimbursement;

public class ImageDAO {
	Connection connection;
	PreparedStatement ps;
	ReimbursementDAO reimbursementDAO;
	private final String tableName = "images";
	
	public String addImage(Image image) {
		String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?)";
		int reimbursementID = image.getReimbursementID();
		reimbursementDAO = new ReimbursementDAO();
		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement(sql);
			
			if (!(new File(image.getImageName()).exists())) new File(Image.UPLOAD_DIRECTORY).mkdirs();
			try {
				if (image.hasImageFile() && !image.getImageFile().isDirectory()) {
					if (image.getImageName() != null) {
						
						FileInputStream fin = new FileInputStream(image.getImageFile());
						
						ps.setInt(1, image.getEmployeeID());
						ps.setInt(2, reimbursementID);
						ps.setString(3,  image.getRelativePath());
						ps.setString(4, image.getImageName());
						ps.setInt(5, image.getImageSize());
						ps.setBinaryStream(6, fin, image.getImageFile().length());
						
						ps.executeUpdate();
						
						ps.close();
						return null;
					} else {
						System.out.println("Image File Or File Name Is NULL In DAO!");
					}
				} else {
					System.out.println("No Image File In DAO!");
				}
				ps.close();
				return "System Error";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				ps.close();
				return "File Not Found";
			} 
		} catch (PSQLException e) {
			e.printStackTrace(); System.out.println();
			if (reimbursementID >= 0) {
				try {
					reimbursementDAO.deleteReimbursementByID(reimbursementID);
				} catch (Exception ex) {
					ex.printStackTrace(); System.out.println();
					return "System Error. Please Contact An Administrator.";
				}
			}
	    	return getErrorMessage(e.getLocalizedMessage());
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
			if (reimbursementID >= 0) {
				try {
					reimbursementDAO.deleteReimbursementByID(reimbursementID);
				} catch (Exception ex) {
					ex.printStackTrace(); System.out.println();
					return "System Error. Please Contact An Administrator.";
				}
			}
			return "Error: Duplicate Image May Already Exist In The Database.";
		}
	}
	public Image getImage(int employeeID, int reimbursementID) {
		String sql = "SELECT absolute_path, image_length, bytestream FROM images WHERE employee_id=? AND reimbursement_id=?";
		Image image = null;
		
		String name = "";
		int length = 0;
		byte[] buffer;
		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  employeeID);
			ps.setInt(2,  reimbursementID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				name = rs.getString(1);
				if (!name.contains(Image.UPLOAD_DIRECTORY)) name = Image.UPLOAD_DIRECTORY + name.replaceAll("[ ]+", "");
				length = rs.getInt(2);
				File file = new File(name);
				if (!file.exists()) new File(Image.UPLOAD_DIRECTORY).mkdirs();
				buffer = rs.getBytes("bytestream");
				try (FileOutputStream fos = new FileOutputStream(file)){
					fos.write(buffer, 0, length);
					image = new Image(employeeID, reimbursementID, name, file, length);
					System.out.println(image);
					return image;
				} catch (FileNotFoundException e) {
					e.printStackTrace(); System.out.println();
				} catch (IOException e) {
					e.printStackTrace(); System.out.println();
				} finally {
					ps.close(); rs.close();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return image;
	}
	
	private String getErrorMessage(String error) {
		Pattern pattern = Pattern.compile("(?<=ERROR:.{1}?)(.*)(?!.\\n|DETAIL:)");
    	Matcher match = pattern.matcher(error.replace("\\",  "#"));
    	if (match.find()) {
    		System.out.println(match.group(0)); 
    		return match.group(0);
    	}
    	return "Unable To Process Your Request.";
	}
	
	public ArrayList<String> getRelativePathsByEmployeeID(int employee_id) {
		String tableName = "images";
		ArrayList<String> relativePaths = new ArrayList<>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT relative_path FROM " + tableName + " WHERE employee_id = " + employee_id + " ORDER BY employee_id;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				relativePaths.add(rs.getString(1));
			}
			statement.close(); rs.close();
			//log.debug("Got employees ArraLiys With Size = " + employees.size());
			if (relativePaths.size() > 0) return relativePaths;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return null;
	}
}

