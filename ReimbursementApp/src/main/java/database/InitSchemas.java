package database;

public class InitSchemas {
	final String employeeSchema = "CREATE TABLE employees ("
			+ "employee_id INTEGER PRIMARY KEY CHECK (employee_id >= 0), "
			+ "username VARCHAR(255) UNIQUE NOT NULL, "
			+ "password VARCHAR(255), "
			+ "firstname VARCHAR(255), "
			+ "lastname VARCHAR(255), "
			+ "department VARCHAR(255) "
			+ ");";
	final String reimbursementSchema = "CREATE TABLE reimbursements ("
			+ "employee_id UNIQUE NOT NULL CHECK (employee_id >= 0) REFERENCES employees(employee_id), "
			+ "rid INTEGER NOT NULL, "
			+ "description VARCHAR(255) NOT NULL, "
			+ "amount DECIMAL(12, 2) CHECK (amount > 0.0), "
			+ "comments VARCHAR(255), "
			+ "image BLOB "
			+ ");";
	final String managerSchema = "CREATE TABLE managers ("
			+ "manager_id UNIQUE NOT NULL CHECK (manager_id >= 0), "
			+ "username VARCHAR(255) UNIQUE NOT NULL, "
			+ "password VARCHAR(255), "
			+ "firstname VARCHAR(255), "
			+ "lastname VARCHAR(255) "
			+ ");";
	String[] schemas =     {  employeeSchema,   reimbursementSchema,   managerSchema  };
	String[] schemaNames = { "employeeSchema", "reimbursementSchema", "managerSchema" };
}
