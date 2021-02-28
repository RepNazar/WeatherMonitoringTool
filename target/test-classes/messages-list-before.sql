delete from monitoring_history;

insert into monitoring_history(id, date, temperature, wind_speed, wind_angle, pressure, user_id)
values (1, '01', '011', '012', '013', '014', 1),
       (2, '02', '021', '022', '023', '024', 1),
       (3, '03', '031', '032', '033', '034', 1),
       (4, '11', '141', '142', '143', '144', 2);

alter sequence hibernate_sequence restart with 10;
