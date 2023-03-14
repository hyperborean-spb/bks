INSERT INTO user (date_of_birth, name, password) VALUES(TO_DATE('01.01.2000', 'DD.MM.YYYY)', 'Ivan', 'Ivanov');
INSERT INTO user (date_of_birth, name, password) VALUES(TO_DATE('02.02.2000', 'DD.MM.YYYY'), 'Pavel', 'Pavlov');
INSERT INTO user (date_of_birth, name, password) VALUES(TO_DATE('03.03.2000', 'DD.MM.YYYY'), 'Timofey', 'Timofeyev');

INSERT INTO account (user_id, balance) VALUES(1, 12.26);
INSERT INTO account (user_id, balance) VALUES(2, 14.35);
INSERT INTO account (user_id, balance) VALUES(3, 33.60);