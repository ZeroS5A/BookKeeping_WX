# BookKeeping_WX
## 数据库表：
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
