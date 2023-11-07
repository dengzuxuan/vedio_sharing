/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云docker
 Source Server Type    : MySQL
 Source Server Version : 50736 (5.7.36)
 Source Host           : 8.140.38.47:13306
 Source Schema         : vedio_sharing

 Target Server Type    : MySQL
 Target Server Version : 50736 (5.7.36)
 File Encoding         : 65001

 Date: 07/11/2023 23:02:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collects
-- ----------------------------
DROP TABLE IF EXISTS `collects`;
CREATE TABLE `collects`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `video_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collects
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `video_id` int(11) NULL DEFAULT NULL,
  `comment_id` int(11) NULL DEFAULT NULL,
  `likes` int(11) NULL DEFAULT NULL,
  `flag` tinyint(4) NULL DEFAULT NULL COMMENT '回复视频的时候flag=1；回复第一层评论的时候flag=2；第二层评论之间互相评论的flag=3',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 173 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (168, '测试评论', 27, 59, 0, 1, 1, '2023-11-07 19:41:50', '2023-11-07 19:41:50');
INSERT INTO `comment` VALUES (169, '继续测试', 27, 59, 0, 0, 1, '2023-11-07 19:42:49', '2023-11-07 19:42:49');
INSERT INTO `comment` VALUES (170, '回复2', 27, 59, 169, 0, 2, '2023-11-07 19:43:11', '2023-11-07 19:43:11');
INSERT INTO `comment` VALUES (171, '哈喽', 27, 60, 0, 0, 1, '2023-11-07 19:43:27', '2023-11-07 19:43:27');
INSERT INTO `comment` VALUES (172, '快来分享你的看法！', 27, 117, 0, 0, 1, '2023-11-07 22:52:38', '2023-11-07 22:52:38');

-- ----------------------------
-- Table structure for comment_likes
-- ----------------------------
DROP TABLE IF EXISTS `comment_likes`;
CREATE TABLE `comment_likes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `video_id` int(11) NULL DEFAULT NULL,
  `comment_id` int(11) NULL DEFAULT NULL,
  `send_userid` int(11) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_likes
-- ----------------------------
INSERT INTO `comment_likes` VALUES (34, 59, 168, 27, '2023-11-07 19:41:52', '2023-11-07 19:41:52');

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recv_userid` int(11) NULL DEFAULT NULL,
  `send_userid` int(11) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend
-- ----------------------------

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
  `id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `video_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of likes
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `recv_userid` int(11) NULL DEFAULT NULL,
  `send_userid` int(11) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL COMMENT '/0是未读 1是已读',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 217 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (214, 'B站的小伙伴们！新歌...', '测试评论', 26, 27, 2, 1, '2023-11-07 19:41:50', '2023-11-07 11:46:04');
INSERT INTO `message` VALUES (215, 'B站的小伙伴们！新歌...', '继续测试', 26, 27, 2, 1, '2023-11-07 19:42:49', '2023-11-07 11:46:04');
INSERT INTO `message` VALUES (216, '来唱《stay wi...', '哈喽', 26, 27, 2, 1, '2023-11-07 19:43:27', '2023-11-07 11:46:04');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `password_real` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `sexual` int(11) NULL DEFAULT NULL COMMENT '性别/0未知 1男 2女',
  `photo` longtext CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `friends` int(11) NULL DEFAULT NULL,
  `like_hidden` tinyint(4) NULL DEFAULT NULL COMMENT '/0是不隐藏 1是yinc',
  `collect_hidden` tinyint(4) NULL DEFAULT NULL,
  `send_friends` int(11) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (26, '111111', '啊吧啊吧', '$2a$10$errxTm/zrka2PxSA3axWrud0g7C6HfPCKUFqYWq57hqj8pnHjr6ZG', 'cd111111', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/photo/photo_default.png', 0, 0, 0, 0, '2023-11-07 19:32:29', '2023-11-07 19:32:29');
INSERT INTO `user` VALUES (27, 'dengzuxuan', '牛牛', '$2a$10$/bAiSbOy2dLf8ll7/3hwTOmdWFexJ3WkOMm2J2MtMgxLBuQ80qXvG', 'dengzuxuan2001', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/2023110732ba8a042f010b47888f0f258815c17b1b.jpg', 0, 0, 0, 0, '2023-11-07 19:40:45', '2023-11-07 19:40:45');
INSERT INTO `user` VALUES (28, 'dengzuxuan2', '黄瓜奶油', '$2a$10$A8xmq2XlF1kZWvEzjvNVbuGthmVL2rJ5JEcCTF7L0krZJGisdJtUO', 'dengzuxuan2001', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/202311071556cd12d053ed4d34801ed4fde2a7bb75.jpg', 0, 0, 0, 0, '2023-11-07 20:06:28', '2023-11-07 20:06:28');
INSERT INTO `user` VALUES (29, 'dengzuxuan3', '花生奶酪', '$2a$10$/bAiSbOy2dLf8ll7/3hwTOmdWFexJ3WkOMm2J2MtMgxLBuQ80qXvG', 'dengzuxuan2001', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/202311075168ad3bf931e348e8a5dd13abf7c87f76.jpg', 0, 0, 0, 0, '2023-11-07 19:40:45', '2023-11-07 19:40:45');
INSERT INTO `user` VALUES (30, 'dengzuxuan4', '吃我一掌', '$2a$10$A8xmq2XlF1kZWvEzjvNVbuGthmVL2rJ5JEcCTF7L0krZJGisdJtUO', 'dengzuxuan2001', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/20231107320bb55378223c4d7b9eaf33f9326ab13f.jpg', 0, 0, 0, 0, '2023-11-07 20:06:28', '2023-11-07 20:06:28');
INSERT INTO `user` VALUES (31, 'dengzuxuan5', '玛卡巴卡', '$2a$10$/bAiSbOy2dLf8ll7/3hwTOmdWFexJ3WkOMm2J2MtMgxLBuQ80qXvG', 'dengzuxuan2001', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/202311075035cd0fbb72d142bcaa1d00abf7f7e8f1.jpg', 0, 0, 0, 0, '2023-11-07 19:40:45', '2023-11-07 19:40:45');
INSERT INTO `user` VALUES (32, 'dengzuxuan6', '葡萄三明治', '$2a$10$A8xmq2XlF1kZWvEzjvNVbuGthmVL2rJ5JEcCTF7L0krZJGisdJtUO', 'dengzuxuan2001', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/2023110714d740ab80cbb24b1ea3bea8f9a577bec0.jpg', 0, 0, 0, 0, '2023-11-07 20:06:28', '2023-11-07 20:06:28');
INSERT INTO `user` VALUES (33, 'dengzuxuan7', '薄荷馒头', '$2a$10$/bAiSbOy2dLf8ll7/3hwTOmdWFexJ3WkOMm2J2MtMgxLBuQ80qXvG', 'dengzuxuan2001', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/2023110727cce8bbab0cba47d194ad75acbe94fb5f.jpg', 0, 0, 0, 0, '2023-11-07 19:40:45', '2023-11-07 19:40:45');
INSERT INTO `user` VALUES (34, 'dengzuxuan8', '我是小伟', '$2a$10$A8xmq2XlF1kZWvEzjvNVbuGthmVL2rJ5JEcCTF7L0krZJGisdJtUO', 'dengzuxuan2001', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/2023110701eb5688a2831c4a8586c8c974a96f7206.jpg', 0, 0, 0, 0, '2023-11-07 20:06:28', '2023-11-07 20:06:28');
INSERT INTO `user` VALUES (35, 'dengzuxuan9', '奶油草莓', '$2a$10$/bAiSbOy2dLf8ll7/3hwTOmdWFexJ3WkOMm2J2MtMgxLBuQ80qXvG', 'dengzuxuan2001', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/2023110720fa1f12c59b934a33bcdcb90b304292f6.jpg', 0, 0, 0, 0, '2023-11-07 19:40:45', '2023-11-07 19:40:45');
INSERT INTO `user` VALUES (36, 'dengzuxuan10', '青蛙下蛋', '$2a$10$A8xmq2XlF1kZWvEzjvNVbuGthmVL2rJ5JEcCTF7L0krZJGisdJtUO', 'dengzuxuan2001', '', 0, 'http://s34n6l898.hn-bkt.clouddn.com/202311074473fa547de5ed4f5db5d6abd42f4a89cc.jpg', 0, 0, 0, 0, '2023-11-07 20:06:28', '2023-11-07 20:06:28');

-- ----------------------------
-- Table structure for user_likely
-- ----------------------------
DROP TABLE IF EXISTS `user_likely`;
CREATE TABLE `user_likely`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `sport` int(11) NULL DEFAULT NULL,
  `game` int(11) NULL DEFAULT NULL,
  `food` int(11) NULL DEFAULT NULL,
  `music` int(11) NULL DEFAULT NULL,
  `fun` int(11) NULL DEFAULT NULL,
  `knowledge` int(11) NULL DEFAULT NULL,
  `animal` int(11) NULL DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_likely
-- ----------------------------
INSERT INTO `user_likely` VALUES (26, 26, 10, 10, 10, 10, 10, 10, 10, '2023-11-07 19:32:29', '2023-11-07 19:32:29');
INSERT INTO `user_likely` VALUES (27, 27, 10, 10, 10, 10, 10, 10, 10, '2023-11-07 19:40:45', '2023-11-07 19:40:45');
INSERT INTO `user_likely` VALUES (28, 28, 10, 10, 10, 10, 10, 10, 10, '2023-11-07 20:06:28', '2023-11-07 20:06:28');

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `type` int(11) NULL DEFAULT NULL,
  `video_url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `photo_url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `init_hot_points` int(11) NULL DEFAULT NULL COMMENT '热度 /点赞+1 收藏+3 评论+5',
  `total_hot_points` int(11) NULL DEFAULT NULL,
  `month_hot_points` int(11) NULL DEFAULT NULL,
  `week_hot_points` int(11) NULL DEFAULT NULL,
  `views_points` int(11) NULL DEFAULT NULL,
  `like_points` int(11) NULL DEFAULT NULL,
  `collect_points` int(11) NULL DEFAULT NULL,
  `comment_points` int(11) NULL DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 163 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES (59, 26, 'B站的小伙伴们！新歌《Hall Of Fame》现已上线', '我的新歌《Hall of Fame》现已发行！', 4, 'http://s34n6l898.hn-bkt.clouddn.com/20231107491b10011802d1448097103030c517d04e.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/20231107362fc54c1ec5f142b3aa1316ceb3122fca.jpg', 33, 33, 33, 33, 3, 0, 0, 3, '2023-11-07 19:35:56', '2023-11-07 19:35:56');
INSERT INTO `video` VALUES (60, 27, '来唱《stay with me》', '因为我家茶几声音比较脆哈 加上我拍摄的场景为了避免反光所以开了阳台门拍摄 直接边唱边拍会有杂音 所以我就选择先在餐边柜边敲边唱的哈 餐边柜敲击声音更厚实一些捏 但是餐边柜拍摄的灯光和背景不太好看 所以还是选择在我平时主页拍摄的场景下拍摄视频 也就是说对的是我自己的唱歌口型 因为拍摄场景的小茶几（放手机的桌子）比较小 所以拍视频时看不到我的手敲桌子辣（泣）', 4, 'http://s34n6l898.hn-bkt.clouddn.com/2023110739cf827ed728524d879e1536de538643da.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110704f047f4b9a47f4d3192a26cf2f1b3ad3c.jpg', 38, 38, 38, 38, 4, 0, 0, 1, '2023-11-07 19:40:14', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (61, 28, '洗澡洗到一半冲出浴室做的', '-', 4, 'http://s34n6l898.hn-bkt.clouddn.com/20231107595e8a9ff8ca654a7ba8593c579bed439b.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/20231107224e0261024ef14901a98cf042567e068b.jpg', 17, 17, 17, 17, 0, 0, 0, 0, '2023-11-07 19:48:51', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (62, 29, '我有玉玉症！', '-', 4, 'http://s34n6l898.hn-bkt.clouddn.com/2023110744ca8d4c8c905d428f9144c784a2225751.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/efbbac335c8147aca426f85f93ad5eda.jpg', 15, 15, 15, 15, 0, 0, 0, 0, '2023-11-07 19:51:24', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (63, 30, '在办公室清唱一首《Shadow of the sun》', '-', 4, 'http://s34n6l898.hn-bkt.clouddn.com/20231107521f26bc8ff5e444a999cf7e32727fc7b1.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/c849e0f9e54f4bd8a425dbdaf6383589.jpg', 12, 12, 12, 12, 1, 0, 0, 0, '2023-11-07 19:52:34', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (65, 31, '当我在外国比赛唱起，全场沸腾了。', '得了第二，很满足，也很自豪了，作为唯一个亚洲脸庞。太幸运了！', 4, 'http://s34n6l898.hn-bkt.clouddn.com/20231107052e8fd13f1c8847a58a3aa13512c319d4.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/6ff23a5f5e934a3cba4219378931ca63.jpg', 39, 39, 39, 39, 1, 0, 0, 0, '2023-11-07 19:55:00', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (66, 32, '你来了，你咋又来了', '有手就能弹的钢琴神曲', 4, 'http://s34n6l898.hn-bkt.clouddn.com/2023110730a061573bfcba4358a0255bdf17b308f0.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/201652fa06bb47c7b9b08c9596ed70d1.jpg', 19, 19, 19, 19, 0, 0, 0, 0, '2023-11-07 19:56:15', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (67, 33, '撒泡尿的工夫，会弹《Fake Love》了', '有手就能弹的钢琴神曲', 4, 'http://s34n6l898.hn-bkt.clouddn.com/202311075548bcbed9a22f4c878cbe5eacabfe997a.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/3299e6ff231c4d0b81fb0cdc5bdb85ab.jpg', 13, 13, 13, 13, 0, 0, 0, 0, '2023-11-07 19:57:22', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (68, 34, '风情万种，摇曳风姿的李镶镶。', '-', 4, 'http://s34n6l898.hn-bkt.clouddn.com/2023110704ebbfca23baa644a98c964be9f518693c.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/74f90a26c4394633aec6bfb6750d22cc.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 19:58:46', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (69, 35, '【回不去的何止时间】', '-', 4, 'http://s34n6l898.hn-bkt.clouddn.com/20231107214516a4b4e4b14cb9903d7de60b9fe648.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/20231107057b650086f29343038be43488ce236642.jpg', 17, 17, 17, 17, 0, 0, 0, 0, '2023-11-07 20:00:10', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (70, 36, '用硬币演奏《only my railgun》', '音不准，跑调，就当个乐看', 4, 'http://s34n6l898.hn-bkt.clouddn.com/202311073397d35319530a4325ac0347444fdb4768.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110741c974644297344228b519b7debe3d7dd5.jpg', 15, 15, 15, 15, 0, 0, 0, 0, '2023-11-07 20:01:43', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (71, 26, '【shadybug】建模比宣传图美一万倍的宝宝', '复活了，顺便摸个鱼，看了巴黎篇我可太喜欢shadybug这个建模了(*´I`*)真的很美！！！', 4, 'http://s34n6l898.hn-bkt.clouddn.com/20231107583ff78f19571d427c873ab2510bb12fd8.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110742c016e421bbbe4922b5520fef1ceaba91.jpg', 32, 32, 32, 32, 0, 0, 0, 0, '2023-11-07 20:03:47', '2023-11-07 20:03:47');
INSERT INTO `video` VALUES (72, 27, '《爱永在》24届艺考生！记录小课日常，期待蜕变', '24届（应届）艺考生练习《爱永在》\n一直在调整咬字问题，有很多字依然没有咬好，也以至于最后高音没有唱好，视频为记录，欢迎交流！', 4, 'http://s34n6l898.hn-bkt.clouddn.com/202311073300e6667c8c2f45b2a171714c900ee5c9.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/20231107354f554743a0b34ba8ad2c18c2cda3082e.jpg', 33, 33, 33, 33, 0, 0, 0, 0, '2023-11-07 20:06:38', '2023-11-07 14:49:18');
INSERT INTO `video` VALUES (73, 28, '苏打绿 小情歌 8bit版', '苏打绿 小情歌 8bit版', 4, 'http://s34n6l898.hn-bkt.clouddn.com/2023110748b1347a73542e443085d5537ab2978000.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/202311073592c633afab4f441cb60a17458ecc8e76.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 20:07:39', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (74, 29, '【犬少年|EP】INUTOPIA-不听过错亿的作业BGM', '是直播间的bgm，很多同学觉得非常适合写作业的时候听。', 4, 'http://s34n6l898.hn-bkt.clouddn.com/2023110701e40b0d75b1ba4b45a61954c85d399b8a.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110709c8323c58c7fd45c5ab614a2ed5be9270.jpg', 31, 31, 31, 31, 0, 0, 0, 0, '2023-11-07 20:09:12', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (75, 30, '【原创】做了一段非常Funky的高潮「Can You Hea', '-', 4, 'http://s34n6l898.hn-bkt.clouddn.com/202311071997087387bc1a4c199bf39965fbe34864.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110709b051f67ea11c428587ce5bc8727105a1.jpg', 19, 19, 19, 19, 0, 0, 0, 0, '2023-11-07 20:10:13', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (76, 31, '活人单妹和AI单妹合唱（？）我是我的狗！', '活人单妹完败）', 4, 'http://s34n6l898.hn-bkt.clouddn.com/2023110721a1fbe1b25c6f47688299b07a7e79426b.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/7a8d581b62f24ff8b23ffb42ddf93709.jpg', 18, 18, 18, 18, 0, 0, 0, 0, '2023-11-07 20:11:20', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (77, 32, '“当你踏过沼泽去拯救小猫时，自己也会深陷其中', '-', 4, 'http://s34n6l898.hn-bkt.clouddn.com/202311073228c67e39f34147cb82a9b4232219ec56.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/94d67b00e97f4fe2bee2b7c8dc643ad5.jpg', 11, 11, 11, 11, 0, 0, 0, 0, '2023-11-07 20:12:23', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (78, 33, '【SLPA】当我用琵琶弹爵士...| 原创《Dummy Li', '原创《Dummy Lips》\n演奏: THE EITHER - Jiaju', 4, 'http://s34n6l898.hn-bkt.clouddn.com/2023110732df0cfff2f3984759869cd27caf306797.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/bd5e8b50bbd74b9fb2d13549bbbefcf6.jpg', 39, 39, 39, 39, 1, 0, 0, 0, '2023-11-07 20:13:23', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (79, 34, '楼下的车一直在响警报，借此机会，给邻居们弹首周杰伦吧。', '-', 4, 'http://s34n6l898.hn-bkt.clouddn.com/2023110729f95edde0bfdd41d89b11056617781695.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/20231107391ff01fd935a04128aa56018af634cd91.jpg', 19, 19, 19, 19, 0, 0, 0, 0, '2023-11-07 20:19:43', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (80, 35, '一段很有力量的话', '我从未见过，一个早起、勤奋、谨慎、诚实的人，抱怨命运不好的', 6, 'http://s34n6l898.hn-bkt.clouddn.com/2023110758a441b1b1d30f4fda86acfd59826bd174.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/288dbcd19f3a4204a8075498a60c493d.jpg', 36, 36, 36, 36, 0, 0, 0, 0, '2023-11-07 20:23:09', '2023-11-07 14:49:18');
INSERT INTO `video` VALUES (82, 36, '最近运气如何？', '最近运气如何？', 4, 'http://s34n6l898.hn-bkt.clouddn.com/20231107409de3084a397c4c9fb9f3b9daf1b6d88c.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110721b536de5e10c24c59ab82a149d79077c9.jpg', 14, 14, 14, 14, 0, 0, 0, 0, '2023-11-07 20:24:28', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (83, 26, '给他一个苹果，立刻还一个橘子的人！真的不能深交吗？【强边界感', '给他一个苹果，立刻还一个橘子的人！真的不能深交吗？', 6, 'http://s34n6l898.hn-bkt.clouddn.com/2023110725a0586519f5424660825053466edb3800.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/20231107148d54646a851346d8b3c0e4bf6a6792a0.jpg', 37, 37, 37, 37, 0, 0, 0, 0, '2023-11-07 20:28:20', '2023-11-07 20:28:20');
INSERT INTO `video` VALUES (84, 27, '16 岁 的他孤身犯险潜入敌营一个人干掉一个鬼子', '台儿庄战役 16 岁 的他孤身犯险潜入敌营一个人干掉一个鬼子特工营，被誉为广西最年轻的狼兵战士，先后参加了淞沪会战、徐州会战、台儿庄战役、武汉会战，最终在武汉会战时为保护战友，自己被飞机轰炸牺牲！', 6, 'http://s34n6l898.hn-bkt.clouddn.com/2023110757a234e59319f9474fa018dc78662ef8f4.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/bf226d6fe03940dca85f3f63626838f8.jpg', 36, 36, 36, 36, 0, 0, 0, 0, '2023-11-07 20:29:58', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (85, 28, '全国金太阳高二11月联考试卷答案考前 get!', '全国金太阳高二11月联考试卷答案考前 get!', 6, 'http://s34n6l898.hn-bkt.clouddn.com/20231107044b0c4df7e2f34effa1b670b6366bd81c.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/5d05ee5779e44da99ba92934987c5c8b.jpg', 37, 37, 37, 37, 1, 0, 0, 0, '2023-11-07 20:30:31', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (86, 29, '这就是50天极限考研的后果......', '-', 6, 'http://s34n6l898.hn-bkt.clouddn.com/202311074050a00ee44a8748e6ac3554400a01cf68.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/278571996a094c4c9576a309a7c0e648.jpg', 19, 19, 19, 19, 1, 0, 0, 0, '2023-11-07 20:31:26', '2023-11-07 14:49:22');
INSERT INTO `video` VALUES (87, 30, '面试必考题：你目前还在面试哪些公司？', '-', 6, 'http://s34n6l898.hn-bkt.clouddn.com/2023110732a2ab7355da474b74ad6e65265a8d096a.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/0510ced5939540cfbcec736f1fd3a3e8.jpg', 18, 18, 18, 18, 1, 0, 0, 0, '2023-11-07 20:32:10', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (88, 31, '军宅必备的战术用语合集1', '：b', 6, 'http://s34n6l898.hn-bkt.clouddn.com/202311071644014ee3618840a5b7625f2ef0ef2622.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/0cd2103136934bcba78ca13fff7bf3f1.jpg', 12, 12, 12, 12, 1, 0, 0, 0, '2023-11-07 20:33:03', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (89, 32, '高端显卡动画工程—18个分镜头', '领取工程文件加：xiaomac4d，截图备注c4d即可', 6, 'http://s34n6l898.hn-bkt.clouddn.com/2023110710cc74d5feceda4acebc6cdc981cf351f6.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/20231107297bd0a697990c40b3bed19eda9ba98648.jpg', 36, 36, 36, 36, 0, 0, 0, 0, '2023-11-07 20:34:32', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (90, 33, '逆反 别做正常人', '-', 6, 'http://s34n6l898.hn-bkt.clouddn.com/202311075782d36546cd33453bb4aa65d26f768a04.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110714458bdddd0f274b2eb9ad908d3d26ee73.jpg', 14, 14, 14, 14, 0, 0, 0, 0, '2023-11-07 20:51:18', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (91, 34, '四招解决版面空', '-', 6, 'http://s34n6l898.hn-bkt.clouddn.com/2023110717a38d9a56a3f64000b130a271ecaa0e85.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110725f55103099a094648b9bdf5f8760ba4c4.jpg', 14, 14, 14, 14, 0, 0, 0, 0, '2023-11-07 21:03:28', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (92, 35, '【简易字体设计教程】素颜', '今年最后一期字体设计系统课报名中，更多课程详情可单独咨询', 6, 'http://s34n6l898.hn-bkt.clouddn.com/2023110741ada9bdc8c3c3498fa292d3e9e8f06981.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/177dc71aeec1408497129e50ffd59cd5.jpg', 34, 34, 34, 34, 0, 0, 0, 0, '2023-11-07 21:04:27', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (93, 36, '桌子上的连锁反应', '-', 6, 'http://s34n6l898.hn-bkt.clouddn.com/2023110738f0d1df7b97684178a992ac19f6d533b5.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/d7201760d86848c5a1c84eeb706091af.jpg', 15, 15, 15, 15, 0, 0, 0, 0, '2023-11-07 21:05:49', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (94, 26, '模拟节点 - 粒子平流 - blender', '教程 ：https://www.youtube.com/watch?v=MhREfLSHLQM&t=12s', 6, 'http://s34n6l898.hn-bkt.clouddn.com/20231107145c8a7ea91fb94760b0509a4b3d33e7c3.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/53584f5e15d546f7a8f2fb57c376001b.jpg', 31, 31, 31, 31, 1, 0, 0, 0, '2023-11-07 21:07:41', '2023-11-07 21:07:41');
INSERT INTO `video` VALUES (95, 27, '帅啊，这个味太对了！', 'https://www.youtube.com/watch?v=m1ijQhuJkCY\nAzuki Elementals ✦', 6, 'http://s34n6l898.hn-bkt.clouddn.com/2023110759e2c494096fd841ffbc591a4dd9b742e4.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/44e6821bc8764f4e934ee077f30ebe88.jpg', 39, 39, 39, 39, 1, 0, 0, 0, '2023-11-07 21:09:19', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (96, 28, '【Adobe全家桶2023】', '-', 6, 'http://s34n6l898.hn-bkt.clouddn.com/202311072632314b7467be4ffbb3fa727399d4ca71.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/7da073aeef474909bf9dca34a255aae1.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 21:10:23', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (97, 29, '这才叫PR转场！', '1', 6, 'http://s34n6l898.hn-bkt.clouddn.com/20231107448d3447e27e374acb8c13a30b49c453d0.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/62b19a3fae314457937faba913ae0dac.jpg', 19, 19, 19, 19, 0, 0, 0, 0, '2023-11-07 21:11:35', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (98, 30, '【一句话系列23】这样说不仅高情商，关键还“有趣”', '-', 6, 'http://s34n6l898.hn-bkt.clouddn.com/20231107412fc3f742a83e44bb95e67416296a53f0.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/ead21aad88624e9ea04fe6a556fca927.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 21:12:13', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (99, 31, '审美提升 摄影分享 一叶舟', '审美提升 摄影分享 一叶舟\n', 6, 'http://s34n6l898.hn-bkt.clouddn.com/202311072081b5a95e45dc4374b505341a483b574e.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/25668a72997342eab774e480768aa656.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 21:13:16', '2023-11-07 14:49:18');
INSERT INTO `video` VALUES (100, 32, '完全不理解，谁说她没有刘海就变普女的！！', '-', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110703b2603cde29ce44e28f9924c9ead273e9.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/fd5cfea9ae6a4a2f8a3772eaa9c1ad3f.jpg', 15, 15, 15, 15, 0, 0, 0, 0, '2023-11-07 21:17:05', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (101, 33, '宁艺卓 | “是斯莱特林的Vivian小姐”', '封面这张照片尊嘟很蛇院 特别是眼睛\n我立马开剪一个\n但是莫名剪到最后的Vivian~有点搞笑哈哈哈哈骚瑞\n还有一个喜欢的bgm 抉择不出来 好想放P2 啊啊啊但是不知道怎么放P2', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110712c7b4eaa2517b4dc4b35b62bdc13b0120.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/202311075304d7b271cd5b4df4a82edc1e29c7a9e1.jpg', 33, 33, 33, 33, 0, 0, 0, 0, '2023-11-07 21:18:56', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (102, 34, '戒断失败！她俩转圈圈，我还能再看一百遍！！｜绝世舞姬', '素材全部来源于网络，权侵删。\n封面来源于/五月流影/视频截图。\n感谢封面字素·大雾天气-\n', 5, 'http://s34n6l898.hn-bkt.clouddn.com/20231107137f4d310b06794204b64dbccce0689569.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110728d7dbe0ae040c475eaa3e71630e400e94.jpg', 32, 32, 32, 32, 0, 0, 0, 0, '2023-11-07 21:20:36', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (103, 35, '【aespa】假如Spicy的拍摄现场去掉BGM时…', '助力aespa回归 请多多期待吧', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110743b362b8ddec8945f8bd29c1f1d147e8af.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/202311074082ae550c6b24466e8ec8de551b77d64e.jpg', 34, 34, 34, 34, 1, 0, 0, 0, '2023-11-07 21:21:43', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (104, 36, '谁懂！剪的时候腿快溢出屏幕啦！！（张元英一键换装） 48.6', '-', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110751f39de2a6dcab4239aa6b69f6c5c4741a.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/05613354052d4706ac898c70d5486eeb.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 21:22:52', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (105, 26, '14岁就被妈妈逼着整容的Bella Hadid从2013到2', 'Tiktok', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110757803251f70e804db6ac1dfad46324084f.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110755e3e31f35b8de40da9c87e0f44414dae2.jpg', 11, 11, 11, 11, 0, 0, 0, 0, '2023-11-07 21:23:59', '2023-11-07 21:23:59');
INSERT INTO `video` VALUES (106, 27, '小蓝鸟博主推荐那f', '-', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110711e32b724f31ee433293cfb09a27d29415.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110719d6223f607f114081b81fab685c43a381.jpg', 11, 11, 11, 11, 0, 0, 0, 0, '2023-11-07 21:25:24', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (107, 28, '康师傅冰红茶涨价最新后续，引起轩然大波，紧急撤回涨价通知。', '康师傅冰红茶涨价最新后续，引起轩然大波，紧急撤回涨价通知。', 5, 'http://s34n6l898.hn-bkt.clouddn.com/20231107330d4a29789b0843569a2fd2b7c420b844.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110719e65c4dd43b3f4353b72573e6d73cce25.jpg', 38, 38, 38, 38, 0, 0, 0, 0, '2023-11-07 21:26:30', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (108, 29, '陈泽再次教育宇大将军 将军求饶请求出狱', '-', 5, 'http://s34n6l898.hn-bkt.clouddn.com/202311073586e78c1ab1c6489eb81c94df54e79e57.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/1cf6a8d20e8144fdb107e18138fbf87a.jpg', 15, 15, 15, 15, 0, 0, 0, 0, '2023-11-07 21:28:03', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (109, 30, '冬泳怪鸽真男人！下跪救父！他第一次在直播里哭了', '冬泳怪鸽 独自一人照顾着年迈的父亲和智力残缺的弟弟，他走投无路，看着父亲的医院缴费单，他用颤抖的手打开了直播间的打赏功能，跪在地上泪洒直播间说，我已经用了大家的钱治病，但我不能再用大家的钱发财，他真的是网红圈的一股清流！', 5, 'http://s34n6l898.hn-bkt.clouddn.com/20231107116258a6c1894640b5a5723c7d386e0cdc.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/aad3ab39db0c441796767366897f994b.jpg', 34, 34, 34, 34, 0, 0, 0, 0, '2023-11-07 21:29:03', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (110, 31, '王一博出发金鸡奖红毯！加油加油！', '-', 5, 'http://s34n6l898.hn-bkt.clouddn.com/202311071070339af64db1417ba38c5dcbfd1a69de.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/82c42266f2d54cbb8f129c21c75ad03d.jpg', 19, 19, 19, 19, 0, 0, 0, 0, '2023-11-07 21:29:54', '2023-11-07 14:49:18');
INSERT INTO `video` VALUES (111, 32, '【宋亚轩】小宋老师，你看你是这意思不', '-', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110759aec84f81f10645c2a4642393d66f8228.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/100e7e7dba414c2e86fc1322d4aae917.jpg', 14, 14, 14, 14, 0, 0, 0, 0, '2023-11-07 21:32:13', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (112, 33, 'Jam Republic Ling和Emma Tiktok更', 'x', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110719e0f1627b0ee543dca153ec45d85232b4.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110710fc6ec672f0d0484f94f35e3e38de51e3.jpg', 19, 19, 19, 19, 0, 0, 0, 0, '2023-11-07 21:34:13', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (113, 34, '告诉我这是谁出的主意，这么超前的营业我第一次见！', '-', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110727a23d83726d7842929a8197515c378737.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/62ad2e7960e2401e95ed4cc61f92dc3b.jpg', 14, 14, 14, 14, 0, 0, 0, 0, '2023-11-07 21:34:53', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (114, 35, '【TREASURE｜yoshi金本芳典】精雕细琢的美人相', '-', 5, 'http://s34n6l898.hn-bkt.clouddn.com/202311075898ee8c1765fe46729370d26a609ef97d.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/5131ed1b08fd4d15a94c221945996e0b.jpg', 13, 13, 13, 13, 0, 0, 0, 0, '2023-11-07 21:35:25', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (115, 36, '【黄铉辰】VERSACE23假日系列宣传片', 'Hyunjin for Versace Holiday | Campaign Film | Versace', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110731c972ff26fe84439fb998daa980155b24.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110735e26c86aa08504901a25e15d46f8168d4.jpg', 34, 34, 34, 34, 0, 0, 0, 0, '2023-11-07 21:36:47', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (116, 26, '“开门红转黑-勇士雷霆”', '-', 5, 'http://s34n6l898.hn-bkt.clouddn.com/2023110706d377644ec36a47e9aa6208984f29e09f.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/a22cc21eed074a5e890a0e03571a8c3c.jpg', 17, 17, 17, 17, 0, 0, 0, 0, '2023-11-07 21:42:14', '2023-11-07 21:42:14');
INSERT INTO `video` VALUES (117, 27, '“开门红转黑-勇士雷霆”', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/20231107258c0ff10f0b554487ba8720cacf7bac9d.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/9e26bc04edc34e77b69874fe6ab2e848.jpg', 19, 19, 19, 19, 1, 0, 0, 1, '2023-11-07 21:42:45', '2023-11-07 14:49:22');
INSERT INTO `video` VALUES (118, 28, '#南韩第一亚比#孙彩瑛', '-', 5, 'http://s34n6l898.hn-bkt.clouddn.com/202311075419e2bef9734b4978ab8903d8a5562e7c.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/eae757013c494f6d8409bd7d7f1ad4d9.jpg', 18, 18, 18, 18, 0, 0, 0, 0, '2023-11-07 21:43:30', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (119, 29, '“你手机通讯录最出名的人是谁？”英超快问快答', '快问快答之英超球星版', 1, 'http://s34n6l898.hn-bkt.clouddn.com/202311074504646645930b4969944ee6342d3d5a16.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/404ae61c098f4433b27d3e16f7905397.jpg', 15, 15, 15, 15, 0, 0, 0, 0, '2023-11-07 21:44:42', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (120, 30, '【卡卡】一 见 卡 卡 误 终 身', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/2023110751249bf5a8aa7e4588a93c53ed01a75d6d.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/1c259d6c52db4f53b593485ba88eba91.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 21:45:26', '2023-11-07 14:49:18');
INSERT INTO `video` VALUES (121, 31, '【帕雷德斯 迪巴拉】队花队草一起做运动', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/20231107358193137bdeab447f86b849e149022729.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/20231107285f1311332c024445bb51cef08064eb84.jpg', 17, 17, 17, 17, 0, 0, 0, 0, '2023-11-07 21:46:31', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (122, 32, '3种假传过人', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/2023110744260e3c109eea4302a496f81c2d33f0eb.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/a596bf2ccaa74e6489ea3d19e0b2e86a.jpg', 19, 19, 19, 19, 0, 0, 0, 0, '2023-11-07 21:47:10', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (123, 33, '别再三步上篮了，教你“慢三步”轻松上篮！', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/2023110719d67037d02b134360b3f3a3599b418d45.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/8c508da64f8d40438916a13c0b65b734.jpg', 12, 12, 12, 12, 1, 0, 0, 0, '2023-11-07 21:48:30', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (124, 34, '喊你不会上篮的兄弟来看看吧。', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/20231107309cb3b3afdc014616bc37dbe3d001cef4.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/0f03a552fbe645fd8128ec34b39fd716.jpg', 18, 18, 18, 18, 0, 0, 0, 0, '2023-11-07 21:49:51', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (125, 35, '滑雪教练在中级道被不会滑雪的鱼雷创飞第一视角拍摄', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/2023110758fbebdfe4df2a4397b89f5f76e7053776.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/ceec172d48de4fc9a5b25124f686ec3b.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 21:51:10', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (126, 36, '今天没得黑！周琦找回状态证明自己 6投6中拿12分5板', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/2023110722b66026b0812347299db80b704f94635e.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/51403f8ba67b4eefa1ba4f7aceb9346a.jpg', 14, 14, 14, 14, 0, 0, 0, 0, '2023-11-07 21:51:51', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (127, 26, '暮年的乔丹科比，不也被球迷喊话？', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/2023110704140ca5d2737f4fdfac61eb777394b6ee.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/7824851407654648ac85f8a97e1b25bb.jpg', 12, 12, 12, 12, 0, 0, 0, 0, '2023-11-07 21:54:51', '2023-11-07 21:54:51');
INSERT INTO `video` VALUES (128, 27, '新手必学4个过人动作，熟练使用后已无人能防！', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/20231107598b930d268b2b4e4c9003e14b48fa8ba9.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/8f2ee3fcf1eb49d1b5c933ab81e27c49.jpg', 13, 13, 13, 13, 1, 0, 0, 0, '2023-11-07 21:58:18', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (129, 28, '休闲鞋也能有跑鞋技术？？', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/20231107256e6957b2e7b740ed999ef825e09460b3.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/a4f5f58e80d84021b210b562971f222b.jpg', 11, 11, 11, 11, 0, 0, 0, 0, '2023-11-07 21:58:43', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (130, 29, '羽毛球原来这么简单…mp4', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/2023110750a9cd4d7233964abd9123a9bf28b8999a.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2dcf6c657d7d46748befb4ecbb6303a5.jpg', 18, 18, 18, 18, 0, 0, 0, 0, '2023-11-07 21:59:14', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (131, 30, '这招太帅了，兄弟们', '-', 1, 'http://s34n6l898.hn-bkt.clouddn.com/202311071945f2303616c04fbe9bb6a47ee7153134.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/6bb7acb213e34af6b65a3405b9c1f40a.jpg', 13, 13, 13, 13, 0, 0, 0, 0, '2023-11-07 21:59:29', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (132, 31, '当初反对你养狗的父母，是不是都变成这样了…', '-', 7, 'http://s34n6l898.hn-bkt.clouddn.com/2023110727db26d5b7a3f84828bc264f86363ea712.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/1b1df6fbcd4f4ec1830b24dd0adf95ba.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 22:00:46', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (133, 32, '“爷爷：老伙计，我带你回家…”', '-', 7, 'http://s34n6l898.hn-bkt.clouddn.com/2023110756c6b0216e76cd4d45b7668584e9db3281.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/52d6bd158ef34ea480cb52ec799b9a24.jpg', 11, 11, 11, 11, 0, 0, 0, 0, '2023-11-07 22:01:30', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (134, 33, '【十一喵3】更新啦，赶紧来围观吧！', '-', 7, 'http://s34n6l898.hn-bkt.clouddn.com/20231107152a40c5e47d5c47f68991892c5cb06c47.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/d5f65f01ec8042eeb87cd1a91acf9d59.jpg', 11, 11, 11, 11, 0, 0, 0, 0, '2023-11-07 22:02:47', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (135, 34, '穿上袜子的鱼仔：你说公主请吃饭', '--', 7, 'http://s34n6l898.hn-bkt.clouddn.com/202311071429959f19d9fa40089bef7d3884dfd442.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2000f836d8cc459b8973d6d245f70cf1.jpg', 17, 17, 17, 17, 0, 0, 0, 0, '2023-11-07 22:03:44', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (136, 35, '猫房子来客人了', '-', 7, 'http://s34n6l898.hn-bkt.clouddn.com/2023110752b51bb25ce4f94afe9c3451f6963d28d8.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/b17249912b524d35a7578dd9073b56e4.jpg', 14, 14, 14, 14, 0, 0, 0, 0, '2023-11-07 22:04:26', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (137, 36, '听说昨天大家都在偶遇七崽', '-', 7, 'http://s34n6l898.hn-bkt.clouddn.com/202311073242823c15e5ae4e9f9c8fd15f9401e061.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/57e936d622bc4f63ab3a5cd2490660dc.jpg', 11, 11, 11, 11, 1, 0, 0, 0, '2023-11-07 22:04:46', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (138, 26, '小狗怎么那么喜欢揣手手啊', '-', 7, 'http://s34n6l898.hn-bkt.clouddn.com/20231107510c513bbb125e4c9bb64cae357077d28f.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/d3269fad2b964d84998685f5d00da308.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 22:05:03', '2023-11-07 22:05:03');
INSERT INTO `video` VALUES (139, 27, '这这这…为什么它会这样，为什么睡一觉起来就变烧狗了', '-', 7, 'http://s34n6l898.hn-bkt.clouddn.com/20231107086559293f2852475a8939fb7562cfdb71.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/697f18da13c24ee1a3c5ed7908c590de.jpg', 12, 12, 12, 12, 0, 0, 0, 0, '2023-11-07 22:05:39', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (140, 28, '抓住限时降温，赶紧烤起来啊', '-', 7, 'http://s34n6l898.hn-bkt.clouddn.com/2023110744bbdb580e57e34caea4f7b7b9e7bb7667.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/299dd434912f494a9ebd63b597f0d7c5.jpg', 18, 18, 18, 18, 0, 0, 0, 0, '2023-11-07 22:06:16', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (141, 29, '【猎杀对决】《钳 形 战 术》', '-', 2, 'http://s34n6l898.hn-bkt.clouddn.com/20231107302ba8b7240c5249e986f450b9fc9fbf11.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/5c7604e3ee2a41ed834c1f61884e7db0.jpg', 19, 19, 19, 19, 0, 0, 0, 0, '2023-11-07 22:07:06', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (142, 30, '【原神】你也想把派蒙抱在怀里吗？', '-', 2, 'http://s34n6l898.hn-bkt.clouddn.com/2023110729cc75a4b4c8304730af48844efa061e63.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/1cb491d0f56b416d9ec16117e29659c7.jpg', 15, 15, 15, 15, 0, 0, 0, 0, '2023-11-07 22:07:50', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (143, 31, '【原神】你也想把派蒙抱在怀里吗？', '-', 2, 'http://s34n6l898.hn-bkt.clouddn.com/20231107578abf8944c38948a285e055dc307a7247.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/88d495e70444472a946cf46e9b1b573f.jpg', 15, 15, 15, 15, 0, 0, 0, 0, '2023-11-07 22:08:19', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (144, 32, '11.0资料片泄露了？', '-', 2, 'http://s34n6l898.hn-bkt.clouddn.com/20231107259eb956df0dff4f209401b21c78db3179.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/bab8c15968124e96830c38e342055c3e.jpg', 14, 14, 14, 14, 1, 0, 0, 0, '2023-11-07 22:09:00', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (145, 33, '梵天百兽，加诸此身', '-', 2, 'http://s34n6l898.hn-bkt.clouddn.com/2023110706ec2e3dccf99a4d958903029303912aa0.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/57c1d6a57f9142f9aef0912a3fe1a945.jpg', 19, 19, 19, 19, 0, 0, 0, 0, '2023-11-07 22:09:44', '2023-11-07 14:49:21');
INSERT INTO `video` VALUES (146, 34, '挺近加贪欲 伤害99000', '-', 2, 'http://s34n6l898.hn-bkt.clouddn.com/2023110758c631478ccbda452d9cd1cc0d9b809ce9.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/1be9899290f144b3b837bd2b9b522941.jpg', 13, 13, 13, 13, 0, 0, 0, 0, '2023-11-07 22:10:24', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (147, 35, '王者里有哪些皮肤相当于换了个英雄！', '-', 2, 'http://s34n6l898.hn-bkt.clouddn.com/202311073220bfb71ede654503a6dde264f3df54d2.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/af6cedd0b64f4ad5b89e2da09c22fd7c.jpg', 13, 13, 13, 13, 0, 0, 0, 0, '2023-11-07 22:11:13', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (148, 36, '湖北菜为什么没特点？武汉是一个美食“洼地”？', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/20231107339a3471189f2844459879bf6d1ddc41b5.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/f4741bfd99c2437bb9c4d589ad3fda30.jpg', 17, 17, 17, 17, 0, 0, 0, 0, '2023-11-07 22:12:09', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (149, 26, '「重阳节」品尝古法重阳节花糕，松软糯口香甜，传统习俗中的永恒魅力！', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/202311072360b28281445c4a9fad086fce8bc838d2.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/8d82e50243b7492481844148332818d4.jpg', 13, 13, 13, 13, 0, 0, 0, 0, '2023-11-07 22:13:55', '2023-11-07 22:13:55');
INSERT INTO `video` VALUES (150, 27, '韩国街头的综合性摊位，各种小吃可抵上一个集市，均价实惠还美味', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/2023110702110362b4bbec49ec9934274fdad1fb7b.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/4735c293c8414ba5ab9b9bac4311b73c.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 22:14:52', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (151, 28, '杰克逊的灵魂宵夜！~【名人爱吃啥！？】', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/20231107590ae1c790221742419de34169c83353cf.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/ea07afc531b24cccbc23bed02330f031.jpg', 12, 12, 12, 12, 0, 0, 0, 0, '2023-11-07 22:15:19', '2023-11-07 14:49:18');
INSERT INTO `video` VALUES (152, 29, '年大将军这是不想活了呀，竟然在皇上面前以下犯上', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/2023110724e22e3983db37402a9e2e3170f2c87b87.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/b24b8bd367e94421a4796dc432dd004c.jpg', 12, 12, 12, 12, 0, 0, 0, 0, '2023-11-07 22:15:59', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (153, 30, '吐司上放块巧克力，压出来个圈，就是巧克力夹心派！', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/20231107059d928ea29fb34f7891b0e37d6972a15c.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/ab57a0eb29464b74b555238e846d3f21.jpg', 17, 17, 17, 17, 1, 0, 0, 0, '2023-11-07 22:16:24', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (154, 31, '小学生早餐不重样，家里有剩米饭的神仙吃法', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/2023110730d9b7f70d69aa4319a23d1b9447b9e79f.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/67dbaa7fb9ee4446ac1560f25c45b906.jpg', 11, 11, 11, 11, 1, 0, 0, 0, '2023-11-07 22:16:49', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (155, 32, '小学生早餐不重样，家里有剩米饭的神仙吃法', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/20231107545cc5718b1be4450db3d3ef18b1cf6000.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/da02326d74b846078d75fa629f38941e.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 22:17:39', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (156, 33, '一人食-低脂高蛋白的西兰花口蘑炒鸡胸肉', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/202311074555edbd048ca54d2eb45724ea1267f9e5.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/369d100a60424b4785a793e9c80719e9.jpg', 11, 11, 11, 11, 0, 0, 0, 0, '2023-11-07 22:18:46', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (157, 34, '【营养师教你减肥怎么吃小北减脂食谱】', '第2天午饭，饭菜肉一锅煮好，低脂低热量，简单又好吃', 3, 'http://s34n6l898.hn-bkt.clouddn.com/20231107461fd11c7106c34458a8b3e3c405590f59.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/e4511328b04542d0b8cf0cd0159b5dd9.jpg', 39, 39, 39, 39, 0, 0, 0, 0, '2023-11-07 22:21:48', '2023-11-07 14:49:20');
INSERT INTO `video` VALUES (158, 35, '米其林名师配方\"一生中最好的焦糖布丁“', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/2023110726d99bdb068f3d4af5ba9e35ba91108e81.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2f90d5e620f54308b40fd3fba0d3a1ee.jpg', 12, 12, 12, 12, 0, 0, 0, 0, '2023-11-07 22:23:23', '2023-11-07 14:49:19');
INSERT INTO `video` VALUES (159, 36, '辛苦了一天回来，来上一根麻辣香肠，顿时感觉很满足。', '-', 3, 'http://s34n6l898.hn-bkt.clouddn.com/20231107428f877dec801a42c8906efb5d709c72cc.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/3ac9eca579d04285b3f83e890b2d682f.jpg', 16, 16, 16, 16, 0, 0, 0, 0, '2023-11-07 22:24:07', '2023-11-07 14:49:18');
INSERT INTO `video` VALUES (160, 26, '胜负我不好评定 我只能做好自己', '胜负我不好评定 我只能做好自己', 2, 'http://s34n6l898.hn-bkt.clouddn.com/2023110718f4b027e4772e47468b3c78e987d4e1c8.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/57331f2efb224f9fa198c4de51867901.jpg', 16, 16, 16, 16, 1, 0, 0, 0, '2023-11-07 22:28:47', '2023-11-07 22:28:47');
INSERT INTO `video` VALUES (161, 27, '越看越红？盘点原神里那些优秀品质！', '一句话让宅男们集体破防，都别吵了', 2, 'http://s34n6l898.hn-bkt.clouddn.com/2023110722e3afd4483ee14d6a9a44daccd66825e2.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/40b6fe21cd3648a692bb5f8031ab7a7c.jpg', 38, 38, 38, 38, 0, 0, 0, 0, '2023-11-07 22:29:45', '2023-11-07 14:49:18');
INSERT INTO `video` VALUES (162, 28, '感谢宵宫的热情，宵宫的可爱，宵宫的活泼，感谢宵宫这一路上陪我', '-', 2, 'http://s34n6l898.hn-bkt.clouddn.com/20231107258e5603002ec640c2b780857b7e2a8a47.mp4', 'http://s34n6l898.hn-bkt.clouddn.com/2023110754995df2eba4ec408abfb9b5420918168b.jpg', 14, 14, 14, 14, 0, 0, 0, 0, '2023-11-07 22:30:58', '2023-11-07 14:49:20');

SET FOREIGN_KEY_CHECKS = 1;
