CREATE DATABASE `springbatch` /*!40100 DEFAULT CHARACTER SET utf8 */;

DROP TABLE IF EXISTS `springbatch`.`user`;
CREATE TABLE  `springbatch`.`user` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(45) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO user(id,name) VALUES(1, 'Jack Rutorial demo 1');
INSERT INTO user(id,name) VALUES(2, 'Jack Rutorial demo 2');
INSERT INTO user(id,name) VALUES(3, 'Jack Rutorial demo 3');