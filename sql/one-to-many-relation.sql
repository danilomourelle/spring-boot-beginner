CREATE TABLE IF NOT EXISTS course (
   id serial PRIMARY KEY,
   title VARCHAR(128) UNIQUE DEFAULT NULL,
   instructor_id INT DEFAULT NULL,
   CONSTRAINT "instructor_id_fkey" FOREIGN KEY (instructor_id) REFERENCES instructor(id)
);

CREATE TABLE IF NOT EXISTS review (
   id serial PRIMARY KEY,
   comment TEXT DEFAULT NULL,
   course_id INT,
   CONSTRAINT "course_id_fkey" FOREIGN KEY (course_id) REFERENCES course(id)
);