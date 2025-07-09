-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: full_stack_ecommerce_tecnology
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id_producto` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(5000) DEFAULT NULL,
  `precio` decimal(15,2) NOT NULL,
  `stock` smallint unsigned DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  `id_categoria` bigint NOT NULL,
  `id_marca` bigint NOT NULL,
  PRIMARY KEY (`id_producto`),
  KEY `id_categoria` (`id_categoria`),
  KEY `id_marca` (`id_marca`),
  CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`),
  CONSTRAINT `producto_ibfk_2` FOREIGN KEY (`id_marca`) REFERENCES `marca` (`id_marca`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'MacBook Air M2','Laptop ultraligera 13.6\"',5499000.00,15,'2025-03-20 18:14:22','2025-03-20 18:14:22',1,1),(2,'Galaxy Book3 Pro','Laptop AMOLED 16\" 120Hz',4899000.00,10,'2025-03-20 18:14:22','2025-03-20 18:14:22',1,2),(3,'VAIO SX14','Laptop empresarial 14\"',7299000.00,8,'2025-03-20 18:14:22','2025-03-20 18:14:22',1,3),(4,'ThinkPad X1 Carbon','Laptop militar certificada',8999000.00,12,'2025-03-20 18:14:22','2025-03-20 18:14:22',1,2),(5,'Chromebook 514','Laptop educativa',1999000.00,20,'2025-03-20 18:14:22','2025-03-20 18:14:22',1,4),(6,'iPhone 15 Pro','Cámara 48MP 5x zoom',7999000.00,25,'2025-03-20 18:14:22','2025-03-20 18:14:22',2,1),(7,'Galaxy S24 Ultra','S-Pen integrado',8999000.00,18,'2025-03-20 18:14:22','2025-03-20 18:14:22',2,2),(8,'Xperia 1 VI','Pantalla 4K 21:9',6499000.00,12,'2025-03-20 18:14:22','2025-03-20 18:14:22',2,3),(9,'Pixel 8 Pro','Google Tensor G3',5999000.00,15,'2025-03-20 18:14:22','2025-03-20 18:14:22',2,4),(10,'ROG Phone 7','Pantalla 165Hz',7599000.00,8,'2025-03-20 18:14:22','2025-03-20 18:14:22',2,2),(11,'AirPods Pro 2','Cancelación ruido activa',1299000.00,30,'2025-03-20 18:14:22','2025-03-20 18:14:22',3,1),(12,'WH-1000XM5','Audífonos premium Sony',1899000.00,25,'2025-03-20 18:14:22','2025-03-20 18:14:22',3,3),(13,'Galaxy Buds2 Pro','Sonido 360 Hi-Res',899000.00,40,'2025-03-20 18:14:22','2025-03-20 18:14:22',3,2),(14,'Zone Vibe 100','Audífonos oficina',499000.00,50,'2025-03-20 18:14:22','2025-03-20 18:14:22',3,4),(15,'SoundLink Flex','Parlante portátil Bose',799000.00,35,'2025-03-20 18:14:22','2025-03-20 18:14:22',3,3),(16,'Studio Display','5K Retina 27\"',12999000.00,5,'2025-03-20 18:14:22','2025-03-20 18:14:22',4,1),(17,'Odyssey Neo G9','Super ultra-wide 49\"',19999000.00,3,'2025-03-20 18:14:22','2025-03-20 18:14:22',4,2),(18,'InZone M9','4K 144Hz gaming',8999000.00,7,'2025-03-20 18:14:22','2025-03-20 18:14:22',4,3),(19,'UltraSharp U3223QE','4K IPS Black',6999000.00,10,'2025-03-20 18:14:22','2025-03-20 18:14:22',4,4),(20,'ProArt PA32UCX','Monitor para creativos',29999000.00,2,'2025-03-20 18:14:22','2025-03-20 18:14:22',4,5),(21,'SSD T7 Shield','2TB USB 3.2',899000.00,50,'2025-03-20 18:14:22','2025-03-20 18:14:22',5,2),(22,'WD My Passport','Disco 5TB USB-C',599000.00,40,'2025-03-20 18:14:22','2025-03-20 18:14:22',5,5),(23,'SanDisk Extreme','MicroSD 1TB 160MB/s',399000.00,60,'2025-03-20 18:14:22','2025-03-20 18:14:22',5,4),(24,'LaCie Rugged','Disco 4TB IP67',799000.00,30,'2025-03-20 18:14:22','2025-03-20 18:14:22',5,3),(25,'X5 Thunderbolt','SSD NVMe 4TB',1999000.00,15,'2025-03-20 18:14:22','2025-03-20 18:14:22',5,1),(26,'MacBook Air M2','Laptop ultraligera 13.6\"',5499000.00,15,'2025-03-20 18:14:22','2025-03-20 18:14:22',1,1),(27,'MacBook Air M2','Laptop ultraligera 13.6\"',5499000.00,15,'2025-03-20 18:14:22','2025-03-20 18:14:22',1,1),(28,'MacBook Air M2','Laptop ultraligera 13.6\"',5499000.00,15,'2025-03-20 18:14:22','2025-03-20 18:14:22',1,1),(30,'MacBook Air M2','Laptop ultraligera 13.6\"',5499000.00,15,'2025-03-20 18:14:22','2025-03-20 18:14:22',1,1);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-08 22:37:19
