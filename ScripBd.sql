DROP DATABASE books;

CREATE DATABASE IF NOT EXISTS books;
USE books;

-- Inventory Table
CREATE TABLE inventory (
  id INT AUTO_INCREMENT PRIMARY KEY,
  exam VARCHAR(255),
  level VARCHAR(255),
  material_type VARCHAR(255),
  publisher VARCHAR(255),
  year INT,
  format VARCHAR(255),
  title VARCHAR(500),
  description TEXT,
  price DECIMAL(10,2),
  img VARCHAR(255),
  drive_link VARCHAR(500)
);

SHOW TABLES;
SELECT * FROM inventory;

-- INSERTS FOR INVENTORY
INSERT INTO inventory
(exam, level, material_type, publisher, year, format, title, price, description, img, drive_link)
VALUES
-- STARTERS
('STARTERS', 'PRE A1', 'Practice tests', 'Cambridge', 2017, 'PDF,AK,MP3', 'Starters Authentic Examination Papers 1', '3.00', 'Three practice tests: PDF + MP3 + Keys','starters-aup-1.png',"https://drive.google.com/drive/folders/1bipY8yoQT0FB_pbh3XubjYjeYVL06G8b?usp=drive_link"),
('STARTERS', 'PRE A1', 'Practice tests', 'Cambridge', 2018, 'PDF,AK,MP3', 'Pre A1 Starters Mini Trainer', '2.00', 'Two practice tests: PDF + MP3 + Keys','starters-minitrainer.jpg',"https://drive.google.com/drive/folders/1bipY8yoQT0FB_pbh3XubjYjeYVL06G8b?usp=drive_link"),
-- MOVERS
('MOVERS', 'A1', 'Practice tests', 'Cambridge', 2017, 'PDF,AK,MP3', 'Movers Authentic Examination Papers 1', '3.00', 'Three practice tests: PDF + MP3 + Keys','movers-cambridge-2017.jpg',"https://drive.google.com/drive/folders/1bipY8yoQT0FB_pbh3XubjYjeYVL06G8b?usp=drive_link"),
('MOVERS', 'A1', 'Practice tests', 'Cambridge', 2018, 'PDF,AK,MP3', 'A1 Movers Mini Trainer', '3.00', 'Two practice tests: PDF + MP3 + Keys','movers-collins-2018.jpg',"https://drive.google.com/drive/folders/1bipY8yoQT0FB_pbh3XubjYjeYVL06G8b?usp=drive_link"),
-- FLYERS
('FLYERS', 'A2', 'Practice tests', 'Cambridge', 2017, 'PDF,AK,MP3', 'Flyers Authentic Examination Papers 1', '4.00', 'Three practice tests: PDF + MP3 + Keys','flyers-cambridge-2017.jpg',"https://drive.google.com/drive/folders/1bipY8yoQT0FB_pbh3XubjYjeYVL06G8b?usp=drive_link"),
('FLYERS', 'A2', 'Practice tests', 'Cambridge', 2018, 'PDF,AK,MP3', 'A2 Flyers Mini Trainer', '4.00', 'Two practice tests: PDF + MP3 + Keys','flyers-cambridge-2019.jpg',"https://drive.google.com/drive/folders/1bipY8yoQT0FB_pbh3XubjYjeYVL06G8b?usp=drive_link"),
-- KET
('KET', 'A2 KEY', 'Practice tests', 'Cambridge', 2024, 'SB,TB,AK,MP3', 'A2 KEY for Schools Trainer 2', '5.00', 'Six practice tests + Teacher''s Notes: PDF + MP3 +SB + TB + AK','ket-cambridge-2024.jpeg',"https://drive.google.com/drive/folders/1bipY8yoQT0FB_pbh3XubjYjeYVL06G8b?usp=drive_link"),
('KET', 'A2 KEY', 'Practice tests', 'Oxford', 2019, 'PDF,AK,MP3', 'KEY for Schools Exam Trainer', '5.00', 'Seven practice tests: PDF + MP3 + AK','ket-oxford-2019.png',"https://drive.google.com/drive/folders/1bipY8yoQT0FB_pbh3XubjYjeYVL06G8b?usp=drive_link");

-- Clients Table
CREATE TABLE clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    last_name_father VARCHAR(100),
    last_name_mother VARCHAR(100),
    age INT,
    user VARCHAR(100),
    email VARCHAR(150),
    password VARCHAR(255),
    country VARCHAR(100),
    registration_date DATE,
    purchases INT,
    sessions INT,
    role VARCHAR(50),
    active BOOLEAN DEFAULT TRUE
);

-- INSERTS FOR CLIENTS
INSERT INTO clients
(name, last_name_father, last_name_mother, age, user, email, password, country, registration_date, purchases, sessions, role, active)
VALUES
('Daniela', 'Ojeda', 'Arrelucea', 21, 'lila324', 'daniela.oxeda@gmail.com',"1234567", 'Peru', '2025-10-01', 3, 10,'CLIENT', TRUE),
('Maria', 'Lopez', 'Ramirez', 30, 'marial456', 'maria@gmail.com',"1234567", 'Chile', '2025-08-15', 5, 20,'CLIENT', TRUE),
('Carlos', 'Torres', 'Fernandez', 28, 'carlost33', 'carlos@gmail.com',"1234567", 'Mexico', '2025-09-05', 2, 5,'CLIENT', TRUE),
('Lucia', 'Martinez', 'Diaz', 22, 'luciam56', 'lucia@gmail.com',"1234567", 'Argentina', '2025-07-20', 1, 8,'CLIENT', TRUE),
('Admin', 'Planet', 'Books', 18, 'admin123', 'planetbooks.pe@gmail.com', '1234567', 'Peru', CURDATE(), 0, 0, 'ADMIN', TRUE);

SELECT * FROM clients;

-- Sales Table
CREATE TABLE ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT,
    inventory_id INT,
    quantity INT NOT NULL,
    total_price DECIMAL(10,2),
    payment_method VARCHAR(50),
    sale_date DATE,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (inventory_id) REFERENCES inventory(id)
);