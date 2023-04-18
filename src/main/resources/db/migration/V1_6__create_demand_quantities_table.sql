CREATE TABLE IF NOT EXISTS demand_quantities
(
  demand_quantity_id INT NOT NULL AUTO_INCREMENT,
  demand_id INT NOT NULL,
  amount DOUBLE  NOT NULL,
  demand_quantity_amount DOUBLE  NOT NULL,
  from_time bigint,
  to_time bigint,
  balanced_hour_id INT NOT NULL,
PRIMARY KEY (demand_quantity_id),
FOREIGN KEY(demand_id) REFERENCES demands (demand_id),
FOREIGN KEY (balanced_hour_id) REFERENCES balanced_hours (balanced_hour_id)
);