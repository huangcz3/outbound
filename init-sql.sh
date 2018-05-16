#!/bin/sh

schema=fcm
tbs=tbs_app_imcd
db2 -v "set current schema ${schema}"
#外呼渠道活动信息推送记录表
db2 -v "drop table outbound_activity_push_record_info "
db2 -v "create table outbound_activity_push_record_info
(
   activity_id            VARCHAR(50),
   activity_name          VARCHAR(50),
   activity_type          SMALLINT,
   start_time             VARCHAR(50),
   end_time               VARCHAR(50),
   activity_desc          VARCHAR(2000),
   recommend              VARCHAR(100),
   ftp_flag               INT
) distribute by hash(activity_id) in ${tbs} organize by row"

db2 -v "comment on column outbound_activity_push_record_info.activity_id is '活动id' "
db2 -v "comment on column outbound_activity_push_record_info.activity_name is '活动名称' "
db2 -v "comment on column outbound_activity_push_record_info.activityType is '活动类型 0：非实时 1：实时' "
db2 -v "comment on column outbound_activity_push_record_info.start_time is '活动开始时间' "
db2 -v "comment on column outbound_activity_push_record_info.end_time is '活动结束时间 ' "
db2 -v "comment on column outbound_activity_push_record_info.activity_desc is '营销说明（营销用语）' "
db2 -v "comment on column outbound_activity_push_record_info.recommend is '推荐资费代码列表' "
db2 -v "comment on column outbound_activity_push_record_info.ftp_flag is '用户群是否通过ftp传输至外呼系统 0：否 1：是' "

#外呼渠道活动推送用户记录表（频次管控用）
db2 -v "drop table outbound_activity_user_record_info "
db2 -v "create table outbound_activity_user_record_info
(
   phone_no               VARCHAR(50),
   call_time              DATE,
   frequency              INT,
   tags                   INT,
   state                  INT
) distribute by hash(activity_id) in ${tbs} organize by row"

db2 -v "comment on column outbound_activity_user_record_info_yyyymmdd.phone_no is '手机号' "
db2 -v "comment on column outbound_activity_user_record_info_yyyymmdd.call_time is '外呼时间' "
db2 -v "comment on column outbound_activity_user_record_info_yyyymmdd.frequency is '频次（外呼次数）' "
db2 -v "comment on column outbound_activity_user_record_info_yyyymmdd.tags is '营销辅助标签，多个字段组成（几星级用户）' "
db2 -v "comment on column outbound_activity_user_record_info_yyyymmdd.state is '状态 0-未外呼，1-已外呼' "
