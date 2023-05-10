-- SCHEMA CREATION

CREATE TABLE roles (
  role_id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(30) DEFAULT NULL,
  CONSTRAINT role_pk_rid PRIMARY KEY (role_id)
);

CREATE TABLE users (
  user_id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(60) NOT NULL,
  CONSTRAINT user_pk_uid PRIMARY KEY (user_id)
);

CREATE TABLE users_has_roles (
  user_id INT(11) NOT NULL,
  role_id INT(11) NOT NULL
);

CREATE TABLE programmes (
  programme_id INT(3) NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  CONSTRAINT pro_pk_pid PRIMARY KEY (programme_id)
);

CREATE TABLE persons (
  person_id INT(3) NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(60) NOT NULL,
  last_name VARCHAR(60) NOT NULL,
  birth_date DATE,
  email VARCHAR(100),
  phone_number VARCHAR(20),
  address VARCHAR(100),
  user_id INT(11) NOT NULL,
  programme_id INT(3) NOT NULL,
  CONSTRAINT per_pk_pid PRIMARY KEY (person_id)
);

-- ALTERS

ALTER TABLE roles ADD
  CONSTRAINT rol_uq_nam UNIQUE (name);

ALTER TABLE users ADD
  CONSTRAINT use_uq_nam UNIQUE (username);

ALTER TABLE users_has_roles ADD
  CONSTRAINT uhr_fk_uid FOREIGN KEY (user_id)
	REFERENCES users(user_id);
ALTER TABLE users_has_roles ADD
  CONSTRAINT uhr_fk_rid FOREIGN KEY (role_id)
    REFERENCES roles(role_id);

ALTER TABLE programmes ADD
  CONSTRAINT pro_uq_nam UNIQUE (name);

ALTER TABLE persons ADD
  CONSTRAINT per_uq_ema UNIQUE (email);

ALTER TABLE persons ADD
  CONSTRAINT per_fk_uid FOREIGN KEY (user_id)
    REFERENCES users(user_id);
ALTER TABLE persons ADD
  CONSTRAINT per_fk_pid FOREIGN KEY (programme_id)
    REFERENCES programmes(programme_id);