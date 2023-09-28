create table car(
                    id serial primary key,
                    make varchar(100),
                    model varchar(100),
                    price numeric(20,2)
);

create table owner(
                      id serial primary key,
                      name varchar not null,
                      age serial,
                      has_license boolean default false,
                      car_id serial references car(id)
)