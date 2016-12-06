#tb3rdfilter 增加字段createTime
alter table tb3rdfilter add createTime timestamp default current_timestamp;

#RBAC相关表的记录同步
##1)、首先执行2016.3.23-sso_center-schema.sql,创建相应的数据库表
##2)、然后执行2016.3.23-sso_center-data.sql,插入数据.alter
##3)、然后执行语句插入数据到tbuserrole表.(一般的用户都是roleId为1,admin的角色为3->超级管理员)
insert into tbuserrole select id,1 from tbusermain where username != 'admin';

insert into tbuserrole select id,3 from tbusermain where username = 'admin';


