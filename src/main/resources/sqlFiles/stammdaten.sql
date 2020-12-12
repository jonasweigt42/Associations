INSERT INTO USER (first_name, last_name, mail_address, password, language)
values ('Jonas', 'Weigt', 'jonas@test.at', '$2a$10$uhLqWYxSDcY7kXycCOx17uZJuDeKsgHyk1ikT2dVGduT4DE4GDgl6', 'en'),
values ('Pansen', 'Pansmann', 'pansen@mailinator.com', '$2a$10$0sc.iJa8qEEGAd1wh7m0tuwyB4U3Fidhr1R0dWD.xTycsB2zdiiza', 'en');

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
('Tür', 'de'),
('bauen', 'de'),
('Dach', 'de'),
('Software', 'de'),
('Code', 'de'),
('Programm', 'de'),
('Obst', 'de'),
('gelb', 'de'),
('spontan', 'de'),
('vergesslich', 'de'),
('schön', 'de'),
('fröhlich', 'de'),
('Mann', 'de'),
('Garage', 'de'),
('Teich', 'de'),
('Fisch', 'de'),
('Giraffe', 'de'),
('Baum', 'de'),
('Schornstein', 'de'),
('Zoo', 'de'),
('Schubkarre', 'de'),
('Auto', 'de'),
('Holz', 'de'),
('Leiter', 'de'),
('Bank', 'de'),
('Couch', 'de'),
('Bauernhof', 'de'),
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

INSERT INTO ASSOCIATION(user_id, word_id, association, association_date)
values
('1', '1', 'Tür', CURRENT_TIMESTAMP),
('1', '1', 'bauen', CURRENT_TIMESTAMP),
('1', '1', 'Dach', CURRENT_TIMESTAMP),
('1', '2', 'Software', CURRENT_TIMESTAMP),
('1', '2', 'Code', CURRENT_TIMESTAMP),
('1', '2', 'Programm', CURRENT_TIMESTAMP),
('1', '3', 'Obst', CURRENT_TIMESTAMP),
('1', '3', 'Apfel', CURRENT_TIMESTAMP),
('1', '3', 'gelb', CURRENT_TIMESTAMP),
('1', '4', 'süß', CURRENT_TIMESTAMP),
('1', '4', 'Kerne', CURRENT_TIMESTAMP),
('1', '4', 'Baum', CURRENT_TIMESTAMP);



