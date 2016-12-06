#RBAC相关表 准备

#1、插入tbrole表
INSERT INTO tbrole (roleKey,roleName,description)
VALUES('user','普通用户','普通用户'),
('admin','普通管理员','普通管理员'),
('superAdmin','超级管理员','超级管理员');

#2、插入tbuserrole表
INSERT INTO tbuserrole (userId,roleId) SELECT id,1 FROM tbusermain WHERE userName != 'admin';
INSERT INTO tbuserrole (userId,roleId) SELECT id,2 FROM tbusermain WHERE userName = 'admin';

#3、插入tbresource表
#1)、主页面图标   >type:0
#2)、管理员后台页面菜单>type:1
#3)、第三方导出数据权限>type:0
#添加 className、idName、level、
INSERT INTO tbresource (resKey,resName,resPath,parentId,resType,resUrl,description,className,idName,LEVEL)
VALUES('third_jclass','金课堂','/static/images/yun/jclass.png',0,'0','http://192.168.18.159:8091/load.html','第三方金课堂',NULL,NULL,'1'),
('third_hsbb','华师BB','/static/images/yun/hsbb.png',0,'0','#','第三方华师BB',NULL,NULL,'1'),
('third_lsmk','兰山募课','/static/images/yun/mooc.png',0,'0','http://192.168.18.159:8091/load.html','第三方兰山募课',NULL,NULL,'1'),

('system_personal_settings','个人设置','/static/images/yun/personal_settings.png',0,'1','/sso/personalSettings.html','个人设置',NULL,NULL,'1'),
('system_help_center','帮助中心','/static/images/yun/helper_center.png',0,'1','http://help.01edut.cn/','帮助中心',NULL,NULL,'1'),
('system_know_us','了解我们','/static/images/yun/know_hsjc.png',0,'1','#','了解我们',NULL,NULL,'1'),

#用户管理、第三方平台管理、客服管理、组织机构管理、管理员管理、站点管理
('menu_user_manage','用户管理','#',0,'2','#','用户管理','d1',NULL,'1'),
('menu_third_manage','第三方平台管理','#',0,'2','#','第三方平台管理','d2',NULL,'1'),
('menu_custom_manage','客服管理','#',0,'2','#','客服管理','d3',NULL,'1'),
('menu_organization_manage','组织机构管理','#',0,'2','#','组织机构管理','d4',NULL,'1'),
('menu_admin_manage','管理员管理','#',0,'2','#','管理员管理','d5',NULL,'1'),
('menu_website_manage','站点管理','#',0,'2','#','站点管理','d6',NULL,'1');
-------------------------------

#用户管理 7
INSERT INTO tbresource (resKey,resName,resPath,parentId,resType,resUrl,description,className,idName,LEVEL)
VALUES
('menu_user_list','用户列表','#',7,'2','/page/sso/userList/1,10,0,0,0,0,0.html','用户列表',NULL,'user_list','2'),
('menu_add_user','新增用户','#',7,'2','/page/sso/newUser.html','新增用户',NULL,'new_user','2'),

#第三方平台管理 8
('menu_platform_list','平台列表','#',8,'2','/page/sso/platformList/1,10.html','平台列表',NULL,'platform_list','2'),
('menu_platform_filter_list','导入数据过滤列表','#',8,'2','/page/sso/platformFilterList/1,10.html','导入数据过滤列表',NULL,'platformfilter_list','2'),

#客服管理 9
('menu_service_list','客服员列表','#',9,'2','/page/sso/serviceList.html','客服员列表',NULL,'service_list','2'),
('menu_new_service','新增客服','#',9,'2','/page/sso/newService.html','新增客服',NULL,'new_service','2'),

INSERT INTO tbresource (resKey,resName,resPath,parentId,resType,resUrl,description,className,idName,LEVEL)
VALUES
('menu_one_manage','一次客服管理','#',9,'2','/page/sso/oneManage.html','一次客服管理',NULL,'one_manage','2'),
('menu_two_manage','二次客服管理','#',9,'2','/page/sso/twoManage.html','二次客服管理',NULL,'two_manage','2'),

#组织机构管理 10
('menu_tissue_list','组织机构列表','#',10,'2','/page/sso/tissueList/1,10,s,0,0.html','组织机构列表',NULL,'tissue_list','2'),
('menu_new_tissue','新增组织机构','#',10,'2','/page/sso/newTissue.html','新增组织机构',NULL,'new_tissue','2'),
('menu_invitation_manage','邀请码管理','#',10,'2','/page/sso/invitationManage/1,10,0,0,0,0.html','邀请码管理',NULL,'invitation_manage','2'),
('menu_new_invitation','新增邀请码','#',10,'2','/page/sso/newInvitation.html','新增邀请码',NULL,'new_invitation','2'),

#管理员管理 11
('menu_admin_list','管理员列表','#',11,'2','/page/sso/adminList.html','管理员列表',NULL,'admin_list','2'),
('menu_new_admin','新增管理员','#',11,'2','/page/sso/newAdmin.html','新增管理员',NULL,'new_admin','2'),

#站点管理 12
('menu_site_basic','站点基本设置','#',12,'2','/page/sso/siteBasic.html','站点基本设置',NULL,'site_basic','2'),
('menu_email_port','邮件接口设置','#',12,'2','/email/emailPort.html','邮件接口设置',NULL,'email_port','2'),
('menu_mess_port','短信接口设置','#',12,'2','/sms/messPort.html','短信接口设置',NULL,'mess_port','2'),
('menu_site_log','站点日志','#',12,'2','/page/sso/siteLog.html','站点日志',NULL,'site_log','2');

#4、插入tbroleresource表
SELECT * FROM tbroleresource;

INSERT INTO tbroleresource (roleId,resourceId)
VALUES(1,4),
(1,5),
(1,6),

(2,7),
(2,8),
(2,9),
(2,10),
(2,11),
(2,12),
(2,13),
(2,15),
(2,16),
(2,17),
(2,19),
(2,20),
(2,21),
(2,23),
(2,25),
(2,27),
(2,28),
(2,29),
(2,30),

(3,7),
(3,8),
(3,9),
(3,10),
(3,11),
(3,12),
(3,13),
(3,14),
(3,15),
(3,16),
(3,17),
(3,18),
(3,19),
(3,20),
(3,21),
(3,22),
(3,23),
(3,24),
(3,25),
(3,26),
(3,27),
(3,28),
(3,29),
(3,30);
#5、插入tbuserresource表
CREATE TABLE tbuserresource(
id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
userId BIGINT(20),
resourceId BIGINT(20)
)

SELECT * FROM tbrole;
SELECT * FROM tbresource;
SELECT * FROM tbroleresource;
