-- Создание базы данных
CREATE DATABASE IF NOT EXISTS Human_Friends;
USE Human_Friends;

-- Создание таблицы для всех животных (родительская таблица)
CREATE TABLE Animals (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    animal_type ENUM('Pet', 'Pack_Animal') NOT NULL
);

-- Создание таблицы для домашних животных
CREATE TABLE Pets (
    id INT PRIMARY KEY,
    species ENUM('Dog', 'Cat', 'Hamster') NOT NULL,
    is_vaccinated BOOLEAN DEFAULT false,
    owner_name VARCHAR(50),
    FOREIGN KEY (id) REFERENCES Animals(id) ON DELETE CASCADE
);

-- Создание таблицы для вьючных животных
CREATE TABLE Pack_Animals (
    id INT PRIMARY KEY,
    species ENUM('Horse', 'Camel', 'Donkey') NOT NULL,
    max_load_capacity FLOAT,
    FOREIGN KEY (id) REFERENCES Animals(id) ON DELETE CASCADE
);

-- Создание таблицы для команд животных
CREATE TABLE Commands (
    id INT PRIMARY KEY AUTO_INCREMENT,
    animal_id INT,
    command_name VARCHAR(50) NOT NULL,
    FOREIGN KEY (animal_id) REFERENCES Animals(id) ON DELETE CASCADE
);

-- Заполнение таблиц данными
-- Добавление домашних животных
INSERT INTO Animals (name, birth_date, animal_type) VALUES
('Fido', '2020-01-01', 'Pet'),
('Whiskers', '2019-05-15', 'Pet'),
('Hammy', '2021-03-10', 'Pet'),
('Buddy', '2018-12-10', 'Pet'),
('Smudge', '2020-02-20', 'Pet'),
('Peanut', '2021-08-01', 'Pet'),
('Bella', '2019-11-11', 'Pet'),
('Oliver', '2020-06-30', 'Pet');

INSERT INTO Pets (id, species, is_vaccinated, owner_name) VALUES
(1, 'Dog', true, 'John Smith'),
(2, 'Cat', true, 'Emma Wilson'),
(3, 'Hamster', false, 'Mike Johnson'),
(4, 'Dog', true, 'Sarah Davis'),
(5, 'Cat', true, 'Tom Brown'),
(6, 'Hamster', false, 'Lisa Anderson'),
(7, 'Dog', true, 'James Wilson'),
(8, 'Cat', true, 'Anna White');

-- Добавление вьючных животных
INSERT INTO Animals (name, birth_date, animal_type) VALUES
('Thunder', '2015-07-21', 'Pack_Animal'),
('Sandy', '2016-11-03', 'Pack_Animal'),
('Eeyore', '2017-09-18', 'Pack_Animal'),
('Storm', '2014-05-05', 'Pack_Animal'),
('Dune', '2018-12-12', 'Pack_Animal'),
('Burro', '2019-01-23', 'Pack_Animal'),
('Blaze', '2016-02-29', 'Pack_Animal'),
('Sahara', '2015-08-14', 'Pack_Animal');

INSERT INTO Pack_Animals (id, species, max_load_capacity) VALUES
(9, 'Horse', 150.5),
(10, 'Camel', 200.0),
(11, 'Donkey', 100.0),
(12, 'Horse', 180.0),
(13, 'Camel', 220.0),
(14, 'Donkey', 120.0),
(15, 'Horse', 170.0),
(16, 'Camel', 210.0);

-- Добавление команд для животных
INSERT INTO Commands (animal_id, command_name) VALUES
(1, 'Sit'), (1, 'Stay'), (1, 'Fetch'),
(2, 'Sit'), (2, 'Pounce'),
(3, 'Roll'), (3, 'Hide'),
(4, 'Sit'), (4, 'Paw'), (4, 'Bark'),
(9, 'Trot'), (9, 'Canter'), (9, 'Gallop'),
(10, 'Walk'), (10, 'Carry Load'),
(11, 'Walk'), (11, 'Carry Load'), (11, 'Bray');

-- Удаление записей о верблюдах
DELETE FROM Pack_Animals WHERE species = 'Camel';

-- Объединение таблиц лошадей и ослов
CREATE TABLE Horses_and_Donkeys AS
SELECT * FROM Pack_Animals
WHERE species IN ('Horse', 'Donkey');

-- Создание таблицы для молодых животных (от 1 до 3 лет)
CREATE TABLE Young_Animals AS
SELECT 
    a.name,
    a.birth_date,
    TIMESTAMPDIFF(YEAR, a.birth_date, CURDATE()) AS years,
    TIMESTAMPDIFF(MONTH, a.birth_date, CURDATE()) % 12 AS months,
    CASE 
        WHEN a.animal_type = 'Pet' THEN p.species
        ELSE pa.species
    END AS species
FROM Animals a
LEFT JOIN Pets p ON a.id = p.id
LEFT JOIN Pack_Animals pa ON a.id = pa.id
WHERE a.birth_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 3 YEAR) 
    AND DATE_SUB(CURDATE(), INTERVAL 1 YEAR);

-- Создание общей таблицы всех животных с командами
CREATE TABLE All_Animals_With_Commands AS
SELECT 
    a.name,
    a.birth_date,
    CASE 
        WHEN a.animal_type = 'Pet' THEN p.species
        ELSE pa.species
    END AS species,
    a.animal_type,
    GROUP_CONCAT(c.command_name SEPARATOR ', ') AS commands
FROM Animals a
LEFT JOIN Pets p ON a.id = p.id
LEFT JOIN Pack_Animals pa ON a.id = pa.id
LEFT JOIN Commands c ON a.id = c.animal_id
GROUP BY a.id;
