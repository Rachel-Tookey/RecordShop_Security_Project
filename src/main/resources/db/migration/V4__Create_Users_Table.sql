CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(55) NOT NULL,
    lastname VARCHAR(55) NOT NULL,
    username VARCHAR(55) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role INT NOT NULL
);

ALTER TABLE users
    ADD FOREIGN KEY (role) REFERENCES roles(role_id);

INSERT INTO users (firstname, lastname, username, password, role)
VALUES
("Gillian",  "Thompson",  "GillyT10", "$2a$10$kTJMa8NiWpcGW8GC.NtUy.q28VUCpO5H1/v9exYNr8BgUgN2yWi4q" , 1),
("Mary", "Blossom",  "MaryB", "$10$EuHuYwIqGTSkbcoDv6CyceIAAKM1gYJd9OiIQK7jhZzQepDpy3bKy" , 2);