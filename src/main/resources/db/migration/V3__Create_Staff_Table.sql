CREATE TABLE staff (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(55) NOT NULL,
    last_name VARCHAR(55) NOT NULL
);

INSERT INTO staff (first_name, last_name)
VALUES
("Kelly", "Jones"),
("James", "Tolen");