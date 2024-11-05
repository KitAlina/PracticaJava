-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema vetcli
-- -----------------------------------------------------
create database if not exists vetcli;
use vetcli;
-- -----------------------------------------------------
-- Schema vetcli
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `vetcli` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `vetcli` ;

-- -----------------------------------------------------
-- Table `vetcli`.`calendar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`calendar` (
  `idcalendar` INT NOT NULL AUTO_INCREMENT,
  `day` VARCHAR(45) NULL DEFAULT NULL,
  `time` TIME NULL DEFAULT NULL,
  PRIMARY KEY (`idcalendar`))
ENGINE = InnoDB
AUTO_INCREMENT = 53
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`role` (
  `idrole` INT NOT NULL AUTO_INCREMENT,
  `r_type` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idrole`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `log` VARCHAR(45) NULL DEFAULT NULL,
  `pass` VARCHAR(45) NULL DEFAULT NULL,
  `role_idrole` INT NOT NULL,
  PRIMARY KEY (`iduser`),
  INDEX `fk_user_role` (`role_idrole` ASC) VISIBLE,
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_idrole`)
    REFERENCES `vetcli`.`role` (`idrole`))
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`doctors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`doctors` (
  `iddoctors` INT NOT NULL AUTO_INCREMENT,
  `FIO` VARCHAR(45) NULL DEFAULT NULL,
  `address` VARCHAR(120) NULL DEFAULT NULL,
  `data_birthday` DATE NULL DEFAULT NULL,
  `phone` VARCHAR(45) NULL DEFAULT NULL,
  `user_iduser` INT NOT NULL,
  PRIMARY KEY (`iddoctors`),
  INDEX `fk_doctors_user1` (`user_iduser` ASC) VISIBLE,
  CONSTRAINT `fk_doctors_user1`
    FOREIGN KEY (`user_iduser`)
    REFERENCES `vetcli`.`user` (`iduser`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`doctors_has_calendar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`doctors_has_calendar` (
  `doctors_iddoctors` INT NOT NULL,
  `calendar_idcalendar` INT NOT NULL,
  `id_time` INT NOT NULL,
  PRIMARY KEY (`doctors_iddoctors`, `calendar_idcalendar`),
  INDEX `fk_doctors_has_calendar_calendar1` (`calendar_idcalendar` ASC) VISIBLE,
  CONSTRAINT `fk_doctors_has_calendar_Calendar`
    FOREIGN KEY (`calendar_idcalendar`)
    REFERENCES `vetcli`.`calendar` (`idcalendar`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_doctors_has_calendar_calendar1`
    FOREIGN KEY (`calendar_idcalendar`)
    REFERENCES `vetcli`.`calendar` (`idcalendar`),
  CONSTRAINT `fk_doctors_has_calendar_Doctors`
    FOREIGN KEY (`doctors_iddoctors`)
    REFERENCES `vetcli`.`doctors` (`iddoctors`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_doctors_has_calendar_doctors1`
    FOREIGN KEY (`doctors_iddoctors`)
    REFERENCES `vetcli`.`doctors` (`iddoctors`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`hosts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`hosts` (
  `idhosts` INT NOT NULL AUTO_INCREMENT,
  `FIO` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT NULL DEFAULT NULL,
  `data_birthday` DATE NULL DEFAULT NULL,
  `phone` VARCHAR(45) NULL DEFAULT NULL,
  `address` VARCHAR(120) NULL DEFAULT NULL,
  `user_iduser` INT NOT NULL,
  PRIMARY KEY (`idhosts`),
  INDEX `fk_hosts_user1` (`user_iduser` ASC) VISIBLE,
  CONSTRAINT `fk_hosts_user1`
    FOREIGN KEY (`user_iduser`)
    REFERENCES `vetcli`.`user` (`iduser`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`patients` (
  `idpatients` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT NULL DEFAULT NULL,
  `type_of_animal` VARCHAR(45) NULL DEFAULT NULL,
  `breed` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idpatients`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`hosts_has_patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`hosts_has_patients` (
  `hosts_idhosts` INT NOT NULL,
  `patients_idpatients` INT NOT NULL,
  PRIMARY KEY (`hosts_idhosts`, `patients_idpatients`),
  INDEX `fk_hosts_has_patients_patients1` (`patients_idpatients` ASC) VISIBLE,
  CONSTRAINT `fk_hosts_has_patients_hosts1`
    FOREIGN KEY (`hosts_idhosts`)
    REFERENCES `vetcli`.`hosts` (`idhosts`),
  CONSTRAINT `fk_hosts_has_patients_patients1`
    FOREIGN KEY (`patients_idpatients`)
    REFERENCES `vetcli`.`patients` (`idpatients`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`specialization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`specialization` (
  `idspecialization` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idspecialization`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`service` (
  `idservices` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `cost` DECIMAL(20,2) NULL DEFAULT NULL,
  `description` VARCHAR(500) NULL DEFAULT NULL,
  `specialization_idspecialization` INT NOT NULL,
  PRIMARY KEY (`idservices`),
  INDEX `fk_service_specialization1` (`specialization_idspecialization` ASC) VISIBLE,
  CONSTRAINT `fk_service_specialization1`
    FOREIGN KEY (`specialization_idspecialization`)
    REFERENCES `vetcli`.`specialization` (`idspecialization`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`patients_has_service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`patients_has_service` (
  `idrecord` INT NOT NULL AUTO_INCREMENT,
  `patients_idpatients` INT NOT NULL,
  `service_idservices` INT NOT NULL,
  `calendar_idcalendar` INT NOT NULL,
  PRIMARY KEY (`idrecord`),
  INDEX `fk_patients_has_service_patients1` (`patients_idpatients` ASC) VISIBLE,
  INDEX `fk_patients_has_service_service1` (`service_idservices` ASC) VISIBLE,
  INDEX `fk_patients_has_service_calendar1` (`calendar_idcalendar` ASC) VISIBLE,
  CONSTRAINT `fk_patients_has_service_calendar1`
    FOREIGN KEY (`calendar_idcalendar`)
    REFERENCES `vetcli`.`calendar` (`idcalendar`),
  CONSTRAINT `fk_patients_has_service_Patients`
    FOREIGN KEY (`patients_idpatients`)
    REFERENCES `vetcli`.`patients` (`idpatients`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_patients_has_service_patients1`
    FOREIGN KEY (`patients_idpatients`)
    REFERENCES `vetcli`.`patients` (`idpatients`),
  CONSTRAINT `fk_patients_has_service_Service`
    FOREIGN KEY (`service_idservices`)
    REFERENCES `vetcli`.`service` (`idservices`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_patients_has_service_service1`
    FOREIGN KEY (`service_idservices`)
    REFERENCES `vetcli`.`service` (`idservices`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetcli`.`service_has_doctors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetcli`.`service_has_doctors` (
  `service_idservices` INT NOT NULL,
  `doctors_iddoctors` INT NOT NULL,
  PRIMARY KEY (`service_idservices`, `doctors_iddoctors`),
  INDEX `fk_service_has_doctors_doctors1` (`doctors_iddoctors` ASC) VISIBLE,
  CONSTRAINT `fk_service__has_doctorsDoctors`
    FOREIGN KEY (`doctors_iddoctors`)
    REFERENCES `vetcli`.`doctors` (`iddoctors`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_service__has_doctorsService`
    FOREIGN KEY (`service_idservices`)
    REFERENCES `vetcli`.`service` (`idservices`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_service_has_doctors_doctors1`
    FOREIGN KEY (`doctors_iddoctors`)
    REFERENCES `vetcli`.`doctors` (`iddoctors`),
  CONSTRAINT `fk_service_has_doctors_service1`
    FOREIGN KEY (`service_idservices`)
    REFERENCES `vetcli`.`service` (`idservices`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `vetcli` ;

-- -----------------------------------------------------
-- procedure add_patient
-- -----------------------------------------------------

DELIMITER $$
USE `vetcli`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_patient`(
    IN p_idpatients INT, 
    IN p_name VARCHAR(100),
    IN p_age INT,
    IN p_type_of_animal VARCHAR(50),
    IN p_breed VARCHAR(50)
)
BEGIN
    DECLARE patient_count INT;
    SELECT COUNT(*) INTO patient_count FROM patients WHERE name = p_name AND age = p_age AND type_of_animal = p_type_of_animal AND breed = p_breed;
    
    IF (patient_count > 0) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Пациент с такими данными уже существует';
    ELSE
        INSERT INTO patients (idpatients, name, age, type_of_animal, breed)
        VALUES (p_idpatients, p_name, p_age, p_type_of_animal, p_breed);
    END IF;
END$$

DELIMITER ;
USE `vetcli`;

DELIMITER $$
USE `vetcli`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `vetcli`.`check_duplicate_record_2`
BEFORE INSERT ON `vetcli`.`patients_has_service`
FOR EACH ROW
BEGIN
    DECLARE count_records INT;

    SELECT COUNT(*) INTO count_records 
    FROM patients_has_service 
    WHERE patients_idpatients = NEW.patients_idpatients 
    AND service_idservices = NEW.service_idservices 
    AND calendar_idcalendar = NEW.calendar_idcalendar;

    IF count_records > 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Пациент уже записан на данную услугу в данное время.';
    END IF;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
