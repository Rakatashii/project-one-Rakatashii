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