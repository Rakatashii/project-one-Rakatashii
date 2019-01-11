DROP TABLE IF EXISTS images;
CREATE TABLE images (
	image_id INTEGER PRIMARY KEY UNIQUE CHECK (image_id >= 0) REFERENCES reimbursements(reimbursement_id),
	image_name VARCHAR(255) UNIQUE NOT NULL,
	bytestream BYTEA NOT NULL
);