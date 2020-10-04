INSERT INTO USER (first_name, last_name, mail_address, password, language)
values ('Jonas', 'Weigt', 'jonas@test.at', '$2a$10$uhLqWYxSDcY7kXycCOx17uZJuDeKsgHyk1ikT2dVGduT4DE4GDgl6', 'en');

INSERT INTO WORD (name, language) 
values 
('Haus', 'de'),
('Informatik', 'de'),
('Banane', 'de'),
('Apfel', 'de'),
('Java', 'de'),
('Server', 'de'),
('Blockchain', 'de'),
('künstliche Intelligenz', 'de'),
('Monitor', 'de'),
('Computer', 'de'),
('Schreibtisch', 'de'),
('Politik', 'de'),
('Aktie', 'de'),
('Schrank', 'de'),
('Bier', 'de');

INSERT INTO WORD (name, language) 
values 
('House', 'en'),
('Computer', 'en'),
('Banana', 'en'),
('Apple', 'en'),
('Java', 'en'),
('Server', 'en'),
('Blockchain', 'en'),
('Table', 'en'),
('Picture', 'en'),
('Hair', 'en'),
('Desk', 'en'),
('Politics', 'en'),
('Share', 'en'),
('Dog', 'en'),
('Beer', 'en');

INSERT INTO ASSOCIATION(user_id, word_id, association1, association2, association3, association_date)
values
('1', '1', 'Tür', 'bauen', 'Dach', CURRENT_TIMESTAMP),
('1', '2', 'Software', 'Code', 'Programm', CURRENT_TIMESTAMP),
('1', '3', 'Obst', 'Apfel', 'gelb', CURRENT_TIMESTAMP);



