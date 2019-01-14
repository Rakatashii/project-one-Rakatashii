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