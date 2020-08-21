DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS WORD;

CREATE TABLE USER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  mail_address VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL
);
 
CREATE TABLE WORD (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
);


INSERT INTO WORD (name) 
values 
('Haus'),
('Informatik'),
('Banane'),
('Apfel'),
('Java'),
('Server'),
('Blockchain'),
('künstliche Intelligenz'),
('Monitor'),
('Computer'),
('Schreibtisch'),
('Politik'),
('Aktie'),
('Schrank'),
('Bier');


