-- INSERTS

INSERT INTO roles(name) VALUES ('Student'), ('Admin'), ('Teacher');

INSERT INTO programmes(name) VALUES ('Computer and Systems Engineering');

INSERT INTO subjects(subject_name, credits, subscribable, programme_id, requiredsubject_id)
    VALUES ('Cálculo I', 3, 1, 1, NULL), ('Cálculo II', 3, 1, 1, 1);

--INSERT INTO courses(id_code, schedule, max_capacity, subject_id, teacher_id)
--    VALUES ('1-0', 'MARTES: 8-10 RA-301\nJUEVES: 10-12 L-301', 20, 1, 1);

--INSERT INTO courses_has_students(course_id, person_id) VALUES (1, 2);