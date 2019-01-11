package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;
import images.Image;

public class ImageDAO {
	Connection connection;
	
	public void addImage(Image image) {
		String tableName = "images";
		String sql = "INSERT INTO " + tableName + " VALUES(?, ?, ?)";
		try {
			connection = DBConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			
			try {
				if (image.hasImageFile()) {
					if (image.getImageFile() != null && image.getImageName() != null) {
						FileInputStream fin = new FileInputStream(image.getImageFile());
						ps.setInt(1, image.getImageID());
						ps.setString(2, image.getImageName());
						ps.setBinaryStream(3, fin, image.getImageFile().length());
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
			}
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
	}
}
