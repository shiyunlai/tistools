CREATE TABLE IF NOT EXISTS `btf_trans_info` (
  `id` varchar(128) DEFAULT NULL comment '',
  `trans_code` varchar(32) DEFAULT NULL comment '',
  `version_no` varchar(64) DEFAULT NULL comment '',
  `version_flag` varchar(64) DEFAULT NULL comment '',
  `trans_name` varchar(64) DEFAULT NULL comment '',
  `trans_desc` varchar(512) DEFAULT NULL comment '',
  `biz_type` varchar(8) DEFAULT NULL comment '',
  `valid_flag` varchar(1) DEFAULT NULL comment '',
  `href_url` varchar(1024) DEFAULT NULL comment '',
  `trans_func` varchar(1024) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `btf_transjnl_ctx` (
  `trans_serial_no` varchar(32) DEFAULT NULL comment '',
  `trans_date` datetime DEFAULT NULL comment '',
  `trans_step` varchar(8) DEFAULT NULL comment '',
  `trans_ctx` decimal(15,2) DEFAULT NULL comment '',
  `update_stamp` datetime DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `btf_transjnl_ext` (
  `trans_serial_no` varchar(32) DEFAULT NULL comment '',
  `trans_date` datetime DEFAULT NULL comment '',
  `jnlext_field` varchar(32) DEFAULT NULL comment '',
  `face_field` varchar(32) DEFAULT NULL comment '',
  `trans_value` varchar(1024) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `btf_transset` (
  `id` varchar(128) DEFAULT NULL comment '',
  `trans_set_code` varchar(64) DEFAULT NULL comment '',
  `trans_set_name` varchar(128) DEFAULT NULL comment '',
  `trans_set_desc` varchar(256) DEFAULT NULL comment '',
  `all_trans_flag` varchar(1) DEFAULT NULL comment '',
  `all_trans_funcode` varchar(1024) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `btf_transset_trans_auth` (
  `id` varchar(128) DEFAULT NULL comment '',
  `trans_set_code` varchar(64) DEFAULT NULL comment '',
  `trans_code` varchar(16) DEFAULT NULL comment '',
  `trans_funcode` varchar(1024) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `btf_transubjnl` (
  `isrvc_serial_no` varchar(32) DEFAULT NULL comment '',
  `itrans_code` varchar(16) DEFAULT NULL comment '',
  `istatus` varchar(1) DEFAULT NULL comment '',
  `iseqno` int(128) DEFAULT NULL comment '',
  `iprefill_content` decimal(15,2) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `btf_user_info` (
  `id` varchar(128) DEFAULT NULL comment '',
  `user_code` varchar(16) DEFAULT NULL comment '',
  `emp_code` varchar(16) DEFAULT NULL comment '',
  `user_name` varchar(32) DEFAULT NULL comment '',
  `user_type` varchar(2) DEFAULT NULL comment '',
  `user_level` varchar(16) DEFAULT NULL comment '',
  `sub_type` varchar(2) DEFAULT NULL comment '',
  `login_type` varchar(1) DEFAULT NULL comment '',
  `org_code` varchar(16) DEFAULT NULL comment '',
  `core_user_flag` varchar(1) DEFAULT NULL comment '',
  `user_status` varchar(1) DEFAULT NULL comment '',
  `work_status` varchar(1) DEFAULT NULL comment '',
  `login_time` datetime DEFAULT NULL comment '',
  `logout_time` datetime DEFAULT NULL comment '',
  `login_ip` varchar(64) DEFAULT NULL comment '',
  `user_password` varchar(64) DEFAULT NULL comment '',
  `auth_mode` varchar(256) DEFAULT NULL comment '',
  `pw_status` varchar(1) DEFAULT NULL comment '',
  `pw_cnt` int(128) DEFAULT NULL comment '',
  `lock_time` datetime DEFAULT NULL comment '',
  `unlock_time` datetime DEFAULT NULL comment '',
  `chg_date` datetime DEFAULT NULL comment '',
  `chg_interval` int(128) DEFAULT NULL comment '',
  `inval_date` datetime DEFAULT NULL comment '',
  `eff_date` datetime DEFAULT NULL comment '',
  `lose_date` datetime DEFAULT NULL comment '',
  `eff_time_range` varchar(256) DEFAULT NULL comment '',
  `auth_level` varchar(256) DEFAULT NULL comment '',
  `cash_flag` varchar(1) DEFAULT NULL comment '',
  `mc_flag` varchar(1) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `btf_user_org_role` (
  `id` varchar(128) DEFAULT NULL comment '',
  `user_code` varchar(16) DEFAULT NULL comment '',
  `org_code` varchar(16) DEFAULT NULL comment '',
  `org_type` varchar(1) DEFAULT NULL comment '',
  `role_code` varchar(64) DEFAULT NULL comment '',
  `description` varchar(1024) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `btf_user_spcfg` (
  `id` varchar(128) DEFAULT NULL comment '',
  `config_type` varchar(16) DEFAULT NULL comment '',
  `teller_code` varchar(16) DEFAULT NULL comment '',
  `instno_code` varchar(16) DEFAULT NULL comment '',
  `custom_time` varchar(32) DEFAULT NULL comment '',
  `config_file` decimal(15,2) DEFAULT NULL comment '',
  `description` varchar(256) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `btf_workstation` (
  `workstation_code` varchar(64) DEFAULT NULL comment '',
  `org_code` varchar(16) DEFAULT NULL comment '',
  `channel_code` varchar(64) DEFAULT NULL comment '',
  `workstation_kind` varchar(2) DEFAULT NULL comment '',
  `master_flag` varchar(1) DEFAULT NULL comment '',
  `event_msg_mech` varchar(8) DEFAULT NULL comment '',
  `workstation_ip` varchar(64) DEFAULT NULL comment '',
  `msg_push_port` int(128) DEFAULT NULL comment '',
  `mcast_addr` varchar(128) DEFAULT NULL comment '',
  `mcast_port` int(128) DEFAULT NULL comment '',
  `workstation_status` varchar(1) DEFAULT NULL comment '',
  `workstation_switch` varchar(1) DEFAULT NULL comment '',
  `set_date` datetime DEFAULT NULL comment '',
  `open_date` datetime DEFAULT NULL comment '',
  `shutout_date` datetime DEFAULT NULL comment '',
  `suit_code` varchar(24) DEFAULT NULL comment '',
  `ext1` varchar(256) DEFAULT NULL comment '',
  `ext2` varchar(256) DEFAULT NULL comment '',
  `ext3` varchar(256) DEFAULT NULL comment '',
  `ext4` varchar(256) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `ctf_dictionary` (
  `id` varchar(128) DEFAULT NULL comment '',
  `dict_key` varchar(64) DEFAULT NULL comment '',
  `dict_type` varchar(1) DEFAULT NULL comment '',
  `dict_name` varchar(64) DEFAULT NULL comment '',
  `default_value` varchar(64) DEFAULT NULL comment '',
  `table_name` varchar(512) DEFAULT NULL comment '',
  `column_name` varchar(512) DEFAULT NULL comment '',
  `separate_flag` varchar(64) DEFAULT NULL comment '',
  `parent_id` varchar(64) DEFAULT NULL comment '',
  `seqno` int(128) DEFAULT NULL comment '',
  `multi_selected` varchar(1) DEFAULT NULL comment '',
  `ext1` varchar(512) DEFAULT NULL comment '',
  `ext2` varchar(512) DEFAULT NULL comment '',
  `ext3` varchar(512) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `ctf_dictionary_item` (
  `id` varchar(128) DEFAULT NULL comment '',
  `dict_key` varchar(64) DEFAULT NULL comment '',
  `item_name` varchar(64) DEFAULT NULL comment '',
  `item_value` varchar(64) DEFAULT NULL comment '',
  `send_value` varchar(64) DEFAULT NULL comment '',
  `seqno` int(128) DEFAULT NULL comment '',
  `ext1` varchar(512) DEFAULT NULL comment '',
  `ext2` varchar(512) DEFAULT NULL comment '',
  `ext3` varchar(512) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `ctf_errcode` (
  `id` varchar(128) DEFAULT NULL comment '',
  `err_code` varchar(16) DEFAULT NULL comment '',
  `err_name` varchar(128) DEFAULT NULL comment '',
  `err_desc` varchar(128) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `ctf_retcode` (
  `id` varchar(128) DEFAULT NULL comment '',
  `host_code` varchar(16) DEFAULT NULL comment '',
  `ret_code` varchar(64) DEFAULT NULL comment '',
  `ret_exp` varchar(128) DEFAULT NULL comment '',
  `ret_desc` varchar(512) DEFAULT NULL comment '',
  `ctf_errcode` varchar(16) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `ctf_run_config` (
  `id` varchar(128) DEFAULT NULL comment '',
  `module_name` varchar(64) DEFAULT NULL comment '',
  `group_name` varchar(64) DEFAULT NULL comment '',
  `key_name` varchar(64) DEFAULT NULL comment '',
  `value` varchar(1024) DEFAULT NULL comment '',
  `description` varchar(128) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `ctf_version_info` (
  `version_no` varchar(64) DEFAULT NULL comment '',
  `app_name` varchar(32) DEFAULT NULL comment '',
  `version_location` varchar(128) DEFAULT NULL comment '',
  `version_issue_time` int(128) DEFAULT NULL comment '',
  `version_effect_time` int(128) DEFAULT NULL comment '',
  `version_status` varchar(1) DEFAULT NULL comment '',
  `version_md` varchar(128) DEFAULT NULL comment '',
  `version_md_file` varchar(128) DEFAULT NULL comment '',
  `version_creater` varchar(16) DEFAULT NULL comment '',
  `version_log_info` varchar(4000) DEFAULT NULL comment '',
  `workstation_type` varchar(64) DEFAULT NULL comment '',
  `ext1` varchar(256) DEFAULT NULL comment '',
  `ext2` varchar(256) DEFAULT NULL comment '',
  `ext3` varchar(256) DEFAULT NULL comment '',
  `ext4` varchar(256) DEFAULT NULL comment ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
