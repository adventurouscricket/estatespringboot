CREATE SCHEMA IF NOT EXISTS `estatejspservlet`;
USE `estatejspservlet` ;

-- -----------------------------------------------------
-- Table `estatejspservlet`.`building`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estatejspservlet`.`building` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) CHARACTER SET 'utf8' NOT NULL,
  `ward` VARCHAR(255) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `street` VARCHAR(255) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `district` VARCHAR(255) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `structure` VARCHAR(255) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `numberofbasement` INT(11) NULL DEFAULT NULL,
  `buildingarea` INT(11) NULL DEFAULT NULL,
  `costrent` INT(11) NULL DEFAULT NULL,
  `costdescription` TEXT NULL DEFAULT NULL,
  `servicecost` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `carcost` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `motorbikecost` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `overtimecost` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `electricitycost` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `deposit` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `payment` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `timerent` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `timedecorator` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `managername` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `managerphone` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `type` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  -- `timecontract` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `createddate` TIMESTAMP NULL DEFAULT NULL,
  `modifieddate` TIMESTAMP NULL DEFAULT NULL,
  `createdby` VARCHAR(150) NULL DEFAULT NULL,
  `modifiedby` VARCHAR(150) NULL DEFAULT NULL,
  `direction` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `level` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `estatejspservlet`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estatejspservlet`.`user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(150) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `fullname` VARCHAR(150) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `status` TINYINT(4) NOT NULL,
  `createddate` TIMESTAMP NULL DEFAULT NULL,
  `modifieddate` TIMESTAMP NULL DEFAULT NULL,
  `createdby` VARCHAR(150) NULL DEFAULT NULL,
  `modifiedby` VARCHAR(150) NULL DEFAULT NULL,
  PRIMARY KEY (`id`));

CREATE UNIQUE INDEX `username` ON `estatejspservlet`.`user` (`username` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `estatejspservlet`.`assignmentbuilding`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estatejspservlet`.`assignmentbuilding` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `staffid` BIGINT(20) NOT NULL,
  `buildingid` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_building_id FOREIGN KEY (buildingid) REFERENCES building(id),
  CONSTRAINT fk_staff_id FOREIGN KEY (staffid) REFERENCES user(id)
  );


-- -----------------------------------------------------
-- Table `estatejspservlet`.`rentarea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estatejspservlet`.`rentarea` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `buildingid` BIGINT(20) NOT NULL,
  `value` INT NOT NULL,
  `createddate` TIMESTAMP NULL DEFAULT NULL,
  `modifieddate` TIMESTAMP NULL DEFAULT NULL,
  `createdby` VARCHAR(150) NULL DEFAULT NULL,
  `modifiedby` VARCHAR(150) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_rentarea_building_id FOREIGN KEY (buildingid) REFERENCES building(id)
  );


-- -----------------------------------------------------
-- Table `estatejspservlet`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estatejspservlet`.`role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(150) NOT NULL,
  `name` VARCHAR(150) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT un_code UNIQUE (code)
  );


-- -----------------------------------------------------
-- Table `estatejspservlet`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estatejspservlet`.`user_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `userid` BIGINT(20) NOT NULL,
  `roleid` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_user_id FOREIGN KEY (userid) REFERENCES user(id),
  CONSTRAINT fk_role_id FOREIGN KEY (roleid) REFERENCES role(id)
  );