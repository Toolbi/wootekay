CREATE TABLE IF NOT EXISTS users
(
	id int UNIQUE NOT NULL ,
	first_name varchar(20),
	last_name varchar(50),
	mobile varchar(20) NOT NULL,
	email varchar(50) NOT NULL,
	gender int(2) DEFAULT 0,
	dob varchar(50),
	allowed_mail int(2) DEFAULT 0,
	about varchar(1000) ,
	allowed_show_number int(2) DEFAULT 0,
	allowed_sms int(2) DEFAULT 0,
	allowed_food int(2) DEFAULT 0,
	allowed_pet int(2) DEFAULT 0,
	allowed_smoke int(2) DEFAULT 0,
	allowed_chat int(2) DEFAULT 0,
	allowed_music int(2) DEFAULT 0,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS vehicles
(
	id int UNIQUE NOT NULL ,
	type_id int,
	v_number varchar(50),
	logo varchar(250),
	comfort int NOT NULL,
	user_id int,
	type_name varchar(50),
	image varchar(255),
	category_id int ,
	category_name varchar(50),
	is_active int(2) DEFAULT 0,
	PRIMARY KEY (id)
);