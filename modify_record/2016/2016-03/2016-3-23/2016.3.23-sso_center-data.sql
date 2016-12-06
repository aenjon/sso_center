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
-- Dumping data for table `tbrole`
--

LOCK TABLES `tbrole` WRITE;
/*!40000 ALTER TABLE `tbrole` DISABLE KEYS */;
INSERT INTO `tbrole` VALUES (1,'user','普通用户','1','普通用户'),(2,'admin','普通管理员','1','普通管理员'),(3,'superAdmin','超级管理员','1','超级管理员');
/*!40000 ALTER TABLE `tbrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tbresource`
--

LOCK TABLES `tbresource` WRITE;
/*!40000 ALTER TABLE `tbresource` DISABLE KEYS */;
INSERT INTO `tbresource` VALUES (1,'third_jclass','金课堂','/static/images/yun/jclass.png',0,'0','http://192.168.18.159:8091/load.html','1','第三方金课堂','2016-03-16 04:24:07',NULL,NULL,'1','6NqlFnLG','Pxk8Wq9XWe'),(2,'third_hsbb','华师BB','/static/images/yun/hsbb.png',0,'0','#','1','第三方华师BB','2016-03-16 04:24:07',NULL,NULL,'1','WlE7yrQm','pReVWOmCDQ'),(3,'third_lsmk','兰山募课','/static/images/yun/mooc.png',0,'0','http://192.168.18.159:8091/load.html','1','第三方兰山募课','2016-03-16 04:24:07',NULL,NULL,'1','D0BtRUbQ','L86gTQF8hv'),(4,'system_personal_settings','个人设置','/static/images/yun/personal_settings.png',0,'1','/sso/personalSettings.html','1','个人设置','2016-03-16 04:24:07',NULL,NULL,'1',NULL,NULL),(5,'system_help_center','帮助中心','/static/images/yun/helper_center.png',0,'1','http://help.01edut.cn/','1','帮助中心','2016-03-16 04:24:07',NULL,NULL,'1',NULL,NULL),(6,'system_know_us','了解我们','/static/images/yun/know_hsjc.png',0,'1','#','1','了解我们','2016-03-16 04:24:07',NULL,NULL,'1',NULL,NULL),(7,'menu_user_manage','用户管理','#',0,'2','#','1','用户管理','2016-03-16 04:24:07','d1',NULL,'1',NULL,NULL),(8,'menu_third_manage','第三方平台管理','#',0,'2','#','1','第三方平台管理','2016-03-16 04:24:07','d2',NULL,'1',NULL,NULL),(9,'menu_custom_manage','客服管理','#',0,'2','#','1','客服管理','2016-03-16 04:24:07','d3',NULL,'1',NULL,NULL),(10,'menu_organization_manage','组织机构管理','#',0,'2','#','1','组织机构管理','2016-03-16 04:24:07','d4',NULL,'1',NULL,NULL),(11,'menu_admin_manage','管理员管理','#',0,'2','#','1','管理员管理','2016-03-16 04:24:07','d5',NULL,'1',NULL,NULL),(12,'menu_website_manage','站点管理','#',0,'2','#','1','站点管理','2016-03-16 04:24:07','d6',NULL,'1',NULL,NULL),(13,'menu_user_list','用户列表','#',7,'2','/page/sso/userList/1,10,0,0,0,0,0.html','1','用户列表','2016-03-16 07:15:57',NULL,'user_list','2',NULL,NULL),(14,'menu_add_user','新增用户','#',7,'2','/page/sso/newUser.html','1','新增用户','2016-03-16 07:15:57',NULL,'new_user','2',NULL,NULL),(15,'menu_platform_list','平台列表','#',8,'2','/page/sso/platformList/1,10,0.html','1','平台列表','2016-03-16 07:15:57',NULL,'platform_list','2',NULL,NULL),(16,'menu_platform_filter_list','导入数据过滤列表','#',8,'2','/page/sso/platformFilterList/1,10,0.html','1','导入数据过滤列表','2016-03-16 07:15:57',NULL,'platformfilter_list','2',NULL,NULL),(17,'menu_service_list','客服员列表','#',9,'2','/page/sso/serviceList.html','1','客服员列表','2016-03-16 07:15:57',NULL,'service_list','2',NULL,NULL),(18,'menu_new_service','新增客服','#',9,'2','/page/sso/newService.html','1','新增客服','2016-03-16 07:15:57',NULL,'new_service','2',NULL,NULL),(19,'menu_one_manage','一次客服管理','#',9,'2','/page/sso/oneManage.html','1','一次客服管理','2016-03-16 07:17:27',NULL,'one_manage','2',NULL,NULL),(20,'menu_two_manage','二次客服管理','#',9,'2','/page/sso/twoManage.html','1','二次客服管理','2016-03-16 07:17:27',NULL,'two_manage','2',NULL,NULL),(21,'menu_tissue_list','组织机构列表','#',10,'2','/page/sso/tissueList/1,10,s,0,0.html','1','组织机构列表','2016-03-16 07:17:27',NULL,'tissue_list','2',NULL,NULL),(22,'menu_new_tissue','新增组织机构','#',10,'2','/page/sso/newTissue.html','1','新增组织机构','2016-03-16 07:17:27',NULL,'new_tissue','2',NULL,NULL),(23,'menu_invitation_manage','邀请码管理','#',10,'2','/page/sso/invitationManage/1,10,0,0,0,0.html','1','邀请码管理','2016-03-16 07:17:27',NULL,'invitation_manage','2',NULL,NULL),(24,'menu_new_invitation','新增邀请码','#',10,'2','/page/sso/newInvitation.html','1','新增邀请码','2016-03-16 07:17:27',NULL,'new_invitation','2',NULL,NULL),(25,'menu_admin_list','管理员列表','#',11,'2','/page/sso/adminList.html','1','管理员列表','2016-03-16 07:17:27',NULL,'admin_list','2',NULL,NULL),(26,'menu_new_admin','新增管理员','#',11,'2','/page/sso/newAdmin.html','1','新增管理员','2016-03-16 07:17:27',NULL,'new_admin','2',NULL,NULL),(27,'menu_site_basic','站点基本设置','#',12,'2','/page/sso/siteBasic.html','1','站点基本设置','2016-03-16 07:17:27',NULL,'site_basic','2',NULL,NULL),(28,'menu_email_port','邮件接口设置','#',12,'2','/email/emailPort.html','1','邮件接口设置','2016-03-16 07:17:27',NULL,'email_port','2',NULL,NULL),(29,'menu_mess_port','短信接口设置','#',12,'2','/sms/messPort.html','1','短信接口设置','2016-03-16 07:17:27',NULL,'mess_port','2',NULL,NULL),(30,'menu_site_log','站点日志','#',12,'2','/page/sso/siteLog.html','1','站点日志','2016-03-16 07:17:27',NULL,'site_log','2',NULL,NULL);
/*!40000 ALTER TABLE `tbresource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tbroleresource`
--

LOCK TABLES `tbroleresource` WRITE;
/*!40000 ALTER TABLE `tbroleresource` DISABLE KEYS */;
INSERT INTO `tbroleresource` VALUES (1,1,4),(2,1,5),(3,1,6),(4,2,7),(5,2,8),(6,2,9),(7,2,10),(8,2,11),(9,2,12),(10,2,13),(11,2,15),(12,2,16),(13,2,17),(14,2,19),(15,2,20),(16,2,21),(17,2,23),(18,2,25),(19,2,27),(20,2,28),(21,2,29),(22,2,30),(23,3,7),(24,3,8),(25,3,9),(26,3,10),(27,3,11),(28,3,12),(29,3,13),(30,3,14),(31,3,15),(32,3,16),(33,3,17),(34,3,18),(35,3,19),(36,3,20),(37,3,21),(38,3,22),(39,3,23),(40,3,24),(41,3,25),(42,3,26),(43,3,27),(44,3,28),(45,3,29),(46,3,30);
/*!40000 ALTER TABLE `tbroleresource` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-23 16:50:48
