CREATE TABLE `test`.`payment` (
  `paymentId` BIGINT NOT NULL AUTO_INCREMENT,
  `customerId` BIGINT NULL,
  `amount` DOUBLE NULL,
  `paymentDate` DATE NULL,
  PRIMARY KEY (`paymentId`));
  
  
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1001', '1020', '2020-11-11');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1002', '1111', '1994-01-01');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1003', '3227', '1988-05-04');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1004', '7888', '2001-12-08');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1005', '4324', '2000-01-05');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1006', '6434', '2010-02-10');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1007', '3243', '2014-11-09');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1008', '8866', '2015-12-03');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1009', '9786', '2020-10-03');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1010', '5322', '2011-05-05');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1011', '6777', '2009-09-10');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1012', '9899', '2015-06-12');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1013', '5225', '2017-03-01');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1014', '5474', '1994-03-06');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1015', '8123', '1967-02-08');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1016', '2349', '1976-01-08');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1017', '9090', '1990-08-09');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1018', '7764', '1992-05-08');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1019', '8765', '1997-01-07');
INSERT INTO `test`.`payment` (`customerId`, `amount`, `paymentDate`) VALUES ('1020', '3244', '1980-12-10');
