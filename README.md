hsjc 项目SSO_center

该项目旨在第三方无缝接轨,第三方及时与SSO进行数据同步;CAS登录.

部署环境前提

Intellij Idea

jdk8

git

maven

tomcat

mysql

redis

目录说明: modify_record

1)、该目录旨在记录开发人员每天的开发内容,尤其是修改了sql,需要进行相应的记录,方便运维人员直接进行线上数据库的维护与同步.

2)、目录命名格式:yyyy-MM-dd>username>modify.txt/modify.sql(".sql"文件必须要有,".txt"文件可以不要)
    a、modify.txt记录修改的内容
    b、modify.sql记录当天修改的sql脚本
src>main>

java 开发的Java源代码文件

resources 配置文件目录

web>

public 公共的html文件

static 静态资源文件(css/js/image)

WEB-INF 页面文件

test 测试用例目录

/*部署上线需要修改的地方*/
1、AppInitializer类中setActiveProfiles 
本地修改为:develop
测试修改为:production
线上修改为:online

2、DBUtil中的加载的properties
本地为:application.develop.properties
测试为:application.production.properties
线上为:application.online.properties

3、InitService中需要数据库连接
本地为:application.develop.properties 中的连接
测试为:application.production.properties 中的连接
线上为:application.online.properties 中的连接

4、数据库表tbsystemproperties中的数据库连接参数要设置成相应环境的
本地为:application.develop.properties 中的连接
测试为:application.production.properties 中的连接
线上为:application.online.properties 中的连接
..0

PageHelper包含的信息：
endRow 结束的行数
firstPage 当前导航页码的第一个页码
hastNextPage 是否有下一页
hasPrevioisPage 是否有上一页
isFirstPage 是否是第一页
isLastPage 是否是最后一页
lastPage 当前导航页码的最后一个页码
list 所有的记录
navigatePages 导航页码数量 
navigatepageNums 导航页码(数组)
nextPage 下一页码
pageNum 当前页数
pageSize 每一页显示的记录数
pages 总页数
prePage 前一页码
size 当前页面的记录数
startRow 开始的行数(从第几行记录开始)
total 记录总数

上线导数据需要注意的地方;
需要保留的表数据:
1)、tb3rdclients、tbindexicos、tbrestactiontype、tbsystemproperties

2)、导入数据用mongo-mysql项目导入

3)、导入数据后需要执行此sql更新相应的用户表中的organizationCode.
UPDATE tbusermain tu SET tu.organizationCode =
 (SELECT tor.`organizationCode` FROM tborganization tor,tborganizationuser tou
 WHERE tou.organizationId = tor.`organizationId` AND tu.`userId` = tou.userId
 )