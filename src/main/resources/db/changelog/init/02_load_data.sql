INSERT INTO client (id, date_of_birth, name, password) VALUES(nextval('MY_CUSTOM_SEQ'), TO_DATE('01.01.2000', 'DD.MM.YYYY'), 'Ivan Ivanov', 'passIvanov');
INSERT INTO account (id, client_id, balance) VALUES(currval('MY_CUSTOM_SEQ'), currval('MY_CUSTOM_SEQ'), 12.26);
INSERT INTO phone_data (id, client_id, phone) VALUES(currval('MY_CUSTOM_SEQ'),  currval('MY_CUSTOM_SEQ'), '79207865432');
INSERT INTO email_data (id, client_id, mail) VALUES(currval('MY_CUSTOM_SEQ'), currval('MY_CUSTOM_SEQ'), 'test@mail.com');

INSERT INTO client (id, date_of_birth, name, password) VALUES(nextval('MY_CUSTOM_SEQ'), TO_DATE('02.02.2000', 'DD.MM.YYYY'), 'Pavel Pavlov', 'passPavlov');
INSERT INTO account (id, client_id, balance) VALUES(currval('MY_CUSTOM_SEQ'), currval('MY_CUSTOM_SEQ'), 14.35);
INSERT INTO phone_data (id, client_id, phone) VALUES(currval('MY_CUSTOM_SEQ'),  currval('MY_CUSTOM_SEQ'),  '79207865433');
INSERT INTO email_data (id, client_id, mail) VALUES(currval('MY_CUSTOM_SEQ'), currval('MY_CUSTOM_SEQ'), 'test@yahoo.com');

INSERT INTO client (id, date_of_birth, name, password) VALUES(nextval('MY_CUSTOM_SEQ'), TO_DATE('03.03.2000', 'DD.MM.YYYY'), 'Timofey Timofeyev', 'passTimofeyev');
INSERT INTO account (id, client_id, balance) VALUES(currval('MY_CUSTOM_SEQ'), currval('MY_CUSTOM_SEQ'), 33.60);
INSERT INTO phone_data (id, client_id, phone) VALUES(currval('MY_CUSTOM_SEQ'),  currval('MY_CUSTOM_SEQ'), '79207865434');
INSERT INTO email_data (id, client_id, mail) VALUES(currval('MY_CUSTOM_SEQ'), currval('MY_CUSTOM_SEQ'), 'test@gmail.com');