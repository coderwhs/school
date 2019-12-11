DROP TABLE tb_edu_dorm;;/*SkipError*/
CREATE TABLE tb_edu_dorm(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    dorm_building_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '宿舍楼名称' ,
    dorm_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '宿舍房间号' ,
    bed_num INT NOT NULL  DEFAULT 0 COMMENT '床位数' ,
    dorm_tel VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '宿舍电话' ,
    dorm_address VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '宿舍地址' ,
    dorm_admin VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '宿舍管理员' ,
    dorm_admin_tel VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '管理员电话' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '宿舍信息表 ';;



DROP TABLE tb_edu_dorm_student;;/*SkipError*/
CREATE TABLE tb_edu_dorm_student(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    drom_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '宿舍主键id' ,
    student_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '学号' ,
    bed_no VARCHAR(32)    COMMENT '床位号' ,
    start_date DATE    COMMENT '入住开始日期' ,
    end_date DATE    COMMENT '入住结束日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '宿舍学生入住表 ';;



DROP TABLE tb_edu_dorm_student_leave;;/*SkipError*/
CREATE TABLE tb_edu_dorm_student_leave(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    dorm_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '宿舍主键id' ,
    student_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '学号' ,
    leave_cause VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '请假原因' ,
    start_date DATETIME    COMMENT '开始日期' ,
    end_date DATETIME    COMMENT '结束日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '宿舍学生请假表 ';;



DROP TABLE tb_edu_student;;/*SkipError*/
CREATE TABLE tb_edu_student(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    student_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '学号' ,
    student_name VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '姓名' ,
    gender INT NOT NULL  DEFAULT 0 COMMENT '性别' ,
    birth_date DATE    COMMENT '出生日期' ,
    nation VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '民族' ,
    native_province VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '籍贯省' ,
    native_city VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '籍贯市' ,
    id_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '证件类型' ,
    id_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '证件号码' ,
    mobile VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '手机号码' ,
    admission_date DATE    COMMENT '入学日期' ,
    admission_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '入学成绩' ,
    student_status VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '学籍状态' ,
    political_status VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '政治面貌' ,
    college_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '学院代码' ,
    major_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '专业代码' ,
    class_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '班级代码' ,
    drom_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '宿舍主键id' ,
    duty VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '职务' ,
    photo_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '照片类型' ,
    photo_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '照片名称' ,
    photo_path VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '照片路径' ,
    upload_date DATETIME    COMMENT '照片上传时间' ,
    bone_age INT NOT NULL  DEFAULT 0 COMMENT '骨龄' ,
    student_height INT NOT NULL  DEFAULT 0 COMMENT '身高' ,
    student_weight DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '体重' ,
    father VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '父亲姓名' ,
    father_height INT NOT NULL  DEFAULT 0 COMMENT '父亲身高' ,
    father_weight DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '父亲体重' ,
    father_mobile VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '父亲电话' ,
    father_job VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '父亲工作单位' ,
    mother VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '母亲姓名' ,
    mother_height INT NOT NULL  DEFAULT 0 COMMENT '母亲身高' ,
    mother_weight DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '母亲体重' ,
    mother_mobile VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '母亲电话' ,
    mother_job VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '母亲工作单位' ,
    home_tel VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '家庭电话' ,
    home_address VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '家庭住址' ,
    police_station VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '户口所在派出所' ,
    resume VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '简介' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    del_flag VARCHAR(1) NOT NULL  DEFAULT '' COMMENT '删除标志' ,
    PRIMARY KEY (id)
) COMMENT = '学生信息表 ';;


DROP TABLE tb_edu_teacher;;/*SkipError*/
CREATE TABLE tb_edu_teacher(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    teacher_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教师代码' ,
    name VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '姓名' ,
    gender VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '性别' ,
    birth_date DATE    COMMENT '出生日期' ,
    nation VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '民族' ,
    native_province VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '籍贯省' ,
    native_city VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '籍贯市' ,
    id_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '证件类型' ,
    id_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '证件号码' ,
    mobile VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '手机号码' ,
    college_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '学院代码' ,
    major_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '专业代码' ,
    title VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '职称' ,
    duty VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '职务' ,
    home_tel VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '家庭电话' ,
    home_address VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '家庭地址' ,
    photo_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '照片类型' ,
    photo_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '照片名称' ,
    photo_path VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '照片路径' ,
    upload_date DATETIME    COMMENT '照片上传时间' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    del_flat VARCHAR(1) NOT NULL  DEFAULT '' COMMENT '删除标志' ,
    PRIMARY KEY (id)
) COMMENT = '教师信息表 ';;


DROP TABLE tb_edu_sport;;/*SkipError*/
CREATE TABLE tb_edu_sport(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '项目代码' ,
    sport_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '大项名称' ,
    disciplines_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '分项名称' ,
    event_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '小项名称' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动项目表 ';;


DROP TABLE tb_edu_sport_class;;/*SkipError*/
CREATE TABLE tb_edu_sport_class(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    class_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练班名称' ,
    coach_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教练员代码' ,
    sport_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动项目代码' ,
    training_year INT NOT NULL  DEFAULT 0 COMMENT '训练年度' ,
    training_stage VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练阶段' ,
    start_date DATE    COMMENT '开始日期' ,
    end_date DATE    COMMENT '结束日期' ,
    training_address VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '训练地点' ,
    training_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '主要训练任务' ,
    remark VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '备注' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动项目训练班表 ';;


DROP TABLE tb_edu_sport_class_task;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_task(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练班主键id' ,
    task_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练任务名称' ,
    teacher_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '发布人' ,
    task_date DATE    COMMENT '训练日期' ,
    task_start_time DATETIME    COMMENT '训练开始时间' ,
    task_end_time DATETIME    COMMENT '训练结束时间' ,
    task_repeat VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '重复周期' ,
    task_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '训练内容' ,
    task_method VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练手段' ,
    task_duration_time INT NOT NULL  DEFAULT 0 COMMENT '训练持续时间（分钟）' ,
    task_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练量' ,
    task_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练强度' ,
    task_recovery_method VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练恢复手段' ,
    remark VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '备注' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动项目训练班训练计划表 ';;


DROP TABLE tb_edu_sport_class_task_attend;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_task_attend(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_task_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练计划主键id' ,
    student_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员学号' ,
    attend_status VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '考勤状态' ,
    attend_time DATETIME    COMMENT '考勤时间' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员训练任务出勤表 ';;


DROP TABLE tb_edu_sport_class_task_leave;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_task_leave(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_task_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练计划主键id' ,
    student_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员学号' ,
    leave_cause VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '请假原因' ,
    start_date DATETIME    COMMENT '开始日期' ,
    end_date DATETIME    COMMENT '结束日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员训练任务请假表 ';;


DROP TABLE tb_edu_athlete_sport_class;;/*SkipError*/
CREATE TABLE tb_edu_athlete_sport_class(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练班主键id' ,
    student_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员学号' ,
    attend_date DATE    COMMENT '参加日期' ,
    athlete_best_score VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛最好成绩' ,
    athlete_award_tech_grade VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '获得等级' ,
    about_break_record VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '破纪录状况' ,
    remark VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '备注' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员训练班经历表 ';;



DROP TABLE tb_edu_athlete_sport_score;;/*SkipError*/
CREATE TABLE tb_edu_athlete_sport_score(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练班主键id' ,
    student_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员学号' ,
    test_event VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '测试小项' ,
    test_score VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '测试成绩' ,
    test_date DATE    COMMENT '测试日期' ,
    remark VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '备注' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员项目测试表 ';;


DROP TABLE tb_edu_athlete_contest;;/*SkipError*/
CREATE TABLE tb_edu_athlete_contest(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    student_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员学号' ,
    contest_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '参赛名称' ,
    contest_date DATE    COMMENT '参赛日期' ,
    contest_address VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '参赛地点' ,
    contest_sport VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '参赛项目' ,
    contest_score VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '参赛成绩' ,
    contest_result VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '参赛名次' ,
    awarded_tech_grade VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '授予技术等级' ,
    awarded_department VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '授予单位' ,
    awarded_date DATE    COMMENT '授予日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员参赛信息表 ';;


DROP TABLE tb_edu_athlete_transport;;/*SkipError*/
CREATE TABLE tb_edu_athlete_transport(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    student_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员学号' ,
    sport_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动项目代码' ,
    athlete_tech_grade VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员等级' ,
    transport_department VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '输送单位' ,
    transport_teacher_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '输送教练员代码' ,
    transport_date DATE    COMMENT '输送时间' ,
    accept_department VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '吸收单位' ,
    accept_department_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '吸收单位类别' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员输送表 ';;


