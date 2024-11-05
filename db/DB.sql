-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema vetclinica
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema vetclinica
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `vetclinica` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `vetclinica` ;

-- -----------------------------------------------------
-- Table `vetclinica`.`calendar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`calendar` (
  `idcalendar` INT NOT NULL AUTO_INCREMENT,
  `day` DATE NULL DEFAULT NULL,
  `time` TIME NULL DEFAULT NULL,
  PRIMARY KEY (`idcalendar`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`role` (
  `idrole` INT NOT NULL AUTO_INCREMENT,
  `r_type` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idrole`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `log` VARCHAR(45) NULL DEFAULT NULL,
  `pass` VARCHAR(45) NULL DEFAULT NULL,
  `role_idrole` INT NOT NULL,
  PRIMARY KEY (`iduser`),
  INDEX `fk_user_role` (`role_idrole` ASC) VISIBLE,
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_idrole`)
    REFERENCES `vetclinica`.`role` (`idrole`))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`doctors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`doctors` (
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
    REFERENCES `vetclinica`.`user` (`iduser`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`doctors_has_calendar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`doctors_has_calendar` (
  `id_time` INT NOT NULL AUTO_INCREMENT,
  `doctors_iddoctors` INT NOT NULL,
  `calendar_idcalendar` INT NOT NULL,
  PRIMARY KEY (`id_time`),
  INDEX `fk_doctors_has_calendar_doctors1` (`doctors_iddoctors` ASC) VISIBLE,
  INDEX `fk_doctors_has_calendar_calendar1` (`calendar_idcalendar` ASC) VISIBLE,
  CONSTRAINT `fk_doctors_has_calendar_calendar1`
    FOREIGN KEY (`calendar_idcalendar`)
    REFERENCES `vetclinica`.`calendar` (`idcalendar`),
  CONSTRAINT `fk_doctors_has_calendar_doctors1`
    FOREIGN KEY (`doctors_iddoctors`)
    REFERENCES `vetclinica`.`doctors` (`iddoctors`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`hosts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`hosts` (
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
    REFERENCES `vetclinica`.`user` (`iduser`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`patients` (
  `idpatients` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT NULL DEFAULT NULL,
  `type_of_animal` VARCHAR(45) NULL DEFAULT NULL,
  `breed` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idpatients`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`hosts_has_patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`hosts_has_patients` (
  `hosts_idhosts` INT NOT NULL,
  `patients_idpatients` INT NOT NULL,
  PRIMARY KEY (`hosts_idhosts`, `patients_idpatients`),
  INDEX `fk_hosts_has_patients_patients1` (`patients_idpatients` ASC) VISIBLE,
  CONSTRAINT `fk_hosts_has_patients_hosts1`
    FOREIGN KEY (`hosts_idhosts`)
    REFERENCES `vetclinica`.`hosts` (`idhosts`),
  CONSTRAINT `fk_hosts_has_patients_patients1`
    FOREIGN KEY (`patients_idpatients`)
    REFERENCES `vetclinica`.`patients` (`idpatients`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`specialization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`specialization` (
  `idspecialization` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idspecialization`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`service` (
  `idservice` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(120) NULL DEFAULT NULL,
  `cost` DECIMAL(10,2) NULL DEFAULT NULL,
  `specialization_idspecialization` INT NOT NULL,
  PRIMARY KEY (`idservice`),
  INDEX `fk_service_specialization1` (`specialization_idspecialization` ASC) VISIBLE,
  CONSTRAINT `fk_service_specialization1`
    FOREIGN KEY (`specialization_idspecialization`)
    REFERENCES `vetclinica`.`specialization` (`idspecialization`))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`patients_has_service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`patients_has_service` (
  `idrecord` INT NOT NULL AUTO_INCREMENT,
  `patients_idpatients` INT NOT NULL,
  `service_idservice` INT NOT NULL,
  `doctors_has_calendar_id_time` INT NOT NULL,
  PRIMARY KEY (`idrecord`),
  INDEX `fk_patients_has_service_patients1` (`patients_idpatients` ASC) VISIBLE,
  INDEX `fk_patients_has_service_service1` (`service_idservice` ASC) VISIBLE,
  INDEX `fk_patients_has_service_doctors_has_calendar1` (`doctors_has_calendar_id_time` ASC) VISIBLE,
  CONSTRAINT `fk_patients_has_service_doctors_has_calendar1`
    FOREIGN KEY (`doctors_has_calendar_id_time`)
    REFERENCES `vetclinica`.`doctors_has_calendar` (`id_time`),
  CONSTRAINT `fk_patients_has_service_patients1`
    FOREIGN KEY (`patients_idpatients`)
    REFERENCES `vetclinica`.`patients` (`idpatients`),
  CONSTRAINT `fk_patients_has_service_service1`
    FOREIGN KEY (`service_idservice`)
    REFERENCES `vetclinica`.`service` (`idservice`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetclinica`.`service_has_doctors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetclinica`.`service_has_doctors` (
  `service_idservice` INT NOT NULL,
  `doctors_iddoctors` INT NOT NULL,
  PRIMARY KEY (`service_idservice`, `doctors_iddoctors`),
  INDEX `fk_service_has_doctors_doctors1` (`doctors_iddoctors` ASC) VISIBLE,
  CONSTRAINT `fk_service_has_doctors_doctors1`
    FOREIGN KEY (`doctors_iddoctors`)
    REFERENCES `vetclinica`.`doctors` (`iddoctors`),
  CONSTRAINT `fk_service_has_doctors_service1`
    FOREIGN KEY (`service_idservice`)
    REFERENCES `vetclinica`.`service` (`idservice`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
