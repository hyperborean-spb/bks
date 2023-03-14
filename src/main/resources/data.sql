SET search_path TO bks;

INSERT INTO client (id, date_of_birth, name, "password") VALUES(1, TO_DATE('01.01.2000', 'DD.MM.YYYY'), 'Ivan', 'Ivanov');
INSERT INTO client (id, date_of_birth, name, "password") VALUES(2, TO_DATE('02.02.2000', 'DD.MM.YYYY'), 'Pavel', 'Pavlov');
INSERT INTO client (id, date_of_birth, name, "password") VALUES(3, TO_DATE('03.03.2000', 'DD.MM.YYYY'), 'Timofey', 'Timofeyev');

INSERT INTO account (id, user_id, balance) VALUES(1, 1, 12.26);
INSERT INTO account (id, user_id, balance) VALUES(2, 2, 14.35);
INSERT INTO account (id, user_id, balance) VALUES(3, 3, 33.60);