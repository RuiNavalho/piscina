/*==============================================================*/
/* DBMS name:      ProjManagement 0.1                                   */
/* Created on:     18-05-2017 18:00:00                          */
/*==============================================================*/






/*==============================================================*/
/* Tables:      												*/
/*==============================================================*/

/*==============================================================*/
/* DBMS name:      ProjManagement 0.1                                   */
/* Created on:     18-05-2017 18:00:00                          */
/*==============================================================*/


Use proj_management;

drop table if exists user;
drop table if exists role;
drop table if exists project;
drop table if exists client;
drop table if exists business;
drop table if exists stage;
drop table if exists task;
drop table if exists tasktype;
drop table if exists taskstage;
drop table if exists attachment;
drop table if exists skill;
drop table if exists tipology;
drop table if exists taskwork;
drop table if exists projmanager;
drop table if exists allocation;
drop table if exists user_role;
drop table if exists user_skill;



/*==============================================================*/
/* Tables:      												*/
/*==============================================================*/


CREATE TABLE user(
	id		 int AUTO_INCREMENT,
	email	 varchar(65) UNIQUE NOT NULL,
	fullname	 varchar(65) NOT NULL,
	passw	 varchar(65) NOT NULL,
	photo	 longblob,
	registrydate timestamp NOT NULL,
	active	 bool NOT NULL,
	salt	 varchar(1024) NOT NULL,

	PRIMARY KEY(id)
);

CREATE TABLE role(
	id	 int AUTO_INCREMENT,
	role varchar(65) UNIQUE NOT NULL,

	PRIMARY KEY(id)
);

CREATE TABLE project(
	id		 int AUTO_INCREMENT,
	idproject	 varchar(65) UNIQUE NOT NULL,
	title	 varchar(65) NOT NULL,
	description	 varchar(65) NOT NULL,
	begindate	 timestamp NOT NULL,
	enddate	 timestamp default current_timestamp NULL,
	budget	 float(8) NOT NULL,
	creationdate timestamp default current_timestamp NULL,
	tipology_id	 int NOT NULL ,
	user_creator	 int NOT NULL ,
	stage_id	 int NOT NULL ,
	client_id	 int NOT NULL ,

	PRIMARY KEY(id)
);

CREATE TABLE client(
	id		 int AUTO_INCREMENT,
	company	 varchar(65) UNIQUE NOT NULL,
	address	 varchar(65) NOT NULL,
	contact	 varchar(65) NOT NULL,
	phone	 varchar(1024) NOT NULL,
	business_id int NOT NULL ,

	PRIMARY KEY(id)
);

CREATE TABLE business(
	id	 int AUTO_INCREMENT,
	area varchar(65) UNIQUE NOT NULL,

	PRIMARY KEY(id)
);

CREATE TABLE stage(
	id	 int AUTO_INCREMENT,
	stage varchar(65) UNIQUE NOT NULL,

	PRIMARY KEY(id)
);

CREATE TABLE task(
	id			 int AUTO_INCREMENT,
	taskname		 varchar(65) NOT NULL,
	description		 varchar(65) NOT NULL,
	begindate		 timestamp NOT NULL,
	enddate		 timestamp default current_timestamp NULL,
	durationhoursestimate int NOT NULL,
	hourcost		 float(8) NOT NULL,
	skill_id		 int ,
	user_creator		 int NOT NULL ,
	taskstage_id		 int NOT NULL ,
	tasktype_id		 int NOT NULL ,
	task_id		 int NOT NULL ,
	project_id		 int NOT NULL ,

	PRIMARY KEY(id)
);

CREATE TABLE tasktype(
	id	 int AUTO_INCREMENT,
	tasktype varchar(65) UNIQUE NOT NULL,

	PRIMARY KEY(id)
);

CREATE TABLE taskstage(
	id	 int AUTO_INCREMENT,
	taskstage varchar(65) UNIQUE NOT NULL,

	PRIMARY KEY(id)
);

CREATE TABLE attachment(
	id		 int AUTO_INCREMENT,
	document	 longblob NOT NULL,
	taskwork_id int ,

	PRIMARY KEY(id)
);

CREATE TABLE skill(
	id	 int AUTO_INCREMENT,
	skill varchar(65) UNIQUE NOT NULL,

	PRIMARY KEY(id)
);

CREATE TABLE tipology(
	id	 int AUTO_INCREMENT,
	tipology varchar(65) UNIQUE NOT NULL,

	PRIMARY KEY(id)
);

CREATE TABLE taskwork(
	id		 int AUTO_INCREMENT,
	creationdate	 timestamp NOT NULL,
	hoursworked	 int NOT NULL,
	remaininghours int NOT NULL,
	comments	 varchar(65) NOT NULL,
	allocation_id	 int NOT NULL ,
	task_id	 int NOT NULL ,

	PRIMARY KEY(id)
);

CREATE TABLE projmanager(
	id			 int AUTO_INCREMENT,
	begindate		 timestamp NOT NULL,
	enddate		 timestamp default current_timestamp NULL,
	project_id		 int NOT NULL ,
	user_manager		 int NOT NULL ,

	PRIMARY KEY(id)
);

CREATE TABLE allocation(
	id		 int AUTO_INCREMENT,
	allocpercentage float(8) NOT NULL,
	begindate	 timestamp NOT NULL,
	enddate	 timestamp default current_timestamp NOT NULL,
	task_id	 int UNIQUE NOT NULL ,
	user_id	 int NOT NULL ,

	PRIMARY KEY(id)
);

CREATE TABLE user_role(
	user_id int,
	role_id int ,

	PRIMARY KEY(user_id,role_id)
);

CREATE TABLE user_skill(
	user_id	 int ,
	skill_id int ,

	PRIMARY KEY(user_id,skill_id)
);

ALTER TABLE project ADD CONSTRAINT project_fk1 FOREIGN KEY (tipology_id) REFERENCES tipology(id);
ALTER TABLE project ADD CONSTRAINT project_fk2 FOREIGN KEY (user_creator) REFERENCES user(id);
ALTER TABLE project ADD CONSTRAINT project_fk3 FOREIGN KEY (stage_id) REFERENCES stage(id);
ALTER TABLE project ADD CONSTRAINT project_fk4 FOREIGN KEY (client_id) REFERENCES client(id);
ALTER TABLE client ADD CONSTRAINT client_fk1 FOREIGN KEY (business_id) REFERENCES business(id);
ALTER TABLE task ADD CONSTRAINT task_fk1 FOREIGN KEY (skill_id) REFERENCES skill(id);
ALTER TABLE task ADD CONSTRAINT task_fk2 FOREIGN KEY (user_creator) REFERENCES user(id);
ALTER TABLE task ADD CONSTRAINT task_fk3 FOREIGN KEY (taskstage_id) REFERENCES taskstage(id);
ALTER TABLE task ADD CONSTRAINT task_fk4 FOREIGN KEY (tasktype_id) REFERENCES tasktype(id);
ALTER TABLE task ADD CONSTRAINT task_fk5 FOREIGN KEY (task_id) REFERENCES task(id);
ALTER TABLE task ADD CONSTRAINT task_fk6 FOREIGN KEY (project_id) REFERENCES project(id);
ALTER TABLE attachment ADD CONSTRAINT attachment_fk1 FOREIGN KEY (taskwork_id) REFERENCES taskwork(id);
ALTER TABLE taskwork ADD CONSTRAINT taskwork_fk1 FOREIGN KEY (allocation_id) REFERENCES allocation(id);
ALTER TABLE taskwork ADD CONSTRAINT taskwork_fk2 FOREIGN KEY (task_id) REFERENCES task(id);
ALTER TABLE projmanager ADD CONSTRAINT projmanager_fk1 FOREIGN KEY (project_id) REFERENCES project(id);
ALTER TABLE projmanager ADD CONSTRAINT projmanager_fk2 FOREIGN KEY (user_manager) REFERENCES user(id);
ALTER TABLE allocation ADD CONSTRAINT allocation_fk1 FOREIGN KEY (task_id) REFERENCES task(id);
ALTER TABLE allocation ADD CONSTRAINT allocation_fk2 FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE user_role ADD CONSTRAINT user_role_fk1 FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE user_role ADD CONSTRAINT user_role_fk2 FOREIGN KEY (role_id) REFERENCES role(id);
ALTER TABLE user_skill ADD CONSTRAINT user_skill_fk1 FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE user_skill ADD CONSTRAINT user_skill_fk2 FOREIGN KEY (skill_id) REFERENCES skill(id);




/*==============================================================*/
/* Add Data: 													*/
/*==============================================================*/

/* users */
INSERT INTO proj_management.user (email, fullname, passw, photo, registrydate, active, salt) VALUES ('catarinag@gmail.com', 'cat', '1234', '', '', '', '');
