/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/1/20 1:50:35                            */
/*==============================================================*/


drop table if exists admin;

drop table if exists comment;

drop table if exists comment_report;

drop table if exists composition;

drop table if exists composition_report;

drop table if exists error;

drop table if exists favorite;

drop table if exists feedback;

drop table if exists follow;

drop table if exists help;

drop table if exists history;

drop table if exists push_article;

drop table if exists support;

drop table if exists system_message;

drop table if exists user;

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   admin_name           varchar(32) not null comment '管理员账号',
   password             varchar(32) comment '密码',
   primary key (admin_name)
);

alter table admin comment '管理员';

/*==============================================================*/
/* Table: comment                                               */
/*==============================================================*/
create table comment
(
   comment_id           int not null auto_increment comment '作文评论ID',
   composition_id       int comment '作文ID',
   username             varchar(32) comment '用户名',
   comment_body         varchar(512) comment '评论内容',
   time                 timestamp comment '时间',
   status               int comment '状态',
   primary key (comment_id)
);

alter table comment comment '对作文的评论';

/*==============================================================*/
/* Table: comment_report                                        */
/*==============================================================*/
create table comment_report
(
   comment_report_id    int not null auto_increment comment '举报ID',
   comment_id           int comment '作文评论ID',
   username             varchar(32) comment '用户名',
   comment_report_body  varchar(64) comment '举报内容',
   time                 timestamp comment '时间',
   status               int comment '状态',
   primary key (comment_report_id)
);

alter table comment_report comment '评论举报';

/*==============================================================*/
/* Table: composition                                           */
/*==============================================================*/
create table composition
(
   composition_id       int not null auto_increment comment '作文ID',
   username             varchar(32) comment '用户名',
   release_time         timestamp comment '发布时间',
   composition_body     varchar(4096) comment '作文内容',
   status               int comment '状态',
   title                varchar(64) comment '标题',
   description          varchar(4096) comment '描述',
   visibility           int comment '可见性，1为仅自己可见，2为关注的人可见，3为所有人可见',
   score                int comment '分数',
   valid                int comment '合法性',
   word_score           decimal(10) comment '单词评分',
   grammar_score        decimal(10) comment '语法评分',
   sentence_fluency_score decimal(10) comment '句子流畅程度评分',
   length_score         decimal(10) comment '长度评分',
   richness_score       decimal(10) comment '丰富程度评分',
   primary key (composition_id)
);

/*==============================================================*/
/* Table: composition_report                                    */
/*==============================================================*/
create table composition_report
(
   composition_report_id int not null auto_increment comment '举报ID',
   username             varchar(32) comment '用户名',
   composition_id       int comment '作文ID',
   composition_report_body varchar(64) comment '举报内容',
   time                 timestamp comment '时间',
   status               int comment '状态',
   primary key (composition_report_id)
);

alter table composition_report comment '作文举报';

/*==============================================================*/
/* Table: error                                                 */
/*==============================================================*/
create table error
(
   error_id             int not null auto_increment comment '错误ID',
   composition_id       int comment '作文ID',
   text                 varchar(256) comment '内容',
   error_type           varchar(32) comment '错误类型',
   primary key (error_id)
);

alter table error comment '错误';

/*==============================================================*/
/* Table: favorite                                              */
/*==============================================================*/
create table favorite
(
   favorite_id          int not null auto_increment comment '作文收藏ID',
   composition_id       int comment '作文ID',
   username             varchar(32) comment '用户名',
   time                 timestamp comment '时间',
   primary key (favorite_id)
);

alter table favorite comment '收藏';

/*==============================================================*/
/* Table: feedback                                              */
/*==============================================================*/
create table feedback
(
   feedback_id          int not null auto_increment comment '反馈ID',
   username             varchar(32) comment '用户名',
   feedback_body        varchar(1024) comment '反馈内容',
   feedback_type        varchar(16) comment '反馈类型',
   time                 timestamp comment '时间',
   status               int comment '状态',
   primary key (feedback_id)
);

alter table feedback comment '反馈';

/*==============================================================*/
/* Table: follow                                                */
/*==============================================================*/
create table follow
(
   follow_id            int not null auto_increment comment '关注ID',
   username             varchar(32) comment '用户名',
   target_username      varchar(32) comment '用户名',
   time                 timestamp comment '时间',
   primary key (follow_id)
);

alter table follow comment '关注';

/*==============================================================*/
/* Table: help                                                  */
/*==============================================================*/
create table help
(
   help_id              int not null auto_increment comment '帮助ID',
   admin_name           varchar(32) comment '管理员账号',
   help_title           varchar(64) comment '帮助标题',
   help_body            varchar(2048) comment '帮助内容',
   time                 timestamp comment '时间',
   primary key (help_id)
);

alter table help comment '帮助';

/*==============================================================*/
/* Table: history                                               */
/*==============================================================*/
create table history
(
   history_id           int not null auto_increment comment '作文浏览历史ID',
   username             varchar(32) comment '用户名',
   composition_id       int comment '作文ID',
   time                 timestamp comment '时间',
   primary key (history_id)
);

alter table history comment '浏览历史';

/*==============================================================*/
/* Table: push_article                                          */
/*==============================================================*/
create table push_article
(
   article_id           int not null auto_increment comment '文章ID',
   admin_name           varchar(32) comment '管理员账号',
   article_title        varchar(64),
   article_body         varchar(2048) comment '文章内容',
   time                 timestamp comment '时间',
   avatar_url           varchar(128) comment '图片地址',
   primary key (article_id)
);

alter table push_article comment '推送文章';

/*==============================================================*/
/* Table: support                                               */
/*==============================================================*/
create table support
(
   support_id           int not null auto_increment comment '作文点赞ID',
   username             varchar(32) comment '用户名',
   composition_id       int comment '作文ID',
   time                 timestamp comment '时间',
   primary key (support_id)
);

alter table support comment '点赞';

/*==============================================================*/
/* Table: system_message                                        */
/*==============================================================*/
create table system_message
(
   system_message_id    int not null auto_increment comment '系统推送信息ID',
   admin_name           varchar(32) comment '管理员账号',
   system_message_body  varchar(2048) comment '系统推送信息内容',
   time                 timestamp comment '时间',
   primary key (system_message_id)
);

alter table system_message comment '系统信息';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   username             varchar(32) not null comment '用户名',
   password             varchar(32) not null comment '密码',
   nickname             varchar(64) comment '昵称',
   email                varchar(32) not null comment '电子邮件',
   phone                varchar(16) comment '电话(座机、手机)',
   signature            varchar(512) comment '个性签名',
   avatar_url           varchar(128) comment '图片地址',
   is_male              bool comment '是否为男性',
   vip_days             int comment 'VIP剩余时间',
   frozen               bool default false comment '是否被冻结',
   defrosting_time      timestamp comment '解冻时间',
   primary key (username)
);

alter table user comment '用户';

alter table comment add constraint FK_Relationship_12 foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table comment add constraint FK_Relationship_5 foreign key (username)
      references user (username) on delete restrict on update restrict;

alter table comment_report add constraint FK_Relationship_27 foreign key (comment_id)
      references comment (comment_id) on delete restrict on update restrict;

alter table comment_report add constraint FK_Relationship_28 foreign key (username)
      references user (username) on delete restrict on update restrict;

alter table composition add constraint FK_Relationship_9 foreign key (username)
      references user (username) on delete restrict on update restrict;

alter table composition_report add constraint FK_Relationship_24 foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table composition_report add constraint FK_Relationship_26 foreign key (username)
      references user (username) on delete restrict on update restrict;

alter table error add constraint FK_Relationship_14 foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table favorite add constraint FK_Relationship_7 foreign key (username)
      references user (username) on delete restrict on update restrict;

alter table favorite add constraint FK_Relationship_8 foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table feedback add constraint FK_Relationship_2 foreign key (username)
      references user (username) on delete restrict on update restrict;

alter table follow add constraint FK_Relationship_1 foreign key (target_username)
      references user (username) on delete restrict on update restrict;

alter table follow add constraint FK_Relationship_19 foreign key (username)
      references user (username) on delete restrict on update restrict;

alter table help add constraint FK_Relationship_25 foreign key (admin_name)
      references admin (admin_name) on delete restrict on update restrict;

alter table history add constraint FK_Relationship_13 foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table history add constraint FK_Relationship_6 foreign key (username)
      references user (username) on delete restrict on update restrict;

alter table push_article add constraint FK_Relationship_23 foreign key (admin_name)
      references admin (admin_name) on delete restrict on update restrict;

alter table support add constraint FK_Relationship_10 foreign key (username)
      references user (username) on delete restrict on update restrict;

alter table support add constraint FK_Relationship_11 foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table system_message add constraint FK_Relationship_22 foreign key (admin_name)
      references admin (admin_name) on delete restrict on update restrict;

