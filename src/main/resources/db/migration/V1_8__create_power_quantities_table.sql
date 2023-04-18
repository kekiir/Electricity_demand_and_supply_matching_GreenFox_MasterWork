CREATE TABLE IF NOT EXISTS power_quantities
(
  power_quantity_id INT NOT NULL AUTO_INCREMENT,
  capacity_id INT NOT NULL,
  power_quantity_amount DOUBLE  NOT NULL,
  from_time bigint,
  to_time bigint,
  balanced_hour_id INT NOT NULL,
PRIMARY KEY (power_quantity_id),
FOREIGN KEY (capacity_id) REFERENCES capacities(capacity_id),
FOREIGN KEY (balanced_hour_id) REFERENCES balanced_hours (balanced_hour_id)
);