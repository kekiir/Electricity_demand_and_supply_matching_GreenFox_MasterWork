CREATE TABLE IF NOT EXISTS demands
(
  demand_id INT NOT NULL AUTO_INCREMENT,
  consumer_id  INT NOT NULL,
  amount      FLOAT  NOT NULL,
  covered     FLOAT  NOT NULL,
  price float not null ,
PRIMARY KEY (demand_id),
FOREIGN KEY(consumer_id)REFERENCES consumers(id)
);