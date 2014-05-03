-- Koushik@codestrive.com
-- Codestrive.com
--

CREATE TABLE IF NOT EXISTS `state` (
  `state_id` int(11) NOT NULL AUTO_INCREMENT,
  `state_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`state_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=39 ;

--
-- Dumping data for table `state`
--

INSERT INTO `state` (`state_id`, `state_name`) VALUES
(1, 'Andaman & Nicobar'),
(2, 'Andhra Pradesh'),
(3, 'Arunachal Pradesh'),
(4, 'Assam '),
(5, 'Bihar '),
(6, 'Chandigarh '),
(7, 'Chhattisgarh'),
(8, 'Dadra & Nagar Haveli'),
(9, 'Daman & Diu'),
(10, 'Delhi '),
(11, 'Goa '),
(12, 'Gujarat '),
(13, 'Haryana '),
(14, 'Himachal Pradesh '),
(15, 'Jammu'),
(16, 'Jharkhand '),
(17, 'Karnataka '),
(18, 'Kerala '),
(19, 'Lakshadweep '),
(20, 'Madhya Pradesh '),
(21, 'Maharashtra '),
(22, 'Manipur'),
(23, 'Meghalaya '),
(24, 'Mizoram '),
(25, 'Nagaland '),
(26, 'Orissa '),
(27, 'Puducherry '),
(28, 'Punjab '),
(29, 'Rajasthan '),
(30, 'Sikkim '),
(31, 'Tamil Nadu '),
(32, 'Tripura '),
(33, 'Uttar Pradesh '),
(34, 'Uttarakhand '),
(35, 'West Bengal '),
(36, 'Kashmir');

