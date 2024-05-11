CREATE database if NOT EXISTS `ai_bi` default character set utf8mb4 collate utf8mb4_general_ci;
use
    `ai_bi`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_chart
-- ----------------------------
DROP TABLE IF EXISTS `t_chart`;
CREATE TABLE `t_chart`
(
    `chart_id`         bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `chart_name`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '图表名称',
    `chart_goal`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '分析目标',
    `chart_data`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '图标数据',
    `chart_type`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '图表类型',
    `chart_gen_chart`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '生成的图表数据',
    `chart_gen_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '生成的分析结论',
    `chart_user_id`    bigint                                                        NULL     DEFAULT NULL COMMENT '用户id',
    `create_time`      timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`       tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`chart_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1775503637268127747
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '图标信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file`
(
    `file_id`       bigint                                                        NOT NULL COMMENT 'id',
    `file_name`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
    `file_url`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件地址',
    `file_suffix`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件格式',
    `file_user_id`  bigint                                                        NOT NULL COMMENT '用户id',
    `file_chart_id` bigint                                                        NOT NULL COMMENT '图表id',
    `create_time`   timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_file
-- ----------------------------

-- ----------------------------
-- Table structure for t_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_operation`;
CREATE TABLE `t_operation`
(
    `operation_id`           bigint                                                        NOT NULL COMMENT 'id',
    `operation_api`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作接口',
    `operation_user_id`      bigint                                                        NOT NULL COMMENT '操作人ID',
    `operation_user_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '操作人账号',
    `operation_description`  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '操作描述',
    `create_time`            timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`            timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`             tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`operation_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_operation
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `user_id`       bigint UNSIGNED                                               NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_account`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '账号',
    `user_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '密码',
    `user_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '用户昵称',
    `user_avatar`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '用户头像',
    `user_role`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT 'user' COMMENT '用户角色：user/admin',
    `user_state`    tinyint                                                       NOT NULL COMMENT '用户状态（0表示启用，1表示禁用）',
    `create_time`   timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`user_id`) USING BTREE,
    INDEX `idx_userAccount` (`user_account` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1783441483094167554
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user`
VALUES (1770428273925709826, 'admin', '207acd61a3c1bd506d7e9a4535359f8a', 'admin',
        'http://127.0.0.1:9000/ai-bi/avatar/2024/04/25/409ac133bba849719baf3ec8b9ab92da1714040298699.jpg', 'admin', 0,
        '2024-03-21 12:32:48', '2024-04-25 18:18:19', 0);
INSERT INTO `t_user`
VALUES (1783441483094167553, 'wangfei', '207acd61a3c1bd506d7e9a4535359f8a', '王菲',
        'http://127.0.0.1:9000/ai-bi/avatar/2024/04/25/3d0973db1ea04cccb27e5f3f10b0117c1714040604155.jpg', 'user', 0,
        '2024-04-25 18:22:39', '2024-04-25 23:54:06', 0);

SET FOREIGN_KEY_CHECKS = 1;
