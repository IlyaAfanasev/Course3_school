create table car(
id serial primary key,
brand text,
model text,
cost numeric
);

create table driver(
id Integer primary key,
name text,
age smallint,
have_license boolean default false,
car_id Integer references car(id)
);

