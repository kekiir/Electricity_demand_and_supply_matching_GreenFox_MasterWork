CREATE TABLE IF NOT EXISTS capacities
(
  capacity_id INT NOT NULL AUTO_INCREMENT,
  suplier_id  INT NOT NULL,
  amount      FLOAT  NOT NULL,
  available     FLOAT  NOT NULL,
  price float not null ,
PRIMARY KEY (capacity_id), FOREIGN KEY(suplier_id)REFERENCES supliers(id)
)