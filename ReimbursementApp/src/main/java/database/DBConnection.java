package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.reimbursementApp.ReimbursementApp;

public class DBConnection {
	private static Connection connection = null;
	
	private static String pgUrl = null;
	private static String pgUsername = null;
	private static String pgPassword = null;
	
	private static File pgInfo = new File(new String("preferences/pgInfo.txt"));
	static final Logger log = Logger.getLogger(DBConnection.class);
	
	public DBConnection() { }
	
	public static void startConnection() {
		if (pgUrl == null)
			getPgInfo();
		
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(pgUrl,  pgUsername,  pgPassword);
		} catch (SQLException e) {
			log.debug("Unable To Start Connection."); 
			log.debug("\t" + e.getLocalizedMessage());
		}
	}
	
	public static Connection getConnection(){ 
		if (connection == null) 
			startConnection();
		return connection;
	}
	
	public static void getPgInfo() {
		Scanner in;
		try {
			in = new Scanner(pgInfo);
			pgUrl = in.nextLine();
			pgUsername = in.nextLine();
			pgPassword = in.nextLine();
		} catch (FileNotFoundException e) {
			log.debug("Unable To Verify pgDB Credentials.");
			log.debug("\t" + e.getLocalizedMessage());
		}
	}
}
