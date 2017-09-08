alter table user add column email varchar(30) after id;
alter table user add column phone varchar(15) after email;
alter table user add column password varchar(18) after phone;
