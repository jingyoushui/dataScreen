-- MySQL dump 10.13  Distrib 5.7.32, for Linux (x86_64)
--
-- Host: localhost    Database: vuedata
-- ------------------------------------------------------
-- Server version	5.7.32-0ubuntu0.18.04.1

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
-- Table structure for table `sql_bean`
--

DROP TABLE IF EXISTS `sql_bean`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sql_bean` (
  `id` varchar(255) NOT NULL,
  `param_list` varchar(255) DEFAULT NULL,
  `sql_des` varchar(255) DEFAULT NULL,
  `Sql_string` text,
  `sql_url` varchar(255) DEFAULT NULL,
  `sql_api` varchar(45) NOT NULL,
  `sql_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sql_bean`
--

LOCK TABLES `sql_bean` WRITE;
/*!40000 ALTER TABLE `sql_bean` DISABLE KEYS */;
INSERT INTO `sql_bean` VALUES ('1','%2020%调%','调解成功数','select count(*) from PUB_DJ_JB where AH like \'%调%\' and LARQ>=\'2020-01-01\' and (CLJG=\'DSRHJ\' or CLJG <> null)','getTiaoJie','JHApi','getNumber'),('10',NULL,'网上立案数','select count(*) from PUB_DJ_JB where DJR=\'网上立案\' and LARQ >=\'#\'','getNetLiAn','JHApi','getNumber'),('11','静海镇法庭,蔡公庄法庭,独流法庭,王口法庭,大邱庄法庭,子牙法庭,陈官屯法庭','地图上的数据','select count(*) from PUB_AJ_JB where BASPT=\'A0\' and LARQ >=\'#\',select count(*) from PUB_AJ_JB where BASPT=\'A1\' and LARQ >=\'#\',select count(*) from PUB_AJ_JB where BASPT=\'A2\' and LARQ >=\'#\',select count(*) from PUB_AJ_JB where BASPT=\'A3\' and LARQ >=\'#\',select count(*) from PUB_AJ_JB where BASPT=\'A4\' and LARQ >=\'#\',select count(*) from PUB_AJ_JB where BASPT=\'A6\' and LARQ >=\'#\',select count(*) from PUB_AJ_JB where BASPT=\'A7\' and LARQ >=\'#\'','getMap','JHApi','getNumbers'),('12','案由,数量','案由排行','select top 5 AY,count(*) as a from PUB_LA_AY  where AJXH in (select AJXH from PUB_DJ_JB where AH like \'%调%\' and LARQ >=\'2020-01-01\') group by AY order by a desc','getAnYou','JHApi','getList'),('13',NULL,'立案案件案由排名','select top 5 LAAY ,count(*) as value from PUB_LA_AY  where AJXH in (select AJXH from PUB_AJ_JB where  LARQ >=\'2020-01-01\') group by LAAY order by value desc','getLiAnYou','JHApi','getList'),('2',NULL,'调解员工作量','select count(*) from PUB_DJ_JB where AH like \'%调%\' and LARQ>=\'2020-01-01\' and TJY <> null','getGongzuo','JHApi','getNumber'),('4',NULL,'联络法官数','select count(*) from XF_LLFGDJ where DJFY=\'静海区人民法院\' and DJSJ >=\'2020-01-01\'','getFaguan','XFApi','getNumber'),('5',NULL,'判后答疑数','select count(*) from XF_PHDYDJ where DJFY=\'静海区人民法院\' and DJSJ >=\'2020-01-01\'','getPanhoudayi','XFApi','getNumber'),('6',NULL,'案件查询数','select count(*) from XF_AJCXDJ where DJFY=\'静海区人民法院\' and DJSJ >=\'2020-01-01\'','anjianCount','XFApi','getNumber'),('7',NULL,'保全数','select * from PUB_AJ_JB where  LARQ >=\'2020-01-01\' and AH like \'%财保%\'','getBaoQuan','JHApi','getList'),('8','','共同诉讼排行','select top 5 DWMC,count(*) as value from DSR_DW  where AJXH in (select AJXH from PUB_AJ_JB where  LARQ >=\'2020-01-01\') group by DWMC order by value desc','getGongShuPai','JHApi','getList'),('9',NULL,'当天立案总数','select count(*) from PUB_AJ_JB where LARQ>=\'#\'','getLiAn','JHApi','getNumber');
/*!40000 ALTER TABLE `sql_bean` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-14 15:37:01
