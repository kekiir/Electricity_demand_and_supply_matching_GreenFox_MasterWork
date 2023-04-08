CREATE TABLE IF NOT EXISTS consumers
(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username varchar(100) UNIQUE not null,
  password varchar(100) not null
);