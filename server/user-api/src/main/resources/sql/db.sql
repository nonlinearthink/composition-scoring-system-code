/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/11/17 14:40:41                          */
/*==============================================================*/

drop table if exists admin;

drop table if exists advertisement;

drop table if exists advertiser;

drop table if exists composition;

drop table if exists composition_comment;

drop table if exists composition_favorite;

drop table if exists composition_history;

drop table if exists composition_support;

drop table if exists dynamic;

drop table if exists dynamic_comment;

drop table if exists dynamic_support;

drop table if exists essay;

drop table if exists essay_comment;

drop table if exists essay_favorite;

drop table if exists essay_history;

drop table if exists essay_support;

drop table if exists feedback;

drop table if exists follow;

drop table if exists grammer_error;

drop table if exists message;

drop table if exists spell_error;

drop table if exists system_message;

drop table if exists userEntity;

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   admin_name           varchar(32) not null  comment '管理员账号',
   password             varchar(32)  comment '密码',
   primary key (admin_name)
);

/*==============================================================*/
/* Table: advertisement                                         */
/*==============================================================*/
create table advertisement
(
   advertisement_id     int not null auto_increment  comment '广告ID',
   advertiser_name      varchar(32)  comment '广告商账号',
   location             varchar(16)  comment '投放位置',
   image_url            varchar(32)  comment '展示图片地址',
   source_url           varchar(32)  comment '跳转链接地址',
   view_count           int  comment '观看计数',
   click_count          int  comment '点击计数',
   primary key (advertisement_id)
);

/*==============================================================*/
/* Table: advertiser                                            */
/*==============================================================*/
create table advertiser
(
   advertiser_name      varchar(32) not null  comment '广告商账号',
   password             varchar(32)  comment '密码',
   company              varchar(64)  comment '所属公司',
   address              varchar(64)  comment '地址',
   phone                varchar(16)  comment '电话(座机、手机)',
   email                varchar(32)  comment '电子邮件',
   valid_days           int  comment '服务有效日期',
   primary key (advertiser_name)
);

/*==============================================================*/
/* Table: composition                                           */
/*==============================================================*/
create table composition
(
   composition_id       int not null auto_increment  comment '作文ID',
   user_name            varchar(32)  comment '用户名',
   title                varchar(64)  comment '标题',
   composition_body     varchar(4096)  comment '作文内容',
   release_time         timestamp  comment '发布时间',
   composition_type     varchar(8)  comment '作文类型',
   share_count          int  comment '被分享次数',
   score                int  comment '分数',
   primary key (composition_id)
);

/*==============================================================*/
/* Table: composition_comment                                   */
/*==============================================================*/
create table composition_comment
(
   ccomment_id3         int not null auto_increment  comment '作文评论ID',
   composition_id       int  comment '作文ID',
   next_ccomment_id3    int  comment '作文评论ID',
   user_name            varchar(32)  comment '用户名',
   comment_body         varchar(512)  comment '评论内容',
   date_time            timestamp  comment '日期',
   primary key (ccomment_id3)
);

/*==============================================================*/
/* Table: composition_favorite                                  */
/*==============================================================*/
create table composition_favorite
(
   c_favotite_id        int not null auto_increment  comment '作文收藏ID',
   composition_id       int  comment '作文ID',
   user_name            varchar(32)  comment '用户名',
   date_time            timestamp  comment '日期',
   primary key (c_favotite_id)
);

/*==============================================================*/
/* Table: composition_history                                   */
/*==============================================================*/
create table composition_history
(
   c_history_id         int not null auto_increment  comment '作文浏览历史ID',
   user_name            varchar(32)  comment '用户名',
   composition_id       int  comment '作文ID',
   date_time            timestamp  comment '日期',
   primary key (c_history_id)
);

/*==============================================================*/
/* Table: composition_support                                   */
/*==============================================================*/
create table composition_support
(
   dsupport_id3         int not null auto_increment  comment '作文点赞ID',
   user_name            varchar(32)  comment '用户名',
   composition_id       int  comment '作文ID',
   date_time            timestamp  comment '日期',
   primary key (dsupport_id3)
);

/*==============================================================*/
/* Table: dynamic                                               */
/*==============================================================*/
create table dynamic
(
   dynamic_id           int not null auto_increment  comment '动态ID',
   user_name            varchar(32)  comment '用户名',
   dynamic_body         varchar(1024)  comment '动态内容',
   release_time         timestamp  comment '发布时间',
   is_share             bool  comment '是否是转发的动态',
   source               varchar(64)  comment '转发的动态的源地址',
   primary key (dynamic_id)
);

/*==============================================================*/
/* Table: dynamic_comment                                       */
/*==============================================================*/
create table dynamic_comment
(
   dcomment_id2         int not null auto_increment  comment '动态评论ID',
   user_name            varchar(32)  comment '用户名',
   next_dcomment_id2    int  comment '动态评论ID',
   dynamic_id           int  comment '动态ID',
   comment_body         varchar(512)  comment '评论内容',
   date_time            timestamp  comment '日期',
   primary key (dcomment_id2)
);

/*==============================================================*/
/* Table: dynamic_support                                       */
/*==============================================================*/
create table dynamic_support
(
   dsupport_id2         int not null auto_increment  comment '动态点赞ID',
   user_name            varchar(32)  comment '用户名',
   dynamic_id           int  comment '动态ID',
   date_time            timestamp  comment '日期',
   primary key (dsupport_id2)
);

/*==============================================================*/
/* Table: essay                                                 */
/*==============================================================*/
create table essay
(
   essay_id             int not null auto_increment  comment '随笔ID',
   user_name            varchar(32)  comment '用户名',
   title                varchar(64)  comment '标题',
   essay_body           varchar(8192)  comment '随笔内容',
   release_time         timestamp  comment '发布时间',
   category             varchar(64)  comment '分类',
   tag                  varchar(256)  comment '标签',
   share_count          int  comment '被分享次数',
   primary key (essay_id)
);

/*==============================================================*/
/* Table: essay_comment                                         */
/*==============================================================*/
create table essay_comment
(
   e_comment_id         int not null auto_increment  comment '随笔评论ID',
   essay_id             int  comment '随笔ID',
   user_name            varchar(32)  comment '用户名',
   comment_body         varchar(512)  comment '评论内容',
   date_time            timestamp  comment '日期',
   primary key (e_comment_id)
);

/*==============================================================*/
/* Table: essay_favorite                                        */
/*==============================================================*/
create table essay_favorite
(
   e_favorite_id        int not null auto_increment  comment '随笔收藏ID',
   essay_id             int  comment '随笔ID',
   user_name            varchar(32)  comment '用户名',
   date_time            timestamp  comment '日期',
   primary key (e_favorite_id)
);

/*==============================================================*/
/* Table: essay_history                                         */
/*==============================================================*/
create table essay_history
(
   e_history_id         int not null auto_increment  comment '收藏浏览历史ID',
   essay_id             int  comment '随笔ID',
   user_name            varchar(32)  comment '用户名',
   date_time            timestamp  comment '日期',
   primary key (e_history_id)
);

/*==============================================================*/
/* Table: essay_support                                         */
/*==============================================================*/
create table essay_support
(
   e_support_id         int not null auto_increment  comment '随笔点赞ID',
   user_name            varchar(32)  comment '用户名',
   essay_id             int  comment '随笔ID',
   date_time            timestamp  comment '日期',
   primary key (e_support_id)
);

/*==============================================================*/
/* Table: feedback                                              */
/*==============================================================*/
create table feedback
(
   feedback_id          int not null auto_increment  comment '反馈ID',
   user_name            varchar(32)  comment '用户名',
   feedback_body        varchar(1024)  comment '反馈内容',
   feedback_type        varchar(16)  comment '反馈类型',
   primary key (feedback_id)
);

/*==============================================================*/
/* Table: follow                                                */
/*==============================================================*/
create table follow
(
   follow_id            int not null auto_increment  comment '关注ID',
   user_name            varchar(32) not null  comment '用户名',
   date_time            timestamp  comment '日期',
   primary key (follow_id, user_name)
);

/*==============================================================*/
/* Table: grammer_error                                         */
/*==============================================================*/
create table grammer_error
(
   grammer_error_id     int not null auto_increment  comment '语法错误ID',
   composition_id       int  comment '作文ID',
   text                 varchar(256)  comment '错误信息',
   primary key (grammer_error_id)
);

/*==============================================================*/
/* Table: message                                               */
/*==============================================================*/
create table message
(
   message_id           int not null auto_increment  comment '通信ID',
   user_name            varchar(32)  comment '用户名',
   next_user_name       varchar(32)  comment '用户名',
   `order`              int  comment '序号',
   message_body         varchar(256)  comment '消息内容',
   date_time            timestamp  comment '日期',
   primary key (message_id)
);

/*==============================================================*/
/* Table: spell_error                                           */
/*==============================================================*/
create table spell_error
(
   spell_error_id       int not null auto_increment  comment '拼写错误ID',
   composition_id       int  comment '作文ID',
   text                 varchar(256)  comment '错误信息',
   primary key (spell_error_id)
);

/*==============================================================*/
/* Table: system_message                                        */
/*==============================================================*/
create table system_message
(
   s_message_id         int not null auto_increment  comment '系统推送信息ID',
   s_message_body       varchar(2048)  comment '系统推送信息内容',
   date_time            timestamp  comment '日期',
   primary key (s_message_id)
);

/*==============================================================*/
/* Table: userEntity                                                  */
/*==============================================================*/
create table userEntity
(
   user_name            varchar(32) not null  comment '用户名',
   password             varchar(32) not null  comment '密码',
   nick_name            varchar(64)  comment '昵称',
   signature            varchar(512)  comment '个性签名',
   avatar_url           varchar(128)  comment '头像地址',
   phone                varchar(16)  comment '电话(座机、手机)',
   email                varchar(32)  comment '电子邮件',
   is_male              bool  comment '是否为男性',
   vip_days             int  comment 'VIP剩余时间',
   anthority            int  comment '权限等级',
   primary key (user_name)
);

alter table advertisement add constraint FK_ADVERTIS_RELEASE_ADVERTIS foreign key (advertiser_name)
      references advertiser (advertiser_name) on delete restrict on update restrict;

alter table composition add constraint FK_COMPOSIT_WRITE_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table composition_comment add constraint FK_COMPOSIT_HAVE8_COMPOSIT foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table composition_comment add constraint FK_COMPOSIT_REPLY3_COMPOSIT foreign key (next_ccomment_id3)
      references composition_comment (ccomment_id3) on delete restrict on update restrict;

alter table composition_comment add constraint FK_COMPOSIT_SUBMIT_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table composition_favorite add constraint FK_COMPOSIT_FAVORITE_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table composition_favorite add constraint FK_COMPOSIT_HAVE10_COMPOSIT foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table composition_history add constraint FK_COMPOSIT_HAVE12_COMPOSIT foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table composition_history add constraint FK_COMPOSIT_HAVE13_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table composition_support add constraint FK_COMPOSIT_GIVE6_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table composition_support add constraint FK_COMPOSIT_HAVE11_COMPOSIT foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table dynamic add constraint FK_DYNAMIC_WRITE3_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table dynamic_comment add constraint FK_DYNAMIC__COMMENT_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table dynamic_comment add constraint FK_DYNAMIC__HAVE7_DYNAMIC foreign key (dynamic_id)
      references dynamic (dynamic_id) on delete restrict on update restrict;

alter table dynamic_comment add constraint FK_DYNAMIC__REPLY2_DYNAMIC_ foreign key (next_dcomment_id2)
      references dynamic_comment (dcomment_id2) on delete restrict on update restrict;

alter table dynamic_support add constraint FK_DYNAMIC__GIVE4_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table dynamic_support add constraint FK_DYNAMIC__HAVE6_DYNAMIC foreign key (dynamic_id)
      references dynamic (dynamic_id) on delete restrict on update restrict;

alter table essay add constraint FK_ESSAY_WRITE2_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table essay_comment add constraint FK_ESSAY_CO_GIVE2_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table essay_comment add constraint FK_ESSAY_CO_HAVE3_ESSAY foreign key (essay_id)
      references essay (essay_id) on delete restrict on update restrict;

alter table essay_favorite add constraint FK_ESSAY_FA_GIVE_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table essay_favorite add constraint FK_ESSAY_FA_HAVE5_ESSAY foreign key (essay_id)
      references essay (essay_id) on delete restrict on update restrict;

alter table essay_history add constraint FK_ESSAY_HI_HAVE_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table essay_history add constraint FK_ESSAY_HI_HAVE2_ESSAY foreign key (essay_id)
      references essay (essay_id) on delete restrict on update restrict;

alter table essay_support add constraint FK_ESSAY_SU_GIVE3_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table essay_support add constraint FK_ESSAY_SU_HAVE4_ESSAY foreign key (essay_id)
      references essay (essay_id) on delete restrict on update restrict;

alter table feedback add constraint FK_FEEDBACK_GIVE5_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table follow add constraint FK_FOLLOW_RELATIONS_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table grammer_error add constraint FK_GRAMMER__RELATIONS_COMPOSIT foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;

alter table message add constraint FK_MESSAGE_RECEIVE_USER foreign key (next_user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table message add constraint FK_MESSAGE_SEND_USER foreign key (user_name)
      references userEntity (user_name) on delete restrict on update restrict;

alter table spell_error add constraint FK_SPELL_ER_RELATIONS_COMPOSIT foreign key (composition_id)
      references composition (composition_id) on delete restrict on update restrict;
