-- Table specifying KB meta information
--
CREATE TABLE `SOURCES` (
  `uid` int(11) NOT NULL,
  `namespace` varchar(20) NOT NULL,
  `filename` varchar(60) NOT NULL,
  `label` varchar(40) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- Main table for entity entries
--
CREATE TABLE `ENTRIES` (
  `uid` INT NOT NULL AUTO_INCREMENT,
  `text` varchar(80) NOT NULL,
  `namespace` varchar(40) NOT NULL,
  `id` varchar(80)  NOT NULL,
  `label` varchar(40)  NOT NULL,
  `is_gene_name` TINYINT(1) NOT NULL,
  `is_short_name` TINYINT(1) NOT NULL,
  `species` varchar(256) NOT NULL,
  `priority` INT NOT NULL,
  `source_ndx` INT NOT NULL,
  PRIMARY KEY (uid),
  INDEX `text_ndx` (`text`),
  INDEX `namespace_ndx` (`namespace`),
  INDEX `id_ndx` (`id`),
  INDEX `label_ndx` (`label`),
  KEY `SRC_FK`(`source_ndx`),
  CONSTRAINT SRC_FK FOREIGN KEY(`source_ndx`) REFERENCES `SOURCES`(`uid`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- A table of alternate keys which reference corresponding entities in the entries table
--
CREATE TABLE `TKEYS` (
  `text` varchar(80) NOT NULL,
  `entry_ndx` INT NOT NULL,
  KEY `ENT_FK`(`entry_ndx`),
  CONSTRAINT ENT_FK FOREIGN KEY(`entry_ndx`) REFERENCES `ENTRIES`(`uid`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
