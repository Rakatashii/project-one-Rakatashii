package managers;

import java.io.Serializable;

public class Manager implements Serializable {
	private static final long serialVersionUID = 1540643449385895023L;
	
	private String username, password;
	private String firstname, lastname;
	private int managerID;
	
	public Manager(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
