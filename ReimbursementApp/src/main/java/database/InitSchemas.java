package database;

public class InitSchemas {
	private final String employeeSchema = "CREATE TABLE employees ("
			+ "employee_id UNIQUE INTEGER PRIMARY KEY CHECK (employee_id >= 0), "
			+ "username VARCHAR(255) UNIQUE NOT NULL, "
			+ "password VARCHAR(255), "
			+ "firstname VARCHAR(255), "
			+ "lastname VARCHAR(255), "
			+ "num_reimbursements INTEGER CHECK (num_reimbursements >= 0)"
			+ ");";
	private final String reimbursementSchema = "CREATE TABLE reimbursements ("
			+ "employee_id INTEGER UNIQUE NOT NULL CHECK (employee_id >= 0) REFERENCES employees(employee_id), "
			+ "request_id INTEGER PRIMARY KEY UNIQUE CHECK (request_id >= 0), "
			+ "description VARCHAR(255) NOT NULL, "
			+ "amount DECIMAL(12, 2) CHECK (amount > 0.0), "
			+ "comments VARCHAR(255), "
			+ "image BYTEA"
			+ ");";
	private final String managerSchema = "CREATE TABLE managers ("
			+ "manager_id INTEGER PRIMARY KEY UNIQUE CHECK (manager_id >= 0), "
			+ "username VARCHAR(255) UNIQUE NOT NULL, "
			+ "password VARCHAR(255) NOT NULL, "
			+ "firstname VARCHAR(255), "
			+ "lastname VARCHAR(255) "
			+ ");";
	
	private String[] schemas =     {  employeeSchema,   reimbursementSchema,   managerSchema  };
	private String[] schemaNames = { "employeeSchema", "reimbursementSchema", "managerSchema" };
	
	public void initiateSchemas() {
		
		for (String name : schemaNames) {
			
		}
	}
}
