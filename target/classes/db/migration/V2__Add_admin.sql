insert into usr (id, username, password, active)
values (1, 'main_admin', 'scnjhsz', true);

insert into user_role (user_id, roles)
values (1, 'ADMIN'), (1, 'MAIN_ADMIN');