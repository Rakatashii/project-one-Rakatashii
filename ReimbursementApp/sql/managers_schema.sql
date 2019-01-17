DROP TABLE IF EXISTS managers;
CREATE TABLE managers (
	manager_id INTEGER PRIMARY KEY CONSTRAINT Manager_ID_Must_Be_Unique UNIQUE CONSTRAINT Invalid_Manager_ID CHECK (manager_id >= 0), 
	username VARCHAR(255) CONSTRAINT Manager_Username_Must_Be_Unique UNIQUE CONSTRAINT Manager_Username_Cannot_Be_Null NOT NULL, 
	password VARCHAR(255) CONSTRAINT Manager_Password_Cannot_Be_Null NOT NULL,
	firstname VARCHAR(255), 
	lastname VARCHAR(255)
);
INSERT INTO managers VALUES(0, 'manager', 'password', 'Randy', 'Montana');