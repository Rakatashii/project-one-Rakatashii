DROP TABLE IF EXISTS reimbursements;
CREATE TABLE reimbursements (
	employee_id INTEGER UNIQUE CHECK (employee_id >= 0) REFERENCES employees(employee_id), 
	request_id INTEGER PRIMARY KEY UNIQUE CHECK (request_id >= 0), 
	description VARCHAR(255) NOT NULL, 
	amount DECIMAL(12, 2) CHECK (amount > 0.0), 
	comments VARCHAR(255), 
	image BYTEA
);