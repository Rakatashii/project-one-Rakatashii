DROP TABLE IF EXISTS images;
CREATE TABLE images (
	employee_id INTEGER CHECK (employee_id >= 0) REFERENCES employees(employee_id),
	reimbursement_id INTEGER CHECK (reimbursement_id >= 0) REFERENCES reimbursements(reimbursement_id),
	image_name VARCHAR(255) UNIQUE NOT NULL,
	image_length INT CHECK (image_length > 0),
	bytestream BYTEA NOT NULL,
	PRIMARY KEY (employee_id, reimbursement_id)
);