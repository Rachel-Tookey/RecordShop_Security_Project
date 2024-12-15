CREATE TABLE purchases (
    purchase_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(40) NOT NULL,
    item_id INT NOT NULL,
    price FLOAT NOT NULL,
    purchase_date DATE NOT NULL
);


ALTER TABLE purchases
    ADD FOREIGN KEY (item_id) REFERENCES records(record_id);


INSERT INTO purchases (customer_name, item_id, price, purchase_date)
VALUES
("Gillian Thompson", 2, 2.98, '2024-07-12'),
("Sarah Thompson", 2, 2.98, '2024-07-14');