insert into role_registry(role_id, role) values(1, 'admin');
insert into role_registry(role_id, role) values(2, 'user');

insert into user_registry(user_id, username, password)
values(1, 'alice', '$2a$12$YCEMnkVkKbn0JHxQHMQ0EeU6mdXq.lXDaOTx1f2d2RxQmyMu5bYI6');
insert into user_registry(user_id, username, password)
values(2, 'bob', '$2a$12$YCEMnkVkKbn0JHxQHMQ0EeU6mdXq.lXDaOTx1f2d2RxQmyMu5bYI6');
insert into user_registry(user_id, username, password)
values(3, 'mary', '$2a$12$YCEMnkVkKbn0JHxQHMQ0EeU6mdXq.lXDaOTx1f2d2RxQmyMu5bYI6');

insert into user_role_registry(user_id, role_id) values(1, 1);
insert into user_role_registry(user_id, role_id) values(1, 2);
insert into user_role_registry(user_id, role_id) values(2, 2);
insert into user_role_registry(user_id, role_id) values(3, 2);