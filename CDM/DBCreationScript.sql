CREATE DATABASE IF NOT EXISTS cdm;

USE cdm;

DROP TABLE IF EXISTS districts;
DROP TABLE IF EXISTS cities;

CREATE TABLE cdm.cities (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE cdm.districts (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) DEFAULT NULL,
  cityID INT(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_districts_cityID FOREIGN KEY (cityID)
    REFERENCES cdm.cities(id) ON DELETE NO ACTION ON UPDATE RESTRICT
);

INSERT INTO cities (name) VALUES ('TestCity1');
INSERT INTO cities (name) VALUES ('TestCity2');
INSERT INTO districts (name, cityID) VALUES ('TestDistrict1', 1);
INSERT INTO districts (name, cityID) VALUES ('TestDistrict2', 1);
INSERT INTO districts (name, cityID) VALUES ('TestDistrict3', 2);
INSERT INTO districts (name, cityID) VALUES ('TestDistrict4', 2);