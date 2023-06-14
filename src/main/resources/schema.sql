DROP TABLE IF EXISTS `authorities` ;
DROP TABLE IF EXISTS `users` ;

CREATE TABLE users
(
    username VARCHAR(255)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled  TINYINT      NOT NULL DEFAULT 1,
    PRIMARY KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE authorities
(
    username  VARCHAR(255) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
