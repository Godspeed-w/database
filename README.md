# database

课程设计一：

1、给定某个学生的学号，查询这个学生的相关信息（姓名，性别，所在系名，所在寝室名称）  
		1 200906091
		
2、给定某个学生的学号，查询选修了这位学生全部课程的学生信息。
		2 200906091
		
3、给定某个教师的工号，查询其所上的每门课的平均成绩。
		3 T005
		
 4、统计每门课程的成绩信息（平均成绩，最高成绩，最低成绩，不及格人数）。
		4
		
5、查询每个寝室里住的同学的平均成绩信息，并按平均成绩从高到低排列。
		5
		
6、找出每个学生超过他选修课程平均成绩的课程号。
		6
		
7、查找那些选课不符合规定（即选课中同时选了某门课及其先行课）的学生姓名。
		7
		
8、朱红恒同学新选了课程：计算机控制理论及应用，成绩为80分，请插入记录。
		8
		
9、保密专业的系编号改为08，请完成更改（分三种情况）。
		9 1       //不允许修改
		9 2       //将系编号置空
		9 3       //级联修改
10、电子商务系要撤消，请完成更改（分三种情况）。
		10 1      //不允许修改
		10 2     //将电子商务系整行置空
		10 3     //级联删除
		
课程设计二：
提供用户定义完整性的功能。
1）提供定义主键与撤消的界面。定义主键：用户可以输入一个或多个字段后，程序将检查这些字段是否适合做主键，并给出提示，如果适合，则记录此主键。撤消主键：输入主键约束的名字后，将撤消此主键。
		21 学生表 sno,sname add
		
2）提供定义外键与撤消的界面。
		21 学生表 sno,sname delete

定义外键：用户在输入参照表名，字段，被参照表名，字段后，程序记录外键关系，并提供一个外键相关的删除例子。撤消外键：输入外键约束的名字后，将撤消此外键关系。
		22 学生表 sno,dorno 寝室信息表 dorno,dorname add
		22 学生表 sno,dorno 寝室信息表 dorno,dorname delete
		
		
课程设计三：
提供索引的功能。
1）建立索引：B+树，哈希，动态散列等中任选一种实现；
2）在某一个查询中利用索引；

课程设计四：
提供网络服务的功能
1）实现服务器/客户端的架构，数据文件放在服务器；
2）客户端提交查询，服务器通过网络返回查询结果。服务器接收到多个客户端提交的查询请求时，需要有调度算法来进行调度。
