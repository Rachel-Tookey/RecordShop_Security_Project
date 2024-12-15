CREATE TABLE users (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(55) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

INSERT INTO users (user_name, password, role)
VALUES
("kjones123", "password123", "ADMIN"),
("jtolen13", "jjtoles", "ADMIN");