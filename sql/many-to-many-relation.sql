CREATE TABLE IF NOT EXISTS student (
   id serial PRIMARY KEY,
   first_name VARCHAR(128) NOT NULL,
   last_name VARCHAR(128) NOT NULL,
   email VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS student_course (
   student_id INT,
   course_id INT,
   PRIMARY KEY (student_id, course_id),
   CONSTRAINT "student_id_fkey" FOREIGN KEY (student_id) REFERENCES student(id),
   CONSTRAINT "course_id_fkey" FOREIGN KEY (course_id) REFERENCES course(id)
);