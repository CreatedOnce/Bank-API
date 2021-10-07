INSERT INTO CLIENT (first_name, last_name)
VALUES ('Ivan', 'Petrov'),
       ('Mikhail', 'Pavlov'),
       ('Nikita', 'Felixov');

INSERT INTO ACCOUNT (number, balance, client_id)
VALUES ('40804810200003497183', 0, 1);

INSERT INTO CARD (number, month, year, code, account_id)
VALUES ('42024305346286324576', '07', '2028', '353', 1);
