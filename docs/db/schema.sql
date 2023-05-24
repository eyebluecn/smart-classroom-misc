CREATE TABLE `scm_article` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `column_id` bigint unsigned NOT NULL COMMENT '专栏id',
  `author_id` bigint unsigned NOT NULL COMMENT '作者id',
  `status` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'NEW' COMMENT '状态 NEW/OK/DISABLED 未发布/已生效/已禁用',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='专栏文章表'

CREATE TABLE `scm_author` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `realname` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '真实姓名',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='作者表'

CREATE TABLE `scm_column` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '专栏名称',
  `author_id` bigint unsigned NOT NULL COMMENT '作者id',
  `status` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'NEW' COMMENT '状态 NEW/OK/DISABLED 未发布/已生效/已禁用',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='专栏表'

CREATE TABLE `scm_column_quote` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `column_id` bigint unsigned NOT NULL COMMENT '专栏id',
  `editor_id` bigint unsigned NOT NULL COMMENT '编辑id',
  `price` bigint unsigned NOT NULL COMMENT '价格（单位：分）',
  `status` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'DISABLED' COMMENT '报价状态 DISABLED/OK 未生效/已生效',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='专栏报价表'

CREATE TABLE `scm_commission` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `amount` bigint unsigned NOT NULL COMMENT '金额(单位：分)',
  `contract_id` bigint unsigned NOT NULL COMMENT '合同id',
  `author_id` bigint unsigned NOT NULL COMMENT '作者id',
  `receipt_id` bigint unsigned NOT NULL COMMENT '收款单id',
  `payment_days` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账期',
  `status` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'NEW' COMMENT '状态 CREATED/RECEIVED/CANCELED/CLOSED 未收款/已收款/已取消/已关闭',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='佣金表'

CREATE TABLE `scm_contract` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `column_id` bigint unsigned NOT NULL COMMENT '专栏id',
  `author_id` bigint unsigned NOT NULL COMMENT '作者id',
  `status` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'NEW' COMMENT '状态 NEW/OK/DISABLED 未生效/已生效/已禁用',
  `percentage` decimal(5,2) DEFAULT NULL COMMENT '分成比例',
  `payment_day` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账期日',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='作者表'

CREATE TABLE `scm_editor` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `work_no` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工号',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='小二表'

CREATE TABLE `scm_payment` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `order_no` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单编号',
  `method` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付方式 ALIPAY/WEIXIN 支付宝/微信',
  `third_transaction_no` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付平台订单号',
  `amount` bigint unsigned NOT NULL COMMENT '金额(单位：分)',
  `status` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'UNPAID' COMMENT '支付状态 UNPAID/PAID/CLOSED 未支付/已支付/已关闭',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付单，用户支付给平台的单据'

CREATE TABLE `scm_reader` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='读者表'

CREATE TABLE `scm_receipt` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `order_no` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单编号',
  `method` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收款方式 ALIPAY/WEIXIN 支付宝/微信',
  `third_transaction_no` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收款平台订单号',
  `amount` bigint unsigned NOT NULL COMMENT '金额(单位：分)',
  `status` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'UNPAID' COMMENT '支付状态 UNPAID/PAID/CLOSED 未收款/已收款/已关闭',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收款单，作者从平台收款的单据'

CREATE TABLE `scs_order` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `no` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单唯一编号，整个系统唯一，带有前缀',
  `reader_id` bigint unsigned NOT NULL COMMENT '读者id',
  `column_id` bigint unsigned NOT NULL COMMENT '专栏id',
  `column_quote_id` bigint unsigned NOT NULL COMMENT '专栏报价id',
  `payment_id` bigint unsigned NOT NULL COMMENT '支付单id',
  `price` bigint unsigned NOT NULL COMMENT '价格（单位：分）',
  `status` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'CREATED' COMMENT '状态 CREATED/PAID/SUBSCRIBED/CLOSED/CANCELED',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`),
  KEY `ix_reader_id` (`reader_id`),
  KEY `ix_column_id` (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表'

CREATE TABLE `scs_subscription` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `reader_id` bigint unsigned NOT NULL COMMENT '读者id',
  `column_id` bigint unsigned NOT NULL COMMENT '专栏id',
  `order_id` bigint unsigned NOT NULL COMMENT '订单id',
  `status` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'CREATED' COMMENT '状态 CREATED/OK/DISABLED',
  PRIMARY KEY (`id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_update_time` (`update_time`),
  KEY `ix_reader_id` (`reader_id`),
  KEY `ix_column_id` (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订阅表'

