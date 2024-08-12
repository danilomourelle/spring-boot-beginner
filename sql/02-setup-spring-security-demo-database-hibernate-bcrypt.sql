DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS role;

--
-- Table structure for table user
--
CREATE TABLE users (
  id serial PRIMARY KEY,
  username varchar NOT NULL,
  "password" varchar NOT NULL,
  enabled boolean NOT NULL,  
  first_name varchar NOT NULL,
  last_name varchar NOT NULL,
  email varchar NOT NULL
);

--
-- Dumping data for table user
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: http://www.luv2code.com/generate-bcrypt-password
--
-- Default passwords here are: fun123
--

INSERT INTO users (username, "password", enabled, first_name, last_name, email)
VALUES 
('john','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', TRUE, 'John', 'Doe', 'john@luv2code.com'),
('mary','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', TRUE, 'Mary', 'Smith', 'mary@luv2code.com'),
('susan','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', TRUE, 'Susan', 'Public', 'susan@luv2code.com');


--
-- Table structure for table role
--

CREATE TABLE role (
  id serial PRIMARY KEY,
  name varchar DEFAULT NULL
);

--
-- Dumping data for table roles
--

INSERT INTO role (name)
VALUES 
('ROLE_EMPLOYEE'),('ROLE_MANAGER'),('ROLE_ADMIN');

SET FOREIGN_KEY_CHECKS = 0;

--
-- Table structure for table users_roles
--

DROP TABLE IF EXISTS users_roles;

CREATE TABLE users_roles (
  user_id SERIAL NOT NULL,
  role_id SERIAL NOT NULL,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE cascade ON UPDATE cascade,
  CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE cascade ON UPDATE cascade 
);

SET FOREIGN_KEY_CHECKS = 1;

--
-- Dumping data for table users_roles
--

INSERT INTO users_roles (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3)