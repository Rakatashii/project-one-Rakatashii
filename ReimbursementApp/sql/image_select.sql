SELECT employee_id, reimbursement_id, 
	(SELECT SUBSTRING(image_name, '[a-zA-Z0-9]+\..*')) AS image_name, 
	image_length, LENGTH(bytestream) AS bytestream_length
	FROM images;