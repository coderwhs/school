
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS tb_edu_dorm;;/*SkipError*/
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



DROP TABLE IF EXISTS tb_edu_dorm_athlete_living;;/*SkipError*/
CREATE TABLE tb_edu_dorm_athlete_living(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    dorm_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '宿舍' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
    bed_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '床位号' ,
    start_date DATE    COMMENT '入住开始日期' ,
    end_date DATE    COMMENT '入住结束日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '宿舍运动员入住表 ';;


DROP TABLE IF EXISTS tb_edu_dorm_athlete_leave;;/*SkipError*/
CREATE TABLE tb_edu_dorm_athlete_leave(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    dorm_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '宿舍主键id' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
    leave_cause VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '请假原因' ,
    start_date DATETIME    COMMENT '开始日期' ,
    end_date DATETIME    COMMENT '结束日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '宿舍运动员请假表 ';;



DROP TABLE IF EXISTS tb_edu_athlete;;/*SkipError*/
CREATE TABLE tb_edu_athlete(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    athlete_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '学号' ,
    athlete_name VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '姓名' ,
    gender VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '性别' ,
    nation VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '民族' ,
    birth_date DATE    COMMENT '出生日期' ,
    id_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '身份证号' ,
    mobile VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '手机号码' ,
    native_province VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '籍贯省' ,
    native_city VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '籍贯市' ,
    zipcode VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '邮政编码' ,
    police_station VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '户籍所在派出所' ,
    home_address VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '家庭住址' ,
    father VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '父亲姓名' ,
    father_height INT NOT NULL  DEFAULT 0 COMMENT '父亲身高' ,
    father_mobile VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '父亲电话' ,
    mother VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '母亲姓名' ,
    mother_height INT NOT NULL  DEFAULT 0 COMMENT '母亲身高' ,
    mother_mobile VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '母亲电话' ,
    transport_department_city VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '输送单位区县' ,
    transport_department_school VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '输送单位名称' ,
    admission_date DATE    COMMENT '入学日期' ,
    grade VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '就读年级' ,
    major_sport VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '专业项目' ,
    major_sport_attend_date DATE    COMMENT '入队时间' ,
    major_sport_teacher_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '带训教练代码' ,
    major_sport_teacher_name VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '带训教练姓名' ,
    student_height INT NOT NULL  DEFAULT 0 COMMENT '身高' ,
    student_weight DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '体重' ,
    retire_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '退役时间' ,
    accept_department_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '吸收单位类别' ,
    accept_department VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '吸收单位' ,
    photo_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '照片类型' ,
    photo_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '照片名称' ,
    photo_path VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '照片路径' ,
    upload_date DATETIME    COMMENT '照片上传时间' ,
    resume VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '简介' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    del_flag VARCHAR(1) NOT NULL  DEFAULT '' COMMENT '删除标志' ,
    PRIMARY KEY (id)
) COMMENT = '运动员信息表 ';;


DROP TABLE IF EXISTS tb_edu_athlete_sport_class;;/*SkipError*/
CREATE TABLE tb_edu_athlete_sport_class(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练队' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
    attend_date DATE    COMMENT '入队日期' ,
    athlete_best_score VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛最好成绩' ,
    athlete_award_tech_grade VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '获得等级' ,
    about_break_record VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '破纪录状况' ,
    remark VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '备注' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '训练队成员表（运动员训练班经历） ';;



DROP TABLE IF EXISTS tb_edu_athlete_sport_score;;/*SkipError*/
CREATE TABLE tb_edu_athlete_sport_score(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练队' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
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




DROP TABLE IF EXISTS tb_edu_athlete_contest;;/*SkipError*/
CREATE TABLE tb_edu_athlete_contest(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
    coach_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教练员' ,
    contest_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '比赛名称' ,
    contest_date DATE    COMMENT '比赛日期' ,
    contest_address VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '比赛地点' ,
    contest_sport VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛项目' ,
    contest_event VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '比赛小项' ,
    contest_result VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛名次' ,
    athlete_department VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '所在单位' ,
    awarded_tech_grade VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '授予技术等级' ,
    awarded_department VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '授予单位' ,
    awarded_date DATE    COMMENT '授予日期' ,
    athlete_news TEXT    COMMENT '个人风采' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员参赛信息表 ';;



DROP TABLE IF EXISTS tb_edu_athlete_transport;;/*SkipError*/
CREATE TABLE tb_edu_athlete_transport(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
    sport_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动项目' ,
    athlete_tech_grade VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员等级' ,
    transport_department VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '输送单位' ,
    transport_coach_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '输送教练员' ,
    transport_date DATE    COMMENT '输送时间' ,
    accept_department_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '吸收单位类别' ,
    accept_department VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '吸收单位' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员输送表 ';;



DROP TABLE IF EXISTS tb_edu_athlete_coach_evaluation;;/*SkipError*/
CREATE TABLE tb_edu_athlete_coach_evaluation(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    athlete_sport_class_id VARCHAR(32) NOT NULL   COMMENT '训练队成员' ,
    start_date DATE    COMMENT '开始日期' ,
    end_date DATE    COMMENT '结束日期' ,
    evaluation VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '总体评价' ,
    evaluator VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '评价教练' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员评价信息表 ';;



DROP TABLE IF EXISTS tb_edu_athlete_other_trianning_info;;/*SkipError*/
CREATE TABLE tb_edu_athlete_other_trianning_info(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
    record_date DATE    COMMENT '记录时间' ,
    content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '内容' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员训练其他事项记录信息表 ';;




DROP TABLE IF EXISTS tb_edu_athlete_literacy_score;;/*SkipError*/
CREATE TABLE tb_edu_athlete_literacy_score(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
    grade VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '年级' ,
    academic_year VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教学年度' ,
    semester VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '学期' ,
    chinese_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '语文' ,
    maths_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '数学' ,
    english_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '英语' ,
    politics_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '政治' ,
    physics_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '物理' ,
    chemistry_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '化学' ,
    history_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '历史' ,
    geography_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '地理' ,
    biology_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '生物' ,
    music_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '音乐' ,
    art_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '美术' ,
    pe_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '体育与健康' ,
    it_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '信息技术' ,
    gt_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '通用技术' ,
    society_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '历史与社会' ,
    science_score DECIMAL(32,10) NOT NULL  DEFAULT 0 COMMENT '科学' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员文化课成绩信息表 ';;




DROP TABLE IF EXISTS tb_edu_coach;;/*SkipError*/
CREATE TABLE tb_edu_coach(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    coach_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教练员代码' ,
    coach_name VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '姓名' ,
    gender VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '性别' ,
    nation VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '民族' ,
    birth_date DATE    COMMENT '出生日期' ,
    id_no VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '身份证号' ,
    native_province VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '籍贯省' ,
    native_city VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '籍贯市' ,
    political_status VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '政治面貌' ,
    home_address VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '家庭地址' ,
    mobile VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '手机号码' ,
    edu_background VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '学历' ,
    edu_degree VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '学位' ,
    staff_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '编制类型' ,
    hire_date DATE    COMMENT '聘用日期' ,
    hire_years INT NOT NULL  DEFAULT 0 COMMENT '聘用年限' ,
    marjor_info VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '何年毕业于何学校专业' ,
    coach_start_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '初任教练员时间' ,
    l3_appointment_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '三级教练员聘任时间' ,
    l2_appointment_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '二级教练员聘任时间' ,
    l1_appointment_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '一级教练员聘任时间' ,
    ladv_cert_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '取得高级教练员资格时间' ,
    ladv_appointment_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '高级教练员聘任时间' ,
    lcn_cert_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '取得国家级教练员资格时间' ,
    lcn_appointment_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '国家级教练员聘任时间' ,
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
) COMMENT = '教练员信息表 ';;




DROP TABLE IF EXISTS tb_edu_coach_edu_resume;;/*SkipError*/
CREATE TABLE tb_edu_coach_edu_resume(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    coach_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教练员' ,
    start_date DATE    COMMENT '开始日期' ,
    end_date DATE    COMMENT '结束日期' ,
    resume VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '从事何种工作' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '教练员个人简介信息表 ';;



DROP TABLE IF EXISTS tb_edu_coach_sport_resume;;/*SkipError*/
CREATE TABLE tb_edu_coach_sport_resume(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    coach_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教练员' ,
    sport_name VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动项目' ,
    attend_date DATE    COMMENT '参加时间' ,
    awarded_highest_grade VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '获得最高等级' ,
    awarded_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '获得年度(年)' ,
    highest_training_unit VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '最高训练单位' ,
    training_date VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练年度(年)' ,
    contest_event VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '比赛小项' ,
    contest_score VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '最好成绩' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '教练员运动经历信息表 ';;




DROP TABLE IF EXISTS tb_edu_coach_performance;;/*SkipError*/
CREATE TABLE tb_edu_coach_performance(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    coach_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教练员' ,
    performance_year INT NOT NULL  DEFAULT 0 COMMENT '业务年度' ,
    athlete_num INT NOT NULL  DEFAULT 0 COMMENT '现带训(人)' ,
    attend_num INT NOT NULL  DEFAULT 0 COMMENT '年度进队(人)' ,
    leave_num INT NOT NULL  DEFAULT 0 COMMENT '年度离队(人)' ,
    city_school_num INT NOT NULL  DEFAULT 0 COMMENT '市体校(人)' ,
    province_school_num INT NOT NULL  DEFAULT 0 COMMENT '省体校(人)' ,
    province_team_num INT NOT NULL  DEFAULT 0 COMMENT '省队集试训(人)' ,
    bayi_team_num INT NOT NULL  DEFAULT 0 COMMENT '八一集试训(人)' ,
    to_national_team INT NOT NULL  DEFAULT 0 COMMENT '输送国家队(人)' ,
    to_national_youth_team INT NOT NULL  DEFAULT 0 COMMENT '输送国青队(人)' ,
    to_province_team INT NOT NULL  DEFAULT 0 COMMENT '输送省队(人)' ,
    to_province_school INT NOT NULL  DEFAULT 0 COMMENT '输送省体校(人)' ,
    to_city_school INT NOT NULL  DEFAULT 0 COMMENT '输送市体校(人)' ,
    to_college INT NOT NULL  DEFAULT 0 COMMENT '输送高校(人)' ,
    to_remark VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '输送备注' ,
    contest_first INT NOT NULL  DEFAULT 0 COMMENT '锦标赛第一(人)' ,
    contest_second INT NOT NULL  DEFAULT 0 COMMENT '锦标赛第二(人)' ,
    contest_third INT NOT NULL  DEFAULT 0 COMMENT '锦标赛第三(人)' ,
    contest_excellence INT NOT NULL  DEFAULT 0 COMMENT '锦标赛达优(人)' ,
    contest_good INT NOT NULL  DEFAULT 0 COMMENT '锦标赛达良(人)' ,
    contest_total_points INT NOT NULL  DEFAULT 0 COMMENT '锦标赛总分' ,
    contest_remark INT NOT NULL  DEFAULT 0 COMMENT '锦标赛备注' ,
    is_sc_team CHAR(1) NOT NULL  DEFAULT '' COMMENT '是否年度少儿锦标赛精神文明运动队' ,
    rewards_punishments VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '奖惩情况' ,
    appraisal_score VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '年度业务考核得分' ,
    appraisal_grade VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '年度业务考核等级' ,
    audit_opinion VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '学校训练部门审核意见' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '教练员年度业务情况表 ';;




DROP TABLE IF EXISTS tb_edu_coach_training;;/*SkipError*/
CREATE TABLE tb_edu_coach_training(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    coach_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教练员' ,
    start_date DATE    COMMENT '开始日期' ,
    end_date DATE    COMMENT '结束日期' ,
    training_place VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '培训地点' ,
    training_course VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '培训名称' ,
    organizer VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '主办单位' ,
    remark VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '备注' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '教练员学习培训信息表 ';;





DROP TABLE IF EXISTS tb_edu_coach_paper;;/*SkipError*/
CREATE TABLE tb_edu_coach_paper(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    coach_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教练员' ,
    paper_title VARCHAR(128)    COMMENT '论文名称' ,
    publication_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '刊物名称' ,
    publication_level VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '刊物等级' ,
    publish_date DATE    COMMENT '发表日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '教练员论文发表信息表 ';;





DROP TABLE IF EXISTS tb_edu_sport;;/*SkipError*/
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



DROP TABLE IF EXISTS tb_edu_sport_venue;;/*SkipError*/
CREATE TABLE tb_edu_sport_venue(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动项目' ,
    venue_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '场馆名称' ,
    venue_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '场馆类型' ,
    venue_capacity VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '容纳人数' ,
    venue_address VARCHAR(256) NOT NULL  DEFAULT '' COMMENT '场馆地址' ,
    venue_admin VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '管理员' ,
    venue_admin_tel VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '联系电话' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动场馆信息表 ';;





DROP TABLE IF EXISTS tb_edu_sport_equipment;;/*SkipError*/
CREATE TABLE tb_edu_sport_equipment(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动项目' ,
    equipment_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '器材分类' ,
    equipment_sn VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '器材序列号' ,
    equipment_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '器材名称' ,
    equipment_price VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '器材价格' ,
    store_location VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '存放地点' ,
    inventory_qty INT NOT NULL  DEFAULT 0 COMMENT '库存数量' ,
    available_qty VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '可借数量' ,
    lending_qty VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '借出数量' ,
    book_qty VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '预定数量' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动器材信息表 ';;



DROP TABLE IF EXISTS tb_edu_sport_class;;/*SkipError*/
CREATE TABLE tb_edu_sport_class(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    class_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练班名称' ,
    coach_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '教练员' ,
    sport_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动项目' ,
    training_year INT NOT NULL  DEFAULT 0 COMMENT '训练年度' ,
    training_stage VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练阶段' ,
    training_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练形式' ,
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
) COMMENT = '训练队表 ';;




DROP TABLE IF EXISTS tb_edu_sport_class_file;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_file(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练队' ,
    pub_title VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '发布标题' ,
    pub_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '内容描述' ,
    file_type VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '文件类型' ,
    file_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '文件名称' ,
    file_url VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '文件地址' ,
    file_path DATE    COMMENT '文件路径' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动项目训练班上传文件表 ';;




DROP TABLE IF EXISTS tb_edu_sport_class_athlete_attend;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_athlete_attend(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练队' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
    attend_status VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '考勤状态' ,
    attend_time DATETIME    COMMENT '考勤时间' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员训练出勤表 ';;






DROP TABLE IF EXISTS tb_edu_sport_class_athlete_leave;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_athlete_leave(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练队' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
    start_date DATETIME    COMMENT '开始日期' ,
    end_date DATETIME    COMMENT '结束日期' ,
    leave_cause VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '请假原因' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '运动员训练请假表 ';;





DROP TABLE IF EXISTS tb_edu_sport_class_week_plan;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_week_plan(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练队' ,
    plan_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练计划名称' ,
    start_date DATE    COMMENT '周开始日期' ,
    end_time DATE    COMMENT '周结束日期' ,
    task_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '目的任务' ,
    mon_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周一训练量' ,
    mon_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周一训练强度' ,
    mon_time VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周一训练时间' ,
    mon_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '周一训练内容' ,
    tue_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周二训练量' ,
    tue_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周二训练强度' ,
    tue_time VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周二训练时间' ,
    tue_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '周二训练内容' ,
    wed_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周三训练量' ,
    wed_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周三训练强度' ,
    wed_time VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周三训练时间' ,
    wed_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '周三训练内容' ,
    thu_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周四训练量' ,
    thu_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周四训练强度' ,
    thu_time VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周四训练时间' ,
    thu_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '周四训练内容' ,
    fri_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周五训练量' ,
    fri_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周五训练强度' ,
    fri_time VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周五训练时间' ,
    fri_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '周五训练内容' ,
    sat_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周六训练量' ,
    sat_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周六训练强度' ,
    sat_time VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周六训练时间' ,
    sat_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '周六训练内容' ,
    sun_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周日训练量' ,
    sun_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周日训练强度' ,
    sun_time VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '周日训练时间' ,
    sun_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '周日训练内容' ,
    week_days VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '本周完成训练天数' ,
    week_courses VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '本周完成训练课数' ,
    week_hours VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '本周完成训练时数' ,
    week_workload VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '本周运动量完成情况' ,
    week_summary VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '本周训练小结' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '周训练计划信息表 ';;





DROP TABLE IF EXISTS tb_edu_sport_class_course_plan;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_course_plan(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练队' ,
    plan_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练计划名称' ,
    course_date DATE    COMMENT '课训日期' ,
    task_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '目的任务' ,
    prepare_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '准备训练量' ,
    prepare_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '准备训练强度' ,
    prepare_time VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '准备训练时间' ,
    prepare_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '准备训练内容' ,
    basic_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '基本训练量' ,
    basic_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '基本训练强度' ,
    basic_time VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '基本训练时间' ,
    basic_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '基本训练内容' ,
    finish_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '结束训练量' ,
    finish_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '结束训练强度' ,
    finish_time VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '结束训练时间' ,
    finish_content VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '结束训练内容' ,
    course_summary VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '课后小结' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '课训练计划信息表 ';;





DROP TABLE IF EXISTS tb_edu_sport_class_winter_plan;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_winter_plan(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练队' ,
    plan_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练计划名称' ,
    prepare_start_date DATE    COMMENT '准备期开始日期' ,
    prepare_end_date DATE    COMMENT '准备期结束日期' ,
    prepare_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '准备期训练任务' ,
    prepare_quality_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期素质训练' ,
    prepare_tech_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期技术训练' ,
    prepare_tactical_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期战术训练' ,
    prepare_mental_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期心理训练' ,
    prepare_method VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '准备期训练手段' ,
    prepare_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '准备期训练量' ,
    prepare_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '准备期训练强度' ,
    basic_start_date DATE    COMMENT '基本期开始日期' ,
    basic_end_date DATE    COMMENT '基本期结束日期' ,
    basic_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '基本期训练任务' ,
    basic_quality_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '基本期素质训练' ,
    basic_tech_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '基本期技术训练' ,
    basic_tactical_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '基本期战术训练' ,
    basic_mental_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '基本期心理训练' ,
    basic_method VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '基本期训练手段' ,
    basic_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '基本期训练量' ,
    basic_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '基本期训练强度' ,
    finish_start_date DATE    COMMENT '结束期开始日期' ,
    finish_end_date DATE    COMMENT '结束期结束日期' ,
    finish_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '结束期训练任务' ,
    finish_quality_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '结束期素质训练' ,
    finish_tech_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '结束期技术训练' ,
    finish_tactical_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '结束期战术训练' ,
    finish_mental_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '结束期心理训练' ,
    finish_method VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '结束期训练手段' ,
    finish_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '结束期训练量' ,
    finish_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '结束期训练强度' ,
    dept_evaluation VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '竞赛科评价' ,
    dept_evaluation_date DATE    COMMENT '竞赛科评价日期' ,
    school_evaluation VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '校领导评价' ,
    school_evaluation_date DATE    COMMENT '校领导评价日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '冬训阶段训练计划信息表 ';;





DROP TABLE IF EXISTS tb_edu_sport_class_summer_plan;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_summer_plan(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练队' ,
    plan_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练计划名称' ,
    prepare_start_date DATE    COMMENT '准备期开始日期' ,
    prepare_end_date DATE    COMMENT '准备期结束日期' ,
    prepare_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '准备期训练任务' ,
    prepare_quality_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期素质训练' ,
    prepare_tech_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期技术训练' ,
    prepare_tactical_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期战术训练' ,
    prepare_mental_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期心理训练' ,
    prepare_method VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '准备期训练手段' ,
    prepare_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '准备期训练量' ,
    prepare_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '准备期训练强度' ,
    contest_start_date DATE    COMMENT '比赛期开始日期' ,
    contest_end_date DATE    COMMENT '比赛期结束日期' ,
    contest_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '比赛期训练任务' ,
    contest_quality_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛期素质训练' ,
    contest_tech_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛期技术训练' ,
    contest_tactical_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛期战术训练' ,
    contest_mental_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛期心理训练' ,
    contest_method VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '比赛期训练手段' ,
    contest_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '比赛期训练量' ,
    contest_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '比赛期训练强度' ,
    transition_start_date DATE    COMMENT '过渡期开始日期' ,
    transition_end_date DATE    COMMENT '过渡期结束日期' ,
    transition_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '过渡期训练任务' ,
    transition_quality_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '过渡期素质训练' ,
    transition_tech_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '过渡期技术训练' ,
    transition_tactical_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '过渡期战术训练' ,
    transition_mental_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '过渡期心理训练' ,
    transition_method VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '过渡期训练手段' ,
    transition_workload VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '过渡期训练量' ,
    transition_intensity VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '过渡期训练强度' ,
    dept_evaluation VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '竞赛科评价' ,
    dept_evaluation_date DATE    COMMENT '竞赛科评价日期' ,
    school_evaluation VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '校领导评价' ,
    school_evaluation_date DATE    COMMENT '校领导评价日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '夏训阶段训练计划信息表 ';;




DROP TABLE IF EXISTS tb_edu_sport_class_year_plan;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_year_plan(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    sport_class_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练队' ,
    plan_name VARCHAR(128) NOT NULL  DEFAULT '' COMMENT '训练计划名称' ,
    team_situation TEXT    COMMENT '初始情况' ,
    year_goal TEXT    COMMENT '全年任务' ,
    prepare_start_date DATE    COMMENT '准备期开始日期' ,
    prepare_end_date DATE    COMMENT '准备期结束日期' ,
    prepare_weeks VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期周数' ,
    prepare_days VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期天数' ,
    prepare_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '准备期训练任务' ,
    prepare_body_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期身体训练' ,
    prepare_quality_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期素质训练' ,
    prepare_tech_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期技术训练' ,
    prepare_other_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '准备期其他训练' ,
    prepare_method VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '准备期训练手段' ,
    contest_start_date DATE    COMMENT '比赛期开始日期' ,
    contest_end_date DATE    COMMENT '比赛期结束日期' ,
    contest_weeks VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛期周数' ,
    contest_days VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛期天数' ,
    contest_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '比赛期训练任务' ,
    contest_body_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛期身体训练' ,
    contest_quality_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛期素质训练' ,
    contest_tech_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛期技术训练' ,
    contest_other_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '比赛期其他训练' ,
    contest_method VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '比赛期训练手段' ,
    transition_start_date DATE    COMMENT '过渡期开始日期' ,
    transition_end_date DATE    COMMENT '过渡期结束日期' ,
    transition_weeks VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '过渡期周数' ,
    transition_days VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '过渡期天数' ,
    transition_goal VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '过渡期训练任务' ,
    transition_body_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '过渡期身体训练' ,
    transition_quality_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '过渡期素质训练' ,
    transition_tech_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '过渡期技术训练' ,
    transition_other_percent VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '过渡期其他训练' ,
    transition_method VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '过渡期训练手段' ,
    m1_workload INT NOT NULL  DEFAULT 0 COMMENT '1月训练量' ,
    m2_workload INT NOT NULL  DEFAULT 0 COMMENT '2月训练量' ,
    m3_workload INT NOT NULL  DEFAULT 0 COMMENT '3月训练量' ,
    m4_workload INT NOT NULL  DEFAULT 0 COMMENT '4月训练量' ,
    m5_workload INT NOT NULL  DEFAULT 0 COMMENT '5月训练量' ,
    m6_workload INT NOT NULL  DEFAULT 0 COMMENT '6月训练量' ,
    m7_workload INT NOT NULL  DEFAULT 0 COMMENT '7月训练量' ,
    m8_workload INT NOT NULL  DEFAULT 0 COMMENT '8月训练量' ,
    m9_workload INT NOT NULL  DEFAULT 0 COMMENT '9月训练量' ,
    m10_workload INT NOT NULL  DEFAULT 0 COMMENT '10月训练量' ,
    m11_workload INT NOT NULL  DEFAULT 0 COMMENT '11月训练量' ,
    m12_workload INT NOT NULL  DEFAULT 0 COMMENT '12月训练量' ,
    m1_intensity INT NOT NULL  DEFAULT 0 COMMENT '1月训练强度' ,
    m2_intensity INT NOT NULL  DEFAULT 0 COMMENT '2月训练强度' ,
    m3_intensity INT NOT NULL  DEFAULT 0 COMMENT '3月训练强度' ,
    m4_intensity INT NOT NULL  DEFAULT 0 COMMENT '4月训练强度' ,
    m5_intensity INT NOT NULL  DEFAULT 0 COMMENT '5月训练强度' ,
    m6_intensity INT NOT NULL  DEFAULT 0 COMMENT '6月训练强度' ,
    m7_intensity INT NOT NULL  DEFAULT 0 COMMENT '7月训练强度' ,
    m8_intensity INT NOT NULL  DEFAULT 0 COMMENT '8月训练强度' ,
    m9_intensity INT NOT NULL  DEFAULT 0 COMMENT '9月训练强度' ,
    m10_intensity INT NOT NULL  DEFAULT 0 COMMENT '10月训练强度' ,
    m11_intensity INT NOT NULL  DEFAULT 0 COMMENT '11月训练强度' ,
    m12_intensity INT NOT NULL  DEFAULT 0 COMMENT '12月训练强度' ,
    performance_check VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '考核安排' ,
    target_check VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '指标检查标准' ,
    action_steps VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '实施措施' ,
    dept_evaluation VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '竞赛科评价' ,
    dept_evaluation_date DATE    COMMENT '竞赛科评价日期' ,
    school_evaluation VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '校领导评价' ,
    school_evaluation_date DATE    COMMENT '校领导评价日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '年度训练计划信息表 ';;




DROP TABLE IF EXISTS tb_edu_sport_class_year_summary;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_year_summary(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    year_plan_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练计划' ,
    coach_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '发布人' ,
    year_performance TEXT    COMMENT '执行情况' ,
    year_weakness TEXT    COMMENT '全年任务' ,
    improvement VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '改进方向' ,
    dept_evaluation VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '竞赛科评价' ,
    dept_evaluation_date DATE    COMMENT '竞赛科评价日期' ,
    school_evaluation VARCHAR(512) NOT NULL  DEFAULT '' COMMENT '校领导评价' ,
    school_evaluation_date DATE    COMMENT '校领导评价日期' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '年度训练工作总结信息表 ';;





DROP TABLE IF EXISTS tb_edu_sport_class_athlete_year_goal;;/*SkipError*/
CREATE TABLE tb_edu_sport_class_athlete_year_goal(
    id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    year_plan_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '训练计划' ,
    athlete_id VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '运动员' ,
    event_code VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '小项代码' ,
    performance_goal VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '成绩目标' ,
    create_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32) NOT NULL  DEFAULT '' COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '年度训练运动员素质目标信息表 ';;




