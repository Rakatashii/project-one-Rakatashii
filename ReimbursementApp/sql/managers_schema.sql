DROP TABLE IF EXISTS managers;
CREATE TABLE managers (
	manager_id INTEGER PRIMARY KEY UNIQUE CHECK (manager_id >= 0), 
	username VARCHAR(255) UNIQUE NOT NULL, 
	password VARCHAR(255) NOT NULL, 
	firstname VARCHAR(255), 
	lastname VARCHAR(255) 
);
	