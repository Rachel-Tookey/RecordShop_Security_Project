CREATE TABLE staff (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(55) NOT NULL,
    last_name VARCHAR(55) NOT NULL,
    user_name VARCHAR(55) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

INSERT INTO staff (first_name, last_name)
VALUES
("Kelly", "Jones", "kjones123", "password123", "ADMIN"),
("James", "Tolen", "jtolen13", "jjtoles", "ADMIN");