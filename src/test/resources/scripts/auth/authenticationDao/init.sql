DELETE FROM user_credentials;
DELETE FROM user_profile;
DELETE FROM web_role;

INSERT INTO web_role (id, text_id) 
VALUES (1, 'EMPLOYEE');

INSERT INTO user_profile (id, first_name, last_name, email)
VALUES (1, 'Auth', 'User', 'test@mail.com');

INSERT INTO user_profile (id, first_name, last_name, email)
VALUES (2, 'No', 'Access', 'noAccess@mail.com');

INSERT INTO user_status(user_id, account_status, app_access, updated_user_id)
VALUES (1, 'APPROVED', 1, 1);

INSERT INTO user_status(user_id, account_status, app_access, updated_user_id)
VALUES (2, 'PENDING', 0, 1);

INSERT INTO user_credentials(user_id, password)
VALUES (1, '$2a$10$mRP0e8e2WH33nKbdgJPuhe55UZwnsOr7ZQAAFfmkiKzPOnZ3ojnEa');

INSERT INTO user_credentials(user_id, password)
VALUES (2, '$2a$10$mRP0e8e2WH33nKbdgJPuhe55UZwnsOr7ZQAAFfmkiKzPOnZ3ojnEa');