CREATE TABLE IF NOT EXISTS supliers(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username varchar(100) UNIQUE not null,
    password varchar(100) not null,
    energy_source ENUM('COAL','GAS','NUCLEAR','HYDRO','WIND','SOLAR','BIO','WASTE') DEFAULT 'COAL'
);
