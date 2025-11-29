

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
  price VARCHAR(50)
);

-- Clients Table
CREATE TABLE clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name_father VARCHAR(100),
    last_name_mother VARCHAR(100),
    age INT,
    username VARCHAR(100),
    email VARCHAR(150),
    country VARCHAR(100),
    registration_date DATE,
    purchases INT,
    sessions INT,
    active BOOLEAN DEFAULT TRUE
);

SHOW TABLES;
SELECT * FROM inventory;

-- INSERTS FOR INVENTORY (NO ID BECAUSE AUTO_INCREMENT)
INSERT INTO inventory
(exam, level, material_type, publisher, year, format, title, price)
VALUES
('STARTERS', 'Pre A1', 'Practice tests', 'Cambridge', 2017, 'PDF,AK,MP3', 'Starters Authentic Examination Papers 1', 'S/ 10.00'),
('STARTERS', 'Pre A1', 'Practice tests', 'Cambridge', 2018, 'PDF,AK,MP3', 'Starters Authentic Examination Papers 2', 'S/ 10.00'),
('MOVERS', 'A1', 'Practice tests', 'Cambridge', 2017, 'PDF,AK,MP3', 'Movers Authentic Examination Papers 1', 'S/ 12.00'),
('MOVERS', 'A1', 'Practice tests', 'Cambridge', 2018, 'PDF,AK,MP3', 'Movers Authentic Examination Papers 2', 'S/ 12.00'),
('FLYERS', 'A2', 'Practice tests', 'Cambridge', 2017, 'PDF,AK,MP3', 'Flyers Authentic Examination Papers 1', 'S/ 15.00'),
('FLYERS', 'A2', 'Practice tests', 'Cambridge', 2018, 'PDF,AK,MP3', 'Flyers Authentic Examination Papers 2', 'S/ 15.00'),
('KET', 'A2', 'Practice tests', 'Cambridge', 2024, 'SB,TB,AK,MP3', 'KEY for Schools Trainer 2 Six Practice Tests and Teachers Notes', 'S/ 18.00'),
('KET', 'A2', 'Practice tests', 'Cambridge', 2019, 'PDF,AK,MP3', 'Succeed in A2 Key 8 Practice Tests', 'S/ 18.00'),
('PET', 'B1', 'Practice tests', 'Cambridge', 2024, 'SB,TB,AK,MP3', 'PET for Schools Trainer 2 Six Practice Tests and Teachers Notes', 'S/ 18.00'),
('PET', 'B1', 'Practice tests', 'Cambridge', 2019, 'PDF,AK,MP3', 'Succeed in B1 PET 8 Practice Tests', 'S/ 18.00');

-- INSERTS FOR CLIENTS
INSERT INTO clients
(first_name, last_name_father, last_name_mother, age, username, email, country, registration_date, purchases, sessions, active)
VALUES
('Juan', 'Perez', 'Gomez', 25, 'juanp', 'juanp@gmail.com', 'Peru', '2024-10-01', 3, 10, TRUE),
('Maria', 'Lopez', 'Ramirez', 30, 'marial', 'maria@gmail.com', 'Chile', '2024-08-15', 5, 20, TRUE),
('Carlos', 'Torres', 'Fernandez', 28, 'carlost', 'carlos@gmail.com', 'Mexico', '2024-09-05', 2, 5, TRUE),
('Lucia', 'Martinez', 'Diaz', 22, 'luciam', 'lucia@gmail.com', 'Argentina', '2024-07-20', 1, 8, TRUE),
('Diego', 'Sanchez', 'Ruiz', 35, 'diegos', 'diego@gmail.com', 'Colombia', '2024-06-10', 7, 15, TRUE);
