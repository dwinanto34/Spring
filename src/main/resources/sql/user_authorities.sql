
-- store the user authorities to database
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY NOT NULL,
    password VARCHAR(50) NOT NULL,
    enabled INT NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    PRIMARY KEY (username, authority),
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users (username, password, enabled)
VALUES
('andy', '{noop}andypassword', 1),
('tom', '{noop}tompassword', 1),
('mike', '{noop}mikepassword', 1);

INSERT INTO authorities (username, authority)
VALUES
('andy', 'ROLE_EMPLOYEE'),
('tom', 'ROLE_EMPLOYEE'),
('tom', 'ROLE_SUPERVISOR'),
('mike', 'ROLE_EMPLOYEE'),
('mike', 'ROLE_SUPERVISOR'),
('mike', 'ROLE_MANAGER');

-- preiously, we store plain text password to database
-- it is not a good idea to do so
-- we want to encrypt the password using Bcrypt
-- Why Bcrypt?
-- 1. BCrypt performs one-way encrypted hashing.
-- 2. Adds random salt to the password.
-- 3. Supports to defeat brute force attacks.

ALTER TABLE users
ALTER COLUMN password TYPE VARCHAR(68);

UPDATE users SET password = '{bcrypt}$2a$10$1gxl0k9atRyZmyhqSQ2SpO/01jQ8xjZd2kk6cCqpcMddCAOKwP9ne' WHERE username = 'andy';
UPDATE users SET password = '{bcrypt}$2a$10$taFnYaRFia771tjb9SVU/eMIdzHVDTmecWMKdw622s9jWFrxGXdkm' WHERE username = 'tom';
UPDATE users SET password = '{bcrypt}$2a$10$aB40cdsw4t9yzZbpsXZNeun5jLAxJquuq4zGl8lQ1QtWn9vYxRDSC' WHERE username = 'mike';
