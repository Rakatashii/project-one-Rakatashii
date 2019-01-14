DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS reimbursements;
DROP TABLE IF EXISTS employees;
CREATE TABLE employees (
	employee_id INTEGER PRIMARY KEY CONSTRAINT Employee_ID_Must_Be_Unique UNIQUE CONSTRAINT Invalid_Employee_ID CHECK (employee_id >= 0), 
	username VARCHAR(255) CONSTRAINT Employee_Username_Must_Be_Unique UNIQUE CONSTRAINT Employee_Username_Cannot_Be_Null NOT NULL, 
	password VARCHAR(255) CONSTRAINT Employee_Password_Cannot_Be_Null NOT NULL,
	firstname VARCHAR(255), 
	lastname VARCHAR(255),
	num_reimbursements INTEGER CONSTRAINT Invalid_Number_Of_Reimbursements CHECK (num_reimbursements >= 0)
);
INSERT INTO employees VALUES(0, 'employee', 'password', 'Randy', 'Montana', 0);

DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS reimbursements;
CREATE TABLE reimbursements (
	employee_id INTEGER CONSTRAINT Invalid_Employee_ID CHECK (employee_id >= 0) REFERENCES employees(employee_id), 
	reimbursement_id INTEGER PRIMARY KEY CONSTRAINT Invalid_Reimbursement_ID CHECK (reimbursement_id >= 0), 
	expense VARCHAR(255) CONSTRAINT Expense_Cannot_Be_Left_Blank NOT NULL, 
	source VARCHAR(255) CONSTRAINT Source_Cannot_Be_Left_Blank NOT NULL, 
	amount DECIMAL(12, 2) CONSTRAINT Amount_Must_Be_A_Positive_Number CHECK (amount BETWEEN +0.0 AND +10000.0),
	comments VARCHAR(255),
	status VARCHAR(255) CONSTRAINT Invalid_Status_Value CHECK (status='pending' OR status='approved' OR status='denied'),
	CONSTRAINT Employee_Cannot_Have_Reimbursements_With_Same_ID UNIQUE(employee_id, reimbursement_id)
);

DROP TABLE IF EXISTS images;
CREATE TABLE images (
	employee_id INTEGER CONSTRAINT Invalid_Employee_ID CHECK (employee_id >= 0) REFERENCES employees(employee_id),
	reimbursement_id INTEGER CONSTRAINT Invalid_Image_ID CHECK (reimbursement_id >= 0) REFERENCES reimbursements(reimbursement_id),
	image_name VARCHAR(255) CONSTRAINT Image_Name_Cannot_Be_Left_Blank NOT NULL,
	image_length INT CONSTRAINT Invalid_Image_Size CHECK (image_length > 0),
	bytestream BYTEA CONSTRAINT Image_Has_No_Contents NOT NULL,
	PRIMARY KEY (employee_id, reimbursement_id),
	CONSTRAINT Reimbursement_Images_Must_Be_Unique UNIQUE(employee_id, image_name)
);