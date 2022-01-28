-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema yourmovies
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema yourmovies
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `yourmovies` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `yourmovies` ;

-- -----------------------------------------------------
-- Table `yourmovies`.`t_actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yourmovies`.`t_actors` (
  `actor_id` BIGINT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(255) NULL DEFAULT NULL,
  `f_born` DATETIME NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`actor_id`))
ENGINE = MyISAM
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `yourmovies`.`t_movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yourmovies`.`t_movies` (
  `movie_id` BIGINT NOT NULL AUTO_INCREMENT,
  `anno` INT NULL DEFAULT NULL,
  `direccion` VARCHAR(255) NULL DEFAULT NULL,
  `duracion` INT NULL DEFAULT NULL,
  `genero` VARCHAR(255) NULL DEFAULT NULL,
  `imagen` VARCHAR(255) NULL DEFAULT NULL,
  `pais` VARCHAR(255) NULL DEFAULT NULL,
  `sinopsis` VARCHAR(9999) NULL DEFAULT NULL,
  `titulo` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`movie_id`))
ENGINE = MyISAM
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `yourmovies`.`t_movies_actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yourmovies`.`t_movies_actors` (
  `movie_id` BIGINT NOT NULL,
  `actor_id` BIGINT NOT NULL,
  INDEX `FKgwm2c860umtfy5rpxix18bccc` (`actor_id` ASC) VISIBLE,
  INDEX `FKid81x3734rm0itebo0b4ahdi` (`movie_id` ASC) VISIBLE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
