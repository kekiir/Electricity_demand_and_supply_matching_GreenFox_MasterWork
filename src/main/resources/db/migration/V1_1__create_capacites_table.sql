CREATE TABLE IF NOT EXISTS capacities
(
  capacity_id INT NOT NULL AUTO_INCREMENT,
  supplier_id INT NOT NULL,
  energy_source ENUM('COAL','GAS','NUCLEAR','HYDRO','WIND','SOLAR','BIO','WASTE') NOT NULL,
  capacity_amount DOUBLE  NOT NULL,
  available DOUBLE NOT NULL,
  price DOUBLE NOT NULL,
  capacity_from_time bigint,
  capacity_to_time bigint,
PRIMARY KEY (capacity_id),
FOREIGN KEY (supplier_id) REFERENCES suppliers (id)
);