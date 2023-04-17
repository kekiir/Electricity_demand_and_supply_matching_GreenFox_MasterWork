CREATE TABLE IF NOT EXISTS contracts
(
  contract_id     INT NOT NULL AUTO_INCREMENT,
  contract_amount DOUBLE not null,
  capacity_id INT,
  demand_id INT,
PRIMARY KEY (contract_id),
FOREIGN KEY (capacity_id) REFERENCES capacities (capacity_id),
FOREIGN KEY (demand_id) REFERENCES demands (demand_id)

)