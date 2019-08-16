CREATE DATABASE `springbatch` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE  `springbatch`.`user` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(45) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;