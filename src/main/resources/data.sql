INSERT INTO client (id, date_of_birth, name, password) VALUES(1, TO_DATE('01.01.2000', 'DD.MM.YYYY'), 'Ivan Ivanov', 'passIvanov');
INSERT INTO client (id, date_of_birth, name, password) VALUES(2, TO_DATE('02.02.2000', 'DD.MM.YYYY'), 'Pavel Pavlov', 'passPavlov');
INSERT INTO client (id, date_of_birth, name, password) VALUES(3, TO_DATE('03.03.2000', 'DD.MM.YYYY'), 'Timofey Timofeyev', 'passTimofeyev');

INSERT INTO account (id, client_id, balance) VALUES(1, 1, 12.26);
INSERT INTO account (id, client_id, balance) VALUES(2, 2, 14.35);
INSERT INTO account (id, client_id, balance) VALUES(3, 3, 33.60);

INSERT INTO phone_data (id, client_id, phone) VALUES(1, 1, '79207865432');
INSERT INTO phone_data (id, client_id, phone) VALUES(2, 2, '79207865433');
INSERT INTO phone_data (id, client_id, phone) VALUES(3, 3, '79207865434');

INSERT INTO email_data (id, client_id, mail) VALUES(1, 1, 'test@mail.com');
INSERT INTO email_data (id, client_id, mail) VALUES(2, 2, 'test@yahoo.com');
INSERT INTO email_data (id, client_id, mail) VALUES(3, 3, 'test@gmail.com');