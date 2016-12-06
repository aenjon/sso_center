#添加sex、realName字段
ALTER TABLE tbusermain ADD gender ENUM('0','1') ;
ALTER TABLE tbusermain ADD realName VARCHAR(100);

DELETE FROM tbusermain WHERE id > 2;

#删除数据,修改auto_increment
DELETE FROM tborganization;
ALTER TABLE tborganization AUTO_INCREMENT = 1;

DELETE FROM tborganizationuser;
ALTER TABLE tborganizationuser AUTO_INCREMENT = 1;


#用mongo-mysql依次插入数据:tborganization、tbusermain、tborganizationuser,然后执行如下sql语句.更新organizationCode.
UPDATE tbusermain tu SET tu.organizationCode =
 (SELECT tor.`organizationCode` FROM tborganization tor,tborganizationuser tou
 WHERE tou.organizationId = tor.`organizationId` AND tu.`userId` = tou.userId
 )

