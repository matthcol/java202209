create table pilot (
	id serial constraint pk_pilot primary key,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	birthdate date,
	agility decimal
);