CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL DEFAULT ' ',
  `password` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;

CREATE TABLE `authority` (
  `username` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`username`,`role`),
  KEY `username` (`username`),
  CONSTRAINT `username_fk1` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `user` 
VALUES (1,'john@gmail.com','john','$2y$12$x4vDSMh/Pzmbd6d7YCMXIOkb9Lh9GSrFcf0fHSqfuzbuPPWiqRhay',1),
       (2,'amanda@gmail.com','amanda','$2y$12$x4vDSMh/Pzmbd6d7YCMXIOkb9Lh9GSrFcf0fHSqfuzbuPPWiqRhay',1),
       (3,'richard@gmail.com','richard','$2y$12$x4vDSMh/Pzmbd6d7YCMXIOkb9Lh9GSrFcf0fHSqfuzbuPPWiqRhay',1),
       (4,'julia@gmail.com','julia','$2y$12$x4vDSMh/Pzmbd6d7YCMXIOkb9Lh9GSrFcf0fHSqfuzbuPPWiqRhay',1);
       
       
INSERT INTO `authority` VALUES
('john','ROLE_user'),
('john','ROLE_admin'),
('amanda','ROLE_user'),
('richard','ROLE_user'),
('julia','ROLE_user');       