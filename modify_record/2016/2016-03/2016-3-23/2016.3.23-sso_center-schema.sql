-- MySQL dump 10.13  Distrib 5.6.11, for Win64 (x86_64)
--
-- Host: localhost    Database: sso_center
-- ------------------------------------------------------
-- Server version	5.6.11-log

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
-- Table structure for table `tbrole`
--

DROP TABLE IF EXISTS `tbrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbrole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id(自动增长)',
  `roleKey` varchar(50) DEFAULT NULL COMMENT '角色Key',
  `roleName` varchar(50) DEFAULT NULL COMMENT '角色名',
  `state` enum('0','1') DEFAULT '1' COMMENT '状态',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbresource`
--

DROP TABLE IF EXISTS `tbresource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbresource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id(自动增长)',
  `resKey` varchar(200) DEFAULT NULL COMMENT '资源Key',
  `resName` varchar(200) DEFAULT NULL COMMENT '资源名',
  `resPath` varchar(500) DEFAULT NULL COMMENT '资源地址',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父Id',
  `resType` enum('0','1','2') DEFAULT NULL COMMENT '资源类型',
  `resUrl` varchar(500) DEFAULT NULL COMMENT '访问URL',
  `state` enum('0','1') DEFAULT '1' COMMENT '资源类型',
  `description` varchar(500) DEFAULT NULL COMMENT '资源描述',
  `createDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `className` varchar(50) DEFAULT NULL COMMENT '样式class名',
  `idName` varchar(50) DEFAULT NULL COMMENT '样式id名',
  `level` char(1) DEFAULT NULL COMMENT '资源级别',
  `clientId` varchar(8) DEFAULT NULL COMMENT '第三方clietnId',
  `ssoPassword` varchar(50) DEFAULT NULL COMMENT 'SSO访问第三方的密码',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbroleresource`
--

DROP TABLE IF EXISTS `tbroleresource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbroleresource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id(自动增长)',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色Id',
  `resourceId` bigint(20) DEFAULT NULL COMMENT '资源Id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbuserrole`
--

DROP TABLE IF EXISTS `tbuserrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbuserrole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户名',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16389 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbuserresource`
--

DROP TABLE IF EXISTS `tbuserresource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbuserresource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `resourceId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-23 16:53:46
