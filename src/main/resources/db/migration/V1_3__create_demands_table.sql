CREATE TABLE IF NOT EXISTS demands
(
  demand_id INT NOT NULL AUTO_INCREMENT,
  consumer_id  INT NOT NULL,
  demand_amount      DOUBLE  NOT NULL,
  remained     DOUBLE  NOT NULL,
  price DOUBLE not null ,
  demand_from_time bigint,
  demand_to_time bigint,
PRIMARY KEY (demand_id),
FOREIGN KEY(consumer_id)REFERENCES consumers(id)

);