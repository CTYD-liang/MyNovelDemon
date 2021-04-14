/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.19 : Database - novelblog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`novelblog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `novelblog`;

/*Table structure for table `manager` */

DROP TABLE IF EXISTS `manager`;

CREATE TABLE `manager` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `manager_name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `announcement` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `manager` */

insert  into `manager`(`id`,`manager_name`,`password`,`announcement`) values 
(1,'admin','123456','这是笔记管理系统1.0\n欢迎您的注册和使用！\n如果有什么问题，\n或者忘记密码，\n请联系管理员，\n联系方式QQ：1932896399');

/*Table structure for table `novel` */

DROP TABLE IF EXISTS `novel`;

CREATE TABLE `novel` (
  `id` bigint(100) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `author` varchar(50) NOT NULL COMMENT '作者名称',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` longtext COMMENT '文章内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `novel` */

insert  into `novel`(`id`,`author`,`title`,`content`) values 
(10,'洪哥','当咸鱼！','   今天我是一条咸鱼！\n\n明天我也是一条咸鱼！\n\n我当咸鱼不好吗？'),
(12,'荣哥','程序猿的一天！','8:45 闹钟响起，起床刷牙洗脸\n\n　　9:00 去冰箱拿昨晚在公司买的夜宵（酸奶、蛋糕），边刷新闻边吃\n\n　　9:30 出门，小区距离园区不远，骑个小电驴15分钟就到公司了。\n\n　　9:45 到工位打卡，然后打开电脑看了5分钟阿里内外的热帖\n\n　　10:00-12:00 高效工作时间，做特征，跑sql，画框架图，看数据表\n\n　　12:00 和同事一起去食堂吃饭，饭菜很便宜，13块钱的饭补基本用不完\n\n　　12:30 回到工位，打开行军床睡个午觉\n\n　　14:00-18:00 开始每天最有效率的攻坚时间，疯狂写码，基本连续4小时，除了上厕所，都是高强度的写码\n\n　　18:00 把代码放服务器上跑起来，然后就去吃饭了。晚上喜欢吃5号楼的黄焖鸡，加一份豆泡真的舒服。\n\n　　18:30 吃完饭和同事沿着园区人造湖散步，走上2圈，消消食\n\n　　19:00 回到工位，晚上一般是学术攻坚时间，大家一起看paper，一起讨论各种算法在业务上的落地。在阿里做算法，发paper也是一个kpi。\n\n　　21:00 去食堂拿份免费夜宵，然后去2号楼健身房运动一下。阿里9点后打车是可以报销的。\n\n　　21:30-23:00 洗澡，和朋友玩会游戏，看看书\n\n　　23:00 睡觉');

/*Table structure for table `user_message` */

DROP TABLE IF EXISTS `user_message`;

CREATE TABLE `user_message` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(20) NOT NULL COMMENT '用户名称',
  `password` varchar(50) NOT NULL COMMENT '用户密码',
  `age` int(10) DEFAULT NULL COMMENT '用户年龄',
  `sex` char(10) DEFAULT NULL COMMENT '用户性别',
  `person_describe` varchar(100) DEFAULT NULL COMMENT '用户描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `user_message` */

insert  into `user_message`(`id`,`user_name`,`password`,`age`,`sex`,`person_describe`) values 
(1,'洪哥','11111',16,'男','我不配！'),
(2,'lianghongrong','123456',18,'男','靓仔呀！'),
(3,'上头了','1233211234567',18,'女',NULL),
(4,'荣哥','123456',NULL,NULL,NULL);

/*Table structure for table `usernovel` */

DROP TABLE IF EXISTS `usernovel`;

CREATE TABLE `usernovel` (
  `id` bigint(100) NOT NULL AUTO_INCREMENT,
  `author` varchar(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `usernovel` */

insert  into `usernovel`(`id`,`author`,`title`,`content`) values 
(5,'洪哥','我喜欢你！','我真的好想你\n\n在每一个夜里！\n\n最遗憾的，是我最不舍的！'),
(6,'上头了','什么是上头了！？？','上头了\n就是上瘾了的意思哈哈哈！'),
(7,'洪哥','小情歌！','这是一首简单的小情歌\n《小情歌》MV图片\n《小情歌》MV图片\n唱着人们心肠的曲折\n我想我很快乐\n当有你的温热\n脚边的空气转了\n这是一首简单的小情歌\n唱着我们心头的白鸽\n我想我很适合\n当一个歌颂者\n青春在风中飘着\n你知道\n就算大雨让这座城市颠倒\n我会给你怀抱\n受不了看见你背影来到\n写下我度秒如年难捱的离骚\n就算整个世界被寂寞绑票\n我也不会奔跑\n逃不了最后谁也都苍老\n写下我时间和琴声交错的城堡\n这是一首简单的小情歌\n唱着我们心头的白鸽\n我想我很适合\n当一个歌颂者\n青春在风中飘着\n你知道\n就算大雨让这座城市颠倒\n我会给你怀抱\n受不了看见你背影来到\n写下我度秒如年难捱的离骚\n就算整个世界被寂寞绑票\n我也不会奔跑\n逃不了最后谁也都苍老\n写下我时间和琴声交错的城堡\n你知道\n就算大雨让这座城市颠倒\n我会给你怀抱\n小情歌 MV\n小情歌 MV\n受不了看见你背影来到\n写下我度秒如年难捱的离骚\n就算整个世界被寂寞绑票\n我也不会奔跑\n最后谁也都苍老\n写下我时间和琴声交错的城堡'),
(8,'lianghongrong','真不错！','  这笔记系统真不错！\n因为我们都是工具人！\n  好了，我要继续去玩耍了\n哈哈哈哈哈哈！'),
(11,'荣哥','世界表白日！','   今天我们助班脱单了！\n可是不请我吃饭！\n   竟然有点伤心！\n可能因为我不是班委吧！\n');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
