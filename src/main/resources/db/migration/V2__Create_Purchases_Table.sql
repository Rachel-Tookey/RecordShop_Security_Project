CREATE TABLE Purchases (
    purchase_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(30) NOT NULL,
    item_id INT NOT NULL,
    price FLOAT NOT NULL,
    purchase_date DATE NOT NULL
);


ALTER TABLE Purchases
    ADD FOREIGN KEY (item_id) REFERENCES Records(record_id);


INSERT INTO Purchases (customer_name, item_id, price, purchase_date)
VALUES
("Gillian Thompson", 2, 2.98, '2024-07-12'),
("Sarah Thompson", 2, 2.98, '2024-07-14');