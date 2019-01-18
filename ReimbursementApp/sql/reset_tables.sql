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
INSERT INTO employees VALUES(1, 'Jstunna', 'drstunna', 'json', 'soffer', 0);
INSERT INTO employees VALUES(2, 'bearo', 'jacksonh', 'john', 'leary', 0);
INSERT INTO employees VALUES(3, 'tcracken', 'lauren', 'Trever', 'McCracken', 0);
INSERT INTO employees VALUES(4, 'HarrisV', 'password', 'nathn', 'VH', 0);
INSERT INTO employees VALUES(6, 'employee99', 'p4sswOrd', 'Lily', 'Fern', 0);
INSERT INTO employees VALUES(7, 'dnew', 'newlinL5', 'Dan', 'Newlin', 0);
INSERT INTO employees VALUES(8, 'sassy', 'jackson', 'sassy', 'jackson', 0);
INSERT INTO employees VALUES(9, 'toleary', 'weedlord', 'Tim', 'Oleary', 0);
INSERT INTO employees VALUES(10, 'rsweetwps', 'password', 'ryan', 'sweet', 0);
INSERT INTO employees VALUES(11, 'employee23p', 'passowrd', 'Bill', 'Saggin', 0);
INSERT INTO employees VALUES(12, 'krod', 'vero5', 'kyle', 'sanderson', 0);
INSERT INTO employees VALUES(13, 'ghayezer', 'password', 'gavin', 'hacker', 0);
INSERT INTO employees VALUES(14, 'employee23k', 'passowrd', 'Don', 'Lewis', 0);
INSERT INTO employees VALUES(15, 'ddecembre', 'ny234', 'daniel', 'decembre', 0);

CREATE TABLE reimbursements (
	employee_id INTEGER CONSTRAINT Invalid_Employee_ID CHECK (employee_id >= 0) REFERENCES employees(employee_id), 
	reimbursement_id INTEGER PRIMARY KEY CONSTRAINT Invalid_Reimbursement_ID CHECK (reimbursement_id >= 0), 
	expense_name VARCHAR(255) CONSTRAINT Expense_Cannot_Be_Left_Blank NOT NULL, 
	expense_source VARCHAR(255) CONSTRAINT Source_Cannot_Be_Left_Blank NOT NULL, 
	expense_amount DECIMAL(12, 2) CONSTRAINT Amount_Must_Be_A_Positive_Number CHECK (expense_amount BETWEEN +0.0 AND +100000.0),
	expense_comments VARCHAR(255),
	img_relative_path VARCHAR(255),
	status VARCHAR(255) CONSTRAINT Invalid_Status_Value CHECK (status='pending' OR status='approved' OR status='denied'),
	CONSTRAINT Employee_Cannot_Have_Reimbursements_With_Same_ID UNIQUE(employee_id, reimbursement_id)
);

CREATE TABLE images (
	employee_id INTEGER CONSTRAINT Invalid_Employee_ID CHECK (employee_id >= 0) REFERENCES employees(employee_id),
	reimbursement_id INTEGER CONSTRAINT Invalid_Image_ID CHECK (reimbursement_id >= 0) REFERENCES reimbursements(reimbursement_id),
	relative_path VARCHAR(255) CONSTRAINT Image_Must_Have_Relative_Path NOT NULL,
	absolute_path VARCHAR(255) CONSTRAINT Image_Must_Have_Absolute_Path NOT NULL,
	image_length INT CONSTRAINT Invalid_Image_Size CHECK (image_length > 0),
	bytestream BYTEA CONSTRAINT Image_Has_No_Contents NOT NULL,
	PRIMARY KEY (employee_id, reimbursement_id),
	CONSTRAINT Reimbursement_Images_Must_Be_Unique UNIQUE(employee_id, relative_path)
);