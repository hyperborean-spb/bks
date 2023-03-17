--SET search_path TO bks;

--truncate client cascade;

--NOTE. dBeaver requires scheme name even if scheme is selected as default

INSERT INTO client (id, date_of_birth, name, password) VALUES(11, TO_DATE('01.01.2000', 'DD.MM.YYYY'), 'Ivan', 'Ivanov');
INSERT INTO client (id, date_of_birth, name, password) VALUES(12, TO_DATE('02.02.2000', 'DD.MM.YYYY'), 'Pavel', 'Pavlov');
INSERT INTO client (id, date_of_birth, name, password) VALUES(13, TO_DATE('03.03.2000', 'DD.MM.YYYY'), 'Timofey', 'Timofeyev');

INSERT INTO account (id, client_id, balance) VALUES(1, 11, 12.26);
INSERT INTO account (id, client_id, balance) VALUES(2, 12, 14.35);
INSERT INTO account (id, client_id, balance) VALUES(3, 13, 33.60);

INSERT INTO phone_data (id, client_id, phone) VALUES(1, 11, '79207865432');
INSERT INTO phone_data (id, client_id, phone) VALUES(2, 12, '79207865433');
INSERT INTO phone_data (id, client_id, phone) VALUES(3, 13, '79207865434');

INSERT INTO email_data (id, client_id, mail) VALUES(1, 11, 'test@mail.com');
INSERT INTO email_data (id, client_id, mail) VALUES(2, 12, 'test@yahoo.com');
INSERT INTO email_data (id, client_id, mail) VALUES(3, 13, 'test@gmail.com');