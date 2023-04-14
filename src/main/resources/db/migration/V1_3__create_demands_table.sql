CREATE TABLE IF NOT EXISTS demands
(
  demand_id INT NOT NULL AUTO_INCREMENT,
  consumer_id  INT NOT NULL,
  amount      DOUBLE  NOT NULL,
  covered     DOUBLE  NOT NULL,
  price DOUBLE not null ,
PRIMARY KEY (demand_id),
FOREIGN KEY(consumer_id)REFERENCES consumers(id)
);