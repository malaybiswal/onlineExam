# onlineExam
CREATE TABLE `question` (
  `sno` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(4000) DEFAULT NULL,
  `a1` varchar(1000) DEFAULT NULL,
  `a2` varchar(1000) DEFAULT NULL,
  `a3` varchar(1000) DEFAULT NULL,
  `a4` varchar(1000) DEFAULT NULL,
  `ans` varchar(10) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `subtype` varchar(100) DEFAULT NULL,
  `created_t` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=latin1

CREATE TABLE `questioninprogress` (
  `email` varchar(100) DEFAULT NULL,
  `sno` int(7) DEFAULT NULL,
  `starttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `current` varchar(1) DEFAULT NULL,
  `answer` varchar(5) DEFAULT NULL,
  `qno` int(3) DEFAULT NULL,
  `testno` int(4) DEFAULT NULL,
  `score` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1

CREATE TABLE `result` (
  `email` varchar(100) DEFAULT NULL,
  `starttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `score` varchar(3) DEFAULT NULL,
  `type` varchar(40) DEFAULT NULL,
  `subtype` varchar(50) DEFAULT NULL,
  `duration` int(4) DEFAULT NULL,
  `testno` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1
