CREATE TABLE records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    record_name VARCHAR(55) NOT NULL,
    artist VARCHAR(55) NOT NULL,
    quantity INT NOT NULL,
    price FLOAT NOT NULL
);

INSERT INTO records (record_name, artist, quantity, price)
VALUES
('Thriller', 'Michael Jackson', 1, 9.99),
('Back in Black', 'AC/DC', 2, 2.98),
('The Dark Side of the Moon', 'Pink Floyd', 5, 9.95),
('The Wall', 'Pink Floyd', 8, 19.99);