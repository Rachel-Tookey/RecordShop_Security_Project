CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(55) NOT NULL,
    lastname VARCHAR(55) NOT NULL,
    username VARCHAR(55) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

INSERT INTO users (firstname, lastname, username, password, role)
VALUES
("Gillian",  "Thompson",  "GillyT10", "$2a$10$kTJMa8NiWpcGW8GC.NtUy.q28VUCpO5H1/v9exYNr8BgUgN2yWi4q" , "ADMIN");