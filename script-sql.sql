DROP DATABASE IF EXISTS crud_manager;

CREATE DATABASE IF NOT EXISTS crud_manager;

USE crud_manager;

CREATE TABLE IF NOT EXISTS users(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    sexo ENUM('M', 'F'),
    email VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS posts(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    post_date DATE NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY(user_id) 
    REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS companies (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(128) NOT NULL,
	`role` VARCHAR(128) NOT NULL,
	`start` DATE NOT NULL,
	`end` DATE,
	user_id INT NOT NULL,
	FOREIGN KEY(user_id)
	REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS companies (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` VARCHAR(128) NOT NULL,
`role` VARCHAR(128) NOT NULL,
`start` DATE NOT NULL,
`end` DATE,
user_id INT NOT NULL,
FOREIGN KEY(user_id)
REFERENCES users(id)
);

INSERT INTO users VALUES
(DEFAULT, "Emerson Carvalho", "M", "emerson@mail.com"),
(DEFAULT, "Luiza Carvalho", "F", "lu@mail.com"),
(DEFAULT, "Elenice Carvalho", "F", "le@mail.com"),
(DEFAULT, "Noé Carvalho", "M", "noe@mail.com"),
(DEFAULT, "Rosânia Carvalho", "F", "ro@mail.com");

INSERT INTO posts VALUES
(DEFAULT, "Olá do Emerson", CURDATE(), 1),
(DEFAULT, "Olá da Luiza", CURDATE(), 2),
(DEFAULT, "Olá da Denise", CURDATE(), 3),
(DEFAULT, "Olá do Noé", CURDATE(), 4),
(DEFAULT, "Olá da Rosânia", CURDATE(), 5),
(DEFAULT, "Olá da Rosânia 1", CURDATE(), 5),
(DEFAULT, "Olá da Rosânia 2", CURDATE(), 5),
(DEFAULT, "Olá da Rosânia 3", CURDATE(), 5);


CREATE TABLE IF NOT EXISTS pets(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species ENUM('CAO','GATO','OUTRO') NOT NULL,
    birth_date DATE NOT NULL,
    weight DECIMAL(6,2) NOT NULL,
    neutered TINYINT(1) NOT NULL DEFAULT 0,
    user_id INT NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS appointments(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    reason TEXT NOT NULL,
    appointment_date DATE NOT NULL,
    price DECIMAL(8,2) NOT NULL,
    return_needed TINYINT(1) NOT NULL DEFAULT 0,
    pet_id INT NOT NULL,
    FOREIGN KEY(pet_id) REFERENCES pets(id)
);

CREATE TABLE IF NOT EXISTS accounts(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password CHAR(64) NOT NULL
);

INSERT INTO pets VALUES
(DEFAULT, 'Rex', 'CAO', '2020-03-10', 12.50, 1, 1),
(DEFAULT, 'Mia', 'GATO', '2021-07-22', 4.20, 1, 2),
(DEFAULT, 'Thor', 'CAO', '2019-01-15', 30.00, 0, 3),
(DEFAULT, 'Luna', 'GATO', '2022-05-30', 3.80, 0, 4),
(DEFAULT, 'Bidu', 'CAO', '2018-11-02', 8.90, 1, 5),
(DEFAULT, 'Piu', 'OUTRO', '2023-02-14', 0.15, 0, 1),
(DEFAULT, 'Nina', 'CAO', '2021-09-09', 15.00, 1, 2);

INSERT INTO appointments VALUES
(DEFAULT, 'Vacina anual', CURDATE(), 80.00, 0, 1),
(DEFAULT, 'Check-up', CURDATE(), 120.00, 1, 2),
(DEFAULT, 'Dermatite', CURDATE(), 150.00, 1, 3),
(DEFAULT, 'Vermifugação', CURDATE(), 60.00, 0, 4),
(DEFAULT, 'Cirurgia dentária', CURDATE(), 450.00, 1, 5),
(DEFAULT, 'Exame de sangue', CURDATE(), 200.00, 0, 6),
(DEFAULT, 'Vacina antirrábica', CURDATE(), 70.00, 0, 7);

INSERT INTO accounts VALUES (DEFAULT, 'admin', SHA2('admin123', 256));
