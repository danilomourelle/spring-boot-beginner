DROP TABLE IF EXISTS instructor_detail;
DROP TABLE IF EXISTS instructor;

CREATE TABLE IF NOT EXISTS instructor_detail (
   id serial PRIMARY KEY,
   youtube_channel VARCHAR(128) NOT NULL,
   hobby VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS instructor (
   id serial PRIMARY KEY,
   first_name VARCHAR(45) NOT NULL,
   last_name VARCHAR(45) NOT NULL,
   email VARCHAR(45) NOT NULL UNIQUE,
   instructor_detail_id INT,
   CONSTRAINT "instructor_detail_id_fkey" FOREIGN KEY (instructor_detail_id) REFERENCES instructor_detail(id)
);
