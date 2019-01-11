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