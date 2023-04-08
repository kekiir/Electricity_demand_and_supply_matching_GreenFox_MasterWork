CREATE TABLE IF NOT EXISTS hours (
  hour_id INT NOT NULL AUTO_INCREMENT,
  hour_from DATETIME NOT NULL,
  hour_to DATETIME NOT NULL,
  hour_day DATETIME NOT NULL,
  capacity_id INT NOT NULL,
  PRIMARY KEY (hour_id),
FOREIGN KEY (capacity_id) REFERENCES capacities (capacity_id)
);