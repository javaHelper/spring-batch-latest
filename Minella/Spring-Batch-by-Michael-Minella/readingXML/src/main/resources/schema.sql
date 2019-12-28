create table customer (
       id bigint not null auto_increment,
        birthdate datetime,
        first_name varchar(255),
        last_name varchar(255),
        primary key (id)
    ) engine=InnoDB