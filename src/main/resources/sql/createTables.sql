-- Main table for entity entries
--
CREATE TABLE ENTRIES (
  `uid` INT NOT NULL AUTO_INCREMENT,
  `text` text COLLATE utf8_bin NOT NULL,
  `namespace` text COLLATE utf8_bin NOT NULL,
  `id` text COLLATE utf8_bin NOT NULL,
  `label` text COLLATE utf8_bin NOT NULL,
  `is_gene_name` TINYINT(1) NOT NULL,
  `is_short_name` TINYINT(1) NOT NULL,
  `species` text COLLATE utf8_bin NOT NULL,
  `priority` INT NOT NULL,
  `source_ndx` INT NOT NULL,
  PRIMARY KEY (uid),
  CONSTRAINT SRC_FK FOREIGN KEY (source_ndx) REFERENCES `SOURCES` (`uid`)
    ON DELETE NO ACTION ON UPDATE NO ACTION, INDEX SRC_FK (source_ndx)
)
  ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Table specifying KB meta information
--
CREATE TABLE `SOURCES` (
  `uid` int(11) NOT NULL,
  `namespace` text COLLATE utf8_bin NOT NULL,
  `filename` text COLLATE utf8_bin NOT NULL,
  `label` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin
