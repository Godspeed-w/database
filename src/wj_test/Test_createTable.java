package wj_test;

public class Test_createTable {
	public static void main(String[] args) {
		CREATE TABLE `cainiao` (
				  `runoob_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
				  `runoob_title` varchar(100) NOT NULL,
				  `runoob_author` varchar(40) NOT NULL,
				  `submission_date` date DEFAULT NULL,
				  PRIMARY KEY (`runoob_id`)
				) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8
		
	}
	
	CREATE TABLE cao (
			  `runoob_id` int(10) unsigned NOT NULL,
			  `runoob_title` varchar(100) NOT NULL,
			  `runoob_author` varchar(40) NOT NULL,
			  `submission_date` date DEFAULT NULL)
}

CREATE TABLE Persons
(
Id_P int,
LastName varchar(255),
FirstName varchar(255),
Address varchar(255),
City varchar(255)
)