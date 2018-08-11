/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : worldcup

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 11/08/2018 13:11:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(2) NOT NULL DEFAULT '0',
  `title` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `file_name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for login_user
-- ----------------------------
DROP TABLE IF EXISTS `login_user`;
CREATE TABLE `login_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `password` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `real_name` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `user_email` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `user_phone` varchar(15) COLLATE utf8_bin NOT NULL DEFAULT '',
  `invest_code` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `registry_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
