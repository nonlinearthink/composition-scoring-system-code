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
   admin_name           varchar(32) not null comment '����Ա�˺�',
   password             varchar(32) comment '����',
   primary key (admin_name)
);

alter table admin comment '����Ա';

/*==============================================================*/
/* Table: comment                                               */
/*==============================================================*/
create table comment
(
   comment_id           int not null auto_increment comment '��������ID',
   composition_id       int comment '����ID',
   username             varchar(32) comment '�û���',
   comment_body         varchar(512) comment '��������',
   time                 timestamp comment 'ʱ��',
   status               int comment '״̬',
   primary key (comment_id)
);

alter table comment comment '�����ĵ�����';

/*==============================================================*/
/* Table: comment_report                                        */
/*==============================================================*/
create table comment_report
(
   comment_report_id    int not null auto_increment comment '�ٱ�ID',
   comment_id           int comment '��������ID',
   username             varchar(32) comment '�û���',
   comment_report_body  varchar(64) comment '�ٱ�����',
   time                 timestamp comment 'ʱ��',
   status               int comment '״̬',
   primary key (comment_report_id)
);

alter table comment_report comment '���۾ٱ�';

/*==============================================================*/
/* Table: composition                                           */
/*==============================================================*/
create table composition
(
   composition_id       int not null auto_increment comment '����ID',
   username             varchar(32) comment '�û���',
   release_time         timestamp comment '����ʱ��',
   composition_body     varchar(4096) comment '��������',
   status               int comment '״̬',
   title                varchar(64) comment '����',
   description          varchar(4096) comment '����',
   visibility           int comment '�ɼ��ԣ�1Ϊ���Լ��ɼ���2Ϊ��ע���˿ɼ���3Ϊ�����˿ɼ�',
   score                int comment '����',
   valid                int comment '�Ϸ���',
   word_score           decimal(10) comment '��������',
   grammar_score        decimal(10) comment '�﷨����',
   sentence_fluency_score decimal(10) comment '���������̶�����',
   length_score         decimal(10) comment '��������',
   richness_score       decimal(10) comment '�ḻ�̶�����',
   primary key (composition_id)
);

/*==============================================================*/
/* Table: composition_report                                    */
/*==============================================================*/
create table composition_report
(
   composition_report_id int not null auto_increment comment '�ٱ�ID',
   username             varchar(32) comment '�û���',
   composition_id       int comment '����ID',
   composition_report_body varchar(64) comment '�ٱ�����',
   time                 timestamp comment 'ʱ��',
   status               int comment '״̬',
   primary key (composition_report_id)
);

alter table composition_report comment '���ľٱ�';

/*==============================================================*/
/* Table: error                                                 */
/*==============================================================*/
create table error
(
   error_id             int not null auto_increment comment '����ID',
   composition_id       int comment '����ID',
   text                 varchar(256) comment '����',
   error_type           varchar(32) comment '��������',
   primary key (error_id)
);

alter table error comment '����';

/*==============================================================*/
/* Table: favorite                                              */
/*==============================================================*/
create table favorite
(
   favorite_id          int not null auto_increment comment '�����ղ�ID',
   composition_id       int comment '����ID',
   username             varchar(32) comment '�û���',
   time                 timestamp comment 'ʱ��',
   primary key (favorite_id)
);

alter table favorite comment '�ղ�';

/*==============================================================*/
/* Table: feedback                                              */
/*==============================================================*/
create table feedback
(
   feedback_id          int not null auto_increment comment '����ID',
   username             varchar(32) comment '�û���',
   feedback_body        varchar(1024) comment '��������',
   feedback_type        varchar(16) comment '��������',
   time                 timestamp comment 'ʱ��',
   status               int comment '״̬',
   primary key (feedback_id)
);

alter table feedback comment '����';

/*==============================================================*/
/* Table: follow                                                */
/*==============================================================*/
create table follow
(
   follow_id            int not null auto_increment comment '��עID',
   username             varchar(32) comment '�û���',
   target_username      varchar(32) comment '�û���',
   time                 timestamp comment 'ʱ��',
   primary key (follow_id)
);

alter table follow comment '��ע';

/*==============================================================*/
/* Table: help                                                  */
/*==============================================================*/
create table help
(
   help_id              int not null auto_increment comment '����ID',
   admin_name           varchar(32) comment '����Ա�˺�',
   help_title           varchar(64) comment '��������',
   help_body            varchar(2048) comment '��������',
   time                 timestamp comment 'ʱ��',
   primary key (help_id)
);

alter table help comment '����';

/*==============================================================*/
/* Table: history                                               */
/*==============================================================*/
create table history
(
   history_id           int not null auto_increment comment '���������ʷID',
   username             varchar(32) comment '�û���',
   composition_id       int comment '����ID',
   time                 timestamp comment 'ʱ��',
   primary key (history_id)
);

alter table history comment '�����ʷ';

/*==============================================================*/
/* Table: push_article                                          */
/*==============================================================*/
create table push_article
(
   article_id           int not null auto_increment comment '����ID',
   admin_name           varchar(32) comment '����Ա�˺�',
   article_title        varchar(64),
   article_body         varchar(2048) comment '��������',
   time                 timestamp comment 'ʱ��',
   avatar_url           varchar(128) comment 'ͼƬ��ַ',
   primary key (article_id)
);

alter table push_article comment '��������';

/*==============================================================*/
/* Table: support                                               */
/*==============================================================*/
create table support
(
   support_id           int not null auto_increment comment '���ĵ���ID',
   username             varchar(32) comment '�û���',
   composition_id       int comment '����ID',
   time                 timestamp comment 'ʱ��',
   primary key (support_id)
);

alter table support comment '����';

/*==============================================================*/
/* Table: system_message                                        */
/*==============================================================*/
create table system_message
(
   system_message_id    int not null auto_increment comment 'ϵͳ������ϢID',
   admin_name           varchar(32) comment '����Ա�˺�',
   system_message_body  varchar(2048) comment 'ϵͳ������Ϣ����',
   time                 timestamp comment 'ʱ��',
   primary key (system_message_id)
);

alter table system_message comment 'ϵͳ��Ϣ';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   username             varchar(32) not null comment '�û���',
   password             varchar(32) not null comment '����',
   nickname             varchar(64) comment '�ǳ�',
   email                varchar(32) not null comment '�����ʼ�',
   phone                varchar(16) comment '�绰(�������ֻ�)',
   signature            varchar(512) comment '����ǩ��',
   avatar_url           varchar(128) comment 'ͼƬ��ַ',
   is_male              bool comment '�Ƿ�Ϊ����',
   vip_days             int comment 'VIPʣ��ʱ��',
   frozen               bool default false comment '�Ƿ񱻶���',
   defrosting_time      timestamp comment '�ⶳʱ��',
   primary key (username)
);

alter table user comment '�û�';

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

