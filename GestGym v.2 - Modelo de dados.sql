-- MySQL Workbench Forward Engineering ***
-- MySQL Workbench Forward Engineering ***

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema GestGym
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema GestGym
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `GestGym` DEFAULT CHARACTER SET utf8 ;
USE `GestGym` ;

-- -----------------------------------------------------
-- Table `GestGym`.`pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`pessoa` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `genero` VARCHAR(2) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `nacionalidade` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `nif` VARCHAR(20) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `rua` VARCHAR(200) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `rua_complemento` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `cod_postal` VARCHAR(8) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `localidade` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `data_nascimento` DATE NULL DEFAULT NULL,
  `seguro` TINYINT NULL DEFAULT NULL,
  `Nome_pai` VARCHAR(100) NULL,
  `nome_mae` VARCHAR(100) NULL,
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
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `pessoa_id` BIGINT(20) NOT NULL,
  `numero` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  PRIMARY KEY (`id`, `pessoa_id`),
  UNIQUE INDEX `numero_UNIQUE` (`numero` ASC),
  INDEX `fk_cartao_acesso_pessoa1_idx` (`pessoa_id` ASC),
  CONSTRAINT `fk_cartao_acesso_pessoa1`
    FOREIGN KEY (`pessoa_id`)
    REFERENCES `GestGym`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`acesso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`acesso` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cartao_acesso_id` BIGINT(20) NOT NULL,
  `data` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
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
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `inicio` DATE NOT NULL,
  `fim` DATE NOT NULL,
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
  `atividade_id` BIGINT(20) NOT NULL,
  `n_ordem` INT(11) NOT NULL DEFAULT 0,
  `descricao` VARCHAR(45) NOT NULL,
  `idade_min` INT(11) NOT NULL DEFAULT '0',
  `idade_max` INT(11) NOT NULL DEFAULT '200',
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
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `escalao_id` BIGINT(20) NOT NULL,
  `n_ordem` INT(11) NOT NULL DEFAULT 0,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
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
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
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
  `n_ordem` INT(11) NOT NULL DEFAULT 0,
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
  `tipo_contacto_id` BIGINT(20) NOT NULL,
  `pessoa_id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`, `tipo_contacto_id`, `pessoa_id`),
  INDEX `fk_contacto_tipo_contacto1_idx` (`tipo_contacto_id` ASC),
  INDEX `fk_contacto_pessoa1_idx` (`pessoa_id` ASC),
  CONSTRAINT `fk_contacto_tipo_contacto1`
    FOREIGN KEY (`tipo_contacto_id`)
    REFERENCES `GestGym`.`tipo_contacto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_contacto_pessoa1`
    FOREIGN KEY (`pessoa_id`)
    REFERENCES `GestGym`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GestGym`.`instalacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`instalacao` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `rua` VARCHAR(200) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `rua_complemento` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `cod_postal` VARCHAR(8) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `localidade` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`espaco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`espaco` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `instalacao_id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
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
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `espaco_id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
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
-- Table `GestGym`.`tipo_doc_identificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`tipo_doc_identificacao` (
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
  `pessoa_id` BIGINT(20) NOT NULL,
  `tipo_doc_identificacao_id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(100) NOT NULL,
  `validade` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `pessoa_id`, `tipo_doc_identificacao_id`),
  UNIQUE INDEX `numero` (`descricao` ASC),
  INDEX `fk_doc_identificacao_pessoa1_idx` (`pessoa_id` ASC),
  INDEX `fk_doc_identificacao_tipo_doc_identificacao1_idx` (`tipo_doc_identificacao_id` ASC),
  CONSTRAINT `fk_doc_identificacao_pessoa1`
    FOREIGN KEY (`pessoa_id`)
    REFERENCES `GestGym`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_doc_identificacao_tipo_doc_identificacao1`
    FOREIGN KEY (`tipo_doc_identificacao_id`)
    REFERENCES `GestGym`.`tipo_doc_identificacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GestGym`.`periodicidade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`periodicidade` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
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
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `periodicidade_id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
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
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `periodo_id` BIGINT(20) NOT NULL,
  `atividade_epoca_id` BIGINT(20) NOT NULL,
  `responsavel_id` BIGINT(20) NOT NULL,
  `divisao_id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(45) NULL DEFAULT NULL,
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
  CONSTRAINT `fk_horario_pessoa1`
    FOREIGN KEY (`responsavel_id`)
    REFERENCES `GestGym`.`pessoa` (`id`)
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
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `n_ordem` INT(11) NOT NULL DEFAULT 0,
  `descricao` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `antonimo` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`relacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`relacao` (
  `tipo_relacao_id` BIGINT(20) NOT NULL,
  `pessoa_id_1` BIGINT(20) NOT NULL,
  `pessoa_id_2` BIGINT(20) NOT NULL,
  PRIMARY KEY (`pessoa_id_1`, `pessoa_id_2`),
  INDEX `fk_relacao_tipo_relacao1_idx` (`tipo_relacao_id` ASC),
  INDEX `fk_relacao_pessoa1_idx` (`pessoa_id_1` ASC),
  INDEX `fk_relacao_pessoa2_idx` (`pessoa_id_2` ASC),
  CONSTRAINT `fk_relacao_tipo_relacao1`
    FOREIGN KEY (`tipo_relacao_id`)
    REFERENCES `GestGym`.`tipo_relacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_relacao_pessoa1`
    FOREIGN KEY (`pessoa_id_1`)
    REFERENCES `GestGym`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_relacao_pessoa2`
    FOREIGN KEY (`pessoa_id_2`)
    REFERENCES `GestGym`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`tipo_utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`tipo_utente` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `n_ordem` INT(11) NOT NULL DEFAULT 0,
  `descricao` VARCHAR(45) NOT NULL,
  `desconto_percentagem` DECIMAL NOT NULL DEFAULT 0,
  `desconto_valor` DECIMAL NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GestGym`.`utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`utente` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `tipo_utente_id` BIGINT(20) NOT NULL,
  `pessoa_id` BIGINT(20) NOT NULL,
  `num_utente` VARCHAR(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NOT NULL,
  `descricao` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `pessoa_id`),
  INDEX `fk_utente_tipo_utente1_idx` (`tipo_utente_id` ASC),
  INDEX `fk_utente_pessoa1_idx` (`pessoa_id` ASC),
  CONSTRAINT `fk_utente_tipo_utente1`
    FOREIGN KEY (`tipo_utente_id`)
    REFERENCES `GestGym`.`tipo_utente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_utente_pessoa1`
    FOREIGN KEY (`pessoa_id`)
    REFERENCES `GestGym`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GestGym`.`utente_horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`utente_horario` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `horario_id` BIGINT(20) NOT NULL,
  `utente_id` BIGINT(20) NOT NULL,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NOT NULL,
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


-- -----------------------------------------------------
-- Table `GestGym`.`pessoa_utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`pessoa_utente` (
  `pessoa_id` BIGINT(20) NOT NULL COMMENT 'pessoa É PAGANTE DE UM OU MAIS utente\n\nA pessoa que paga um ou mais utentes é nossa CLIENTE',
  `utente_id` BIGINT(20) NOT NULL COMMENT 'utente É PAGO POR pessoa \n\n',
  PRIMARY KEY (`pessoa_id`, `utente_id`),
  INDEX `fk_pessoa_has_utente_utente1_idx` (`utente_id` ASC),
  INDEX `fk_pessoa_has_utente_pessoa1_idx` (`pessoa_id` ASC),
  CONSTRAINT `fk_pessoa_has_utente_pessoa1`
    FOREIGN KEY (`pessoa_id`)
    REFERENCES `GestGym`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pessoa_has_utente_utente1`
    FOREIGN KEY (`utente_id`)
    REFERENCES `GestGym`.`utente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `GestGym`.`observacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`observacao` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `pessoa_id` BIGINT(20) NOT NULL,
  `descricao` VARCHAR(45) NOT NULL,
  `obs` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`, `pessoa_id`),
  INDEX `fk_observacao_pessoa1_idx` (`pessoa_id` ASC),
  CONSTRAINT `fk_observacao_pessoa1`
    FOREIGN KEY (`pessoa_id`)
    REFERENCES `GestGym`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GestGym`.`Dados_CC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GestGym`.`Dados_CC` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
