#tb3rdfilter 第三方过滤表
CREATE TABLE tb3rdfilter (
  id int(11) NOT NULL AUTO_INCREMENT,
  trdClientId varchar(100) DEFAULT NULL COMMENT '第三方ClientId',
  organizationCode int(11) DEFAULT NULL COMMENT '组织机构Id',
  tstudent varchar(100) DEFAULT NULL COMMENT 'teacher/student',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8



#创建BB同步表
CREATE TABLE tbsynorganizationbb LIKE tbsynorganizationjclass;

CREATE TABLE tbsynuserbb LIKE tbsynuserjclass;

#tbsmssend
alter table tbsmssend add smsType varchar(500);
alter table tbsmssend add smsSignName varchar(500);
alter table tbsmssend add smsSendCode varchar(500);
alter table tbsmssend add smsParam varchar(500);
alter table tbsmssend add smsTemplateCode varchar(500);

#trigger
DELIMITER $$

USE sso_center$$

DROP TRIGGER IF EXISTS tg_OnTb3rdFilterInsert$$

CREATE    
    TRIGGER tg_OnTb3rdFilterInsert AFTER INSERT ON tb3rdfilter
    FOR EACH ROW
BEGIN
    #jclass
    IF(new.3rdClientId = '6NqlFnLG')
    THEN
	INSERT INTO tbsynuserjclass (userId) SELECT id FROM tbusermain WHERE TYPE = new.tstudent AND organizationCode = new.organizationCode;
	INSERT INTO tbsynorganizationjclass (organizationCode) VALUES(new.organizationCode);
    END IF;

    #bb
    IF(new.3rdClientId = '6NqlFnLG')
    THEN
	INSERT INTO tbsynuserbb (userId) SELECT id FROM tbusermain WHERE TYPE = new.tstudent AND organizationCode = new.organizationCode;
	INSERT INTO tbsynorganizationbb (organizationCode) VALUES(new.organizationCode);
    END IF;
END;
$$

DELIMITER ;

#####
DELIMITER $$

USE sso_center$$

DROP TRIGGER IF EXISTS tg_OnTbUserMainInsert$$

CREATE
    TRIGGER tg_OnTbUserMainInsert AFTER INSERT ON tbusermain
    FOR EACH ROW
BEGIN
    DECLARE m_notfound INT DEFAULT 0;
    DECLARE m_clientId VARCHAR(100) DEFAULT '';
    DECLARE m_organizationCode INT;
    DECLARE m_tstudent VARCHAR(100) DEFAULT '';

    #jclass filter info
    DECLARE m_cur1 CURSOR FOR
	SELECT trdClientId,organizationCode,tstudent FROM tb3rdfilter WHERE trdClientId = '6NqlFnLG';
    #bb filter info
    DECLARE m_cur2 CURSOR FOR
	SELECT trdClientId,organizationCode,tstudent FROM tb3rdfilter WHERE trdClientId = 'WlE7yrQm';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET m_notfound = 1;

    SET m_notfound = 0;

    OPEN m_cur1;
    label_at_do:LOOP
    FETCH m_cur1 INTO m_clientId,m_organizationCode,m_tstudent;

    IF ( m_notfound = 1)
    THEN
	LEAVE label_at_do;
    END IF;

    #organizationCode 不相同的话,退出
    IF(m_organizationCode <> new.organizationCode)
    THEN
	LEAVE label_at_do;
    END IF;

    IF((m_tstudent = 'teacher' AND new.type = 'teacher') OR (m_tstudent = 'student' AND new.type = 'student'))
    THEN
	INSERT INTO tbsynuserjclass (userId) VALUES(new.id);
    END IF;

    END LOOP;
    CLOSE m_cur1;
END;
$$
DELIMITER ;