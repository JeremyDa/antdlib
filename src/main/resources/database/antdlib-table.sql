
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
DROP TABLE IF EXISTS `exception`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exception` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(100) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `httpstatus` varchar(50) DEFAULT NULL,
  `returnCode` varchar(10) DEFAULT NULL,
  `returnMsg` text,
  `stackTrace` text,
  `param` text,
  `levelid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `path` varchar(50) NOT NULL COMMENT '访问url',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `icon` varchar(20) DEFAULT NULL COMMENT '菜单图标',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `redirect` varchar(100) DEFAULT NULL COMMENT '重定向',
  `parentid` int(11) NOT NULL COMMENT '上级菜单id',
  `isoper` int(11) DEFAULT '0' COMMENT '是否为操作',
  `hideInMenu` tinyint(1) DEFAULT '0' COMMENT '是否隐藏菜单',
  `hideChildrenInMenu` tinyint(1) DEFAULT '0' COMMENT '是否隐藏子菜单',
  `hideInBreadcrumb` tinyint(1) DEFAULT '0' COMMENT '隐藏面包屑',
  `projectid` varchar(20) DEFAULT NULL COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COMMENT='菜单';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(200) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `clickClose` tinyint(1) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `extra` varchar(50) DEFAULT NULL,
  `read` tinyint(1) DEFAULT '0',
  `account` varchar(100) DEFAULT NULL,
  `broadcast` tinyint(1) DEFAULT '0',
  `from` varchar(50) DEFAULT NULL,
  `issend` int(11) DEFAULT '0' COMMENT '1-已发送，0-未发送',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `desc` varchar(100) DEFAULT NULL COMMENT '说明描述',
  `levelid` varchar(100) DEFAULT NULL COMMENT '所属机构',
  `projectid` varchar(20) DEFAULT NULL COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rolemenu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rolemenu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleid` int(11) DEFAULT NULL COMMENT '角色id',
  `menuid` int(11) DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8 COMMENT='角色菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` varchar(100) DEFAULT NULL COMMENT '登录名',
  `name` varchar(100) DEFAULT NULL COMMENT '用户昵称',
  `roleid` int(11) DEFAULT NULL COMMENT '角色id',
  `password` varchar(1000) DEFAULT '111fb4d92036323891a140cd49383f86' COMMENT '密码',
  `state` int(11) DEFAULT NULL COMMENT '用户状态',
  `levelid` varchar(200) DEFAULT '0' COMMENT '所属机构',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_account_levelid_uindex` (`account`,`levelid`)
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `version` (
  `newest` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

