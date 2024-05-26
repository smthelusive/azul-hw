insert into role_registry(role_id, role) values(1, 'admin');
insert into role_registry(role_id, role) values(2, 'user');

insert into user_registry(user_id, username, password)
values(1, 'alice', '$2a$12$f0SGOXB5cXREEk3XPSP0RO7MSC00M5BPVYW.cZYnCm3pzvDl6TWcm');
insert into user_registry(user_id, username, password)
values(2, 'bob', '$2a$12$qFy/mrGqrGpPatVt8xaYReMVhyRNz.Bibz7kXhXMVahxuQYlsF.aW');
insert into user_registry(user_id, username, password)
values(3, 'mary', '$2a$12$UvKr9DaVIIK1mklWYE38CO0dVugWc4v/W2QKB.SSaNy3Dw9IWSm66');

insert into user_role_registry(user_id, role_id) values(1, 1);
insert into user_role_registry(user_id, role_id) values(1, 2);
insert into user_role_registry(user_id, role_id) values(2, 2);
insert into user_role_registry(user_id, role_id) values(3, 2);