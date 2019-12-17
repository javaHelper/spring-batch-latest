CREATE TABLE `test`.`customer` (
  `id` MEDIUMINT(8) unsigned NOT NULL,
  `firstName` VARCHAR(255) default NULL,
  `lastName` VARCHAR(255) default NULL,
  `birthdate` DATE NULL,
  PRIMARY KEY (`id`)
  ) AUTO_INCREMENT=1;
