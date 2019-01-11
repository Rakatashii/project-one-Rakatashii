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

import database.DBConnection;
import images.Image;

public class ImageDAO {
	Connection connection;
	PreparedStatement ps;
	ReimbursementDAO reimbursementDAO;
	
	public void addImage(Image image) {
		String tableName = "images";
		String sql = "INSERT INTO " + tableName + " VALUES(?, ?, ?, ?, ?)";
		int reimbursementID = image.getReimbursementID();
		reimbursementDAO = new ReimbursementDAO();
		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement(sql);
			
			try {
				if (image.hasImageFile()) {
					if (image.getImageFile() != null && image.getImageName() != null) {
						
						FileInputStream fin = new FileInputStream(image.getImageFile());
						
						ps.setInt(1, image.getEmployeeID());
						ps.setInt(2, reimbursementID);
						ps.setString(3, image.getImageName());
						ps.setInt(4, image.getImageSize());
						ps.setBinaryStream(5, fin, image.getImageFile().length());
						
						ps.executeUpdate();
						
					} else {
						System.out.println("Image File Or File Name Is NULL In DAO!");
					}
				} else {
					System.out.println("No Image File In DAO!");
				}
				ps.close();
			} catch (IOException e) {
				e.printStackTrace(); System.out.println();
				/*
				if (reimbursementID >= 0) {
					try {
						reimbursementDAO.deleteReimbursement(reimbursementID);
					} catch (Exception ex) {
						ex.printStackTrace(); System.out.println();
					}
				}
				*/
			}
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
			/*
			if (reimbursementID >= 0) {
				try {
					reimbursementDAO.deleteReimbursement(reimbursementID);
				} catch (Exception ex) {
					ex.printStackTrace(); System.out.println();
				}
			}
			*/
		}
	}
	public Image getImage(int employeeID, int reimbursementID) {
		String tableName = "images";
		String sql = "SELECT image_name, image_length, bytestream FROM images WHERE employee_id=? AND reimbursement_id=?";
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
				name = Image.UPLOAD_DIRECTORY + rs.getString(1).replaceAll("[ ]+", "");
				length = rs.getInt(2);
				File file = new File(name);
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
}
