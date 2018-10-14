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
-- Table structure for table `tipo_relacao`
--

DROP TABLE IF EXISTS `tipo_relacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_relacao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `relacionamento_id` bigint(20) NOT NULL,
  `pessoa_id` bigint(20) NOT NULL,
  `pessoa_id1` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tipo_relacao_fk1` (`relacionamento_id`),
  KEY `tipo_relacao_fk2` (`pessoa_id`),
  KEY `tipo_relacao_fk3` (`pessoa_id1`),
  CONSTRAINT `tipo_relacao_fk1` FOREIGN KEY (`relacionamento_id`) REFERENCES `relacionamento` (`id`),
  CONSTRAINT `tipo_relacao_fk2` FOREIGN KEY (`pessoa_id`) REFERENCES `pessoa` (`id`),
  CONSTRAINT `tipo_relacao_fk3` FOREIGN KEY (`pessoa_id1`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_relacao`
--

LOCK TABLES `tipo_relacao` WRITE;
/*!40000 ALTER TABLE `tipo_relacao` DISABLE KEYS */;
INSERT INTO `tipo_relacao` VALUES (1,1,1,3),(2,1,1,4),(3,2,2,3),(4,2,2,4),(5,3,3,1),(6,3,3,2),(7,3,4,1),(8,3,4,2),(9,4,3,1),(10,4,4,2),(11,5,3,1),(12,5,4,2);
/*!40000 ALTER TABLE `tipo_relacao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-08 19:30:02
