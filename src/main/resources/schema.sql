-- DROP ALL TABLES

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS persons, programmes, roles, users, users_has_roles;

SET FOREIGN_KEY_CHECKS = 1;

-- SCHEMA CREATION

CREATE TABLE roles (
  role_id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
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

CREATE TABLE refresh_tokens (
  token_id INT(11) NOT NULL AUTO_INCREMENT,
  token VARCHAR(100) NOT NULL,
  expiration_date DATETIME NOT NULL,
  user_id INT(11) NOT NULL,
  CONSTRAINT rto_pk_tid PRIMARY KEY (token_id)
);

CREATE TABLE programmes (
  programme_id INT(3) NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  logo_url VARCHAR(150),
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

CREATE TABLE subjects (
  subject_id INT(11) NOT NULL AUTO_INCREMENT,
  subject_name VARCHAR(100) NOT NULL,
  credits INT(1),
  subscribable TINYINT(1) NOT NULL DEFAULT 0,
  programme_id INT(3) NOT NULL,
  requiredsubject_id INT(11),
  CONSTRAINT sub_pk_sid PRIMARY KEY (subject_id)
);

CREATE TABLE courses (
  course_id INT(11) NOT NULL AUTO_INCREMENT,
  id_code VARCHAR(10) NOT NULL,
  schedule VARCHAR(100) NOT NULL,
  state ENUM('CREATED', 'ACTIVE', 'FINISHED') NOT NULL DEFAULT 'CREATED',
  max_capacity INT(2),
  subject_id INT(11) NOT NULL,
  teacher_id INT(11) NOT NULL,
  CONSTRAINT cou_pk_cid PRIMARY KEY (course_id)
);

CREATE TABLE courses_has_students (
  course_id INT(11) NOT NULL,
  person_id INT(11) NOT NULL,
  final_grade DOUBLE,
  is_subscribed TINYINT(1) NOT NULL DEFAULT 1
);

CREATE VIEW student_related_courses
    AS SELECT row_number() over (order by s.subject_name) id, c.id_code, c.schedule, c.state, c.max_capacity, s.subject_name, +
            p.email, cs.final_grade, cs.is_subscribed, cs.person_id
            FROM courses c, courses_has_students cs, subjects s, persons p
            WHERE c.course_id=cs.course_id AND c.subject_id=s.subject_id
            AND c.teacher_id=p.person_id;

-- ALTERS

-- ROLES
ALTER TABLE roles ADD
  CONSTRAINT rol_uq_nam UNIQUE (name);

-- USERS
ALTER TABLE users ADD
  CONSTRAINT use_uq_nam UNIQUE (username);

-- USERS_HAS_ROLES
ALTER TABLE users_has_roles ADD
  CONSTRAINT uhr_fk_uid FOREIGN KEY (user_id)
	REFERENCES users(user_id);
ALTER TABLE users_has_roles ADD
  CONSTRAINT uhr_fk_rid FOREIGN KEY (role_id)
    REFERENCES roles(role_id);

-- REFRESH_TOKENS
ALTER TABLE refresh_tokens ADD
  CONSTRAINT rto_uq_tok UNIQUE (token);

ALTER TABLE refresh_tokens ADD
  CONSTRAINT rto_fk_uid FOREIGN KEY (user_id)
	REFERENCES users(user_id);

-- PROGRAMMES
ALTER TABLE programmes ADD
  CONSTRAINT pro_uq_nam UNIQUE (name);

-- PERSONS
ALTER TABLE persons ADD
  CONSTRAINT per_uq_ema UNIQUE (email);

ALTER TABLE persons ADD
  CONSTRAINT per_fk_uid FOREIGN KEY (user_id)
    REFERENCES users(user_id);
ALTER TABLE persons ADD
  CONSTRAINT per_fk_pid FOREIGN KEY (programme_id)
    REFERENCES programmes(programme_id);

-- SUBJECTS
ALTER TABLE subjects ADD
  CONSTRAINT sub_uq_sna UNIQUE (subject_name);

ALTER TABLE subjects ADD
  CONSTRAINT sub_fk_pid FOREIGN KEY (programme_id)
    REFERENCES programmes(programme_id);
ALTER TABLE subjects ADD
  CONSTRAINT sub_fk_rsi FOREIGN KEY (requiredsubject_id)
    REFERENCES subjects(subject_id);

-- COURSES
ALTER TABLE courses ADD
  CONSTRAINT cou_uq_idc UNIQUE (id_code);
ALTER TABLE courses ADD
  CONSTRAINT cou_fk_sui FOREIGN KEY (subject_id)
   REFERENCES subjects(subject_id);
ALTER TABLE courses ADD
  CONSTRAINT cou_fk_tei FOREIGN KEY (teacher_id)
   REFERENCES persons(person_id);

-- COURSES_HAS_STUDENTS
ALTER TABLE courses_has_students ADD
  CONSTRAINT chs_fk_pid FOREIGN KEY (person_id)
	REFERENCES persons(person_id);
ALTER TABLE courses_has_students ADD
  CONSTRAINT chs_fk_cid FOREIGN KEY (course_id)
    REFERENCES courses(course_id);