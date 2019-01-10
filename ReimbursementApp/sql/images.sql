DROP IF EXISTS TABLE images;
CREATE TABLE images (
	item_id INTEGER PRIMARY KEY REFERENCES reimbursements.item_id CHECK (item_id >= 0),
	img_byes BYTEA NOT NULL
);