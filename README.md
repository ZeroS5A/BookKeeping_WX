# BookKeeping_WX
## 数据库表：

V1版本：

```mysql
CREATE DATABASE db_antools DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE db_antools;

CREATE TABLE t_user (#用户表
 id INT ( 11 ) NOT NULL AUTO_INCREMENT,#主键
 openId VARCHAR ( 128 ) UNIQUE,#用户唯一标识码
 nickName VARCHAR ( 32 ),#用户昵称
 gender INT ( 1 ),#用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
 avatarUrl VARCHAR ( 256 ),#用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表132*132正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
 PRIMARY KEY ( id )
);

CREATE TABLE t_bookkeeping_income (#收入表
 id INT ( 11 ) NOT NULL AUTO_INCREMENT,#主键
 userId INT ( 11 ) NOT NULL,#用户主键
 bkType VARCHAR ( 32 ),#账单类型
 bkDate DATETIME,#账单日期
 bkMoney FLOAT ( 11, 2 ),#账单金额
 remarkText TEXT,#备注文本
 FOREIGN KEY ( userId ) REFERENCES t_user ( id ),
 PRIMARY KEY ( id )
);

CREATE TABLE t_bookkeeping_expend (#支出表
 id INT ( 11 ) NOT NULL AUTO_INCREMENT,#主键
 userId INT ( 11 ) NOT NULL,#用户主键
 bkType VARCHAR ( 32 ),#账单类型
 bkDate DATETIME,#账单日期
 bkMoney FLOAT ( 11, 2 ),#账单金额
 remarkText TEXT,#备注文本
 FOREIGN KEY ( userId ) REFERENCES t_user ( id ),
 PRIMARY KEY ( id )
);
```

V2版本：

```mysql
/*
 Navicat Premium Data Transfer

 Source Server         : Test
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : db_antools

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 10/02/2020 20:45:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_bookkeeping_expend
-- ----------------------------
DROP TABLE IF EXISTS `t_bookkeeping_expend`;
CREATE TABLE `t_bookkeeping_expend`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `userId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bkType` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bkDate` datetime(0) NULL DEFAULT NULL,
  `bkMoney` float(11, 2) NULL DEFAULT NULL,
  `remarkText` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`, `userId`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_bookkeeping_income
-- ----------------------------
DROP TABLE IF EXISTS `t_bookkeeping_income`;
CREATE TABLE `t_bookkeeping_income`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `userId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bkType` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bkDate` datetime(0) NULL DEFAULT NULL,
  `bkMoney` float(11, 2) NULL DEFAULT NULL,
  `remarkText` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`, `userId`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE `t_feedback`  (
  `openId` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  `model` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `feedbackData` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `openId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` int(0) NULL DEFAULT NULL,
  `avatarUrl` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `openId`) USING BTREE,
  UNIQUE INDEX `openId`(`openId`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  INDEX `id_2`(`id`, `openId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

```



## 接口描述：

token在头部携带，格式为：Authorization：`token`

1. ### /api/login

   **是否需要权限**	：否

   **传入参数**   		：`code`

   **返回参数** 		  ：Login对象（`Token、session_key`）

   **说明**				  ：前端传入code，签发token和获取session后返回

   

2. ### /api/getUserData

   **是否需要权限**	：user

   **传入参数**   		：Login对象（`encryptedData、iv、session_key`）

   **返回参数** 		  ：User对象（头像、昵称、等）

   **说明**				  ：前端传入用户加密数据，由后端解密后返回

   

3. ### /api/feedback

   **是否需要权限**	：否

   **传入参数**   		：`feedbackData、model`

   **返回参数** 		  ：1

   **说明**				  ：前端传入反馈内容、手机型号，后端成功录入后返回1

   

4. ### /bookkeeping/listIncome         [/listExpend相似]

   **是否需要权限**	：user

   **传入参数**   		：

   1：`page、rows`（分页的页数与行数）`[非必须]`

   2：`bkDateStr`（按日期）

   3：`remarkText`（按备注）

   **返回参数** 		  ：

   1：`bookkeepingList`（查询的列表）

   2：`totalIncome`（查询条目的总收入）

   3：`sumIncomeMoney`（所有收入）

   **说明**				  ：前端可以传入2 或者3 其中一个或者两个，将按不同的条件查询收入情况并返回

   

5. ### /bookkeeping/listAll

   **是否需要权限**	：user

   **传入参数**   		：

   1：`page、rows`（分页的页数与行数）`[非必须]`

   2：`bkDateStr`（按日期）

   3：`remarkText`（按备注）

   **返回参数** 		  ：

   1：`bookkeepingAllList`（每天的数据列表）

   ​		a：`bookkeepingDayList`（今天的所有条目）

   ​		b：`dayDate`（今天的日期）

   ​		c：`dayIncomeMoney`（今天的收入）

   ​		d：`dayExpendMoney`（今天的支出）

   2：`totalIncome`（查询区间下总收入条目）

   3：`totalExpend`（查询区间下总支出条目）

   4：`sumIncomeMoney`（查询区间下总收入）

   5：`sumExpendMoney`（查询区间下总支出）

   **说明**				  ：前端传入2-日期或者3-备注，可以按照每天的列表样式返回

   

6. ### /bookkeeping/allIncomeExpendMoney

   **是否需要权限**	：user

   **传入参数**   		：bkDateStr（具体到天，格式 [2020-02-10] ）

   **返回参数** 		  ：

   1：`todayIncomeMoney`

   2：`todayExpendMoney`

   3：`monthIncomeMoney`

   4：`monthExpendMoney`

   5：`allIncomeMoney`

   6：`allExpendMoney`

   **说明**				  ：前端传入当前日期，即可返回今日、本月、全部的收入和支出统计

   

7. ### /bookkeeping/editBookkeeping

   **是否需要权限**	：user

   **传入参数**   		：Bookkeeping对象（incomeOrExpend**必须**输入，判断是支出还是收入）

   **返回参数** 		  ：1、0

   **说明**				  ：增加调用此接口时，对象的id为空，修改调用此接口时，对象id为要修改的id

   

8. ### /bookkeeping/deleteBookkeeping

   **是否需要权限**	：user

   **传入参数**   		：`id`、`incomeOrExpend`（income、expend）

   **返回参数** 		  ：1、0

   **说明**				  ：前端传入id和收入支出的判断，删除成功则返回1

   

9. ### /bookkeeping/listMonthsIncomeExpend

   **是否需要权限**	：user

   **传入参数**   		：无

   **返回参数** 		  ：MonthsStatisticData对象（`Year、Month、totExpend、totIncome`）

   **说明**				  ：无需参数即可查找此账号的每月支出收入统计

   

10. ### /bookkeeping/MonthsExpendTypeStatisticData

    **是否需要权限**	：user

    **传入参数**   		：`dateStr`（日期格式[2020-02]）

    **返回参数** 		  ：MonthsExpendTypeStatisticData对象（`type、data`）

    **说明**				  ：前端传入年份月份，即可查找此月的各种类型支出的统计数据

    

11. ### /Error/notoken

    **说明：**除了/api/login接口外，未携带Token访问接口，则跳转到这里

    

12. ### **/Error/unauthorized**

    **说明：**没有相应的权限访问接口，则跳转到这里