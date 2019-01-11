DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS reimbursements;
DROP TABLE IF EXISTS employees;
CREATE TABLE employees (
	employee_id INTEGER PRIMARY KEY UNIQUE CHECK (employee_id >= 0), 
	username VARCHAR(255) UNIQUE NOT NULL, 
	password VARCHAR(255) NOT NULL,
	firstname VARCHAR(255), 
	lastname VARCHAR(255),
	num_reimbursements INTEGER CHECK (num_reimbursements >= 0)
);
INSERT INTO employees VALUES(0, 'employee', 'password', 'Randy', 'Montana', 0);

DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS reimbursements;
CREATE TABLE reimbursements (
	employee_id INTEGER CHECK (employee_id >= 0) REFERENCES employees(employee_id), 
	reimbursement_id INTEGER PRIMARY KEY UNIQUE CHECK (reimbursement_id >= 0), 
	item VARCHAR(255) NOT NULL, 
	description VARCHAR(255) NOT NULL, 
	amount DECIMAL(12, 2) CHECK (amount >= 0.0), 
	comments VARCHAR(255)
);

DROP TABLE IF EXISTS images;
CREATE TABLE images (
	image_id INTEGER PRIMARY KEY UNIQUE CHECK (image_id >= 0) REFERENCES reimbursements(reimbursement_id),
	image_name VARCHAR(255) UNIQUE NOT NULL,
	bytestream BYTEA NOT NULL
);