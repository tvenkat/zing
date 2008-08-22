/*
SQLyog Community Edition- MySQL GUI v6.14
MySQL - 5.0.27-community-nt : Database - zing
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `zing`;

USE `zing`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `activities` */

DROP TABLE IF EXISTS `activities`;

CREATE TABLE `activities` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `app_id` int(11) NOT NULL,
  `title` char(128) NOT NULL,
  `body` char(255) NOT NULL,
  `created` int(11) NOT NULL,
  KEY `id` (`id`),
  KEY `activity_stream_id` (`user_id`),
  KEY `created` (`created`)
) ENGINE=MyISAM AUTO_INCREMENT=190 DEFAULT CHARSET=latin1;

/*Data for the table `activities` */

/*Table structure for table `activity_media_items` */

DROP TABLE IF EXISTS `activity_media_items`;

CREATE TABLE `activity_media_items` (
  `id` int(11) NOT NULL auto_increment,
  `activity_id` int(11) NOT NULL,
  `mime_type` char(64) collate latin1_general_ci NOT NULL,
  `media_type` enum('AUDIO','IMAGE','VIDEO') collate latin1_general_ci NOT NULL,
  `url` char(128) collate latin1_general_ci NOT NULL,
  KEY `id` (`id`),
  KEY `activity_id` (`activity_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

/*Data for the table `activity_media_items` */

/*Table structure for table `application_settings` */

DROP TABLE IF EXISTS `application_settings`;

CREATE TABLE `application_settings` (
  `application_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  `name` char(128) collate latin1_general_ci NOT NULL,
  `value` char(255) collate latin1_general_ci NOT NULL,
  UNIQUE KEY `application_id` (`application_id`,`user_id`,`module_id`,`name`)
) ENGINE=MyISAM AUTO_INCREMENT=8403 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

/*Data for the table `application_settings` */

insert  into `application_settings`(`application_id`,`user_id`,`module_id`,`name`,`value`) values (8393,120,0,'AppField1','1111'),(8394,120,0,'AppField2','2222'),(8395,120,0,'AppField2','2222'),(8396,121,0,'AppField3','3333'),(8397,121,0,'AppField3','3333'),(8398,121,0,'AppField1','1111'),(8399,121,0,'AppField1','1111'),(8400,121,0,'AppField2','2222'),(8401,121,0,'AppField2','2222'),(8402,121,0,'AppField3','3333');

/*Table structure for table `applications` */

DROP TABLE IF EXISTS `applications`;

CREATE TABLE `applications` (
  `id` int(11) NOT NULL auto_increment,
  `url` char(128) collate latin1_general_ci NOT NULL,
  `title` char(128) collate latin1_general_ci default NULL,
  `directory_title` varchar(128) collate latin1_general_ci default NULL,
  `screenshot` char(128) collate latin1_general_ci default NULL,
  `thumbnail` char(128) collate latin1_general_ci default NULL,
  `author` char(128) collate latin1_general_ci default NULL,
  `author_email` char(128) collate latin1_general_ci default NULL,
  `description` mediumtext collate latin1_general_ci,
  `settings` mediumtext collate latin1_general_ci,
  `version` varchar(64) collate latin1_general_ci default NULL,
  `height` int(11) default NULL,
  `scrolling` int(11) default NULL,
  `modified` int(11) default NULL,
  `ordercount` int(11) default NULL,
  PRIMARY KEY  (`id`,`url`),
  KEY `url` (`url`)
) ENGINE=MyISAM AUTO_INCREMENT=119 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

/*Data for the table `applications` */

insert  into `applications`(`id`,`url`,`title`,`directory_title`,`screenshot`,`thumbnail`,`author`,`author_email`,`description`,`settings`,`version`,`height`,`scrolling`,`modified`,`ordercount`) values (107,'http://nature.pictures.art.googlepages.com/moons.xml','Moon of the Day','Moon of the Day','http://nature.pictures.art.googlepages.com/moons.ss2.jpg','http://nature.pictures.art.googlepages.com/moontnailbestmoonBoston-Skyline-Refl.jpg',NULL,NULL,'Every day enjoy a beautiful picture of the moon.  Check it out!',NULL,NULL,NULL,NULL,NULL,1),(108,'http://www.cammap.net/tvlive/livetvint.xml','Live TV channels','Live TV channels','http://www.cammap.net/tvlive/tv.jpg','http://www.cammap.net/tvlive/tvgadget_tn.jpg',NULL,NULL,'Watch live TV channels. There are over 80 (english) channels to choose from; news channels, music channels, entertainment channels, sport channels and many more.... Note for Firefox users: look at 30minutelunchbreak.com/livetvff.html to get this gadget to work.',NULL,NULL,NULL,NULL,NULL,2),(109,'http://localhost:8080/ZingWeb/gadgets/contact.xml','OAuth Gadget','OAuth Gadget',NULL,'http://tbn0.google.com/images?q=tbn:H9aMbQ6a_n4J::habrahabr.ru/pictures/00/00/00/61/32/picture_20.jpg',NULL,NULL,'OAuth is an open protocol to allow secure API authorization ',NULL,NULL,NULL,NULL,NULL,3);

/*Table structure for table `code_image` */

DROP TABLE IF EXISTS `code_image`;

CREATE TABLE `code_image` (
  `codeid` int(11) NOT NULL auto_increment,
  `codeimageurl` varchar(45) NOT NULL default '',
  `codeimagecaption` char(5) NOT NULL default '',
  PRIMARY KEY  (`codeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `code_image` */

insert  into `code_image`(`codeid`,`codeimageurl`,`codeimagecaption`) values (1,'../images/chkcode/130.jpg','Z4WR'),(2,'../images/chkcode/1057.jpg','2RZJ'),(3,'../images/chkcode/1060.jpg','2bwB'),(4,'../images/chkcode/203.jpg','sq2U'),(5,'../images/chkcode/1019.jpg','VM09'),(6,'../images/chkcode/216.jpg','RMQU'),(7,'../images/chkcode/283.jpg','iubr'),(8,'../images/chkcode/298.jpg','snXI'),(9,'../images/chkcode/305.jpg','UZP7'),(10,'../images/chkcode/310.jpg','QVC6');

/*Table structure for table `user_album` */

DROP TABLE IF EXISTS `user_album`;

CREATE TABLE `user_album` (
  `user_id` int(11) NOT NULL,
  `photo_id` int(11) NOT NULL auto_increment,
  `photo_name` varchar(50) NOT NULL,
  `thumb_name` varchar(50) NOT NULL,
  `photo_caption` varchar(100) default NULL,
  KEY `user_id` (`user_id`),
  KEY `Index_2` (`photo_id`)
) ENGINE=MyISAM AUTO_INCREMENT=121 DEFAULT CHARSET=latin1;

/*Data for the table `user_album` */

/*Table structure for table `user_applications` */

DROP TABLE IF EXISTS `user_applications`;

CREATE TABLE `user_applications` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `application_id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `user_id` (`user_id`),
  KEY `application_id` (`application_id`)
) ENGINE=MyISAM AUTO_INCREMENT=146 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

/*Data for the table `user_applications` */

/*Table structure for table `user_broadcast` */

DROP TABLE IF EXISTS `user_broadcast`;

CREATE TABLE `user_broadcast` (
  `user_id` int(11) NOT NULL,
  `header` varchar(100) default NULL,
  `article` varchar(1024) default NULL,
  `article_title` varchar(50) default NULL,
  `time_expire` datetime NOT NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `user_broadcast` */

/*Table structure for table `user_friend` */

DROP TABLE IF EXISTS `user_friend`;

CREATE TABLE `user_friend` (
  `user_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  `pending` enum('yes','no') NOT NULL,
  PRIMARY KEY  (`friend_id`,`user_id`),
  KEY `friend_id` (`friend_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `user_friend` */

insert  into `user_friend`(`user_id`,`friend_id`,`pending`) values (130,129,'no'),(129,130,'no');

/*Table structure for table `user_main` */

DROP TABLE IF EXISTS `user_main`;

CREATE TABLE `user_main` (
  `user_id` int(11) NOT NULL auto_increment,
  `user_name` varchar(25) NOT NULL,
  `user_password` varchar(25) NOT NULL,
  `user_email` varchar(128) NOT NULL,
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=MyISAM AUTO_INCREMENT=131 DEFAULT CHARSET=latin1;

/*Data for the table `user_main` */

insert  into `user_main`(`user_id`,`user_name`,`user_password`,`user_email`) values (129,'test','test','test@test.com'),(130,'impetus','impetus','impetus@impetus.com');

/*Table structure for table `user_online_status` */

DROP TABLE IF EXISTS `user_online_status`;

CREATE TABLE `user_online_status` (
  `user_id` int(11) NOT NULL,
  `last_online_time` datetime NOT NULL,
  `online_status` enum('yes','no') NOT NULL,
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `user_online_status` */

/*Table structure for table `user_profile` */

DROP TABLE IF EXISTS `user_profile`;

CREATE TABLE `user_profile` (
  `user_id` int(11) NOT NULL,
  `first_name` varchar(25) default NULL,
  `last_name` varchar(25) default NULL,
  `Gender` enum('m','f') NOT NULL default 'm',
  `user_image` varchar(50) default NULL,
  `profile_url` varchar(100) default NULL,
  `country` varchar(50) default NULL,
  `city` varchar(50) default NULL,
  `interests` varchar(100) default NULL,
  `date_of_birth` varchar(20) default NULL,
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `user_profile` */

insert  into `user_profile`(`user_id`,`first_name`,`last_name`,`Gender`,`user_image`,`profile_url`,`country`,`city`,`interests`,`date_of_birth`) values (129,'test','test','m','/images/default.jpg',NULL,NULL,NULL,NULL,NULL),(130,'Impetus','Infotech India','m','/images/default.jpg',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `user_scrap` */

DROP TABLE IF EXISTS `user_scrap`;

CREATE TABLE `user_scrap` (
  `scrap_id` int(11) NOT NULL auto_increment,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `scrap_content` text NOT NULL,
  KEY `user_id` (`receiver_id`),
  KEY `sender_id` (`sender_id`),
  KEY `Index_3` (`scrap_id`)
) ENGINE=MyISAM AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;

/*Data for the table `user_scrap` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
