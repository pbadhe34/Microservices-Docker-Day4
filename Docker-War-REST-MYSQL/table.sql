create table appusers(id bigint not null, username varchar(255),  salary bigint , primary key (id)) engine=MyISAM;  


create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 1 );

Insert into appusers values(22,'Mohan',10000);

Insert into appusers values(23,'Baba',9000);

Insert into appusers values(24,'Rajau',8000);
