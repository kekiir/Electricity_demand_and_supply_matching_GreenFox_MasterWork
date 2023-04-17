CREATE TABLE IF NOT EXISTS time_ranges (
  id INT NOT NULL AUTO_INCREMENT,
  from_time bigint,
  to_time bigint,
  capacity_id INT,
  demand_id INT,
  offer_id int,
  PRIMARY KEY (id),
FOREIGN KEY (capacity_id) REFERENCES capacities (capacity_id),
FOREIGN KEY (demand_id) REFERENCES demands (demand_id)

);