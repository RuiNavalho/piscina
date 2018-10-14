-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 192.168.1.71    Database: Piscina
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ativ_hor_dia_ut`
--

DROP TABLE IF EXISTS `ativ_hor_dia_ut`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ativ_hor_dia_ut` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ficha_cliente_id` bigint(20) NOT NULL,
  `dia_semana_id` bigint(20) NOT NULL,
  `horario_id` bigint(20) NOT NULL,
  `atividade_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ativ_hor_dia_ut_fk1` (`ficha_cliente_id`),
  KEY `ativ_hor_dia_ut_fk2` (`dia_semana_id`),
  KEY `ativ_hor_dia_ut_fk3` (`horario_id`),
  KEY `ativ_hor_dia_ut_fk4` (`atividade_id`),
  CONSTRAINT `ativ_hor_dia_ut_fk1` FOREIGN KEY (`ficha_cliente_id`) REFERENCES `ficha_cliente` (`id`),
  CONSTRAINT `ativ_hor_dia_ut_fk2` FOREIGN KEY (`dia_semana_id`) REFERENCES `dia_semana` (`id`),
  CONSTRAINT `ativ_hor_dia_ut_fk3` FOREIGN KEY (`horario_id`) REFERENCES `horario` (`id`),
  CONSTRAINT `ativ_hor_dia_ut_fk4` FOREIGN KEY (`atividade_id`) REFERENCES `atividade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ativ_hor_dia_ut`
--

LOCK TABLES `ativ_hor_dia_ut` WRITE;
/*!40000 ALTER TABLE `ativ_hor_dia_ut` DISABLE KEYS */;
/*!40000 ALTER TABLE `ativ_hor_dia_ut` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-08 19:30:01
