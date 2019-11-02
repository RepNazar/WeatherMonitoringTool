delete from user_role;
delete from usr;

insert into usr(id, username, password, active) values
(1, 'main_admin_test', 'test', true),
(2, 'mike', '1', true);

create extension if not exists pgcrypto;
update usr set password = crypt(password, gen_salt('bf', 8));

insert into user_role(user_id, roles) values
(1, 'MAIN_ADMIN'), (1, 'ADMIN'),
(2, 'ADMIN');
