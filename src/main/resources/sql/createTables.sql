-- Table specifying KB meta information
--
CREATE TABLE `SOURCES` (
  `uid` int(11) NOT NULL,
  `namespace` text NOT NULL,
  `filename` text NOT NULL,
  `label` text NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- Main table for entity entries
--
CREATE TABLE `ENTRIES` (
  `uid` INT NOT NULL AUTO_INCREMENT,
  `text` text NOT NULL,
  `namespace` text NOT NULL,
  `id` text  NOT NULL,
  `label` text  NOT NULL,
  `is_gene_name` TINYINT(1) NOT NULL,
  `is_short_name` TINYINT(1) NOT NULL,
  `species` text NOT NULL,
  `priority` INT NOT NULL,
  `source_ndx` INT NOT NULL,
  PRIMARY KEY (uid),
  KEY `SRC_FK`(`source_ndx`),
  CONSTRAINT SRC_FK FOREIGN KEY(`source_ndx`) REFERENCES `SOURCES`(`uid`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

