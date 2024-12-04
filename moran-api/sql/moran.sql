/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80020 (8.0.20)
 Source Host           : localhost:3306
 Source Schema         : moran

 Target Server Type    : MySQL
 Target Server Version : 80020 (8.0.20)
 File Encoding         : 65001

 Date: 04/12/2024 14:30:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `parent_id` int NULL DEFAULT NULL COMMENT '上级ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `sort` int NOT NULL DEFAULT 1 COMMENT '排序',
  `is_show` tinyint NOT NULL DEFAULT 1 COMMENT '是否可见',
  `type` int NOT NULL DEFAULT 1 COMMENT '类型:1:菜单,2:按钮',
  `api` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口api',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, NULL, '首页', '/workspace', 'BasicLayout', 0, 1, 1, NULL, '2023-10-11 10:47:32', 'layui-icon-home', NULL);
INSERT INTO `sys_menu` VALUES (2, 1, '首页', '/workspace/workbench', '@/views/workSpace/workbench/index.vue', 1, 1, 1, NULL, '2023-10-11 10:51:04', 'layui-icon-util', NULL);
INSERT INTO `sys_menu` VALUES (3, NULL, '系统管理', '/system', 'BasicLayout', 1, 1, 1, NULL, '2023-10-11 11:00:56', 'layui-icon-set', NULL);
INSERT INTO `sys_menu` VALUES (4, 3, '用户管理', '/system/user', '@/views/system/user/index.vue', 1, 1, 1, '', '2023-10-11 11:02:35', 'layui-icon-user', NULL);
INSERT INTO `sys_menu` VALUES (5, 3, '角色管理', '/system/role', '@/views/system/role/index.vue', 2, 1, 1, '', '2023-10-11 11:02:35', 'layui-icon-user', NULL);
INSERT INTO `sys_menu` VALUES (6, 3, '菜单管理', '/system/menu', '@/views/system/menu/index.vue', 3, 1, 1, '', '2023-10-11 11:02:35', 'layui-icon-spread-left', NULL);
INSERT INTO `sys_menu` VALUES (9, 4, '编辑', NULL, NULL, 1, 0, 2, '/system/user/update', '2023-10-12 17:05:14', NULL, NULL);
INSERT INTO `sys_menu` VALUES (10, 4, '删除', NULL, NULL, 1, 0, 2, '/system/user/del', '2023-10-12 17:05:14', NULL, NULL);
INSERT INTO `sys_menu` VALUES (11, 4, '状态', NULL, NULL, 1, 0, 2, '/system/user/status', '2023-10-12 17:05:14', NULL, NULL);
INSERT INTO `sys_menu` VALUES (13, 5, '编辑', NULL, NULL, 1, 0, 2, '/system/role/update', '2023-10-12 17:05:14', NULL, NULL);
INSERT INTO `sys_menu` VALUES (14, 5, '删除', NULL, NULL, 1, 0, 2, '/system/role/del', '2023-10-12 17:05:14', NULL, NULL);
INSERT INTO `sys_menu` VALUES (16, 6, '编辑', NULL, NULL, 1, 0, 2, '/system/menu/update', '2023-10-12 17:05:14', NULL, NULL);
INSERT INTO `sys_menu` VALUES (17, 6, '删除', NULL, NULL, 1, 0, 2, '/system/menu/del', '2023-10-12 17:05:14', NULL, NULL);
INSERT INTO `sys_menu` VALUES (18, 5, '分配权限', NULL, NULL, 1, 0, 2, '/system/role/menus', '2023-10-12 17:05:14', NULL, NULL);
INSERT INTO `sys_menu` VALUES (23, 4, '查询', '', '', 0, 1, 2, '/system/user/list', '2024-11-28 10:22:14', '', NULL);
INSERT INTO `sys_menu` VALUES (24, 5, '查询', '', '', 0, 1, 2, '/system/role/list', '2024-11-28 10:22:52', '', NULL);
INSERT INTO `sys_menu` VALUES (25, 6, '查询', '', '', 0, 1, 2, '/system/menu/list', '2024-11-28 10:23:18', '', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `menu_ids` json NULL COMMENT '菜单ID集合',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `root` tinyint NOT NULL DEFAULT 0 COMMENT '是否为主账号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', NULL, '[1, 2, 3, 4, 5, 6, 9, 10, 11, 13, 14, 16, 17, 18, 23, 24, 25]', '2023-10-11 11:16:10', 1);
INSERT INTO `sys_role` VALUES (2, '管理员', '角色', '[1, 2, 3, 4, 5, 6, 9, 10, 11, 13, 14, 16, 17, 18, 23, 24, 25]', '2023-10-12 13:27:50', 0);
INSERT INTO `sys_role` VALUES (3, '测试001', '一个测试', '[1, 2, 3, 4, 5, 6, 23, 24, 25]', '2024-06-17 14:18:52', 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码盐',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif' COMMENT '头像',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `role_ids` json NULL COMMENT '角色ID集合',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `root` tinyint NOT NULL DEFAULT 0 COMMENT '是否为主账号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_01`(`account` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'd1ec27500a7448766eb27d31db2407fb', 'da47f607d058bf7abada5f057a39cd09', '管理员', 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif', '12332131', NULL, NULL, '2023-10-11 11:17:57', '[1]', 1, '2024-03-25 15:14:35', 0);
INSERT INTO `sys_user` VALUES (2, 'moran', '0cbc9e18532ab98f06a9f5532d163b44', '309803ac4787502364a1f88f6a736c68', '默然', 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif', '18746176990', '1280520512@qq.com', NULL, '2024-03-25 15:51:14', '[3]', 1, '2024-11-28 10:12:41', 0);

SET FOREIGN_KEY_CHECKS = 1;
