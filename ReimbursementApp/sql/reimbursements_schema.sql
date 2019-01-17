DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS reimbursements;
CREATE TABLE reimbursements (
	employee_id INTEGER CONSTRAINT Invalid_Employee_ID CHECK (employee_id >= 0) REFERENCES employees(employee_id), 
	reimbursement_id INTEGER PRIMARY KEY CONSTRAINT Invalid_Reimbursement_ID CHECK (reimbursement_id >= 0), 
	expense_name VARCHAR(255) CONSTRAINT Expense_Cannot_Be_Left_Blank NOT NULL, 
	expense_source VARCHAR(255) CONSTRAINT Source_Cannot_Be_Left_Blank NOT NULL, 
	expense_amount DECIMAL(12, 2) CONSTRAINT Amount_Must_Be_A_Positive_Number CHECK (expense_amount BETWEEN +0.0 AND +10000.0),
	expense_comments VARCHAR(255),
	img_relative_path VARCHAR(255),
	status VARCHAR(255) CONSTRAINT Invalid_Status_Value CHECK (status='pending' OR status='approved' OR status='denied'),
	CONSTRAINT Employee_Cannot_Have_Reimbursements_With_Same_ID UNIQUE(employee_id, reimbursement_id)
);