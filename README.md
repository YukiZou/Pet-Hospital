# pet-hospital
## 项目介绍
虚拟宠物医院学习系统是一个虚拟宠物医院教学软件，可以使得宠物工作者不去实体医院就能系统地学习各种宠物诊疗专业知识。针对相关专业毕业实习医生，提供了宠物医院结构、科室、病例学习等数据信息，同时为不同岗位角色配置了大量的真实病例。
## 功能介绍
* 用户管理
* 科室管理
* 试题管理
* 角色模块流程管理
* 病例管理
## 技术栈
* 前台框架：Vue.js
* 后台框架: SSM(Spring+SpringMVC+MyBatis）
* 数据库: MySQL
* Web服务器: Tomcat
* 编程语言: js + Java

###mybatis官网学习
可在下面的网址里面学习mapper xml files 怎么写。
http://www.mybatis.org/mybatis-3/

##开发进度
2018.03.13
完成ssm框架搭建。
说明：目前数据库连本地的，在本地mysql新建pet数据库，建一张表为usr,建表语句
CREATE TABLE `usr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) NOT NULL,
  `auth` int(11) NOT NULL COMMENT '普通用户1，管理员2，超级管理员3',
  `picture_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
建好表后，自行添加一些数据测试。
