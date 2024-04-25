/*
 Navicat Premium Data Transfer

 Source Server         : localhost-mysql
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : ai_bi

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 25/04/2024 19:02:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_chart
-- ----------------------------
DROP TABLE IF EXISTS `t_chart`;
CREATE TABLE `t_chart`  (
                            `chart_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                            `chart_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图表名称',
                            `chart_goal` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '分析目标',
                            `chart_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '图标数据',
                            `chart_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图表类型',
                            `chart_gen_chart` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '生成的图表数据',
                            `chart_gen_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '生成的分析结论',
                            `chart_user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
                            PRIMARY KEY (`chart_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1775503637268127747 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '图标信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_chart
-- ----------------------------
INSERT INTO `t_chart` VALUES (1770766576500760577, '商品表', '分析一下商品价格,请使用饼图', '商品,价格\n鞋子,120\n衣服,500\n汽车,200000\n', '饼图', '{\n    \"title\": {\n        \"text\": \"商品价格分布\",\n        \"subtext\": \"\",\n        \"left\": \"center\"\n    },\n    \"tooltip\": {\n        \"trigger\": \"item\",\n        \"formatter\": \"{a} <br/>{b}: {c} ({d}%)\"\n    },\n    \"legend\": {\n        \"orient\": \"vertical\",\n        \"left\": 10,\n        \"data\": [\"鞋子\", \"衣服\", \"汽车\"]\n    },\n    \"series\": [\n        {\n            \"name\": \"商品价格\",\n            \"type\": \"pie\",\n            \"radius\": \"50%\",\n            \"center\": [\"50%\", \"60%\"],\n            \"data\": [\n                {\"value\": 120, \"name\": \"鞋子\"},\n                {\"value\": 500, \"name\": \"衣服\"},\n                {\"value\": 200000, \"name\": \"汽车\"}\n            ],\n            \"itemStyle\": {\n                \"emphasis\": {\n                    \"shadowBlur\": 10,\n                    \"shadowOffsetX\": 0,\n                    \"shadowColor\": \"rgba(0, 0, 0, 0.5)\"\n                }\n            }\n        }\n    ]\n}\n', '\n根据数据分析可得，商品价格分布非常不均衡，鞋子价格最低，衣服价格居中，汽车价格最高。', 1770428273925709826, '2024-03-21 18:57:06', '2024-03-21 18:57:06', 0);
INSERT INTO `t_chart` VALUES (1770770074965532673, '商品一月份到三月份价格表', '分析一下各个商品价格的价格趋势,请使用折线图', '商品,第一月份价格,第二月份价格,第三月份价格\n鞋子,120,200,300\n衣服,500,450,480\n汽车,200000,100000,79800\n', '折线图', '{\n    \"title\": {\n        \"text\": \"商品价格趋势\",\n        \"subtext\": \"\"\n        },\n    \"tooltip\": {\n        \"trigger\": \"axis\"\n    },\n    \"legend\": {\n        \"data\": [\"鞋子\", \"衣服\", \"汽车\"]\n        },\n    \"xAxis\": {\n        \"type\": \"category\",\n        \"data\": [\"第一月份\", \"第二月份\", \"第三月份\"]\n    },\n    \"yAxis\": {\n        \"type\": \"value\"\n    },\n    \"series\": [{\n        \"name\": \"鞋子\",\n        \"type\": \"line\",\n        \"data\": [120, 200, 300]\n    }, {\n        \"name\": \"衣服\",\n        \"type\": \"line\",\n        \"data\": [500, 450, 480]\n    }, {\n        \"name\": \"汽车\",\n        \"type\": \"line\",\n        \"data\": [200000, 100000, 79800]\n    }]\n}\n', '\n根据数据分析可得，鞋子价格在三个月内逐渐增加，衣服价格在三个月内略有波动，汽车价格在三个月内急剧下降。', 1770428273925709826, '2024-03-21 19:11:00', '2024-03-21 19:11:00', 0);
INSERT INTO `t_chart` VALUES (1771878474238558209, '分析图表中学生的综合成绩', '分析图表中学生的综合成绩,请使用折线图', '地区,2024招录人数,2023年最低进面分,2023最高进面分,2023招录人数,2023年报名人数,2022年最低进面分,2022最高进面分,2022招录人数,2022报名人数\n平房区,2,136.5,140.7,2,141,135.5\n呼兰区,10,134.1,140.5,9,249,137.1\n阿城区,4,132.9,149,19,285,129.5\n双城区,15,121.7,140.3,9,62,132\n依兰县,28,124.1,145.4,22,59,117.6\n五常市,58,123.2,145.4,85,385,123.4\n巴彦县,33,123,143.2,16,51,121.3\n宾县,26,122.6,152.4,147,40,123.7\n木兰县,7,122.4,137.5,15,24,122.2\n尚志市,17,121.8,141.8,30,97,122.2\n通河县,20,119.7,153.5,13,33,117.5\n延寿县,4,117.1,135.2,12,21,130.3\n方正县,11,116.7,131,5,14,119.8\n', '折线图', '{\n    \"title\": {\n        \"text\": \"学生综合成绩\",\n        \"subtext\": \"\"\n    },\n    \"tooltip\": {\n        \"trigger\": \"axis\"\n    },\n    \"legend\": {\n        \"data\": [\"2024招录人数\", \"2023年最低进面分\", \"2023最高进面分\", \"2023招录人数\", \"2023年报名人数\", \"2022年最低进面分\", \"2022最高进面分\", \"2022招录人数\", \"2022报名人数\"]\n    },\n    \"xAxis\": {\n        \"type\": \"category\",\n        \"data\": [\"平房区\", \"呼兰区\", \"阿城区\", \"双城区\", \"依兰县\", \"五常市\", \"巴彦县\", \"宾县\", \"木兰县\", \"尚志市\", \"通河县\", \"延寿县\", \"方正县\"]\n    },\n    \"yAxis\": {},\n    \"series\": [\n        {\n            \"name\": \"2024招录人数\",\n            \"type\": \"line\",\n            \"data\": [2, 10, 4, 15, 28, 58, 33, 26, 7, 17, 20, 4, 11]\n        },\n        {\n            \"name\": \"2023年最低进面分\",\n            \"type\": \"line\",\n            \"data\": [136.5, 134.1, 132.9, 121.7, 124.1, 123.2, 123, 122.6, 122.4, 121.8, 119.7, 117.1, 116.7]\n        },\n        {\n            \"name\": \"2023最高进面分\",\n            \"type\": \"line\",\n            \"data\": [140.7, 140.5, 149, 140.3, 145.4, 145.4, 143.2, 152.4, 137.5, 141.8, 153.5, 135.2, 131]\n        },\n        {\n            \"name\": \"2023招录人数\",\n            \"type\": \"line\",\n            \"data\": [2, 9, 19, 9, 22, 85, 16, 147, 15, 30, 13, 12, 5]\n        },\n        {\n            \"name\": \"2023年报名人数\",\n            \"type\": \"line\",\n            \"data\": [141, 249, 285, 62, 59, 385, 51, 40, 24, 97, 33, 21, 14]\n        },\n        {\n            \"name\": \"2022年最低进面分\",\n            \"type\": \"line\",\n            \"data\": [135.5, 137.1, 129.5, 132, 117.6, 123.4, 121.3, 123.7, 122.2, 122.2, 117.5, 130.3, 119.8]\n        },\n        {\n            \"name\": \"2022最高进面分\",\n            \"type\": \"line\",\n            \"data\": [140.7, 140.5, 149, 140.3, 145.4, 145.4, 143.2, 152.4, 137.5, 141.8, 153.5, 135.2, 131]\n        },\n        {\n            \"name\": \"2022招录人数\",\n            \"type\": \"line\",\n            \"data\": [null, null, null, null, null, null, null, null, null, null, null, null, null]\n        },\n        {\n            \"name\": \"2022报名人数\",\n            \"type\": \"line\",\n            \"data\": [null, null, null, null, null, null, null, null, null, null, null, null, null]\n        }\n    ]\n}\n', '\n根据数据分析可得，不同地区的学生综合成绩表现存在差异。\n2024年招录人数最多的地区是五常市，为58人；\n2023年最低进面分最高的地区是呼兰区，为134.1分；\n2023年最高进面分最高的地区是阿城区，为149', 1770428273925709826, '2024-03-24 20:35:23', '2024-03-24 20:35:23', 0);
INSERT INTO `t_chart` VALUES (1771914467224231937, '黑龙江省考今年招考数据表', '我的分数在130分左右，请你针对每个地区帮我分析一下我能考上的概率，形成柱状图,请使用柱状图', '地区,2024招录人数,2023年最低进面分,2023最高进面分,2023招录人数,2023年报名人数,2022年最低进面分,2022最高进面分,2022招录人数,2022报名人数\n平房区,2,136.5,140.7,2,141,135.5\n呼兰区,10,134.1,140.5,9,249,137.1\n阿城区,4,132.9,149,19,285,129.5\n双城区,15,121.7,140.3,9,62,132\n依兰县,28,124.1,145.4,22,59,117.6\n五常市,58,123.2,145.4,85,385,123.4\n巴彦县,33,123,143.2,16,51,121.3\n宾县,26,122.6,152.4,147,40,123.7\n木兰县,7,122.4,137.5,15,24,122.2\n尚志市,17,121.8,141.8,30,97,122.2\n通河县,20,119.7,153.5,13,33,117.5\n延寿县,4,117.1,135.2,12,21,130.3\n方正县,11,116.7,131,5,14,119.8\n', '柱状图', '{\n    \"title\": {\n        \"text\": \"各地区考入概率分析\",\n        \"subtext\": \"根据历年进面分和招录人数\"\n    },\n    \"tooltip\": {\n        \"trigger\": \"axis\",\n        \"axisPointer\": {\n            \"type\": \"shadow\"\n        }\n    },\n    \"legend\": {},\n    \"xAxis\": {\n        \"data\": [\"平房区\", \"呼兰区\", \"阿城区\", \"双城区\", \"依兰县\", \"五常市\", \"巴彦县\", \"宾县\", \"木兰县\", \"尚志市\", \"通河县\", \"延寿县\", \"方正县\"],\n        \"axisLabel\": {\n            \"interval\": 0,\n            \"rotate\": 45\n        }\n    },\n    \"yAxis\": {},\n    \"series\": [{\n        \"name\": \"2024招录人数\",\n        \"type\": \"bar\",\n        \"data\": [2, 10, 4, 15, 28, 58, 33, 26, 7, 17, 20, 4, 11]\n    }, {\n        \"name\": \"2023年最低进面分\",\n        \"type\": \"bar\",\n        \"data\": [136.5, 134.1, 132.9, 121.7, 124.1, 123.2, 123, 122.6, 122.4, 121.8, 119.7, 117.1, 116.7]\n    }, {\n        \"name\": \"2023年最高进面分\",\n        \"type\": \"bar\",\n        \"data\": [140.7, 140.5, 149, 140.3, 145.4, 145.4, 143.2, 152.4, 137.5, 141.8, 153.5, 135.2, 131]\n    }]\n}\n', '\n根据数据分析可得，根据历年的进面分和招录人数统计，\n平房区的考入概率较低，呼兰区、阿城区和双城区的考入概率较高。\n其他地区的考入概率和具体情况需要进一步分析。', 1770428273925709826, '2024-03-24 22:58:24', '2024-03-24 22:58:24', 0);
INSERT INTO `t_chart` VALUES (1775503637268127746, '省考选调分数', '分析一下这个表大致说了个什么,请使用柱状图', '地区,2024招录人数,2023年最低进面分,2023最高进面分,2023招录人数,2023年报名人数,2022年最低进面分,2022最高进面分,2022招录人数,2022报名人数\n平房区,2,136.5,140.7,2,141,135.5\n呼兰区,10,134.1,140.5,9,249,137.1\n阿城区,4,132.9,149,19,285,129.5\n双城区,15,121.7,140.3,9,62,132\n依兰县,28,124.1,145.4,22,59,117.6\n五常市,58,123.2,145.4,85,385,123.4\n巴彦县,33,123,143.2,16,51,121.3\n宾县,26,122.6,152.4,147,40,123.7\n木兰县,7,122.4,137.5,15,24,122.2\n尚志市,17,121.8,141.8,30,97,122.2\n通河县,20,119.7,153.5,13,33,117.5\n延寿县,4,117.1,135.2,12,21,130.3\n方正县,11,116.7,131,5,14,119.8\n', '柱状图', '{\n    \"title\": {\n        \"text\": \"招录人数与进面分对比\",\n        \"subtext\": \"\"\n    },\n    \"tooltip\": {\n        \"trigger\": \"axis\",\n        \"axisPointer\": {\n            \"type\": \"shadow\"\n        }\n    },\n    \"legend\": {\n        \"data\": [\"2024招录人数\", \"2023招录人数\", \"2022招录人数\", \"2023最低进面分\", \"2023最高进面分\", \"2022最低进面分\", \"2022最高进面分\"]\n    },\n    \"grid\": {\n        \"left\": \"3%\",\n        \"right\": \"4%\",\n        \"bottom\": \"3%\",\n        \"containLabel\": true\n    },\n    \"xAxis\": [\n        {\n            \"type\": \"category\",\n            \"data\": [\"平房区\", \"呼兰区\", \"阿城区\", \"双城区\", \"依兰县\", \"五常市\", \"巴彦县\", \"宾县\", \"木兰县\", \"尚志市\", \"通河县\", \"延寿县\", \"方正县\"],\n            \"axisTick\": {\n                \"alignWithLabel\": true\n            }\n        }\n    ],\n    \"yAxis\": {},\n    \"series\": [\n        {\n            \"name\": \"2024招录人数\",\n            \"type\": \"bar\",\n            \"data\": [2, 10, 4, 15, 28, 58, 33, 26, 7, 17, 20, 4, 11]\n        },\n        {\n            \"name\": \"2023招录人数\",\n            \"type\": \"bar\",\n            \"data\": [2, 9, 19, 9, 22, 85, 16, 147, 15, 30, 13, 12, 5]\n        },\n        {\n            \"name\": \"2022招录人数\",\n            \"type\": \"bar\",\n            \"data\": [null, null, null, null, null, null, null, null, null, null, null, null, null]\n        },\n        {\n            \"name\": \"2023最低进面分\",\n            \"type\": \"bar\",\n            \"data\": [null, null, null, null, null, null, null, null, null, null, null, null, null, 132.9]\n        },\n        {\n            \"name\": \"2023最高进面分\",\n            \"type\": \"bar\",\n            \"data\": [null, null, null, null, null, null, null, null, null, null, null, null, null, 149]\n        },\n        {\n            \"name\": \"2022最低进面分\",\n            \"type\": \"bar\",\n            \"data\": [null, null, null, null, null, null, null, null, null, null, null, null, null, null]\n        },\n        {\n            \"name\": \"2022最高进面分\",\n            \"type\": \"bar\",\n            \"data\": [null, null, null, null, null, null, null, null, null, null, null, null, null, null]\n        }\n    ]\n}\n', '\n通过该柱状图可以大致了解到不同地区的招录人数和进面分情况。\n可以观察到2024年只有部分地区有招录人数，其他地区尚未公布数据。\n2023年的招录人数相对较多，呼兰区、宾县和五常市的招录人数最多。\n2023年的进面分最低的地区为方正县，为116.7分，最高的地区为木兰县，为137.5分。\n2022年的数据不完整，没有相关招录人数和进面分的数据。\n综合分析可得，招录人数和进面分在不同地区之间存在差异，可以作为选择地区就业的参考依据。', 1770428273925709826, '2024-04-03 20:40:29', '2024-04-03 20:40:29', 0);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
                           `user_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
                           `user_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
                           `user_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                           `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
                           `user_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
                           `user_role` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'user' COMMENT '用户角色：user/admin',
                           `user_state` tinyint NOT NULL COMMENT '用户状态（0表示启用，1表示禁用）',
                           `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
                           PRIMARY KEY (`user_id`) USING BTREE,
                           INDEX `idx_userAccount`(`user_account` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1783441483094167554 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1770428273925709826, 'admin', '207acd61a3c1bd506d7e9a4535359f8a', 'admin', 'http://127.0.0.1:9000/ai-bi/avatar/2024/04/25/409ac133bba849719baf3ec8b9ab92da1714040298699.jpg', 'admin', 0, '2024-03-21 12:32:48', '2024-04-25 18:18:19', 0);

SET FOREIGN_KEY_CHECKS = 1;
