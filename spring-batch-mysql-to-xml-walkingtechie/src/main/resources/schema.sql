create table user (
	id integer not null, 
	age integer, 
	password varchar(255), 
	username varchar(255), 
	primary key (id)) engine=MyISAM
	
	
INSERT INTO user (id, age, password,username) VALUES (1, 22, 'abc@123', 'john.doe@gmail.com');
INSERT INTO user (id, age, password,username) VALUES (2, 23, 'xyx@123', 'raj.kumar@gmail.com');
INSERT INTO user (id, age, password,username) VALUES (3, 24, 'savani@123', 'savani.shinde@gmail.com');
INSERT INTO user (id, age, password,username) VALUES (4, 25, 'sally@123', 'sally.cox@gmail.com');
INSERT INTO user (id, age, password,username) VALUES (5, 26, 'deepak@123', 'deepak.parate@gmail.com');