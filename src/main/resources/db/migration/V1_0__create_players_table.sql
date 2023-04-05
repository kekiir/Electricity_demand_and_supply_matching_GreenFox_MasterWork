CREATE TABLE IF NOT EXISTS suplier(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(100) UNIQUE not null,
    password varchar(100) not null,
    energy_source varchar(45)

);
