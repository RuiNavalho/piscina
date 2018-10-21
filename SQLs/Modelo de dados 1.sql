-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema GestGym
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema GestGym
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `GestGym` DEFAULT CHARACTER SET utf8 ;
USE `GestGym` ;

-- -----------------------------------------------------
-- Table `GestGym`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`cliente` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `genero` VARCHAR(2) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `nacionalidade` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `nif` VARCHAR(20) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `rua` VARCHAR(200) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `rua_complemento` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `cod_postal` VARCHAR(8) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `localidade` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `data_nascimento` DATE NULL,
  `seguro` TINYINT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nif` (`nif` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 430
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`cartao_acesso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`cartao_acesso` (
  `id` BIGINT(20) NOT NULL,
  `numero` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `cliente_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `cliente_id`),
  UNIQUE INDEX `numero_UNIQUE` (`numero` ASC),
  INDEX `fk_cartao_acesso_cliente1_idx` (`cliente_id` ASC),
  CONSTRAINT `fk_cartao_acesso_cliente1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `GestGym`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`acesso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`acesso` (
  `id` BIGINT(20) NOT NULL,
  `data` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `cartao_acesso_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `cartao_acesso_id`),
  INDEX `fk_acesso_cartao_acesso1_idx` (`cartao_acesso_id` ASC),
  CONSTRAINT `fk_acesso_cartao_acesso1`
    FOREIGN KEY (`cartao_acesso_id`)
    REFERENCES `GestGym`.`cartao_acesso` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`antonimo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`antonimo` (
  `id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `descricao_inversa` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`atividade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`atividade` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GestGym`.`epoca`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`epoca` (
  `id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `inicio` DATE NULL DEFAULT NULL,
  `fim` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`escalao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`escalao` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `n_ordem` INT(11) NOT NULL DEFAULT 0,
  `descricao` VARCHAR(45) NOT NULL,
  `idade_min` INT(11) NOT NULL DEFAULT '0',
  `idade_max` INT(11) NOT NULL DEFAULT '200',
  `atividade_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_escalao_atividade1_idx` (`atividade_id` ASC),
  CONSTRAINT `fk_escalao_atividade1`
    FOREIGN KEY (`atividade_id`)
    REFERENCES `GestGym`.`atividade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GestGym`.`nivel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`nivel` (
  `id` BIGINT(20) NOT NULL,
  `n_ordem` INT(11) NOT NULL DEFAULT 0,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `escalao_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_nivel_escalao1_idx` (`escalao_id` ASC),
  CONSTRAINT `fk_nivel_escalao1`
    FOREIGN KEY (`escalao_id`)
    REFERENCES `GestGym`.`escalao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`atividade_epoca`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`atividade_epoca` (
  `id` BIGINT(20) NOT NULL,
  `epoca_id` BIGINT(20) NOT NULL,
  `nivel_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `epoca_id`, `nivel_id`),
  INDEX `fk_epoca_has_atividade_epoca1_idx` (`epoca_id` ASC),
  INDEX `fk_atividade_epoca_nivel1_idx` (`nivel_id` ASC),
  UNIQUE INDEX `un_nivel_epoca1` (`epoca_id` ASC, `nivel_id` ASC),
  CONSTRAINT `fk_epoca_has_atividade_epoca1`
    FOREIGN KEY (`epoca_id`)
    REFERENCES `GestGym`.`epoca` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_atividade_epoca_nivel1`
    FOREIGN KEY (`nivel_id`)
    REFERENCES `GestGym`.`nivel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`tipo_contacto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`tipo_contacto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `n_ordem` INT(11) NULL DEFAULT NULL,
  `descricao` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `descricao` (`descricao` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GestGym`.`contacto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`contacto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `contacto` VARCHAR(100) NOT NULL,
  `cliente_id` BIGINT(20) NOT NULL,
  `tipo_contacto_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `cliente_id`, `tipo_contacto_id`),
  INDEX `fk_contato_cliente1_idx` (`cliente_id` ASC),
  INDEX `fk_contacto_tipo_contacto1_idx` (`tipo_contacto_id` ASC),
  CONSTRAINT `fk_contacto_tipo_contacto1`
    FOREIGN KEY (`tipo_contacto_id`)
    REFERENCES `GestGym`.`tipo_contacto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_contato_cliente1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `GestGym`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GestGym`.`instalacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`instalacao` (
  `id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `rua` VARCHAR(200) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `rua_complemento` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `cod_postal` VARCHAR(8) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `localidade` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`espaco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`espaco` (
  `id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `instalacao_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `instalacao_id`),
  INDEX `fk_espaco_instalacao1_idx` (`instalacao_id` ASC),
  CONSTRAINT `fk_espaco_instalacao1`
    FOREIGN KEY (`instalacao_id`)
    REFERENCES `GestGym`.`instalacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`divisao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`divisao` (
  `id` BIGINT(20) NOT NULL,
  `nome` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `espaco_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `espaco_id`),
  INDEX `fk_divisao_espaco1_idx` (`espaco_id` ASC),
  CONSTRAINT `fk_divisao_espaco1`
    FOREIGN KEY (`espaco_id`)
    REFERENCES `GestGym`.`espaco` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`tipo_doc_indentificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`tipo_doc_indentificacao` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `n_ordem` INT(11) NOT NULL DEFAULT 0,
  `descricao` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `descricao` (`descricao` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GestGym`.`doc_identificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`doc_identificacao` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `numero` VARCHAR(100) NULL DEFAULT NULL,
  `validade` DATETIME NULL DEFAULT NULL,
  `tipo_doc_indentificacao_id` BIGINT(20) NOT NULL,
  `cliente_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `cliente_id`, `tipo_doc_indentificacao_id`),
  UNIQUE INDEX `numero` (`numero` ASC),
  INDEX `docidentificacao_fk1` (`tipo_doc_indentificacao_id` ASC),
  INDEX `docidentificacao_fk2` (`cliente_id` ASC),
  CONSTRAINT `docidentificacao_fk1`
    FOREIGN KEY (`tipo_doc_indentificacao_id`)
    REFERENCES `GestGym`.`tipo_doc_indentificacao` (`id`),
  CONSTRAINT `docidentificacao_fk2`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `GestGym`.`cliente` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GestGym`.`periodicidade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`periodicidade` (
  `id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`periodo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`periodo` (
  `id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `periodicidade_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `periodicidade_id`),
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC),
  INDEX `fk_periodos_periodicidade1_idx` (`periodicidade_id` ASC),
  CONSTRAINT `fk_periodos_periodicidade1`
    FOREIGN KEY (`periodicidade_id`)
    REFERENCES `GestGym`.`periodicidade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`horario` (
  `id` BIGINT(20) NOT NULL,
  `periodo_id` BIGINT(20) NOT NULL,
  `atividade_epoca_id` BIGINT(20) NOT NULL,
  `responsavel_id` BIGINT(20) NOT NULL,
  `divisao_id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(45) NULL,
  `hora_inicio` TIME NOT NULL,
  `hora_fim` TIME NOT NULL,
  `num_substituicoes` INT(11) NOT NULL,
  `max_inscricoes` INT(11) NOT NULL COMMENT 'Numero maximo de utentes por aula',
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_horario_periodo1_idx` (`periodo_id` ASC),
  INDEX `fk_horario_atividade_epoca1_idx` (`atividade_epoca_id` ASC),
  INDEX `fk_horario_responsavel1_idx` (`responsavel_id` ASC),
  INDEX `fk_horario_divisao1_idx` (`divisao_id` ASC),
  CONSTRAINT `fk_horario_atividade_epoca1`
    FOREIGN KEY (`atividade_epoca_id`)
    REFERENCES `GestGym`.`atividade_epoca` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_horario_cliente1`
    FOREIGN KEY (`responsavel_id`)
    REFERENCES `GestGym`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_horario_divisao1`
    FOREIGN KEY (`divisao_id`)
    REFERENCES `GestGym`.`divisao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_horario_periodos1`
    FOREIGN KEY (`periodo_id`)
    REFERENCES `GestGym`.`periodo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`tipo_relacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`tipo_relacao` (
  `id` BIGINT(20) NOT NULL,
  `n_ordem` INT(11) NOT NULL DEFAULT 0,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `antonimo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`relacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`relacao` (
  `cliente_id_1` BIGINT(20) NOT NULL,
  `cliente_id_2` BIGINT(20) NOT NULL,
  `tipo_relacao_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`cliente_id_1`, `cliente_id_2`),
  INDEX `fk_cliente_has_cliente_cliente2_idx` (`cliente_id_2` ASC),
  INDEX `fk_cliente_has_cliente_cliente1_idx` (`cliente_id_1` ASC),
  INDEX `fk_relacao_tipo_relacao1_idx` (`tipo_relacao_id` ASC),
  CONSTRAINT `fk_cliente_has_cliente_cliente1`
    FOREIGN KEY (`cliente_id_1`)
    REFERENCES `GestGym`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_has_cliente_cliente2`
    FOREIGN KEY (`cliente_id_2`)
    REFERENCES `GestGym`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_relacao_tipo_relacao1`
    FOREIGN KEY (`tipo_relacao_id`)
    REFERENCES `GestGym`.`tipo_relacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`tipo_utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`tipo_utente` (
  `id` BIGINT(20) NOT NULL,
  `n_ordem` INT(11) NOT NULL DEFAULT 0,
  `descricao` VARCHAR(45) NULL,
  `desconto_percentagem` DECIMAL NULL,
  `desconto_valor` DECIMAL NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GestGym`.`utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`utente` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `num_utente` VARCHAR(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NOT NULL,
  `cliente_id` BIGINT(20) NOT NULL,
  `tipo_utente_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_utente_cliente1_idx` (`cliente_id` ASC),
  INDEX `fk_utente_tipo_utente1_idx` (`tipo_utente_id` ASC),
  CONSTRAINT `fk_utente_cliente1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `GestGym`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_utente_tipo_utente1`
    FOREIGN KEY (`tipo_utente_id`)
    REFERENCES `GestGym`.`tipo_utente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GestGym`.`utente_horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`utente_horario` (
  `id` BIGINT(20) NOT NULL,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NOT NULL,
  `horario_id` BIGINT(20) NOT NULL,
  `utente_id` BIGINT(20) NOT NULL,
  INDEX `fk_utente_horario_horario1_idx` (`horario_id` ASC),
  INDEX `fk_utente_horario_utente1_idx` (`utente_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_utente_horario_horario1`
    FOREIGN KEY (`horario_id`)
    REFERENCES `GestGym`.`horario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_utente_horario_utente1`
    FOREIGN KEY (`utente_id`)
    REFERENCES `GestGym`.`utente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
