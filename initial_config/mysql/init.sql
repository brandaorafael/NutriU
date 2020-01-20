CREATE DATABASE IF NOT EXISTS `NUTRIU`;

USE `NUTRIU`;

CREATE TABLE IF NOT EXISTS `ingredient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL UNIQUE,
  `calories` int NOT NULL,
  `carbohydrates` int NOT NULL,
  `proteins` int NOT NULL,
  `fat` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;