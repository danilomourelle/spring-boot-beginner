CREATE TABLE IF NOT EXISTS course (
   id serial PRIMARY KEY,
   title VARCHAR(128) UNIQUE DEFAULT NULL,
   instructor_id INT DEFAULT NULL,
   CONSTRAINT "instructor_id_fkey" FOREIGN KEY (instructor_id) REFERENCES instructor(id)
);