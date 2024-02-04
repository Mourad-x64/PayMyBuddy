-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 04 fév. 2024 à 17:42
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `paymybuddy`
--

-- --------------------------------------------------------

--
-- Structure de la table `friendships`
--

DROP TABLE IF EXISTS `friendships`;
CREATE TABLE IF NOT EXISTS `friendships` (
  `friendship_id` bigint NOT NULL AUTO_INCREMENT,
  `friendship_src_id` bigint DEFAULT NULL,
  `friendship_tgt_id` bigint DEFAULT NULL,
  PRIMARY KEY (`friendship_id`),
  KEY `FKhjahy8uddvc0l8u150hqid5p0` (`friendship_src_id`),
  KEY `FK2o2sls5cvcsg9x7ds4xlxdkxr` (`friendship_tgt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `friendships`
--

INSERT INTO `friendships` (`friendship_id`, `friendship_src_id`, `friendship_tgt_id`) VALUES
(2, 51, 6),
(3, 51, 44),
(4, 51, 21),
(5, 51, 11),
(6, 51, 40),
(7, 51, 38),
(8, 51, 10),
(11, 51, 43),
(12, 51, 29),
(13, 51, 13),
(14, 51, 30),
(15, 51, 39),
(16, 51, 17),
(17, 51, 26),
(19, 51, 36),
(22, 51, 28),
(23, 51, 42),
(24, 51, 49),
(25, 51, 5),
(26, 51, 32),
(27, 51, 24),
(28, 51, 23),
(29, 51, 25),
(31, 51, 22),
(32, 51, 16),
(34, 51, 46),
(36, 51, 8),
(37, 51, 2),
(38, 51, 41),
(39, 51, 45),
(44, 51, 19),
(46, 51, 15),
(49, 51, 34),
(50, 51, 48),
(51, 6, 51);

-- --------------------------------------------------------

--
-- Structure de la table `spring_session`
--

DROP TABLE IF EXISTS `spring_session`;
CREATE TABLE IF NOT EXISTS `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

--
-- Déchargement des données de la table `spring_session`
--

INSERT INTO `spring_session` (`PRIMARY_ID`, `SESSION_ID`, `CREATION_TIME`, `LAST_ACCESS_TIME`, `MAX_INACTIVE_INTERVAL`, `EXPIRY_TIME`, `PRINCIPAL_NAME`) VALUES
('69d087dd-d422-47b3-aec3-7990ff9cac1e', '46d1cfc1-66f5-4524-9069-ebea487f27bd', 1706836618503, 1706840537403, 1800, 1706842337403, 'titi@titi.fr');

-- --------------------------------------------------------

--
-- Structure de la table `spring_session_attributes`
--

DROP TABLE IF EXISTS `spring_session_attributes`;
CREATE TABLE IF NOT EXISTS `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

--
-- Déchargement des données de la table `spring_session_attributes`
--

INSERT INTO `spring_session_attributes` (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`, `ATTRIBUTE_BYTES`) VALUES
('69d087dd-d422-47b3-aec3-7990ff9cac1e', 'org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN', 0xaced0005737200366f72672e737072696e676672616d65776f726b2e73656375726974792e7765622e637372662e44656661756c7443737266546f6b656e5aefb7c82fa2fbd50200034c000a6865616465724e616d657400124c6a6176612f6c616e672f537472696e673b4c000d706172616d657465724e616d6571007e00014c0005746f6b656e71007e0001787074000c582d435352462d544f4b454e7400055f6373726674002435656634633038302d633135362d343164612d386433612d353733393937316365383032),
('69d087dd-d422-47b3-aec3-7990ff9cac1e', 'SPRING_SECURITY_CONTEXT', 0xaced00057372003d6f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e636f6e746578742e5365637572697479436f6e74657874496d706c00000000000002620200014c000e61757468656e7469636174696f6e7400324c6f72672f737072696e676672616d65776f726b2f73656375726974792f636f72652f41757468656e7469636174696f6e3b78707372004f6f72672e737072696e676672616d65776f726b2e73656375726974792e61757468656e7469636174696f6e2e557365726e616d6550617373776f726441757468656e7469636174696f6e546f6b656e00000000000002620200024c000b63726564656e7469616c737400124c6a6176612f6c616e672f4f626a6563743b4c00097072696e636970616c71007e0004787200476f72672e737072696e676672616d65776f726b2e73656375726974792e61757468656e7469636174696f6e2e416273747261637441757468656e7469636174696f6e546f6b656ed3aa287e6e47640e0200035a000d61757468656e746963617465644c000b617574686f7269746965737400164c6a6176612f7574696c2f436f6c6c656374696f6e3b4c000764657461696c7371007e0004787001737200266a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c654c697374fc0f2531b5ec8e100200014c00046c6973747400104c6a6176612f7574696c2f4c6973743b7872002c6a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65436f6c6c656374696f6e19420080cb5ef71e0200014c00016371007e00067870737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a65787000000001770400000001737200426f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e617574686f726974792e53696d706c654772616e746564417574686f7269747900000000000002620200014c0004726f6c657400124c6a6176612f6c616e672f537472696e673b7870740009524f4c455f555345527871007e000d737200486f72672e737072696e676672616d65776f726b2e73656375726974792e7765622e61757468656e7469636174696f6e2e57656241757468656e7469636174696f6e44657461696c7300000000000002620200024c000d72656d6f74654164647265737371007e000f4c000973657373696f6e496471007e000f787074000f303a303a303a303a303a303a303a3174002436343337383134342d623563612d343932382d623331392d3763643864666437346436377073720028636f6d2e6f70656e636c617373726f6f6d732e5061794d7942756464792e6d6f64656c2e557365726396f9c7bf763ed002000a46000762616c616e63654c0005654d61696c71007e000f4c000966697273746e616d6571007e000f4c000b667269656e64736869707371007e00094c000269647400104c6a6176612f6c616e672f4c6f6e673b4c00086c6173746e616d6571007e000f4c000870617373776f726471007e000f4c0004726f6c6571007e000f4c000c7472616e73616374696f6e7371007e00094c0008757365726e616d6571007e000f787043c8e00074000c7469746940746974692e6672740004746974697372002a6f72672e68696265726e6174652e636f6c6c656374696f6e2e7370692e50657273697374656e74426167963c61233a7241920200024c000362616771007e00094c001270726f7669646564436f6c6c656374696f6e71007e0006787200396f72672e68696265726e6174652e636f6c6c656374696f6e2e7370692e416273747261637450657273697374656e74436f6c6c656374696f6e33a4b04a3cf0460c02000b5a001b616c6c6f774c6f61644f7574736964655472616e73616374696f6e49000a63616368656453697a655a000564697274795a000e656c656d656e7452656d6f7665645a000b696e697469616c697a65645a000d697354656d7053657373696f6e4c00036b657971007e00044c00056f776e657271007e00044c0004726f6c6571007e000f4c001273657373696f6e466163746f72795575696471007e000f4c000e73746f726564536e617073686f747400164c6a6176612f696f2f53657269616c697a61626c653b787000ffffffff000000007372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000003371007e0018740034636f6d2e6f70656e636c617373726f6f6d732e5061794d7942756464792e6d6f64656c2e557365722e667269656e6473686970737070707071007e0021740004746f746f74003c24326124313024313546476c644768422e365767437a4f464b4f58544f31706a705a386c374241314e706a47715571326635537a784f36623633587171007e00117371007e001b00ffffffff0000000071007e002171007e0018740035636f6d2e6f70656e636c617373726f6f6d732e5061794d7942756464792e6d6f64656c2e557365722e7472616e73616374696f6e737070707074000c7469746940746974692e6672);

-- --------------------------------------------------------

--
-- Structure de la table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
  `transaction_id` bigint NOT NULL AUTO_INCREMENT,
  `from_id` bigint DEFAULT NULL,
  `to_id` bigint DEFAULT NULL,
  `amount` float NOT NULL,
  `date_created` varchar(255) DEFAULT NULL,
  `commission` float NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `FK3s5dcbinokr2g7glvs4cl3jrx` (`from_id`),
  KEY `FKhq90d8wkd08nmpgwta00qy928` (`to_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `from_id`, `to_id`, `amount`, `date_created`, `commission`, `description`) VALUES
(1, 51, 6, 10, 'Sat Dec 02 17:38:03 CET 2023', 0.05, NULL),
(2, 51, 6, 10, 'Sat Dec 02 17:46:41 CET 2023', 0.05, NULL),
(3, 51, 6, 100, 'Sat Dec 02 17:51:25 CET 2023', 0.5, NULL),
(4, 51, 6, 10, 'Sat Dec 02 17:54:21 CET 2023', 0.05, NULL),
(5, 51, 6, 10, 'Sat Dec 02 17:54:33 CET 2023', 0.05, NULL),
(6, 51, 6, 10, 'Sat Dec 02 17:55:34 CET 2023', 0.05, NULL),
(7, 6, 51, 10, 'Sat Dec 02 17:58:53 CET 2023', 0.05, NULL),
(8, 6, 51, 20, 'Sat Dec 02 18:16:02 CET 2023', 0.1, 'restaurant'),
(9, 51, 6, 10, 'Mon Dec 04 18:12:37 CET 2023', 0.05, 'restau'),
(10, 51, 38, 120, 'Tue Jan 16 17:44:39 CET 2024', 0.6, 'hg,hgj,');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `balance` float NOT NULL,
  `e_mail` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`user_id`, `balance`, `e_mail`, `firstname`, `lastname`, `password`, `username`, `role`) VALUES
(2, 4545.71, 'okernoghan1@yahoo.com', 'Orren', 'Kernoghan', 'bdb8c008fa551ba75f8481963f2201da', 'okernoghan1@yahoo.com', 'ROLE_USER'),
(3, 4078.84, 'cmattock2@deviantart.com', 'Chet', 'Mattock', 'bdb8c008fa551ba75f8481963f2201da', 'cmattock2@deviantart.com', 'ROLE_USER'),
(4, 1450.17, 'dburtonwood3@macromedia.com', 'Drona', 'Burtonwood', 'bdb8c008fa551ba75f8481963f2201da', 'dburtonwood3@macromedia.com', 'ROLE_USER'),
(5, 254.79, 'acaudle4@weibo.com', 'Anthia', 'Caudle', 'bdb8c008fa551ba75f8481963f2201da', 'acaudle4@weibo.com', 'ROLE_USER'),
(6, 999.92, 'trainy5@google.fr', 'Turner', 'Rainy', 'bdb8c008fa551ba75f8481963f2201da', 'trainy5@google.fr', 'ROLE_USER'),
(7, 394.54, 'nguilleton6@hostgator.com', 'Noami', 'Guilleton', 'bdb8c008fa551ba75f8481963f2201da', 'nguilleton6@hostgator.com', 'ROLE_USER'),
(8, 321.28, 'ldienes7@google.ru', 'Leo', 'Dienes', 'bdb8c008fa551ba75f8481963f2201da', 'ldienes7@google.ru', 'ROLE_USER'),
(9, 504.66, 'vserchwell8@blogs.com', 'Vikky', 'Serchwell', 'bdb8c008fa551ba75f8481963f2201da', 'vserchwell8@blogs.com', 'ROLE_USER'),
(10, 4296.88, 'nnadin9@ycombinator.com', 'Nertie', 'Nadin', 'bdb8c008fa551ba75f8481963f2201da', 'nnadin9@ycombinator.com', 'ROLE_USER'),
(11, 3345.74, 'kflooka@boston.com', 'Kalvin', 'Flook', 'bdb8c008fa551ba75f8481963f2201da', 'kflooka@boston.com', 'ROLE_USER'),
(12, 881.09, 'zdmitrievskib@nytimes.com', 'Zelma', 'Dmitrievski', 'bdb8c008fa551ba75f8481963f2201da', 'zdmitrievskib@nytimes.com', 'ROLE_USER'),
(13, 2280.22, 'dferrasc@seesaa.net', 'Dianne', 'Ferras', 'bdb8c008fa551ba75f8481963f2201da', 'dferrasc@seesaa.net', 'ROLE_USER'),
(14, 4732.11, 'rcorstond@state.gov', 'Rodd', 'Corston', 'bdb8c008fa551ba75f8481963f2201da', 'rcorstond@state.gov', 'ROLE_USER'),
(15, 2066.01, 'pschankelborge@narod.ru', 'Pearle', 'Schankelborg', 'bdb8c008fa551ba75f8481963f2201da', 'pschankelborge@narod.ru', 'ROLE_USER'),
(16, 690.55, 'slombf@usgs.gov', 'Sarina', 'Lomb', 'bdb8c008fa551ba75f8481963f2201da', 'slombf@usgs.gov', 'ROLE_USER'),
(17, 4974.07, 'wtonng@huffingtonpost.com', 'Winonah', 'Tonn', 'bdb8c008fa551ba75f8481963f2201da', 'wtonng@huffingtonpost.com', 'ROLE_USER'),
(18, 4583.28, 'mwillmanh@homestead.com', 'Mortie', 'Willman', 'bdb8c008fa551ba75f8481963f2201da', 'mwillmanh@homestead.com', 'ROLE_USER'),
(19, 1543.84, 'beastwoodi@mac.com', 'Brigid', 'Eastwood', 'bdb8c008fa551ba75f8481963f2201da', 'beastwoodi@mac.com', 'ROLE_USER'),
(20, 3308.81, 'mhubnerj@mit.edu', 'Myca', 'Hubner', 'bdb8c008fa551ba75f8481963f2201da', 'mhubnerj@mit.edu', 'ROLE_USER'),
(21, 3651.83, 'ldeguarak@last.fm', 'Leah', 'Deguara', 'bdb8c008fa551ba75f8481963f2201da', 'ldeguarak@last.fm', 'ROLE_USER'),
(22, 2204.86, 'rmccookel@gravatar.com', 'Randolf', 'McCooke', 'bdb8c008fa551ba75f8481963f2201da', 'rmccookel@gravatar.com', 'ROLE_USER'),
(23, 2838.23, 'adaglishm@nba.com', 'Adolph', 'Daglish', 'bdb8c008fa551ba75f8481963f2201da', 'adaglishm@nba.com', 'ROLE_USER'),
(24, 1029.37, 'glabrouen@cloudflare.com', 'Guthrie', 'Labroue', 'bdb8c008fa551ba75f8481963f2201da', 'glabrouen@cloudflare.com', 'ROLE_USER'),
(25, 2040.29, 'kvolageo@domainmarket.com', 'Kenneth', 'Volage', 'bdb8c008fa551ba75f8481963f2201da', 'kvolageo@domainmarket.com', 'ROLE_USER'),
(26, 4861.34, 'astormesp@elpais.com', 'Averell', 'Stormes', 'bdb8c008fa551ba75f8481963f2201da', 'astormesp@elpais.com', 'ROLE_USER'),
(27, 4360.27, 'rdurrellq@ft.com', 'Randee', 'Durrell', 'bdb8c008fa551ba75f8481963f2201da', 'rdurrellq@ft.com', 'ROLE_USER'),
(28, 2813.1, 'blesslier@weibo.com', 'Blisse', 'Lesslie', 'bdb8c008fa551ba75f8481963f2201da', 'blesslier@weibo.com', 'ROLE_USER'),
(29, 161.22, 'dpoppys@jalbum.net', 'Derwin', 'Poppy', 'bdb8c008fa551ba75f8481963f2201da', 'dpoppys@jalbum.net', 'ROLE_USER'),
(30, 3594.95, 'lpischoft@hao123.com', 'La verne', 'Pischof', 'bdb8c008fa551ba75f8481963f2201da', 'lpischoft@hao123.com', 'ROLE_USER'),
(31, 3921.59, 'ogarrowayu@bandcamp.com', 'Odella', 'Garroway', 'bdb8c008fa551ba75f8481963f2201da', 'ogarrowayu@bandcamp.com', 'ROLE_USER'),
(32, 1687.7, 'myitzhokv@biblegateway.com', 'Marice', 'Yitzhok', 'bdb8c008fa551ba75f8481963f2201da', 'myitzhokv@biblegateway.com', 'ROLE_USER'),
(33, 1281.24, 'sspainw@yelp.com', 'Salomo', 'Spain', 'bdb8c008fa551ba75f8481963f2201da', 'sspainw@yelp.com', 'ROLE_USER'),
(34, 3884.06, 'hlebrunx@google.com.au', 'Hugo', 'Le Brun', 'bdb8c008fa551ba75f8481963f2201da', 'hlebrunx@google.com.au', 'ROLE_USER'),
(35, 3906.8, 'besbrooky@stumbleupon.com', 'Barrie', 'Esbrook', 'bdb8c008fa551ba75f8481963f2201da', 'besbrooky@stumbleupon.com', 'ROLE_USER'),
(36, 2321.84, 'tfieldgatez@ft.com', 'Thorin', 'Fieldgate', 'bdb8c008fa551ba75f8481963f2201da', 'tfieldgatez@ft.com', 'ROLE_USER'),
(37, 2182.01, 'hbridgeland10@google.com.hk', 'Halimeda', 'Bridgeland', 'bdb8c008fa551ba75f8481963f2201da', 'hbridgeland10@google.com.hk', 'ROLE_USER'),
(38, 4699.42, 'ljados11@shinystat.com', 'Linet', 'Jados', 'bdb8c008fa551ba75f8481963f2201da', 'ljados11@shinystat.com', 'ROLE_USER'),
(39, 329.4, 'mlythgoe12@admin.ch', 'Mariejeanne', 'Lythgoe', 'bdb8c008fa551ba75f8481963f2201da', 'mlythgoe12@admin.ch', 'ROLE_USER'),
(40, 2177.76, 'kmccrillis13@nsw.gov.au', 'Kirstyn', 'McCrillis', 'bdb8c008fa551ba75f8481963f2201da', 'kmccrillis13@nsw.gov.au', 'ROLE_USER'),
(41, 1231.27, 'credler14@mac.com', 'Celinka', 'Redler', 'bdb8c008fa551ba75f8481963f2201da', 'credler14@mac.com', 'ROLE_USER'),
(42, 2020.77, 'lbradder15@chicagotribune.com', 'Latashia', 'Bradder', 'bdb8c008fa551ba75f8481963f2201da', 'lbradder15@chicagotribune.com', 'ROLE_USER'),
(43, 2569.49, 'nhugues16@bing.com', 'Nollie', 'Hugues', 'bdb8c008fa551ba75f8481963f2201da', 'nhugues16@bing.com', 'ROLE_USER'),
(44, 1693.6, 'ljorgensen17@taobao.com', 'Lorilyn', 'Jorgensen', 'bdb8c008fa551ba75f8481963f2201da', 'ljorgensen17@taobao.com', 'ROLE_USER'),
(45, 692.39, 'pblethin18@yandex.ru', 'Pancho', 'Blethin', 'bdb8c008fa551ba75f8481963f2201da', 'pblethin18@yandex.ru', 'ROLE_USER'),
(46, 2175.17, 'fdelchecolo19@wix.com', 'Frasquito', 'Del Checolo', 'bdb8c008fa551ba75f8481963f2201da', 'fdelchecolo19@wix.com', 'ROLE_USER'),
(47, 3285.08, 'cmcfater1a@domainmarket.com', 'Corabella', 'McFater', 'bdb8c008fa551ba75f8481963f2201da', 'cmcfater1a@domainmarket.com', 'ROLE_USER'),
(48, 1557.39, 'rjansens1b@zdnet.com', 'Robby', 'Jansens', 'bdb8c008fa551ba75f8481963f2201da', 'rjansens1b@zdnet.com', 'ROLE_USER'),
(49, 2823.19, 'bdoone1c@exblog.jp', 'Brinna', 'Doone', 'bdb8c008fa551ba75f8481963f2201da', 'bdoone1c@exblog.jp', 'ROLE_USER'),
(50, 1438.99, 'kordelt1d@shareasale.com', 'Kirsten', 'Ordelt', '$2a$10$15FGldGhB.6WgCzOFKOXTO1pjpZ8l7BA1NpjGqUq2f5SzxO6b63Xq', 'kordelt1d@shareasale.com', 'ROLE_USER'),
(51, 2500, 'titi@titi.fr', 'titi', 'toto', '$2a$10$15FGldGhB.6WgCzOFKOXTO1pjpZ8l7BA1NpjGqUq2f5SzxO6b63Xq', 'titi@titi.fr', 'ROLE_USER'),
(52, 1200, 'admin@toto.fr', 'toto', 'tutu', '$2a$10$15FGldGhB.6WgCzOFKOXTO1pjpZ8l7BA1NpjGqUq2f5SzxO6b63Xq', 'admin@toto.fr', 'ROLE_ADMIN'),
(53, 0, 'mourad.ouamar@outlook.fr', 'mourad', 'ouamar', '$2a$10$15FGldGhB.6WgCzOFKOXTO1pjpZ8l7BA1NpjGqUq2f5SzxO6b63Xq', 'mourad.ouamar@outlook.fr', 'ROLE_USER');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `friendships`
--
ALTER TABLE `friendships`
  ADD CONSTRAINT `FK2o2sls5cvcsg9x7ds4xlxdkxr` FOREIGN KEY (`friendship_tgt_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FKhjahy8uddvc0l8u150hqid5p0` FOREIGN KEY (`friendship_src_id`) REFERENCES `users` (`user_id`);

--
-- Contraintes pour la table `spring_session_attributes`
--
ALTER TABLE `spring_session_attributes`
  ADD CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE;

--
-- Contraintes pour la table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `FK3s5dcbinokr2g7glvs4cl3jrx` FOREIGN KEY (`from_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FKhq90d8wkd08nmpgwta00qy928` FOREIGN KEY (`to_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
