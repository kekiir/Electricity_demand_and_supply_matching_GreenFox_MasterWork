CREATE TABLE IF NOT EXISTS balanced_hours
(
  balanced_hour_id INT NOT NULL AUTO_INCREMENT,
balanced_hour_price DOUBLE  NOT NULL,
balanced_hour_from_time BIGINT,
  balanced_hour_to_time BIGINT,
PRIMARY KEY (balanced_hour_id)
);