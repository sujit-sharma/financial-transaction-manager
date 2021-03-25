select * from file_info;
select * from file_info_file_urls;
select * from information_schema.tables where table_schema='public';

create table testing(
                        name varchar(255),
                        email varchar(255)
);

insert into testing (name, email) values('sujit', 'sujit@gmail.com');

select * from user_entity;
