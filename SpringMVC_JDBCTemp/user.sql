create Database userdb;

use userdb;

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(3) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;


INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `gender`, `city`) VALUES
(1, 'Koushik', 'Ghosh', 'male', 'Dhanbad'),
(2, 'Indrajit', 'Das', 'male', 'Kolkata'),
(3, 'Code', 'Strive', 'male', 'Bangalore'),
(4, 'Ramesh', 'Bokka', 'male', 'AndraPradesh');

