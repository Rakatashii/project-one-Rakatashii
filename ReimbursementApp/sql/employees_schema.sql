DROP TABLE IF EXISTS employees;
CREATE TABLE employees (
	employee_id INTEGER PRIMARY KEY UNIQUE CHECK (employee_id >= 0), 
	username VARCHAR(255) UNIQUE NOT NULL, 
	password VARCHAR(255) NOT NULL,
	firstname VARCHAR(255), 
	lastname VARCHAR(255),
	num_reimbursements INTEGER CHECK (num_reimbursements >= 0)
);