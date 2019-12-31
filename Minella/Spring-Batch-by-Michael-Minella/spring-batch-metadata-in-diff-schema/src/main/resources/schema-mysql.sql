create table Customer (
       id bigint not null auto_increment,
        birthdate varchar(255),
        firstName varchar(255),
        lastName varchar(255),
        primary key (id)
    ) engine=InnoDB;