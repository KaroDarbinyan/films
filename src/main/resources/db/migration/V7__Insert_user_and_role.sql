insert into "user" (username, first_name, last_name, password_hash, status)
values ('admin', 'admin', 'admin', '$2a$10$pAT.MWWl.Dmtc0hkz1VlJ.f31u/dYA3Dckdf62cEvJWVl2XN1CUlS', 'ACTIVE');

insert into role (name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into user_role(user_id, role_id)
values (1, 1);
