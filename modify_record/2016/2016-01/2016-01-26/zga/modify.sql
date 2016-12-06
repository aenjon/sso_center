#2016-1-26

#add new table which called tbsystemproperties
CREATE TABLE tbsystemproperties(
 id INT(11) PRIMARY KEY AUTO_INCREMENT,
 proKey VARCHAR(500),
 proValue VARCHAR(500),
 createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 modifyTime TIMESTAMP
);

#insert need data
INSERT INTO tbsystemproperties (proKey,proValue)
VALUES('dbUrl','jdbc:mysql://192.168.18.201:3323/sso_center?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8'),
('dbUserName','root'),
('dbPassword','123456'),
('redisHost','localhost'),
('redisPort','6379'),
('redisPassword',''),
('redisDictDatabase','3'),
('mailHost','smtp.163.com'),
('mailPort','25'),
('mailUserName','service@hsjc.com.cn'),
('mailPassword','***521HSJC1314'),
('mailFrom','service@hsjc.com.cn'),
('trdPublicKey','hsjcsso'),
('smsUrl','http://gw.api.taobao.com/router/rest'),
('smsAppKey','23300750'),
('smsAppSecret','8781b891cdb666c9e038d368ecafa7ac'),
('smsSignName','华师京城云平台'),
('smsTemplateCode','SMS_4785362'),
('smsType','normal'),
('websiteAddress','http://192.168.18.201:89');

SELECT * FROM tbsystemproperties;


#modify table tb3rdclients--add new column 'synCount'
ALTER TABLE tb3rdclients ADD synCount INT(11);

#modify table tbrestfullog--add new column 'synCount'
ALTER TABLE tbrestfullog ADD synCount INT(11);

#add delete trigger on tbsynuserjclass/tbsynuserbb


#add new table tb3rdsynuserdetaillog
#1)create table info
CREATE TABLE tb3rdsynuserdetaillog(
 id INT(11) PRIMARY KEY AUTO_INCREMENT,
 clientId VARCHAR(8),
 synTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 userId INT(11)
)


#2)create insert trigger info
DELIMITER $$

USE sso_center$$

DROP TRIGGER /*!50032 IF EXISTS */ tg_OnTb3rdSynUserDetailLogInsert$$

CREATE
    /*!50017 DEFINER = 'root'@'%' */
    TRIGGER tg_OnTb3rdSynUserDetailLogInsert AFTER INSERT ON tb3rdsynuserdetaillog
    FOR EACH ROW
BEGIN
  UPDATE tb3rdclients SET num = num + 1;
END;
$$
DELIMITER ;

#add new column 'description' on table tb3rdclients
ALTER TABLE tb3rdclients ADD description VARCHAR(500);

ALTER TABLE tb3rdclients MODIFY synCount INT(11) DEFAULT 0;

ALTER TABLE tb3rdclients ADD callbackUrl VARCHAR(500);

ALTER TABLE tb3rdclients ADD STATUS ENUM('0','1');