

DROP TABLE IF EXISTS report;

CREATE TABLE report  (
    id INT NOT NULL PRIMARY KEY,
    date DATETIME,
    impression BIGINT,
    clicks INT,
    earning DECIMAL(12,4)
  )ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;