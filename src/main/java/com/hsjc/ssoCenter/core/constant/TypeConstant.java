package com.hsjc.ssoCenter.core.constant;

/**
 * @author : zga
 * @date : 2015-11-24
 *
 * 类型常量类
 */
public class TypeConstant {

	public static final int USER_STUDENT = 1;
	public static final int USER_TEACHER = 2;
	public static final int USER_PARENT = 3;

	public static final int ADMIN_NONE = 0;
	public static final int ADMIN_SUPER = 1;
	public static final int ADMIN_SCHOOL = 2;
	public static final int ADMIN_NORMAL = 3;

	public static final int ORGANIZATION_BUREAU = 1;
	public static final int ORGANIZATION_SCHOOL = 2;

	public static final int DATA_MAPPING_USER = 1;
	public static final int DATA_MAPPING_CLASS = 2;
	public static final int DATA_MAPPING_MANAGER = 3;
//	public static final int DATA_MAPPING_GRADE = 0; // 不需要，id相同
//	public static final int DATA_MAPPING_STAGE = 0; // 不需要，id相同
	public static final int DATA_MAPPING_ORGANIZATION = 4;
//	public static final int DATA_MAPPING_SUBJECT = 0; // 不需要，id相同

	/**
	 * 账户状态 0未激活，1已激活
	 */
	public static final int USER_STATUS_ACTIVE= 0;
	public static final int USER_STATUS_INACTIVE = 1;

	/**
	 *	性别
	 */
	public static final int MAN= 1;
	public static final int WOMAN = 2;
	public static final int SECRET = 0;

	/**
	 * 教师职称
	 */
	public static final int TEACHER_20= 20;
	public static final int TEACHER_21 = 21;
	public static final int TEACHER_22 = 22;
	public static final int TEACHER_23 = 23;

	// 资源类型
	public static final int RESOURCE_COURSEWARE = 1;	// 课件
	public static final int RESOURCE_LESSON_PLAN = 2;	// 教案
	public static final int RESOURCE_MATERIAL = 3;		// 素材
	public static final int RESOURCE_VIDEO = 4;//视频
	public static final int RESOURCE_HOMEWORK = 5;
	public static final int RESOURCE_EXPERIMENT = 6;    //实验


	//测试题目类型
	public static final int QUIZZES_ST = 0;   //随堂
	public static final int QUIZZES_ZH= 1;   //综合

	// 视频状态
	public static final int VIDEO_AUDIT_PASS = 1;
	public static final int VIDEO_AUDIT_NO_PASS = 2;
	public static final int VIDEO_AUDIT_WAIT = 3;   //待审核

	//视频类型
	public static final int RESOURCE_DRAFT = 0; //草稿
	public static final int RESOURCE_RELEASE = 1; //发布

	// 资源状态
	public static final String RESOURCE_NOT_TRANS = "0"; //未转码
	public static final String RESOURCE_HAS_TRANS = "1"; //已转码

	// 题目状态
	public static final int EXERCISE_PASS = 0;//"已通过"
	public static final int EXERCISE_NO_PASS = 1;//"未通过"
	public static final int EXERCISE_WAIT = 2;//"待审核"

	//題目類型
	public static final int EXERCISE_SIN_OPTION = 1;	// 单选
	public static final int EXERCISE_MULTI_OPTION = 2;	// 多选
	public static final int EXERCISE_APPLICATION = 3;	// 应用Application
	public static final int EXERCISE_CLOZE = 4;	// 填空
	public static final int EXERCISE_SIMPLE_QUESTION = 5;	// 简答
	public static final int EXERCISE_READ = 6;	// 阅读理解
	public static final int EXERCISE_JUDGE = 7;	// 判断

	//题目难度类型
	public static final int EXERCISE_HARD = 1;	// 难
	public static final int EXERCISE_MEDIUM = 2;	// 中
	public static final int EXERCISE_EASY = 3;	// 易

	//作业发布状态
	public static final int HOMEWORK_RELEASE = 1; //已发布
	public static final int HOMEWORK_DRAFE = 0;  //草稿

	public static final int STATE_NULL = 2;//作业未做
	public static final int STATE_NO = 1;//未批阅
	public static final int STATE_YES = 0;//已批阅


	//错题本状态
	public static final int MISTAKES_PASS = 1;//通过，该错题已被消灭
	public static final int MISTAKES_NOPASS = 0;//没通过

	//视频学习状态
	public static final int STUDY_RESOURCE_OVER = 0;//视频学习完成
	public static final int STUDY_RESOURCE_ING = 1;//视频学习中

	//视频等级
	public static final int RESOURCE_BASE = 1;		//基础视频
	public static final int RESOURCE_EXPAND = 2;	//拓展视频
	public static final int RESOURCE_RESEARCH = 3;	//研究视频

	//提问、回复主题来源类型
	public static final int THEME_RESOURCE = 1;		//视频
	public static final int THEME_HOMEWORK = 2;		//作业

	//dialogue类型
	public static final int DIALOGUE_ASK = 1;		//提问
	public static final int DIALOGUE_ANSWER = 2;	//回答
	public static final int DIALOGUE_COMMENT = 3;	//评论

	//点赞类型
	public static final int UP_CANCEL = 0;			//点赞取消
	public static final int UP_CONFIRM = 1;			//点赞

	/**
	 * 视频暂停回看记录
	 */
	public static final String PLAYRECORD_SEEK = "seek";//回看
	public static final String PLAYRECORD_PAUSE = "pause";//暂停

	/**
	 * 是否助教
	 * 是：1
	 * 否：0
	 */
	public static final int TUTORSTATE_NO = 0;			    //否
	public static final int TUTORSTATE_YES = 1;				//是

	/**
	 * 上传途径
	 * 老师：0
	 * 学生：1
	 * 前期导入：2
	 */
	public static final int UPDATETYPE_TEA = 0;		       //老师
	public static final int UPDATETYPE_STU = 1;		       //学生
	public static final  int UPDATETYPE_INS =2;		       //前期导入


	/**
	 * 虚拟班级
	 */
	public static final int REAL_KLASS = 0;	             //真实班级
	public static final int VIRTUAL_KLASS = 1;           //虚拟班级


	/**
	 *	搜索时给定的上传时间范围
	 */
	public static final String SEARCH_TIME_ALL = "all";	//所有时间
	public static final String SEARCH_TIME_IN_ONE_WEEK = "in_one_week";	//一周内
	public static final String SEARCH_TIME_IN_ONE_MONTH = "in_one_month"; //一个月内
}