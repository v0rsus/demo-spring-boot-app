drop sequence "HIBERNATE_SEQUENCE" if exists;
CREATE SEQUENCE "HIBERNATE_SEQUENCE"
MINVALUE 1
MAXVALUE 999999999
INCREMENT BY 1
START WITH 1
NOCACHE
NOCYCLE;

drop table drones if exists;
create table drones (
id bigint auto_increment,
serial_number varchar(100) not null,
model varchar(30) not null,
weight int not null,
battery_capacity int not null,
state varchar(30) not null,
constraint dronesPK_idx primary key (id)
);

drop index drone_serial_number_idx if exists;
create index drone_serial_number_idx on drones(serial_number);

drop table medications if exists;
create table medications (
id bigint auto_increment,
code varchar(100) not null,
name varchar(200) not null,
weight int not null,
image_url varchar(1000),
constraint medicationsPK_idx primary key (id)
);

drop index med_code_idx if exists;
create index med_code_idx on medications(code);

drop table drones_to_medications if exists;
create table drones_to_medications(
drone_id int,
medication_id int,
assigned timestamp,
constraint dtmPK_idx primary key (drone_id, medication_id),
foreign key (drone_id) references drones(id),
foreign key (medication_id) references medications(id)
);