CREATE TABLE IF NOT EXISTS capacities
(
  capacity_id INT NOT NULL AUTO_INCREMENT,
  supplier_id  INT NOT NULL,
  energy_source ENUM('COAL','GAS','NUCLEAR','HYDRO','WIND','SOLAR','BIO','WASTE') NOT NULL,
  amount      FLOAT  NOT NULL,
  available     FLOAT  NOT NULL,
  price float not null,
PRIMARY KEY (capacity_id), FOREIGN KEY(supplier_id)REFERENCES suppliers(id)
)