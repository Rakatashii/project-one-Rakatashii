package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;

public class ImageDAO {
	Connection connection;
	
	public void addImage(int item_id, File image) {
		String tableName = "images";
		String sql = "INSER INTO " + tableName + " VALES(?, ?)";
		try {
			
			connection = DBConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			
			try {
				FileInputStream fin = new FileInputStream(image);
				
				ps.setInt(1, item_id);
				ps.setBinaryStream(2,  fin, (int) image.length());
				ps.executeUpdate();
				
			} catch (IOException e) {
				e.printStackTrace(); System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
	}
}
