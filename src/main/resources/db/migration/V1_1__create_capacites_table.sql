CREATE TABLE IF NOT EXISTS capacities
(
  capacity_id INT NOT NULL AUTO_INCREMENT,
  supplier_id INT NOT NULL,
  energy_source ENUM('COAL','GAS','NUCLEAR','HYDRO','WIND','SOLAR','BIO','WASTE') NOT NULL,
amount DOUBLE  NOT NULL,
available DOUBLE NOT NULL,
price DOUBLE NOT NULL,
PRIMARY KEY (capacity_id),
FOREIGN KEY (supplier_id) REFERENCES suppliers (id)
);